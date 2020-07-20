package Sort;

import java.util.ArrayList;
import java.util.List;
import Sort.Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

public class BubbleSort extends SortingAlgorithms {

	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private List<Swap> _swap;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;

	public BubbleSort(int numOfBars, ArrayList<Integer> numToSort) throws InvalidInputException {
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
		this.errorCheck(AlgorithmMode.BUBBLE, _numToSort);
		return _swap;
	}

	@Override
	public void sort() {
		_swap = new ArrayList<>();
		for (int i = 0; i < _numOfBars; i++) {
			for (int j = 0; j < _numOfBars - 1; j++) {
				if (_numToSort.get(j) > _numToSort.get(j + 1)) {
					int tempNum = _numToSort.get(j);
					_numToSort.set(j, _numToSort.get(j + 1));
					_numToSort.set(j + 1, tempNum);
					_swap.add(new Swap(j, _numToSort.get(j), 1, true));
				} else {
					_swap.add(new Swap(j, _numToSort.get(j), 0, false));
				}
			}
		}
	}

	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = BubbleSort.makeCheckBox(menu, AlgorithmMode.BUBBLE, "Bubble Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new bubbleHandler());
	}

	public static class bubbleHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.BUBBLE);
			} else {
				_selectedMode.remove(AlgorithmMode.BUBBLE);
			}
		}
	}

	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.BUBBLE);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.BUBBLE);
		}
	}

	@Override
	public ArrayList<Integer> returnSortedList() {
		this.sortAlgorithm();
		return _numToSort;
	}
}