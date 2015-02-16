import os
import subprocess
import errno
import re
import sys
import shutil
import urllib2
import glob
from distutils import dir_util
from distutils import file_util
import zipfile
import fnmatch
import inspect
import time
import string
import traceback

# evil globals
_ = None
debug = False
BILD_LOG_DIR = "."      # where to generate bild.log
logfile = None
bild_completed = set()  # which *targets* have been built.
BILD = os.path.expanduser("~/.bild")
JARCACHE = os.path.join(BILD, "jars")
ERRORS=0  # increment this when error found so we can set exit value
# CLASSPATH = JARCACHE + "/*" + os.pathsep + os.environ['CLASSPATH']

def script_name(): # return name of script that is invoking bilder lib minus the ".py"
    frames = inspect.getouterframes(inspect.currentframe())
    scriptframe = frames[-1]
    scriptname = scriptframe[1]
    scriptname = os.path.basename(scriptname)
    scriptname = scriptname[:-3]
    return scriptname


def log(msg):
    global logfile
    if logfile==None:
        scriptname = script_name()
        logfile = open(BILD_LOG_DIR+"/"+scriptname+".log", "w") # assume this closes upon Python exit
    if msg is None:
        return
    msg = msg.strip()
    if len(msg)==0:
        return
    caller = inspect.currentframe().f_back.f_code.co_name
    line = inspect.currentframe().f_back.f_lineno
    filename = os.path.basename(inspect.currentframe().f_back.f_code.co_filename)
    prefix = time.strftime('%x %X')

    if inspect.currentframe().f_back is not None:
        filename2 = inspect.currentframe().f_back.f_back.f_code.co_filename
        line2 = inspect.currentframe().f_back.f_back.f_lineno

    if caller=="require":
        logfile.write("[%s] %s\n" % (prefix,msg))
    elif caller=="exec_and_log":
        caller = inspect.currentframe().f_back.f_back.f_code.co_name
        line = inspect.currentframe().f_back.f_back.f_lineno
        filename2 = inspect.currentframe().f_back.f_back.f_back.f_code.co_filename
        line2 = inspect.currentframe().f_back.f_back.f_back.f_lineno
    fullmsg = "[%s %s %s:%d %s:%d] %s\n" % (
    prefix, caller, filename2, line2, filename, line, msg)
    logfile.write(fullmsg)
    if debug:
        print fullmsg,


def findjdks_win():
    return {}


def findjdks_linux():
    """
    CentOS: /usr/lib/jvm/java-1.7.0-openjdk-1.7.0.55.x86_64/jre/bin/java
                    /usr/java/jdk1.7.0_51
    ubuntu: /usr/lib/jvm/java-6-openjdk/ for OpenJDK
                    /usr/lib/jvm/* for Oracle JDK
    """
    versions = {}
    for jdk in glob.glob("/usr/lib/jvm/*") + glob.glob("/usr/java/*"):
        name = os.path.basename(jdk)
        if name.startswith("java-1.6") or name.startswith("jdk1.6"):
            versions["1.6"] = jdk
        if name.startswith("java-1.7") or name.startswith("jdk1.7"):
            versions["1.7"] = jdk
        if name.startswith("java-1.8") or name.startswith("jdk1.8"):
            versions["1.8"] = jdk
    return versions


def findjdks_mac():
    """find Java installations on a mac"""
    versions = {}
    dirs = []
    for jdk in glob.glob("/Library/Java/JavaVirtualMachines/*"):
        dirs.append(jdk)
    for jdk in glob.glob("/System/Library/Java/JavaVirtualMachines/*"):
        dirs.append(jdk)
    for jdk in dirs:
        name = os.path.basename(jdk)
        if name.startswith("1.6."):
            versions["1.6"] = jdk + "/Contents/Home"
        elif name.startswith("jdk1.7."):
            versions["1.7"] = jdk + "/Contents/Home"
        elif name.startswith("jdk1.8."):
            versions["1.8"] = jdk + "/Contents/Home"
    return versions


def findjdks():
    if sys.platform == 'win32':
        return findjdks_win()
    if sys.platform == 'darwin':
        return findjdks_mac()
    if sys.platform == 'linux2':
        return findjdks_linux()


jdk = findjdks()
log("platform="+sys.platform)
log("jdk="+str(jdk))

todo = """
def chkjava(version):
    #Version in {1.6,1.7,1.8}
    javahome = 'JAVA' + version
    if os.environ.get(javahome):
        jdk[version] = os.environ[javahome]
        print "Setting java to "+jdk[version]+""
    else:
        jdk = findjdks()
        if version in jdk:
            print javahome+" not set, using "+jdk[version]
        else:
            p = subprocess.Popen(["java", "-version"], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
            out,err = p.communicate()
            # java version "1.7.0_65"
            vline = err.split("\n")[0]
            vquote = vline.split(" ")[2]
            v = vquote.replace('"',"")
            print javahome+" not set, using system default: "+v
"""

def get_java_version():
    p = subprocess.Popen(["java", "-version"], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    out,err = p.communicate()
    # java version "1.7.0_65"
    vline = err.split("\n")[0]
    vquote = vline.split(" ")[2]
    v = vquote.replace('"',"")
    return v

def modtime(fname):
    try:
        return os.path.getmtime(fname)
    except:
        return 0  # mod date of epoch means ancient  sys.float_info.max  # meaning mod date in future if file not there


def uniformpath(dir):
    dir = os.path.expanduser(dir)  # ~parrt -> /Users/parrt on unix
    dir = os.path.abspath(dir)  # expand relative dirs
    return dir


def mkdir(path):
    """
    From: http://stackoverflow.com/questions/600268/mkdir-p-functionality-in-python
    """
    try:
        os.makedirs(path)
    except OSError as exc:  # Python >2.5
        if exc.errno == errno.EEXIST and os.path.isdir(path):
            pass
        else:
            raise


def rmdir(dir):
    shutil.rmtree(dir, ignore_errors=True)


def rmfile(filename):
    """remove this file if it exists; do not give an error if it is not exist"""
    if os.path.isfile(filename):
        os.remove(filename)


def files(pathspec):
    """
    Get all files matching pathspec (nonrecursive)
    """
    return [f for f in glob.glob(pathspec)]


def allfiles(dir, pattern="*"):
    """
    Return list<string> all files in subtree dir, optionally matching
    a pattern spec like "*.java"

    If dir is list<string> or list<list<string>>, etc... assume it
    is a list of directories and collect the result of recursively executing
    this function on each directory
    """
    if isinstance(dir, list):
        combined = []
        for d in dir:
            combined += allfiles(d, pattern)
        return combined
    dir = uniformpath(dir)
    if not os.path.isdir(dir):  # must be file
        return [dir]
    matching_files = []
    for root, subFolders, files in os.walk(dir):
        matching = fnmatch.filter(files, pattern)
        matching_files.extend(os.path.join(root, f) for f in matching)
    return matching_files

def skipfiles(tomatch, skips):
    matched = tomatch
    for skip in skips:
        matched = []
        for file in tomatch:
            if file.find(skip)<0:
               matched.append(file)
        tomatch = matched
    return matched

def copytree(src, trg, ignore=None):
    if os.path.exists(trg) and not os.path.isdir(trg):
        os.remove(trg)  # can't copy onto a file
    mkdir(trg)
    dir_util.copy_tree(src, trg, preserve_mode=True)


def copyfile(src, trg):
    if not os.path.exists(trg):
        trg_dirname = os.path.dirname(trg)
        if len(trg_dirname.strip()) > 0:
            mkdir(trg_dirname)
    file_util.copy_file(src, trg, preserve_mode=True)


def replsuffix(files, suffix):
    """
    Return list<string> all files with their .suffix replaced
    """
    outfiles = []
    if suffix is None: return
    if isinstance(files, basestring):
        files = [files]
    for f in files:
        fname, ext = os.path.splitext(f)
        newfname = fname + suffix
        outfiles.append(newfname)
    return outfiles


def javac_targets(srcdir, trgdir, skip=[]):
    """
    Return a map<string,string> of files javac would create given a subdir of java
    files and a target dir. E.g.,
    javac_targets("/Users/parrt/mantra/code/compiler/src/java", "out")
    generates
        {".../src/java/mantra/Tool.java":"out/mantra/Tool.class", ...}
    """
    srcdir = uniformpath(srcdir)
    trgdir = uniformpath(trgdir)
    mapping = {}
    javafiles = allfiles(srcdir, "*.java")
    javafiles = skipfiles(javafiles, skip)
    classfiles = replsuffix(javafiles, ".class")
    if not os.path.isdir(srcdir):  # must be a Java file
        srcdir = os.path.dirname(srcdir)
    classfiles = [f.replace(srcdir, trgdir) for f in classfiles]  # shift to trg dir
    for i in range(len(javafiles)):
        mapping[javafiles[i]] = classfiles[i]
    return mapping


def grep(file, regex):
    matches = []
    with open(file) as f:
        contents = f.read()
        m = re.search(regex, contents)
        if m:
            matches.append(m.group())
    return matches


def antlr3_targets(srcdir, trgdir, package=None):
    """
    Return a map<string,string> of files antlr3 would create given a subdir of grammars
    files and a target dir. E.g.,
    antlr3_targets("tool/src/org/antlr/v4/codegen", "gen")
    gives:
    {'/Volumes/SSD2/Users/parrt/antlr/code/antlr4/tool/src/org/antlr/v4/codegen/SourceGenTriggers.g':
     '/Volumes/SSD2/Users/parrt/antlr/code/antlr4/gen/SourceGenTriggers.java'}
    """
    srcdir = uniformpath(srcdir)
    if package is not None:
        package = re.sub('[.]', '/', package)
        trgdir = uniformpath(os.path.join(trgdir, package))
    else:
        trgdir = uniformpath(trgdir)
    mapping = {}
    gfiles = allfiles(srcdir, "*.g")
    for f in gfiles:
        fdir, fsuffix = os.path.splitext(f)
        gname = os.path.basename(fdir)
        fullgname = os.path.join(trgdir, gname)
        lexer = grep(f, r"lexer\s+grammar")
        parser = grep(f, r"parser\s+grammar")
        tree = grep(f, r"tree\s+grammar")
        if len(lexer) > 0 or len(parser) > 0 or len(tree) > 0:
            # print "a lexer or parser or tree parser"
            mapping[f] = fullgname + ".java"
        else:
            # must be combined grammar
            # print "a combined"
            mapping[f] = [fullgname + "Parser.java", fullgname + "Lexer.java"]
    return mapping


def antlr4_targets(srcdir, trgdir, package=None):
    """
    Return a map<string,string> of files antlr4 would create given a subdir of grammars
    files and a target dir. E.g.,
    antlr4_targets("tests", "gen")
    gives:
    {'/Volumes/SSD2/Users/parrt/github/bild/tests/sample1/src/grammars/org/foo/T.g4':
      ['/Volumes/SSD2/Users/parrt/github/bild/gen/TParser.java',
       '/Volumes/SSD2/Users/parrt/github/bild/gen/TLexer.java']
    }
    """
    srcdir = uniformpath(srcdir)
    if package is not None:
        package = re.sub('[.]', '/', package)
        trgdir = uniformpath(os.path.join(trgdir, package))
    else:
        trgdir = uniformpath(trgdir)
    mapping = {}
    gfiles = allfiles(srcdir, "*.g4")
    for f in gfiles:
        fdir, fsuffix = os.path.splitext(f)
        gname = os.path.basename(fdir)
        fullgname = os.path.join(trgdir, gname)
        lexer = grep(f, r"lexer\s+grammar")
        parser = grep(f, r"parser\s+grammar")
        if len(lexer) > 0 or len(parser) > 0:
            # print "a lexer or parser"
            mapping[f] = fullgname + ".java"
        else:
            # must be combined grammar
            # print "a combined"
            mapping[f] = [fullgname + "Parser.java", fullgname + "Lexer.java"]
    return mapping


def newer(a, b):
    """
    Return true if a newer than b
    """
    return modtime(a) > modtime(b)  # larger is earlier


def older(a, b):
    """
    Return true if a older or same as b
    """
    return not newer(a, b)

def newest(files):
    """
    From list<string> filenames, return newest file (according to modtime)
    """
    newest_file = None
    for f in files:
        # print f, modtime(f), modtime(newest_file)
        if newer(f,newest_file):
            newest_file = f
    return newest_file

def stale(map):  # accept map<string,string> or map<string,list<string>>
    """
    Return map<string,string-or-list> with files to build as they are out of date
    """
    out = {}
    for src in map:
        trg = map[src]
        fstale = False
        if isinstance(trg, list):
            for t in trg:
                # print src,"->",t
                # print modtime(src), modtime(t)
                if isstale(src, t):
                    fstale = True
                    break
        else:
            # print src,"->",trg
            # print modtime(src), modtime(trg)
            if isstale(src, trg):
                # print "target newer so no build"
                fstale = True
        if fstale:
            out[src] = trg
    return out


def isstale(src, trg):
    return not os.path.exists(trg) or \
           newer(src,trg)


def require(target):
    global ERRORS
    print "require", target.__name__
    log("require "+target.__name__)
    if id(target) in bild_completed:
        return
    bild_completed.add(id(target))
    target()
    caller = inspect.currentframe().f_back.f_code.co_name
    if ERRORS==0:
        print "build", caller
        log("build "+caller)
    else:
        print "skipping", caller
        log("error building "+caller)
        raise Exception()


def antlr3(srcdir, trgdir=".", package=None, version="3.5.1", args=[]):
    srcdir = uniformpath(srcdir)
    trgdir = uniformpath(trgdir)
    map = antlr3_targets(srcdir, trgdir, package)
    tobuild = stale(map).keys()
    if len(tobuild) == 0:
        return
    jarname = "antlr-" + version + "-complete.jar"
    # if jarname not in filelist(JARCACHE):
    download("http://www.antlr3.org/download/" + jarname, JARCACHE)
    if package is not None:
        packageAsDir = re.sub('[.]', '/', package)
        cmd = ["java", "-cp", os.path.join(JARCACHE, jarname),
               "org.antlr.Tool",
               "-o", os.path.join(trgdir, packageAsDir)] + args + tobuild
    else:
        cmd = ["java", "org.antlr.Tool", "-o", trgdir] + args + tobuild
    exec_and_log(cmd)


def antlr4(srcdir, trgdir=".", package=None, version="4.3", args=[]):
    srcdir = uniformpath(srcdir)
    trgdir = uniformpath(trgdir)
    map = antlr4_targets(srcdir, trgdir, package)
    tobuild = stale(map).keys()
    if len(tobuild) == 0:
        return
    jarname = "antlr-" + version + "-complete.jar"
    # if jarname not in filelist(JARCACHE):
    download("http://www.antlr.org/download/" + jarname, JARCACHE)
    if package is not None:
        packageAsDir = re.sub('[.]', '/', package)
        cmd = ["java", "-cp", os.path.join(JARCACHE, jarname),
               "org.antlr.v4.Tool",
               "-o", os.path.join(trgdir, packageAsDir),
               "-package", package] + args + tobuild
    else:
        cmd = ["java", "org.antlr.v4.Tool", "-o", trgdir] + args + tobuild
    exec_and_log(cmd)


def java(classname, cp=None, version=None, vmargs=[], progargs=[]):
    global ERRORS
    if cp is None:
        cp = JARCACHE + "/*"
    j = "java"
    if version is not None:
        j = os.path.join(jdk[version], "bin/java")
    cmd = [j, "-cp", cp] + vmargs + [classname] + progargs
    exec_and_log(cmd)


def javac(srcdir, trgdir=".", cp=None, javacVersion=None, version=None, args=[], skip=[]):
    global ERRORS
    srcdir = uniformpath(srcdir)
    trgdir = uniformpath(trgdir)
    mkdir(trgdir)
    map = javac_targets(srcdir, trgdir, skip)
    tobuild = stale(map).keys()
    # print "build",stale(map)
    if len(tobuild) == 0:
        return
    if cp is None:
        cp = trgdir + os.pathsep + JARCACHE + "/*"
    javac = "javac" # use default system javac if no javacVersion
    if javacVersion is not None:
        javac = os.path.join(jdk[javacVersion], "bin/javac")
    log(get_java_version())
    cmd = [javac, "-d", trgdir, "-cp", cp]
    if version:
        cmd += ["-source", version]
        cmd += ["-target", version]
    if args:
        cmd += args
    cmd += tobuild
    exec_and_log(cmd)


def jar(jarfile, inputfiles=".", srcdir=".", manifest=None):
    trgdir = os.path.dirname(jarfile)
    mkdir(trgdir)
    if isinstance(inputfiles, basestring):
        inputfiles = [inputfiles]
    contents_with_C = []
    for f in inputfiles:
        contents_with_C.append("-C")
        contents_with_C.append(srcdir)
        contents_with_C.append(f)
    # write manifest
    metadir = os.path.join(srcdir, "META-INF")
    rmdir(metadir)
    mkdir(metadir)
    with open(os.path.join(metadir, "MANIFEST.MF"), "w") as mf:
        mf.write(manifest)
    manifest = os.path.join(metadir, "MANIFEST.MF")
    cmd = ["jar", "cmf", manifest, jarfile] + contents_with_C
    subprocess.call(cmd)


def unjar(jarfile, trgdir="."):
    jar = zipfile.ZipFile(jarfile)
    jar.extractall(path=trgdir)
    jar.close()


def make_osgi_ready(jarfile, osgijarfile):
    """
    Create a new jar file from old, using its MANIFEST file and add extra
    stuff needed by OSGi.
    """
    download("https://github.com/bndtools/bnd/releases/download/2.4.0.REL/biz.aQute.bnd-2.4.0.jar", JARCACHE)
    cmd = ["java", "-jar", JARCACHE+"/biz.aQute.bnd-2.4.0.jar", "wrap", "--output", osgijarfile, jarfile]
    exec_and_log(cmd)


def javadoc(srcdir, trgdir, packages=None, classpath=None, title=None, exclude=None):
    if isinstance(srcdir, list):
        srcdir = ":".join(srcdir)
    if isinstance(packages, list):
        packages = ":".join(packages)
    if isinstance(exclude, list):
        exclude = ":".join(exclude)
    cmd = ["javadoc", "-quiet", "-d", trgdir, "-sourcepath", srcdir]
    if packages is not None:
        cmd += ["-subpackages", packages]
    if exclude is not None:
        cmd += ["-exclude", exclude]
    exec_and_log(cmd)


def load_junitjars():
    junit_version = '4.11'
    junit_jar = 'junit-' + junit_version + '.jar'
    hamcrest_version = '1.3'
    hamcrest_jar = 'hamcrest-core-' + hamcrest_version + '.jar'
    download("http://search.maven.org/remotecontent?filepath=junit/junit/" + junit_version + "/" + junit_jar,
             JARCACHE)
    download(
        "http://search.maven.org/remotecontent?filepath=org/hamcrest/hamcrest-core/" + hamcrest_version + "/" + hamcrest_jar,
        JARCACHE)
    return JARCACHE + "/" + junit_jar, JARCACHE + "/" + hamcrest_jar


def junit(srcdir, cp=None, verbose=False, args=[]):
    global ERRORS
    hamcrest_jar, junit_jar = load_junitjars()
    download("https://github.com/parrt/bild/raw/master/lib/bild-junit.jar", JARCACHE)
    srcdir = uniformpath(srcdir)
    testfiles = allfiles(srcdir, "*.class")
    testfiles = [f[len(srcdir) + 1:] for f in testfiles]
    testclasses = replsuffix(testfiles, '')
    testclasses = [c for c in testclasses if os.path.basename(c).startswith("Test") and '$' not in os.path.basename(c)]
    testclasses = [c.replace('/', '.') for c in testclasses]
    cp_ = srcdir + os.pathsep + junit_jar + os.pathsep + hamcrest_jar + os.pathsep + JARCACHE + "/bild-junit.jar"
    if cp is not None:
        cp_ = cp + os.pathsep + cp_
    processes = []
    # launch all tests in srcdir in parallel
    for c in testclasses:
        if verbose:
            cmd = ['java'] + args + ['-cp', cp_, 'org.bild.JUnitLauncher', '-verbose', c]
        else:
            cmd = ['java'] + args + ['-cp', cp_, 'org.bild.JUnitLauncher', c]
        log(' '.join(cmd))
        p = subprocess.Popen(cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        processes.append(p)
    # busy wait with sleep for any results
    while len(processes) > 0:
        for p in processes:
            r = p.poll()
            if r is not None:  # p is done
                processes.remove(p)
                stdout, stderr = p.communicate()  # hush output
                log(stdout)
                log(stderr)
                print stdout,
                summary = stdout.split('\n')[0]
                if "0 failures" not in summary:
                    ERRORS += 1
        time.sleep(0.200)


def junit_runner(testclasses, cp=None, verbose=False, args=[]):
    global ERRORS
    if isinstance(testclasses, basestring):
            testclasses = [testclasses]
    hamcrest_jar, junit_jar = load_junitjars()
    download("https://github.com/parrt/bild/raw/master/lib/bild-junit.jar", JARCACHE)
    testclasses = [c.replace('/', '.') for c in testclasses]
    cp_ = os.pathsep + junit_jar + os.pathsep + hamcrest_jar + os.pathsep + JARCACHE +\
          "/bild-junit.jar"
    if cp is not None:
        cp_ = cp + os.pathsep + cp_
    processes = []
    # launch all tests in srcdir in parallel
    log("############# UNIT TESTS ######################")
    for c in testclasses:
        cmd = ['java'] + args + ['-cp', cp_, 'org.bild.JUnitLauncher', c]
        if verbose:
            cmd = ['java'] + args + ['-cp', cp_, 'org.bild.JUnitLauncher', '-verbose', c]
        log(' '.join(cmd))
        p = subprocess.Popen(cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        processes.append(p)
    # busy wait with sleep for any results
    while len(processes) > 0:
        for p in processes:
            r = p.poll()
            if r is not None:  # p is done
                processes.remove(p)
                stdout, stderr = p.communicate()  # log output
                log(stdout)
                log(stderr)
                print stdout,
                summary = stdout.split('\n')[0]
                if "0 failures" not in summary:
                    ERRORS += 1
        time.sleep(0.200)
    print "Tests complete"

def dot(src, trgdir=".", format="pdf"):
    if not src.endswith(".dot"):
        return
    nosuffix = src[0:-4]
    base = os.path.basename(nosuffix)
    cmd = ["dot", "-T" + format, "-o" + os.path.join(trgdir, base + "." + format), src]
    exec_and_log(cmd)


def download(url, trgdir=".", force=False):
    file_name = url.split('/')[-1]
    mkdir(trgdir)
    target_name = os.path.join(trgdir, file_name)
    if os.path.exists(target_name) and not force:
        return
    log("download "+url)
    try:
        response = urllib2.urlopen(url)
    except urllib2.HTTPError, e:
        msg = "can't download %s: %s\n" % (url, str(e))
        sys.stderr.write(msg)
        log(msg)
    else:
        output = open(target_name, 'wb')
        output.write(response.read())
        output.close()


def wget(url, level=None, trgdir=None, proxy=None, verbose=False):
    """
    $ http_proxy=http://localhost:8080
    $ wget -P foo --proxy=on -r --level 1 http://www.cs.usfca.edu/index.html
    wget("http://www.cs.usfca.edu/index.html", trgdir="foo")
    wget("http://www.cs.usfca.edu/index.html", trgdir="foo", proxy="http://localhost:8080")
    """
    global ERRORS
    cmd = ["wget", "-r", "--wait=0", "--keep-session-cookies"]
    if not verbose:
        cmd += ["-q"]
    if trgdir is not None:
        mkdir(trgdir)
        cmd += ["-P", trgdir]
    env = os.environ.copy()
    if proxy is None:
        cmd += ["--proxy=off"]
    else:
        env["http_proxy"] = proxy
        cmd += ["--proxy=on"]
    if level is not None:
        cmd += ["--level", str(level)]
    cmd += [url]
    returncode, out, err = exec_and_log(cmd)
    if err is not None and len(err)>0:
        print "wget errors:"
        print err
    return returncode


def diff(file1, file2, recursive=False):
    global ERRORS
    cmd = ["diff"]
    if recursive:
        cmd += ["-r"]
    cmd += [file1, file2]
    log(' '.join(cmd))
    output = subprocess.check_output(cmd, stderr=subprocess.STDOUT)
    return output


def scp(src, user, machine, trg):
    cmd = ["scp", src, "%s@%s:%s" % (user, machine, trg)]
    log(cmd)
    subprocess.check_call(cmd)


def zip(zipfilename, srcdirs):  # , recursive=True):
    """
    Last element of srcdir is considered root of zip'd content. E.g.,
    srcdir="doc/Java" gives zip file with Java as the root element.

    srcdir might expand to /Volumes/SSD2/Users/parrt/antlr/code/antlr4/doc/Java
    """
    mkdir(os.path.dirname(zipfilename))
    if isinstance(srcdirs, basestring):
        srcdirs = [srcdirs]
    log("zip "+zipfilename+" "+' '.join(srcdirs))
    with zipfile.ZipFile(zipfilename, mode="w", compression=zipfile.ZIP_DEFLATED) as z:
        for srcdir in srcdirs:
            srcdir = uniformpath(srcdir)
            rootdir = os.path.dirname(srcdir)  # "...doc/Java" gives doc
            rootnameindex = len(rootdir) + 1  # "...doc/Java" gives start of "Java"
            for f in allfiles(srcdir):
                z.write(f, f[rootnameindex:])


def print_and_log(msg):
    print msg
    log(msg)


def exec_and_log(cmd):
    global ERRORS
    log(' '.join(cmd))
    p = subprocess.Popen(cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    out, err = p.communicate()
    log(out)
    log(err)
    if p.returncode != 0:
        ERRORS += 1
    return p.returncode, out, err


def python(filename, workingdir=".", args=[]):
    savedcwd = os.getcwd()
    os.chdir(workingdir)
    try:
        if not isinstance(args, list):
            args = [args]
        cmd = [sys.executable, filename] + args
        exec_and_log(cmd)
    finally:
        os.chdir(savedcwd)


def mvn_install(binjar, srcjar, docjar, groupid, artifactid, version):
    cmd = ["mvn", "install:install-file" ]
    cmd += ["-Dfile=" + uniformpath(binjar) ]
    cmd += ["-Dsources=" + uniformpath(srcjar) ]
    cmd += ["-Djavadoc=" + uniformpath(docjar) ]
    cmd += ["-DgroupId=" + groupid ]
    cmd += ["-DartifactId=" + artifactid ]
    cmd += ["-Dversion=" + version ]
    cmd += ["-Dpackaging=jar"]
    cmd += ["-DgeneratePom=true"]
    cmd += ["-DcreateChecksum=true"]
    exec_and_log(cmd)

""" Do something like this
mvn deploy:deploy-file
    -Durl=https://oss.sonatype.org/content/repositories/snapshots
    -DrepositoryId=ossrh
    -Dfile=/Users/parrt/antlr/code/antlr4/dist/antlr4-4.5-SNAPSHOT.jar
    -Dsources=/Users/parrt/antlr/code/antlr4/dist/antlr4-4.5-SNAPSHOT-javadoc.jar
    -Djavadoc=/Users/parrt/antlr/code/antlr4/dist/antlr4-4.5-SNAPSHOT-sources.jar
    -DgroupId=org.antlr
    -DartifactId=antlr4-runtime
    -Dversion=4.5-SNAPSHOT
    -DpomFile=/Users/parrt/antlr/code/antlr4/runtime/Java/pom.xml
    -DuniqueVersion=true
    -Dpackaging=jar
    -DgeneratePom=false
"""
def mvn(command,
        binjar, srcjar, docjar, pomfile,
        groupid=None, artifactid=None, version=None, repositoryid=None,
        packaging="jar",
        url="https://oss.sonatype.org/content/repositories/snapshots"):
    cmd =  ["mvn", command]
    cmd += ["-Durl="+url]
    if repositoryid:
        cmd += ["-DrepositoryId="+repositoryid]
    cmd += ["-Dfile="+binjar]
    if srcjar:
        cmd += ["-Dsources="+srcjar]
    if docjar:
        cmd += ["-Djavadoc="+docjar]
    if groupid:
        cmd += ["-DgroupId="+groupid]
    if artifactid:
        cmd += ["-DartifactId="+artifactid]
    if version:
        cmd += ["-Dversion="+version]
    cmd += ["-Dpackaging=jar"]
    if pomfile:
        cmd += ["-DpomFile="+pomfile]
    cmd += ["-DuniqueVersion=true"]
    cmd += ["-DgeneratePom=false"]
    cmd += ["-Dpackaging="+packaging]
    exec_and_log(cmd)


def processargs(globals):
    global ERRORS, logfile, debug
    target_index = 1
    if len(sys.argv)>1 and sys.argv[1]=="-debug":
        debug = True
        target_index = 2
    if target_index >= len(sys.argv):
        target = globals["all"]
    else:
        target = globals[sys.argv[target_index]]
    if target is not None:
        print "target", target.__name__
        try:
            target()
        except Exception as e:
            traceback.print_exc(file=sys.stderr)
            log(str(e))
            traceback.print_exc(file=logfile)
        if ERRORS>0 :
            print "bild failed"; sys.exit(1)
        else:
            print "bild succeeded"; sys.exit(0)
    else:
        sys.stderr.write("unknown target: %s\n" % sys.argv[target_index])
