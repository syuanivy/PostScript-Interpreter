package cs601.ps.objects;

import java.util.ArrayList;

public class PSArray extends PSValue {
    public PSArray() {
        super(new ArrayList<PSObject>());
    }

    public void add(PSObject o) {
		ArrayList<PSObject> data = ((ArrayList<PSObject>)value);
        data.add(o);
    }
}
