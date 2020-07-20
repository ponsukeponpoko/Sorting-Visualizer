package Sort;

import java.util.ArrayList;
import java.util.List;
import Sort.Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

/**
 * This quick sort algorithm uses Lomuto partition scheme.
 * 
 * @author kenya
 *
 */

public class LomutoQuickSort extends SortingAlgorithms {
	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private List<Swap> _swap;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;

	public LomutoQuickSort(int numOfBars, ArrayList<Integer> numToSort) throws InvalidInputException {
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
		this.errorCheck(AlgorithmMode.LOMUTO_QUICK, _numToSort);
		return _swap;
	}

	@Override
	public void sort() {
		this.sortHelper(0, _numOfBars - 1);
	}

	private void sortHelper(int low, int high) {
		if (low < high) {
			int pi = this.calculatePartition(low, high);
			this.sortHelper(low, pi - 1);
			this.sortHelper(pi + 1, high);
		}
	}

	private int calculatePartition(int low, int hi) {
		int pivot = _numToSort.get(hi);
		int i = low;
		for (int j = low; j < hi; j++) {
			if (_numToSort.get(j) < pivot) {
				int temp = _numToSort.get(i);
				_numToSort.set(i, _numToSort.get(j));
				_numToSort.set(j, temp);
				_swap.add(new Swap(i, _numToSort.get(i), Math.abs(i - j), true));
				i++;
			} else {
				_swap.add(new Swap(i, _numToSort.get(i), Math.abs(i - j), false));
			}
		}
		int temp = _numToSort.get(i);
		_numToSort.set(i, _numToSort.get(hi));
		_numToSort.set(hi, temp);
		_swap.add(new Swap(i, _numToSort.get(i), Math.abs(i - hi), true));
		return i;
	}

	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = LomutoQuickSort.makeCheckBox(menu, AlgorithmMode.LOMUTO_QUICK, "Lomuto Quick Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new lomutoQuickHandler());
	}

	public static class lomutoQuickHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.LOMUTO_QUICK);
			} else {
				_selectedMode.remove(AlgorithmMode.LOMUTO_QUICK);
			}
		}
	}

	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.LOMUTO_QUICK);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.LOMUTO_QUICK);
		}
	}

	@Override
	public ArrayList<Integer> returnSortedList() {
		this.sortAlgorithm();
		return _numToSort;
	}
}
