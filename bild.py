#!/usr/bin/env python

# bootstrap by downloading bilder.py if not found
import urllib
import os

if not os.path.exists("bilder.py"):
    print "bootstrapping; downloading bilder.py"
    urllib.urlretrieve(
        "https://raw.githubusercontent.com/parrt/bild/master/src/python/bilder.py",
        "bilder.py")

# assumes bilder.py is in current directory
from bilder import *

def parser():
    antlr4(srcdir="src/cs601/ps/parser/EPS.g4", trgdir="src/cs601/ps/parser",
           version="4.5", args=["-no-listener"])

def compile():
    require(parser)
    javac("src", "out", cp="src:"+JARCACHE+"/antlr-4.5-complete.jar")

def mkjar():
    require(compile)
    jar("dist/ps.jar", srcdir="out", manifest="")

def test():
    java(classname="cs601.ps.PS", cp="out:"+JARCACHE+"/antlr-4.5-complete.jar",
         progargs=["tests/fact.ps"])

def all():
    require(compile)
    test()

processargs(globals())
