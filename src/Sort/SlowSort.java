package Sort;

import java.util.ArrayList;
import java.util.List;
import Sort.Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

public class SlowSort extends SortingAlgorithms {
	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private List<Swap> _swap;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;

	public SlowSort(int numOfBars, ArrayList<Integer> numToSort) throws InvalidInputException {
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
		_swap.add(new Swap(0, _numToSort.get(0), 1, true));
		this.errorCheck(AlgorithmMode.SLOW, _numToSort);
		return _swap;
	}

	@Override
	public void sort() {
		this.recursiveSort(0, _numOfBars - 1);
	}

	private void recursiveSort(int i, int j) {
		if (i >= j) {
			return;
		}
		int mid = (i + j) / 2;
		this.recursiveSort(i, mid);
		this.recursiveSort(mid + 1, j);
		if (_numToSort.get(j) < _numToSort.get(mid)) {
			int temp = _numToSort.get(j);
			_numToSort.set(j, _numToSort.get(mid));
			_numToSort.set(mid, temp);
			_swap.add(new Swap(mid, _numToSort.get(mid), Math.abs(j - mid), true));
		} else {
			_swap.add(new Swap(mid, _numToSort.get(j), Math.abs(j - mid), false));
		}
		this.recursiveSort(i, j - 1);
	}

	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = SlowSort.makeCheckBox(menu, AlgorithmMode.SLOW, "Slow Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new slowHandler());
	}

	public static class slowHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.SLOW);
			} else {
				_selectedMode.remove(AlgorithmMode.SLOW);
			}
		}
	}

	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.SLOW);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.SLOW);
		}
	}

	@Override
	public ArrayList<Integer> returnSortedList() {
		this.sortAlgorithm();
		return _numToSort;
	}
}
