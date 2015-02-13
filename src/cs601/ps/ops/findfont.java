package cs601.ps.ops;

import cs601.ps.PSInterpreter;
import cs601.ps.objects.PSFont;
import cs601.ps.objects.PSNameLiteral;

public class findfont extends PSOperator {
    public void execute(PSInterpreter interpreter) {
		PSNameLiteral fontName = (PSNameLiteral)interpreter.pop();
		interpreter.push(new PSFont(fontName.getJavaValue()));
    }
}
