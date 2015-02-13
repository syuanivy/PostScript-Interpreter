package cs601.ps.objects;

public abstract class PSNumber extends PSValue {
    public PSNumber(Object value) {
        super(value);
    }
    public abstract float floatValue();
	public abstract int intValue();
}
