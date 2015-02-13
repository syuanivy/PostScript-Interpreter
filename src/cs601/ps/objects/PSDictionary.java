package cs601.ps.objects;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PSDictionary extends PSValue {
	public PSDictionary() {
		super(new HashMap<String, PSObject>());
	}

	@Override
	public Map<String, PSObject> getJavaValue() {
		return (Map<String, PSObject>)super.getJavaValue();
	}

	public void clear() { getJavaValue().clear(); }
	public void put(String key, PSObject value) { getJavaValue().put(key, value); }
	public PSObject get(String key) { return getJavaValue().get(key); }
	public String findKeyFor(PSObject o) {
		Map<String, PSObject> dict = getJavaValue();
		Set<Map.Entry<String, PSObject>> entries = dict.entrySet();
		for (Map.Entry<String, PSObject> entry : entries) {
			if ( entry.getValue()==o ) return entry.getKey();
		}
		return null;
	}
}
