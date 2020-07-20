package Sort;

import java.util.ArrayList;
import java.util.List;
import Sort.Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

public class BogoSort extends SortingAlgorithms{
	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private List<Swap> _swap;
	private boolean _isSorted;
	private int _randomVal1;
	private int _randomVal2;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;

	public BogoSort(int numOfBars, ArrayList<Integer> numToSort) throws InvalidInputException {
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
		this.errorCheck(AlgorithmMode.BOGO, _numToSort);
		return _swap;
	}

	@Override
	public void sort() {
		int count = 0;
		while (!_isSorted) {
			for (int i = 0; i < _numOfBars; i++) {
				_randomVal1 = (int) (Math.random() * _numOfBars);
				_randomVal2 = (int) (Math.random() * _numOfBars);
				int temp = _numToSort.get(_randomVal1);
				_numToSort.set(_randomVal1, _numToSort.get(_randomVal2));
				_numToSort.set(_randomVal2, temp);
				_swap.add(new Swap(_randomVal1, _numToSort.get(_randomVal1), _randomVal2 - _randomVal1, true));
				count++;
				if (count % 1000000 == 0) {
					System.out.println(count);
				}
			}
			_isSorted = this.checkIfSorted(_numToSort);
		}
		_swap.add(new Swap(_randomVal1, _numToSort.get(_randomVal1), _randomVal2 - _randomVal1, true));
	}

	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = BogoSort.makeCheckBox(menu, AlgorithmMode.BOGO, "Bogo Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new bogoHandler());
//		Menu algorithmSelecter = menu;
//		_checkBox = new CheckBox("Bogo Sort");
//		_checkBox.setTextFill(Color.BLACK);
//		CustomMenuItem bogoSortSelecter = new CustomMenuItem(_checkBox);
//		bogoSortSelecter.setHideOnClick(false);
//		algorithmSelecter.getItems().add(bogoSortSelecter);
	}

//	public static void addToVisualizerList(Menu menu) {
//		MenuItem algorithmName = new MenuItem("Bogo Sort");
//		algorithmName.setOnAction(new bogoVisualizeHandler());
//		menu.getItems().add(algorithmName);
//	}

	public static class bogoHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.BOGO);
			} else {
				_selectedMode.remove(AlgorithmMode.BOGO);
			}
		}
	}

	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.BOGO);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.BOGO);
		}
	}

	@Override
	public ArrayList<Integer> returnSortedList() {
		this.sortAlgorithm();
		return _numToSort;
	}
}
