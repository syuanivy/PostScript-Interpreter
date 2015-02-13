package cs601.ps.objects;

/** A PostScript literal like /average */
public class PSNameLiteral extends PSValue {
    public PSNameLiteral(String name) {
        super(name.substring(1)); // strip / from /f
    }

	@Override
	public String getJavaValue() {
		return (String)value;
	}
}
