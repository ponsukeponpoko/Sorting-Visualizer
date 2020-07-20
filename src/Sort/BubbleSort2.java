package Sort;

import java.util.ArrayList;
import java.util.List;
import Sort.Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

/**
 * BubbleSort2 is an optimized implementation of the bubbleSort algorithm.
 * 
 * @author kenya
 *
 */

public class BubbleSort2 extends SortingAlgorithms {
	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private List<Swap> _swap;
	private boolean _completed;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;

	public BubbleSort2(int numOfBars, ArrayList<Integer> numToSort) throws InvalidInputException {
		if (numToSort == null) {
			throw new InvalidInputException("You can not sort array with no numbers");
		}
		_numOfBars = numOfBars;
		_numToSort = numToSort;
		_completed = false;
	}

	public List<Swap> sortAlgorithm() {
		if (_numOfBars == 0) {
			return null;
		}
		this.sort();
		this.errorCheck(AlgorithmMode.BUBBLE2, _numToSort);
		return _swap;
	}

	@Override
	public void sort() {
		_swap = new ArrayList<>();
		while (!_completed) {
			_completed = true;
			for (int j = 0; j < _numOfBars - 1; j++) {
				if (_numToSort.get(j) > _numToSort.get(j + 1)) {
					int tempNum = _numToSort.get(j);
					_numToSort.set(j, _numToSort.get(j + 1));
					_numToSort.set(j + 1, tempNum);
					_swap.add(new Swap(j, _numToSort.get(j), 1, true));
					_completed = false;
				} else {
					_swap.add(new Swap(j, _numToSort.get(j), 0, false));
				}
			}
		}
	}

	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = BubbleSort2.makeCheckBox(menu, AlgorithmMode.BUBBLE2, "Bubble Sort2");
		_selectedMode = modes;
		_checkBox.setOnAction(new bubble2Handler());
	}

	public static class bubble2Handler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.BUBBLE2);
			} else {
				_selectedMode.remove(AlgorithmMode.BUBBLE2);
			}
		}
	}

	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.BUBBLE2);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.BUBBLE2);
		}
	}

	@Override
	public ArrayList<Integer> returnSortedList() {
		this.sortAlgorithm();
		return _numToSort;
	}
}
