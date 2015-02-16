package cs601.ps.objects;

/**
 * Created by ivy on 2/15/15.
 */
public class PSInt extends PSNumber {


    public PSInt(Object value) {
        super(value);
    }

    @Override
    public float floatValue() {
        return 0;
    }

    @Override
    public int intValue() {
        return 0;
    }
}
