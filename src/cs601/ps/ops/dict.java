package cs601.ps.ops;

import cs601.ps.PSInterpreter;
import cs601.ps.objects.PSDictionary;

public class dict extends PSOperator {
	@Override
	public void execute(PSInterpreter interpreter) {
		interpreter.pop(); // ignore size of dictionary
		interpreter.push(new PSDictionary());
	}
}
