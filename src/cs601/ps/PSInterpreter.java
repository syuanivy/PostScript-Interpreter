package cs601.ps;

import cs601.ps.objects.PSArray;
import cs601.ps.objects.PSDictionary;
import cs601.ps.objects.PSObject;
import cs601.ps.objects.PSOperatorName;
import cs601.ps.objects.PSUndefined;
import cs601.ps.objects.PSValue;
import cs601.ps.ops._if;
import cs601.ps.ops.add;
import cs601.ps.ops.begin;
import cs601.ps.ops.cvs;
import cs601.ps.ops.def;
import cs601.ps.ops.dict;
import cs601.ps.ops.div;
import cs601.ps.ops.dup;
import cs601.ps.ops.end;
import cs601.ps.ops.eq;
import cs601.ps.ops.exch;
import cs601.ps.ops.findfont;
import cs601.ps.ops.forOp;
import cs601.ps.ops.grestore;
import cs601.ps.ops.gsave;
import cs601.ps.ops.ifelse;
import cs601.ps.ops.lineto;
import cs601.ps.ops.load;
import cs601.ps.ops.moveto;
import cs601.ps.ops.mul;
import cs601.ps.ops.repeat;
import cs601.ps.ops.rmoveto;
import cs601.ps.ops.scale;
import cs601.ps.ops.scalefont;
import cs601.ps.ops.setfont;
import cs601.ps.ops.setrgbcolor;
import cs601.ps.ops.show;
import cs601.ps.ops.showpage;
import cs601.ps.ops.stroke;
import cs601.ps.ops.sub;
import cs601.ps.parser.EPSLexer;
import cs601.ps.parser.EPSParser;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class PSInterpreter {
	public PSView view;
	public JPanel mainPane;
	public JFrame frame;

	public GraphicsState gstate = new GraphicsState(this);

	/** Holds dictionary scopes */
	public Stack<PSDictionary> dictionaryStack = new Stack<>();

	/** Default system dictionary with predefined operators, values */
	public PSDictionary systemdict = new PSDictionary();

	/** User-level dictionary where procedures / variables defined */
	public PSDictionary userdict = new PSDictionary();

	/** Hold operand objects; must be value not operator. */
	public Stack<PSObject> operandStack = new Stack<>();

	public Stack<GraphicsState> gstateStack = new Stack<>();

	public String fileName;

	public boolean trace = false;

	public PSInterpreter(String fileName) {
		this.fileName = fileName;
		init();
	}

	public void init() {
		dictionaryStack.clear();
		systemdict.clear();
		userdict.clear();
		operandStack.clear();
		dictionaryStack.push(systemdict); // "outermost" scope
		dictionaryStack.push(userdict);

		systemdict.put("def", new def());
		systemdict.put("if", new _if());
		systemdict.put("ifelse", new ifelse());
		systemdict.put("for", new forOp());
		systemdict.put("repeat", new repeat());
		systemdict.put("load", new load());
		systemdict.put("add", new add());
		systemdict.put("sub", new sub());
		systemdict.put("div", new div());
		systemdict.put("mul", new mul());
		systemdict.put("eq", new eq());
		systemdict.put("cvs", new cvs());
		systemdict.put("dup", new dup());
		systemdict.put("exch", new exch());
		systemdict.put("dict", new dict());
		systemdict.put("begin", new begin());
		systemdict.put("end", new end());
		systemdict.put("show", new show());
		systemdict.put("moveto", new moveto());
		systemdict.put("rmoveto", new rmoveto());
		systemdict.put("lineto", new lineto());
		systemdict.put("showpage", new showpage());
		systemdict.put("setfont", new setfont());
		systemdict.put("findfont", new findfont());
		systemdict.put("scalefont", new scalefont());
		systemdict.put("scale", new scale());
		systemdict.put("stroke", new stroke());
		systemdict.put("gsave", new gsave());
		systemdict.put("grestore", new grestore());
		systemdict.put("setrgbcolor", new setrgbcolor());
	}

	/** From PS book:
	 * • Executing an executable name causes it to be looked up in the current dictionary
	 *   context and the associated value to be executed.
	 * • Executing an executable operator causes some built-in action to be performed.
	 * • Executing an executable array (otherwise known as a procedure) causes the elements
	 *   of the array to be executed in turn.
	 */
	public void execute(PSObject o) {
		boolean showFirst = false;
		if ( o instanceof PSOperatorName ) {
			PSOperatorName op = (PSOperatorName) o;
			showFirst = op.getName().equals("forOp") ||
						op.getName().equals("if") ||
						op.getName().equals("ifelse") ||
						op.getName().equals("forOp") ||
						op.getName().equals("forOp");
		}
		showFirst |= o instanceof PSUndefined;

		if ( trace && showFirst ) {
			System.out.printf("exec %-20s %s\n", o, operandStack);
		}
		o.execute(this);
		if ( trace && !showFirst ) {
			System.out.printf("exec %-20s %s\n",o,operandStack);
		}
	}

	/** Push a postscript object onto the operand stack */
	public void push(PSObject operand) {
	}

    /** pop an object off of the operand stack and return it */
	public PSObject pop() {
	}

    /** Pop a value off the stack and print it. If an operator, print its class name
     *  like lineto or def instead.
     */
	public void popAndPrint() {
	}

	public PSArray popAsArray(int n) {
		PSArray a = new PSArray();
		for (int i = 0; i < n; i++) {
			a.add(pop());
		}
		return a;
	}

	public PSObject peek() {
		return operandStack.peek();
	}

	public void define(String key, PSObject value) {
	}

	/** Lookup a key in stack of dictionaries, top to bottom.
	 *  Return PSUndefined(key) if you can't find it
	 */
	public PSObject lookup(String key) {
	}

	public void parseAndExecute() {
		try {
			//System.out.println("begin parsing "+fileName);
			ANTLRFileStream in = new ANTLRFileStream(fileName);
			EPSLexer lexer = new EPSLexer(in);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			EPSParser parser = new EPSParser(tokens, this);
			parser.setBuildParseTree(false);
			parser.file();
		}
		catch (Exception e) {
			System.err.println("Problem executing PS: "+e);
			e.printStackTrace(System.err);
		}
	}

	public void launch() {
		frame = new JFrame();
		parseAndExecute();

		view = new PSView(this);
		gstate.updateBounds();
		Dimension bounds = gstate.getBounds();
		frame.setTitle("PostScript Viewer: "+fileName+", "+(int)bounds.getWidth()+" x "+(int)bounds.getHeight());

		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(view, BorderLayout.CENTER);

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
