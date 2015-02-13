package cs601.ps.ops;

import cs601.ps.PSInterpreter;
import cs601.ps.objects.PSString;
import cs601.ps.objects.PSValue;


public class show extends PSOperator {
    public void execute(PSInterpreter interpreter) {
        // take string operand on stack and display at current x,y
        PSValue o = (PSValue)interpreter.pop();
        if ( o instanceof PSString ) {
			interpreter.gstate.show((String) o.getJavaValue());
		}
        else {
            throw new IllegalArgumentException("show requires a string argument");
        }
    }
}
