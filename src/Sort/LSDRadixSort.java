package Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Sort.Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

public class LSDRadixSort extends SortingAlgorithms{
	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private int _radix;
	private boolean _isVisualizing;
	private List<VisualChange> _visualChange;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;
	

	public LSDRadixSort(int numOfBars, ArrayList<Integer> numToSort, Boolean isVisualizing) throws InvalidInputException{
		if(numToSort == null) {
			throw new InvalidInputException("You can not sort array with no numbers");
		}
		_numOfBars = numOfBars;
		_numToSort = numToSort;
		_isVisualizing = isVisualizing;
		_visualChange = new ArrayList<>();
	}

	public List<VisualChange> sortAlgorithm() {
		_radix = 10;
		if (_numOfBars == 0) {
			return null;
		}
		this.sort();
		this.errorCheck(AlgorithmMode.LSD_RADIX, _numToSort);
		return _visualChange;

	}

	@Override
	public void sort() {
		int minVal = _numToSort.get(0);
		int maxVal = _numToSort.get(0);
		for (int i = 0; i < _numOfBars; i++) {
			if (_numToSort.get(i) > maxVal) {
				maxVal = _numToSort.get(i);
			}
			if (_numToSort.get(i) < minVal) {
				minVal = _numToSort.get(i);
			}
		}
		int currExponent = 1;
		while((maxVal - minVal) / currExponent >= 1) {
			if (_isVisualizing) {
				for (int i = 0; i < _numOfBars; i ++) {
					_visualChange.add(new VisualChange(i, _numToSort.get(i), false));
				}
			}
			this.countSort(currExponent, minVal);
			if (_isVisualizing) {
				for (int i = 0; i < _numOfBars; i ++) {
					_visualChange.add(new VisualChange(i, _numToSort.get(i), true));
				}
			}
			currExponent *= _radix;
		}
		_visualChange.add(new VisualChange(_numOfBars - 1, _numToSort.get(_numOfBars - 1), true));
	}
	
	private void countSort(int currExponent, int minVal) {
		int[] countArray = new int[_radix];
		int[] sortedArray = new int[_numOfBars];
		Arrays.fill(countArray, 0);
		for (int j = 0; j < _numOfBars; j++) {
			countArray[(int)(((_numToSort.get(j) - minVal) / currExponent) % _radix)]++;
		}
		for (int k = 1; k < countArray.length; k++) {
			countArray[k] += countArray[k - 1];
		}
		for (int m = _numOfBars - 1; m >= 0; m--) {
			sortedArray[countArray[(int)(((_numToSort.get(m) - minVal) / currExponent) % _radix)] - 1] = _numToSort.get(m);
			countArray[(int)(((_numToSort.get(m) - minVal) / currExponent) % _radix)]--;
		}
		for (int n = 0; n < _numOfBars; n++) {
			_numToSort.set(n, sortedArray[n]);
		}
	}

	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = LSDRadixSort.makeCheckBox(menu, AlgorithmMode.LSD_RADIX, "LSD Radix Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new lsdRadixHandler());
	}

	public static class lsdRadixHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.LSD_RADIX);
			} else {
				_selectedMode.remove(AlgorithmMode.LSD_RADIX);
			}
		}
	}
	
	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.LSD_RADIX);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.LSD_RADIX);
		}
	}
	
	@Override
	public ArrayList<Integer> returnSortedList(){
		this.sortAlgorithm();
		return _numToSort;
	}
}
