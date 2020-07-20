package Sort;

import java.util.ArrayList;
import java.util.List;
import Sort.Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

public class PancakeSort extends SortingAlgorithms {
	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private List<Swap> _swap;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;

	public PancakeSort(int numOfBars, ArrayList<Integer> numToSort) throws InvalidInputException {
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
		this.errorCheck(AlgorithmMode.PANCAKE, _numToSort);
		_swap.add(new Swap(0, _numToSort.get(0), 1, true));
		return _swap;
	}

	@Override
	public void sort() {
		_swap = new ArrayList<>();
		for (int curr = _numOfBars; curr > 0; curr--) {
			int currMax = 0;
			for (int i = 0; i < curr; i++) {
				if (_numToSort.get(i) > _numToSort.get(currMax)) {
					currMax = i;
				}
			}
			if (currMax != curr - 1) {
				this.flip(currMax);
				this.flip(curr - 1);
			}
		}
	}

	private void flip(int k) {
		int temp = 0;
		int start = 0;
		while (start <= k) {
			temp = _numToSort.get(start);
			_numToSort.set(start, _numToSort.get(k));
			_numToSort.set(k, temp);
			_swap.add(new Swap(start, _numToSort.get(start), k - start, true));
			start++;
			k--;
		}
	}

	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = PancakeSort.makeCheckBox(menu, AlgorithmMode.PANCAKE, "Pancake Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new pancakeHandler());
	}

	public static class pancakeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.PANCAKE);
			} else {
				_selectedMode.remove(AlgorithmMode.PANCAKE);
			}
		}
	}

	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.PANCAKE);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.PANCAKE);
		}
	}

	@Override
	public ArrayList<Integer> returnSortedList() {
		this.sortAlgorithm();
		return _numToSort;
	}
}
