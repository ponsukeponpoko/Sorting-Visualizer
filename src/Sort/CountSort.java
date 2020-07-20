package Sort;

import java.util.ArrayList;
import java.util.Arrays;
import Sort.Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

public class CountSort extends SortingAlgorithms {
	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;

	public CountSort(int numOfBars, ArrayList<Integer> numToSort) throws InvalidInputException {
		if (numToSort == null) {
			throw new InvalidInputException("You can not sort array with no numbers");
		}
		_numOfBars = numOfBars;
		_numToSort = numToSort;
	}

	public void sortAlgorithm() {
		if (_numOfBars == 0) {
			return;
		}
		this.sort();
		this.errorCheck(AlgorithmMode.COUNT, _numToSort);
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
		int[] countArray = new int[maxVal - minVal + 1];
		int[] sortedArray = new int[_numOfBars];
		Arrays.fill(countArray, 0);
		for (int j = 0; j < _numOfBars; j++) {
			countArray[_numToSort.get(j) - minVal]++;
		}
		for (int k = 1; k < countArray.length; k++) {
			countArray[k] += countArray[k - 1];
		}
		for (int m = _numOfBars - 1; m >= 0; m--) {
			sortedArray[countArray[_numToSort.get(m) - minVal] - 1] = _numToSort.get(m);
			countArray[_numToSort.get(m) - minVal]--;
		}
		for (int n = 0; n < _numOfBars; n++) {
			_numToSort.set(n, sortedArray[n]);
		}
	}

	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = CountSort.makeCheckBox(menu, AlgorithmMode.COUNT, "Count Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new countHandler());
	}

	public static class countHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.COUNT);
			} else {
				_selectedMode.remove(AlgorithmMode.COUNT);
			}
		}
	}

	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.COUNT);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.COUNT);
		}
	}

	@Override
	public ArrayList<Integer> returnSortedList() {
		this.sortAlgorithm();
		return _numToSort;
	}
}
