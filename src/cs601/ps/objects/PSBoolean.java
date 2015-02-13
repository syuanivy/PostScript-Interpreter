package cs601.ps.objects;

public class PSBoolean extends PSValue {
	public PSBoolean(Boolean value) {
		super(value);
	}

	@Override
	public Boolean getJavaValue() {
		return (Boolean)super.getJavaValue();
	}
}
