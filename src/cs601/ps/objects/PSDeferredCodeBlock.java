package cs601.ps.objects;

import cs601.ps.PSInterpreter;

/** Just pushes a code block onto the stack instead of executing.
 *  We use this to define and procedures and defer execution
 *  of the code block for that procedure. For example,
 *
 *  /f {...} def
 *
 *  pushes the PSNameLiteral /f and then a PSDeferredCodeBlock
 *  wrapping the PSProcedure associated with the code block.
 *  Executing the PSDeferredCodeBlock just pushes the procedure
 *  onto the operand stack.
 */
public class PSDeferredCodeBlock extends PSValue {
	public PSDeferredCodeBlock(PSProcedure value) {
		super(value);
	}

	@Override
	public PSProcedure getJavaValue() {
		return (PSProcedure)value;
	}

	@Override
	public void execute(PSInterpreter interpreter) {
		interpreter.push(getJavaValue());
	}
}
