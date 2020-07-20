package Sort;

import java.util.ArrayList;
import java.util.List;
import Sort.Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

public class SelectionSort extends SortingAlgorithms {
	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private List<Swap> _swap;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;

	public SelectionSort(int numOfBars, ArrayList<Integer> numToSort) throws InvalidInputException {
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
		this.errorCheck(AlgorithmMode.SELECTION, _numToSort);
		return _swap;
	}

	@Override
	public void sort() {
		int min = 0;
		int minIndex = 0;
		int i = 0;
		for (i = 0; i < _numOfBars - 1; i++) {
			min = _numToSort.get(i);
			for (int j = 1 + i; j < _numOfBars; j++) {
				if (min > _numToSort.get(j)) {
					min = _numToSort.get(j);
					minIndex = j;
				}
			}
			if (_numToSort.get(i) > min) {
				int temp = _numToSort.get(i);
				_numToSort.set(i, _numToSort.get(minIndex));
				_numToSort.set(minIndex, temp);
				_swap.add(new Swap(minIndex, _numToSort.get(minIndex), i - minIndex, true));
			} else {
				_swap.add(new Swap(minIndex, _numToSort.get(minIndex), i - minIndex, false));
			}
		}
		_swap.add(new Swap(minIndex, _numToSort.get(minIndex), i - minIndex, true));
	}

	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = SelectionSort.makeCheckBox(menu, AlgorithmMode.SELECTION, "Selection Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new selectionHandler());
	}

	public static class selectionHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.SELECTION);
			} else {
				_selectedMode.remove(AlgorithmMode.SELECTION);
			}
		}
	}

	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.SELECTION);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.SELECTION);
		}
	}

	@Override
	public ArrayList<Integer> returnSortedList() {
		this.sortAlgorithm();
		return _numToSort;
	}
}
