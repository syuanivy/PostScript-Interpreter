package cs601.ps.objects;
public class PSReal extends PSNumber {
    public PSReal(String numStr) {
        super(new Float(numStr));
    }
    public PSReal(float f) {
        super(new Float(f));
    }
    public float floatValue() {
        return ((Float) getJavaValue()).floatValue();
    }

	@Override
	public int intValue() {
		return ((Float)value).intValue();
	}
}
