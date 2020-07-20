package Sort;

import java.util.ArrayList;
import java.util.List;
import Sort.Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

public class PigeonholeSort extends SortingAlgorithms {
	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private boolean _isVisualizing;
	private List<VisualChange> _visualChange;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;

	public PigeonholeSort(int numOfBars, ArrayList<Integer> numToSort, Boolean isVisualizing)
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
			return _visualChange;
		}
		this.sort();
		this.errorCheck(AlgorithmMode.PIGEONHOLE, _numToSort);
		return _visualChange;
	}

	@Override
	public void sort() {
		if (_isVisualizing) {
			for (int i = 0; i < _numOfBars; i++) {
				_visualChange.add(new VisualChange(i, _numToSort.get(i), false));
			}
		}
		int minVal = _numToSort.get(0);
		int maxVal = _numToSort.get(0);
		int currIndex = 0;
		for (int i = 0; i < _numOfBars; i++) {
			if (_numToSort.get(i) > maxVal) {
				maxVal = _numToSort.get(i);
			}
			if (_numToSort.get(i) < minVal) {
				minVal = _numToSort.get(i);
			}
		}
		int[] sortedArray = new int[maxVal - minVal + 1];
		for (int j : sortedArray) {
			sortedArray[j] = 0;
		}
		for (int k = 0; k < _numOfBars; k++) {
			sortedArray[_numToSort.get(k) - minVal]++;
		}
		for (int m = 0; m < (maxVal - minVal + 1); m++) {
			for (int n = sortedArray[m]; n > 0; n--) {
				_visualChange.add(new VisualChange(currIndex, m + minVal, true));
				_numToSort.set(currIndex, m + minVal);
				currIndex++;
			}
		}
		_visualChange.add(new VisualChange(currIndex - 1, maxVal, true));
	}

	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = PigeonholeSort.makeCheckBox(menu, AlgorithmMode.PIGEONHOLE, "Pigeonhole Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new pigeonholeHandler());
	}

	public static class pigeonholeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.PIGEONHOLE);
			} else {
				_selectedMode.remove(AlgorithmMode.PIGEONHOLE);
			}
		}
	}

	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.PIGEONHOLE);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.PIGEONHOLE);
		}
	}

	@Override
	public ArrayList<Integer> returnSortedList() {
		this.sortAlgorithm();
		return _numToSort;
	}
}
