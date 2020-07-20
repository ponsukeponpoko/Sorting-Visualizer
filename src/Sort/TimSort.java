package Sort;

import java.util.ArrayList;
import java.util.List;
import Sort.Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

public class TimSort extends SortingAlgorithms{
	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private boolean _isVisualizing;
	private int _sizeOfBlocks;
	private List<VisualChange> _visualChange;
	private VisualChange _lastItem;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;

	public TimSort(int numOfBars, ArrayList<Integer> numToSort, Boolean isVisualizing) throws InvalidInputException {
		if (numToSort == null) {
			throw new InvalidInputException("You can not sort array with no numbers");
		}
		_numOfBars = numOfBars;
		_numToSort = numToSort;
		_isVisualizing = isVisualizing;
		_sizeOfBlocks = 32;
		_visualChange = new ArrayList<>();
	}
	
	public List<VisualChange> sortAlgorithm() {
		if (_numOfBars <= 1) {
			return _visualChange;
		}
		this.sort();
		this.errorCheck(AlgorithmMode.TIM, _numToSort);
		return _visualChange;
	}
	
	@Override
	public void sort() {
		while(_numToSort.size() < _sizeOfBlocks) {
			//merge Sort works the best when the size of array to sort is in power of 2
			_sizeOfBlocks /= 2;
		}
		for (int i = 0; i < _numToSort.size(); i += _sizeOfBlocks) {
			this.insertionSort(i, Math.min((i + 31), (_numToSort.size() - 1)));
		}
		for (int currSize = _sizeOfBlocks; currSize < _numToSort.size(); currSize *= 2) {
			for (int low = 0; low < _numToSort.size(); low += 2 * currSize) {
				int mid = low + currSize - 1;
				int high = Math.min((low + 2 * currSize - 1), (_numToSort.size() - 1));
				this.merge(low, mid, high);
			}
		}
		_visualChange.add(_lastItem);
	}

	private void merge(int low, int mid, int high) {
		if (_isVisualizing) {
			for (int i = low; i < high + 1; i ++) {
				_visualChange.add(new VisualChange(i, _numToSort.get(i), false));
			}
		}
		if (high - mid < 0) {
			return;
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
			_lastItem = new VisualChange(i, _numToSort.get(i), true);
		}
	}
	
	private void insertionSort(int low, int high) {
		for (int i = low + 1; i <= high; i++) { 
            int temp = _numToSort.get(i); 
            int j = i - 1; 
            while (j >= low && _numToSort.get(j) > temp){ 
            	_visualChange.add(new VisualChange(j + 1, _numToSort.get(j), true));
            	_numToSort.set(j + 1, _numToSort.get(j));
                j--; 
            } 
            _visualChange.add(new VisualChange(j + 1, temp, true));
            _numToSort.set(j + 1, temp);
        } 
	}

	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = TimSort.makeCheckBox(menu, AlgorithmMode.TIM, "Tim Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new timHandler());
	}

	public static class timHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.TIM);
			} else {
				_selectedMode.remove(AlgorithmMode.TIM);
			}
		}
	}
	
	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.TIM);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.TIM);
		}
	}
	
	@Override
	public ArrayList<Integer> returnSortedList() {
		this.sortAlgorithm();
		return _numToSort;
	}
}
