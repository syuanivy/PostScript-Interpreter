package cs601.ps.objects;

import cs601.ps.PSInterpreter;

public class PSUndefined extends PSValue {
	public PSUndefined(Object value) {
		super(value);
	}

	@Override
	public String getJavaValue() {
		return (String)super.getJavaValue();
	}

	@Override
	public void execute(PSInterpreter interpreter) {
		// do nothing; don't alter the stack
	}
}
