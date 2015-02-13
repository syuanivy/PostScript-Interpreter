package cs601.ps;

import cs601.ps.ops.add;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PS {
	public static PSInterpreter interpreter = null;

	public static void main(String[] args) throws Exception {
		boolean trace = false;
		if ( args.length>0 ) {
			int a = 0;
			if ( args[a].equals("-trace") ) {
				trace = true;
				a++;
			}
			String fileName = args[a];
			interpreter = new PSInterpreter(fileName);
			interpreter.trace = trace;
			interpreter.launch();
		}
		else {
			System.out.println("missing filename");
			System.exit(1);
		}
	}
}
