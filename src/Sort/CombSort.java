package Sort;

import java.util.ArrayList;
import java.util.List;
import Sort.Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

public class CombSort extends SortingAlgorithms {
	private int _numOfBars;
	private int _gap;
	private ArrayList<Integer> _numToSort;
	private List<Swap> _swap;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;

	public CombSort(int numOfBars, ArrayList<Integer> numToSort) throws InvalidInputException {
		if (numToSort == null) {
			throw new InvalidInputException("You can not sort array with no numbers");
		}
		_numOfBars = numOfBars;
		_numToSort = numToSort;
		_gap = _numOfBars;
		_swap = new ArrayList<>();
	}

	public List<Swap> sortAlgorithm() {
		if (_numOfBars == 0) {
			return null;
		}
		this.sort();
		this.errorCheck(AlgorithmMode.COMB, _numToSort);
		return _swap;
	}

	@Override
	public void sort() {
		boolean swapped = true;
		_gap = _numOfBars;
		while (_gap != 1 || swapped) {
			_gap = this.getNextGap(_gap);
			swapped = false;
			for (int i = 0; i < _numOfBars - _gap; i++) {
				if (_numToSort.get(i) > _numToSort.get(i + _gap)) {
					int tempNum = _numToSort.get(i);
					_numToSort.set(i, _numToSort.get(i + _gap));
					_numToSort.set(i + _gap, tempNum);
					swapped = true;
					_swap.add(new Swap(i, _numToSort.get(i), _gap, true));
				} else {
					_swap.add(new Swap(i, _numToSort.get(i), 0, false));
				}
			}
		}
	}

	private int getNextGap(int gap) {
		gap = gap * 10 / 13;
		if (gap < 1) {
			return 1;
		}
		return gap;
	}

	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = CombSort.makeCheckBox(menu, AlgorithmMode.COMB, "Comb Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new combHandler());
	}

	public static class combHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.COMB);
			} else {
				_selectedMode.remove(AlgorithmMode.COMB);
			}
		}
	}

	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.COMB);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.COMB);
		}
	}

	@Override
	public ArrayList<Integer> returnSortedList() {
		this.sortAlgorithm();
		return _numToSort;
	}
}
