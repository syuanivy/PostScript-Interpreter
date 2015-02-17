package cs601.ps.objects;

public class PSString extends PSValue {
    public PSString(String s) {
        super(s.substring(1,s.length()-1));
    } // strip " "
}
