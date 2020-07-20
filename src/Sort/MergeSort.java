package Sort;

import java.util.ArrayList;
import java.util.List;
import Sort.Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

public class MergeSort extends SortingAlgorithms {
	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private boolean _isVisualizing;
	private List<VisualChange> _visualChange;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;

	public MergeSort(int numOfBars, ArrayList<Integer> numToSort, Boolean isVisualizing) throws InvalidInputException {
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
		this.errorCheck(AlgorithmMode.MERGE, _numToSort);
		return _visualChange;
	}

	@Override
	public void sort() {
		this.recursiveSort(0, _numToSort.size() - 1);
	}

	private void recursiveSort(int l, int h) {
		if (h <= l) {
			return;
		}
		int mid = (l + h) / 2;
		this.recursiveSort(l, mid);
		this.recursiveSort(mid + 1, h);
		this.merge(l, mid, h);
	}

	private void merge(int low, int mid, int high) {
		if (_isVisualizing) {
			for (int i = low; i < high + 1; i++) {
				_visualChange.add(new VisualChange(i, _numToSort.get(i), false));
			}
		}
		// Find sizes of two subarrays to be merged
		int leftArray[] = new int[mid - low + 1];
		int rightArray[] = new int[high - mid];

		// Copying our subarrays into temporaries
		for (int i = 0; i < leftArray.length; i++) {
			leftArray[i] = _numToSort.get(low + i);
		}

		for (int i = 0; i < rightArray.length; i++) {
			rightArray[i] = _numToSort.get(mid + 1 + i);
		}

		// Iterators containing current index of temp subarrays
		int leftIndex = 0;
		int rightIndex = 0;

		// Copying from leftArray and rightArray back into array
		for (int i = low; i < high + 1; i++) {
			// If there are still uncopied elements in R and L, copy minimum of the two
			if (leftIndex < leftArray.length && rightIndex < rightArray.length) {
				if (leftArray[leftIndex] < rightArray[rightIndex]) {
					_numToSort.set(i, leftArray[leftIndex]);
					leftIndex++;
				} else {
					_numToSort.set(i, rightArray[rightIndex]);
					rightIndex++;
				}
			} else if (leftIndex < leftArray.length) {
				// If all elements have been copied from rightArray, copy rest of leftArray
				_numToSort.set(i, leftArray[leftIndex]);
				leftIndex++;
			} else if (rightIndex < rightArray.length) {
				// If all elements have been copied from leftArray, copy rest of rightArray
				_numToSort.set(i, rightArray[rightIndex]);
				rightIndex++;
			}
			_visualChange.add(new VisualChange(i, _numToSort.get(i), true));
		}
	}

	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = MergeSort.makeCheckBox(menu, AlgorithmMode.MERGE, "Merge Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new mergeHandler());
	}

	public static class mergeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.MERGE);
			} else {
				_selectedMode.remove(AlgorithmMode.MERGE);
			}
		}
	}

	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.MERGE);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.MERGE);
		}
	}

	@Override
	public ArrayList<Integer> returnSortedList() {
		this.sortAlgorithm();
		return _numToSort;
	}
}
