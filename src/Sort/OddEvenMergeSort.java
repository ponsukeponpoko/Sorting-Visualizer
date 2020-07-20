package Sort;

import java.util.ArrayList;
import java.util.List;
import Sort.Exceptions.InvalidInputException;
import Sort.Exceptions.NotPowerOfTwoException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

public class OddEvenMergeSort extends SortingAlgorithms {
	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private List<Swap> _swap;
	private Swap _lastSwap;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;

	public OddEvenMergeSort(int numOfBars, ArrayList<Integer> numToSort) throws InvalidInputException, NotPowerOfTwoException {
		if (numToSort == null) {
			throw new InvalidInputException("You can not sort array with no numbers");
		}
		if (!this.checkIfPowerOfTwo(numOfBars)) {
			throw new NotPowerOfTwoException("In order to sort numbers using bitonic sort, th input array size must be a power of 2.");
		}
		_numOfBars = numOfBars;
		_numToSort = numToSort;
		_swap = new ArrayList<>();
	}

	public List<Swap> sortAlgorithm() {
		if (_numOfBars == 0) {
			return null;
		}
		this.sort();
		this.errorCheck(AlgorithmMode.BATCHER_ODD_EVEN_MERGE, _numToSort);
		return _swap;
	}

	@Override
	public void sort() {
		this.recursiveSort(0, _numOfBars - 1);
		_swap.add(_lastSwap);
	}

	private void recursiveSort(int low, int high) {
		if (high - low >= 1) {
			//int mid = (int) (low + (Math.floor((low + high) / 2)));
			int mid = low + (high - low)/2;
			this.recursiveSort(low, mid);
			this.recursiveSort(mid + 1, high);
			this.merge(low, high, 1);
		}
	}
	
	private void merge(int low, int high, int k) {
		int step = 2*k;
		if (step <= high-low || low + k >= _numOfBars) {
			this.merge(low, high, step);
			this.merge(low + k, high, step);
			for (int i = low + k; i < high - k; i += step) {
				if (_numToSort.get(i) > _numToSort.get(i + k)) {
					_lastSwap = new Swap(i, _numToSort.get(i), k, true);
					_swap.add(_lastSwap);
					int temp = _numToSort.get(i);
					_numToSort.set(i, _numToSort.get(i + k));
					_numToSort.set(i + k, temp);
				}
			}
		}
		else {
			if (_numToSort.get(low) > _numToSort.get(low + k)) {
				_lastSwap = new Swap(low, _numToSort.get(low), k, true);
				_swap.add(_lastSwap);
				int temp = _numToSort.get(low);
				_numToSort.set(low, _numToSort.get(low + k));
				_numToSort.set(low + k, temp);
			}
		}
	}

	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = OddEvenSort.makeCheckBox(menu, AlgorithmMode.BATCHER_ODD_EVEN_MERGE, "Odd-Even Merge Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new oddEvenHandler());
	}

	public static class oddEvenHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.BATCHER_ODD_EVEN_MERGE);
			} else {
				_selectedMode.remove(AlgorithmMode.BATCHER_ODD_EVEN_MERGE);
			}
		}
	}

	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.BATCHER_ODD_EVEN_MERGE);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.BATCHER_ODD_EVEN_MERGE);
		}
	}

	@Override
	public ArrayList<Integer> returnSortedList() {
		this.sortAlgorithm();
		return _numToSort;
	}
}
