package Sort;

import java.util.ArrayList;
import java.util.List;
import Sort.Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

public class OddEvenSort extends SortingAlgorithms {
	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private List<Swap> _swap;
	private Swap _lastSwap;;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;

	public OddEvenSort(int numOfBars, ArrayList<Integer> numToSort) throws InvalidInputException {
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
		this.errorCheck(AlgorithmMode.ODD_EVEN, _numToSort);
		return _swap;
	}

	@Override
	public void sort() {
		boolean isSorted = false;
		while (!isSorted) {
			isSorted = true;
			int temp = 0;
			for (int i = 1; i <= _numOfBars - 2; i += 2) {
				if (_numToSort.get(i) > _numToSort.get(i + 1)) {
					temp = _numToSort.get(i);
					_numToSort.set(i, _numToSort.get(i + 1));
					_numToSort.set(i + 1, temp);
					isSorted = false;
					_lastSwap = new Swap(i, _numToSort.get(i), 1, true);
					_swap.add(_lastSwap);
				}
			}
			for (int i = 0; i <= _numOfBars - 2; i = i + 2) {
				if (_numToSort.get(i) > _numToSort.get(i + 1)) {
					temp = _numToSort.get(i);
					_numToSort.set(i, _numToSort.get(i + 1));
					_numToSort.set(i + 1, temp);
					isSorted = false;
					_lastSwap = new Swap(i, _numToSort.get(i), 1, true);
					_swap.add(_lastSwap);
				}
			}
		}
		_swap.add(_lastSwap);
	}

	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = OddEvenSort.makeCheckBox(menu, AlgorithmMode.ODD_EVEN, "Odd-Even Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new oddEvenHandler());
	}

	public static class oddEvenHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.ODD_EVEN);
			} else {
				_selectedMode.remove(AlgorithmMode.ODD_EVEN);
			}
		}
	}

	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.ODD_EVEN);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.ODD_EVEN);
		}
	}

	@Override
	public ArrayList<Integer> returnSortedList() {
		this.sortAlgorithm();
		return _numToSort;
	}
}
