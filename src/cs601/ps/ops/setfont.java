package cs601.ps.ops;

import cs601.ps.PSInterpreter;
import cs601.ps.objects.PSFont;

public class setfont extends PSOperator {
    public void execute(PSInterpreter interpreter) {
		PSFont font = (PSFont)interpreter.pop();
		interpreter.gstate.fontName = (String)font.getJavaValue();
		interpreter.gstate.fontSize = (int)font.fontScale;
	}
}
