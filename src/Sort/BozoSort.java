package Sort;

import java.util.ArrayList;
import java.util.List;
import Sort.Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

public class BozoSort extends SortingAlgorithms {
	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private List<Swap> _swap;
	private boolean _isSorted;
	private int _randomVal1;
	private int _randomVal2;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;

	public BozoSort(int numOfBars, ArrayList<Integer> numToSort) throws InvalidInputException {
		if (numToSort == null) {
			throw new InvalidInputException("You can not sort array with no numbers");
		}
		_numOfBars = numOfBars;
		_numToSort = numToSort;
		_isSorted = false;
		_swap = new ArrayList<>();
	}

	public List<Swap> sortAlgorithm() {
		if (_numOfBars == 0) {
			return null;
		}
		this.sort();
		this.errorCheck(AlgorithmMode.BOZO, _numToSort);
		return _swap;
	}

	@Override
	public void sort() {
		while (!_isSorted) {
			_randomVal1 = (int) (Math.random() * _numOfBars);
			_randomVal2 = (int) (Math.random() * _numOfBars);
			int temp = _numToSort.get(_randomVal1);
			_numToSort.set(_randomVal1, _numToSort.get(_randomVal2));
			_numToSort.set(_randomVal2, temp);
			_swap.add(new Swap(_randomVal1, _numToSort.get(_randomVal1), _randomVal2 - _randomVal1, true));
			_isSorted = this.checkIfSorted(_numToSort);
		}
		_swap.add(new Swap(_randomVal1, _numToSort.get(_randomVal1), _randomVal2 - _randomVal1, true));
	}

	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = BozoSort.makeCheckBox(menu, AlgorithmMode.BOZO, "Bozo Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new bozoHandler());
	}

	public static class bozoHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.BOZO);
			} else {
				_selectedMode.remove(AlgorithmMode.BOZO);
			}
		}
	}

	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.BOZO);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.BOZO);
		}
	}

	@Override
	public ArrayList<Integer> returnSortedList() {
		this.sortAlgorithm();
		return _numToSort;
	}
}
