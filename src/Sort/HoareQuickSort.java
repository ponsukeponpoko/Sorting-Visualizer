package Sort;

import java.util.ArrayList;
import java.util.List;
import Sort.Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

/**
 * This quick sort algorithm uses Hoare partition scheme.
 * 
 * @author kenya
 *
 */

public class HoareQuickSort extends SortingAlgorithms {
	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private List<Swap> _swap;
	private int _i;
	private int _j;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;

	public HoareQuickSort(int numOfBars, ArrayList<Integer> numToSort) throws InvalidInputException {
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
		this.errorCheck(AlgorithmMode.HOARE_QUICK, _numToSort);
		_swap.add(new Swap(_i, _numToSort.get(_i), Math.abs(_i - _j), true));
		return _swap;
	}

	@Override
	public void sort() {
		this.sortHelper(0, _numOfBars - 1);
	}

	public void sortHelper(int low, int high) {
		if (low < high) {
			int pi = this.calculatePartition(low, high);
			this.sortHelper(low, pi);
			this.sortHelper(pi + 1, high);
		}
	}

	private int calculatePartition(int low, int hi) {
		int pivot = _numToSort.get((hi + low) / 2);
		_i = low - 1;
		_j = hi + 1;
		while (true) {
			do {
				_i++;
			} while (_numToSort.get(_i) < pivot);
			do {
				_j--;
			} while (_numToSort.get(_j) > pivot);
			if (_i >= _j) {
				return _j;
			}
			int temp = _numToSort.get(_i);
			_numToSort.set(_i, _numToSort.get(_j));
			_numToSort.set(_j, temp);
			_swap.add(new Swap(_i, _numToSort.get(_i), Math.abs(_i - _j), true));
		}
	}

	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = HoareQuickSort.makeCheckBox(menu, AlgorithmMode.HOARE_QUICK, "Hoare Quick Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new hoareQuickHandler());
	}

	public static class hoareQuickHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.HOARE_QUICK);
			} else {
				_selectedMode.remove(AlgorithmMode.HOARE_QUICK);
			}
		}
	}

	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.HOARE_QUICK);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.HOARE_QUICK);
		}
	}

	@Override
	public ArrayList<Integer> returnSortedList() {
		this.sortAlgorithm();
		return _numToSort;
	}
}
