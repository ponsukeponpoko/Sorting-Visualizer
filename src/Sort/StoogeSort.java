package Sort;

import java.util.ArrayList;
import java.util.List;
import Sort.Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

public class StoogeSort extends SortingAlgorithms {
	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private List<Swap> _swap;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;

	public StoogeSort(int numOfBars, ArrayList<Integer> numToSort) throws InvalidInputException {
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
		this.errorCheck(AlgorithmMode.STOOGE, _numToSort);
		return _swap;
	}

	@Override
	public void sort() {
		this.recursiveSort(0, _numOfBars - 1);
	}

	private void recursiveSort(int low, int high) {
		if (low >= high) {
			return;
		}
		if (_numToSort.get(low) > _numToSort.get(high)) {
			_swap.add(new Swap(low, _numToSort.get(low), high - low, true));
			int temp = _numToSort.get(low);
			_numToSort.set(low, _numToSort.get(high));
			_numToSort.set(high, temp);
		} else {
			_swap.add(new Swap(low, _numToSort.get(low), high - low, false));
		}
		if (high - low + 1 > 2) {
			int temp = (high - low + 1) / 3;
			this.recursiveSort(low, high - temp);
			this.recursiveSort(low + temp, high);
			this.recursiveSort(low, high - temp);
		}
	}

	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = StoogeSort.makeCheckBox(menu, AlgorithmMode.STOOGE, "Stooge Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new stoogeHandler());
	}

	public static class stoogeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.STOOGE);
			} else {
				_selectedMode.remove(AlgorithmMode.STOOGE);
			}
		}
	}

	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.STOOGE);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.STOOGE);
		}
	}

	@Override
	public ArrayList<Integer> returnSortedList() {
		this.sortAlgorithm();
		return _numToSort;
	}
}
