package cs601.ps.objects;

import cs601.ps.PSInterpreter;

/** A reference to an operator within a {...} PSProcedure object
 *  where we need to defer execution. Normally we would directly
 *  execute something like lineto, but within a procedure definition,
 *  we have to store reference to the name until execution. E.g.,
 *
 *  { (hi) length }
 *
 *  defines a procedure with two code elements, a string followed by
 *  a PSOperatorName object.
 *
 *  This is distinguished from PSNameLiteral like /f used
 *  like: /f {...} def
 */
public class PSOperatorName extends PSValue {
    public PSOperatorName(String name) {
        super(name);
    }

    @Override
    public String getJavaValue() {
        return (String)value;
    }

	public String getName() { return getJavaValue(); }

    @Override
    public void execute(PSInterpreter interpreter) {
		PSObject op = interpreter.lookup(getJavaValue());
        op.execute(interpreter);
    }
}
