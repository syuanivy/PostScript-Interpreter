package cs601.ps.ops;


import cs601.ps.PSInterpreter;
import cs601.ps.objects.PSInt;
import cs601.ps.objects.PSNumber;
import cs601.ps.objects.PSObject;
import cs601.ps.objects.PSReal;

public class mul extends PSOperator {
    public void execute(PSInterpreter interpreter) {
        PSNumber right = (PSNumber)interpreter.pop();
        PSNumber left = (PSNumber)interpreter.pop();
        if ( left instanceof PSInt &&
             right instanceof PSInt )
        {
            int result = (int)
                (left.floatValue() * right.floatValue());
            interpreter.push(new PSInt(result));
        }
        else {
            // one or both are float
            float result =
                (left.floatValue() * right.floatValue());
            interpreter.push(new PSReal(result));
        }
    }
}
