package cs601.ps.objects;

import cs601.ps.PSInterpreter;

import java.util.List;

public class PSProcedure extends PSValue {
    public PSProcedure(List<PSObject> code) {
        super(code);
    }

	@Override
	public List<PSObject> getJavaValue() {
		return (List<PSObject>)value;
	}

	public void addCode(PSObject o) {
		getJavaValue().add(o);
    }

	public void setCode(List<PSObject> code) {
		this.value = code;
	}

	@Override
	public void execute(PSInterpreter interpreter) {
		if ( interpreter.trace ) {
			String procName = interpreter.dictionaryStack.peek().findKeyFor(this);
			if ( procName!=null ) {
				System.out.println("call "+procName);
			}
			else {
				System.out.println("exec block: "+this);
			}
		}
		// walk list of code in the procedure, executing it
		for (PSObject o : getJavaValue()) {
			interpreter.execute(o);
		}
	}

	@Override
	public String toString() {
		String s = getJavaValue().toString();
		s = s.substring(1, s.length() - 1);
		return "{"+s+"}";
	}
}
