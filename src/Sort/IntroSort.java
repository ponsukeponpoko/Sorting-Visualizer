package Sort;

import java.util.ArrayList;
import java.util.List;
import Sort.Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

public class IntroSort extends SortingAlgorithms {
	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private List<Swap> _swap;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;

	public IntroSort(int numOfBars, ArrayList<Integer> numToSort) throws InvalidInputException {
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
		this.errorCheck(AlgorithmMode.INTRO, _numToSort);
		return _swap;
	}

	@Override
	public void sort() {
		int depthLimit = (int) (2 * Math.floor(Math.log(_numOfBars) / Math.log(2)));
		this.recursiveSort(0, _numOfBars - 1, depthLimit);
	}

	private void recursiveSort(int start, int end, int depthLimit) {
		if (end - start > Constants.INTRO_SORT_DETERMINATION_VAL) {
			if (depthLimit == 0) {
				this.heapSort(start, end);
				return;
			}
			depthLimit--;
			int pivot = this.findPivot(start, start + ((end - start) / 2) + 1, end);
			int temp = _numToSort.get(pivot);
			_numToSort.set(pivot, _numToSort.get(end));
			_numToSort.set(end, temp);
			_swap.add(new Swap(pivot, _numToSort.get(pivot), end - pivot, true));
			int partition = this.calculatePartition(start, end);
			this.recursiveSort(start, partition - 1, depthLimit);
			this.recursiveSort(partition + 1, end, depthLimit);
		} else {
			this.insertionSort(start, end);
		}
	}

	private int findPivot(int a, int b, int c) {
		int max = Math.max(Math.max(_numToSort.get(a), _numToSort.get(b)), _numToSort.get(c));
		int min = Math.min(Math.min(_numToSort.get(a), _numToSort.get(b)), _numToSort.get(c));
		int median = max ^ min ^ _numToSort.get(a) ^ _numToSort.get(b) ^ _numToSort.get(c);
		if (median == _numToSort.get(a))
			return a;
		if (median == _numToSort.get(b))
			return b;
		return c;
	}

	private int calculatePartition(int start, int end) {
		int pivot = _numToSort.get(end);
		int i = start - 1;
		for (int j = start; j <= end - 1; j++) {
			if (_numToSort.get(j) <= pivot) {
				i++;
				int temp = _numToSort.get(i);
				_numToSort.set(i, _numToSort.get(j));
				_numToSort.set(j, temp);
				_swap.add(new Swap(i, _numToSort.get(i), j - i, true));
			}
		}
		int temp = _numToSort.get(i + 1);
		_numToSort.set(i + 1, _numToSort.get(end));
		_numToSort.set(end, temp);
		_swap.add(new Swap(i + 1, _numToSort.get(i + 1), end - i - 1, true));
		return i + 1;
	}

	private void heapSort(int start, int end) {
		int currSize = end - start;
		for (int i = currSize / 2; i >= 1; i--) {
			this.heap(i, currSize, start);
		}
		for (int i = currSize; i >= 1; i--) {
			int temp = _numToSort.get(start);
			_numToSort.set(start, _numToSort.get(start + i));
			_numToSort.set(start + i, temp);
			_swap.add(new Swap(start, _numToSort.get(0), i, true));
			this.heap(1, i, start);
		}
	}

	private void heap(int i, int currSize, int start) {
		int temp = _numToSort.get(start + i - 1);
		int child = 0;
		while (i <= currSize / 2) {
			child = 2 * i;

			if (child < currSize && _numToSort.get(start + child - 1) < _numToSort.get(start + child)) {
				child++;
			}
			if (temp >= _numToSort.get(start + child - 1)) {
				break;
			}
			_numToSort.set(start + i - 1, _numToSort.get(start + child - 1));
			_numToSort.set(start + child - 1, temp);
			_swap.add(new Swap(start + i - 1, _numToSort.get(start + i - 1), (start + child - 1) - (start + i - 1),
					true));
			temp = _numToSort.get(start + i - 1);
			i = child;
		}
		_numToSort.set(start + child - 1, temp);
		_swap.add(new Swap(start + i - 1, _numToSort.get(start + i - 1), (start + child - 1) - (start + i - 1), true));
	}

	private void insertionSort(int start, int end) {
		for (int i = start; i <= end; i++) {
			int temp = _numToSort.get(i);
			int tempInt = i;
			int j = i;
			while (j > start && _numToSort.get(j - 1) > temp) {
				_numToSort.set(j, _numToSort.get(j - 1));
				_numToSort.set(j - 1, temp);
				_swap.add(new Swap(j - 1, _numToSort.get(j - 1), 1, true));
				temp = _numToSort.get(j - 1);
				tempInt = j - 1;
				j--;
			}
			_numToSort.set(j, temp);
			_swap.add(new Swap(j, _numToSort.get(j), tempInt - j, true));
		}
	}

	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = IntroSort.makeCheckBox(menu, AlgorithmMode.INTRO, "Intro Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new introHandler());
	}

	public static class introHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.INTRO);
			} else {
				_selectedMode.remove(AlgorithmMode.INTRO);
			}
		}
	}

	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.INTRO);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.INTRO);
		}
	}

	@Override
	public ArrayList<Integer> returnSortedList() {
		this.sortAlgorithm();
		return _numToSort;
	}
}
