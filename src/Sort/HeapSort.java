package Sort;

import java.util.ArrayList;
import java.util.List;
import Sort.Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

public class HeapSort extends SortingAlgorithms {
	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private List<Swap> _swap;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;

	public HeapSort(int numOfBars, ArrayList<Integer> numToSort) throws InvalidInputException {
		if (numToSort == null) {
			throw new InvalidInputException("You can not sort array with no numbers");
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
		this.errorCheck(AlgorithmMode.HEAP, _numToSort);
		return _swap;
	}

	@Override
	public void sort() {
		for (int i = _numOfBars / 2 - 1; i >= 0; i--) {
			this.heap(_numOfBars, i);
		}
		for (int i = _numOfBars - 1; i > 0; i--) {
			int temp = _numToSort.get(0);
			_numToSort.set(0, _numToSort.get(i));
			_numToSort.set(i, temp);
			_swap.add(new Swap(0, _numToSort.get(0), Math.abs(i - 0), true));
			this.heap(i, 0);
		}
		_swap.add(new Swap(0, _numToSort.get(0), 1, true));
	}

	private void heap(int currSize, int i) {
		int largest = i; // Initialize largest as root
		int left = 2 * i + 1; // left = 2*i + 1
		int right = 2 * i + 2; // right = 2*i + 2

		// If left child is larger than root
		if (left < currSize && _numToSort.get(left) > _numToSort.get(largest)) {
			largest = left;
		}

		// If right child is larger than largest so far
		if (right < currSize && _numToSort.get(right) > _numToSort.get(largest)) {
			largest = right;
		}

		// If largest is not root
		if (largest != i) {
			int temp = _numToSort.get(i);
			_numToSort.set(i, _numToSort.get(largest));
			_numToSort.set(largest, temp);
			_swap.add(new Swap(i, _numToSort.get(i), Math.abs(i - largest), true));
			this.heap(currSize, largest);
		}
	}

	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = HeapSort.makeCheckBox(menu, AlgorithmMode.HEAP, "Heap Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new heapHandler());
	}

	public static class heapHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.HEAP);
			} else {
				_selectedMode.remove(AlgorithmMode.HEAP);
			}
		}
	}

	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.HEAP);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.HEAP);
		}
	}

	@Override
	public ArrayList<Integer> returnSortedList() {
		this.sortAlgorithm();
		return _numToSort;
	}
}
