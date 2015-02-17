package cs601.ps.objects;

/**
 * Created by ivy on 2/15/15.
 */
public class PSInt extends PSNumber {
    public PSInt(String intStr){super(new Integer(intStr));}
    public PSInt(int i){super(new Integer(i));}

    @Override
    public float floatValue() {
        return ((Integer)value).floatValue();
    }

    @Override
    public int intValue() {
        return ((Integer) getJavaValue()).intValue();
    }



}
