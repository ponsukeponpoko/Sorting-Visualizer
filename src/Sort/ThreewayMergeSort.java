package Sort;

import java.util.ArrayList;
import java.util.List;
import Sort.Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

/*
 * Algorithm taken from https://www.geeksforgeeks.org/3-way-merge-sort/
 */

public class ThreewayMergeSort extends SortingAlgorithms {
	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private boolean _isVisualizing;
	private List<VisualChange> _visualChange;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;

	public ThreewayMergeSort(int numOfBars, ArrayList<Integer> numToSort, Boolean isVisualizing)
			throws InvalidInputException {
		if (numToSort == null) {
			throw new InvalidInputException("You can not sort array with no numbers");
		}
		_numOfBars = numOfBars;
		_numToSort = numToSort;
		_isVisualizing = isVisualizing;
		_visualChange = new ArrayList<>();
	}

	public List<VisualChange> sortAlgorithm() {
		if (_numOfBars == 0) {
			return null;
		}
		this.sort();
		this.errorCheck(AlgorithmMode.THREEWAY_MERGE, _numToSort);
		return _visualChange;
	}

	@Override
	public void sort() {
		int[] tempArray = new int[_numOfBars];
		for (int i = 0; i < _numOfBars; i++) {
			tempArray[i] = _numToSort.get(i);
		}
		this.threeWayMerge(tempArray, 0, _numOfBars);
		_visualChange.add(_visualChange.get(_visualChange.size() - 1));
	}

	private void threeWayMerge(int[] tempArray, int l, int h) {
		if (h - l < 2) {
			return;
		}
		int part1 = l + ((h - l) / 3);
		int part2 = l + 2 * ((h - l) / 3) + 1;
		this.threeWayMerge(tempArray, l, part1);
		this.threeWayMerge(tempArray, part1, part2);
		this.threeWayMerge(tempArray, part2, h);
		this.merge(tempArray, l, part1, part2, h);
	}

	private void merge(int[] tempArray, int low, int part1, int part2, int high) {
		if (_isVisualizing) {
			for (int i = low; i < high; i++) {
				_visualChange.add(new VisualChange(i, _numToSort.get(i), false));
			}
		}
		int i = low;
		int j = part1;
		int k = part2;
		int m = low;

		while ((i < part1) && (j < part2) && (k < high)) {
			if (_numToSort.get(i) - _numToSort.get(j) < 0) {
				if (_numToSort.get(i) - _numToSort.get(k) < 0) {
					tempArray[m] = _numToSort.get(i);
					_visualChange.add(new VisualChange(m, _numToSort.get(i), true));
					i++;
				} else {
					tempArray[m] = _numToSort.get(k);
					_visualChange.add(new VisualChange(m, _numToSort.get(k), true));
					k++;
				}
			} else {
				if (_numToSort.get(j) - _numToSort.get(k) < 0) {
					tempArray[m] = _numToSort.get(j);
					_visualChange.add(new VisualChange(m, _numToSort.get(j), true));
					j++;
				} else {
					tempArray[m] = _numToSort.get(k);
					_visualChange.add(new VisualChange(m, _numToSort.get(k), true));
					k++;
				}
			}
			m++;
		}

		while ((i < part1) && (j < part2)) {
			if (_numToSort.get(i) - _numToSort.get(j) < 0) {
				tempArray[m] = _numToSort.get(i);
				_visualChange.add(new VisualChange(m, _numToSort.get(i), true));
				i++;
			} else {
				tempArray[m] = _numToSort.get(j);
				_visualChange.add(new VisualChange(m, _numToSort.get(j), true));
				j++;
			}
			m++;
		}

		while ((j < part2) && (k < high)) {
			if (_numToSort.get(j) - _numToSort.get(k) < 0) {
				tempArray[m] = _numToSort.get(j);
				_visualChange.add(new VisualChange(m, _numToSort.get(j), true));
				j++;
			} else {
				tempArray[m] = _numToSort.get(k);
				_visualChange.add(new VisualChange(m, _numToSort.get(k), true));
				k++;
			}
			m++;
		}

		while ((i < part1) && (k < high)) {
			if (_numToSort.get(i) - _numToSort.get(k) < 0) {
				tempArray[m] = _numToSort.get(i);
				_visualChange.add(new VisualChange(m, _numToSort.get(i), true));
				i++;
			} else {
				tempArray[m] = _numToSort.get(k);
				_visualChange.add(new VisualChange(m, _numToSort.get(k), true));
				k++;
			}
			m++;
		}

		while (i < part1) {
			tempArray[m] = _numToSort.get(i);
			_visualChange.add(new VisualChange(m, _numToSort.get(i), true));
			i++;
			m++;
		}

		while (j < part2) {
			tempArray[m] = _numToSort.get(j);
			_visualChange.add(new VisualChange(m, _numToSort.get(j), true));
			j++;
			m++;
		}

		while (k < high) {
			tempArray[m] = _numToSort.get(k);
			_visualChange.add(new VisualChange(m, _numToSort.get(k), true));
			k++;
			m++;
		}
		for (int n = 0; n < _numOfBars; n++) {
			_numToSort.set(n, tempArray[n]);
		}
	}

	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = ThreewayMergeSort.makeCheckBox(menu, AlgorithmMode.THREEWAY_MERGE, "Threeway Merge Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new threeWayMergeHandler());
	}

	public static class threeWayMergeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.THREEWAY_MERGE);
			} else {
				_selectedMode.remove(AlgorithmMode.THREEWAY_MERGE);
			}
		}
	}

	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.THREEWAY_MERGE);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.THREEWAY_MERGE);
		}
	}

	@Override
	public ArrayList<Integer> returnSortedList() {
		this.sortAlgorithm();
		return _numToSort;
	}
}
