package Sort;

import java.util.ArrayList;
import java.util.List;
import Sort.Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

public class InsertionSort extends SortingAlgorithms {
	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private List<Swap> _swap;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;

	public InsertionSort(int numOfBars, ArrayList<Integer> numToSort) throws InvalidInputException {
		if (numToSort == null) {
			throw new InvalidInputException("You can not sort array with no numbers");
		}
		_numOfBars = numOfBars;
		_numToSort = numToSort;

	}

	public List<Swap> sortAlgorithm() {
		if (_numOfBars == 0) {
			return null;
		}
		this.sort();
		this.errorCheck(AlgorithmMode.INSERTION, _numToSort);
		return _swap;
	}

	@Override
	public void sort() {
		_swap = new ArrayList<>();
		for (int i = 1; i < _numOfBars; i++) {
			for (int j = i; j > 0; j--) {
				if (_numToSort.get(j) < _numToSort.get(j - 1)) {
					int tempNum = _numToSort.get(j);
					_numToSort.set(j, _numToSort.get(j - 1));
					_numToSort.set(j - 1, tempNum);
					_swap.add(new Swap(j - 1, _numToSort.get(j - 1), 1, true));
				} else {
					_swap.add(new Swap(j - 1, _numToSort.get(j - 1), 0, false));
					j = 1;
					break;
				}
			}
		}
	}

	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = InsertionSort.makeCheckBox(menu, AlgorithmMode.INSERTION, "Insertion Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new insertionHandler());
	}

	public static class insertionHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.INSERTION);
			} else {
				_selectedMode.remove(AlgorithmMode.INSERTION);
			}
		}
	}

	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.INSERTION);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.INSERTION);
		}
	}

	@Override
	public ArrayList<Integer> returnSortedList() {
		this.sortAlgorithm();
		return _numToSort;
	}
}
