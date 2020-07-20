package Sort;

public class VisualChange {
	/*
	 * This is for non-swap based sorting algorithm
	 */
	int _currIndex;
	int _newVal;
	boolean _notScanning;

    public VisualChange(int currIndex, int newVal, boolean notScanning) {
        this._currIndex = currIndex;
        this._newVal = newVal;
        this._notScanning = notScanning;
    }
}
