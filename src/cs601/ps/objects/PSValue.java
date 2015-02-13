package cs601.ps.objects;

import cs601.ps.PSInterpreter;

public abstract class PSValue extends PSObject {
    protected Object value;

    public PSValue(Object value) {
        this.value = value;
    }

    public Object getJavaValue() {
        return value;
    }

	public void execute(PSInterpreter interpreter) {
		// simple kind of object, just push
		interpreter.push(this);
	}

	public String toString() {
//		return getClass().getSimpleName()+":"+value.toString();
		return getJavaValue().toString();
    }
}
