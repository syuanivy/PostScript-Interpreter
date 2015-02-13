package cs601.ps.ops;

import cs601.ps.objects.PSObject;

/** Just a grouping symbol */
public abstract class PSOperator extends PSObject {
	public String toString() {
		return getClass().getSimpleName();
	}
}
