package Sort;

import java.util.ArrayList;
import java.util.List;
import Sort.Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

public class HybridQuickSort extends SortingAlgorithms {
	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private List<VisualChange> _visualChange;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;

	public HybridQuickSort(int numOfBars, ArrayList<Integer> numToSort) throws InvalidInputException {
		if (numToSort == null) {
			throw new InvalidInputException("You can not sort array with no numbers");
		}
		_numOfBars = numOfBars;
		_numToSort = numToSort;
		_visualChange = new ArrayList<>();
	}

	public List<VisualChange> sortAlgorithm() {
		if (_numOfBars == 0) {
			return null;
		}
		this.sort();
		this.errorCheck(AlgorithmMode.HYBRID_QUICK, _numToSort);
		return _visualChange;
	}

	@Override
	public void sort() {
		this.recursiveSort(0, _numOfBars - 1);
	}

	private void recursiveSort(int low, int high) {
		while (low < high) {
			if (high - low < 10) {
				this.insertionSort(low, high);
				break;
			} else {
				int partition = this.findPartition(low, high);
				if (partition - low < high - partition) {
					this.recursiveSort(low, partition - 1);
					low = partition + 1;
				} else {
					this.recursiveSort(partition + 1, high);
					high = partition - 1;
				}
			}
		}
	}

	private void insertionSort(int low, int high) {
		for (int i = low + 1; i <= high; i++) {
			int value = _numToSort.get(i);
			int j = i;
			while (j > low && _numToSort.get(j - 1) > value) {
				_visualChange.add(new VisualChange(j, _numToSort.get(j - 1), true));
				_numToSort.set(j, _numToSort.get(j - 1));
				j--;
			}
			_visualChange.add(new VisualChange(j, value, true));
			_numToSort.set(j, value);
		}
	}

	private int findPartition(int low, int high) {
		int pivot = _numToSort.get(high);
		int index = low;
		for (int i = low; i < high; i++) {
			if (_numToSort.get(i) <= pivot) {
				int temp = _numToSort.get(i);
				_visualChange.add(new VisualChange(i, _numToSort.get(index), true));
				_numToSort.set(i, _numToSort.get(index));
				_visualChange.add(new VisualChange(index, temp, true));
				_numToSort.set(index, temp);
				index++;
			}
		}
		int temp = _numToSort.get(high);
		_visualChange.add(new VisualChange(high, _numToSort.get(index), true));
		_numToSort.set(high, _numToSort.get(index));
		_visualChange.add(new VisualChange(index, temp, true));
		_numToSort.set(index, temp);
		return index;
	}

	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = HybridQuickSort.makeCheckBox(menu, AlgorithmMode.HYBRID_QUICK, "Hybrid Quick Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new hybridQuickHandler());
	}

	public static class hybridQuickHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.HYBRID_QUICK);
			} else {
				_selectedMode.remove(AlgorithmMode.HYBRID_QUICK);
			}
		}
	}

	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.HYBRID_QUICK);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.HYBRID_QUICK);
		}
	}

	@Override
	public ArrayList<Integer> returnSortedList() {
		this.sortAlgorithm();
		return _numToSort;
	}
}
