package cs601.ps.ops;

import cs601.ps.PSInterpreter;
import cs601.ps.objects.PSFont;
import cs601.ps.objects.PSNumber;

public class scalefont extends PSOperator {
    public void execute(PSInterpreter interpreter) {
		PSNumber scale = (PSNumber)interpreter.pop();
		PSFont font = (PSFont)interpreter.pop();
		font.fontScale = scale.floatValue();
        interpreter.push(font); // push back altered font
    }
}
