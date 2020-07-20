package Sort;

import java.util.ArrayList;
import java.util.List;
import Sort.Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

public class CocktailSort extends SortingAlgorithms {
	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private List<Swap> _swap;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;

	public CocktailSort(int numOfBars, ArrayList<Integer> numToSort) throws InvalidInputException {
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
		this.errorCheck(AlgorithmMode.COCKTAIL, _numToSort);
		return _swap;
	}

	@Override
	public void sort() {
		int start = 0;
		int end = _numOfBars;
		boolean swapped = true;
		while (swapped) {
			swapped = false;
			for (int i = start; i < end - 1; i++) {
				if (_numToSort.get(i) > _numToSort.get(i + 1)) {
					int temp = _numToSort.get(i);
					_numToSort.set(i, _numToSort.get(i + 1));
					_numToSort.set(i + 1, temp);
					_swap.add(new Swap(i, _numToSort.get(i), 1, true));
					swapped = true;
				} else {
					_swap.add(new Swap(i, _numToSort.get(i), 1, false));
				}
			}
			if (!swapped) {
				break;
			}
			swapped = false;
			end--;
			for (int j = end - 1; j >= start; j--) {
				if (_numToSort.get(j) > _numToSort.get(j + 1)) {
					int temp = _numToSort.get(j);
					_numToSort.set(j, _numToSort.get(j + 1));
					_numToSort.set(j + 1, temp);
					_swap.add(new Swap(j, _numToSort.get(j), 1, true));
					swapped = true;
				} else {
					_swap.add(new Swap(j, _numToSort.get(j), 1, false));
				}
			}
			start++;
		}
	}

	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = CocktailSort.makeCheckBox(menu, AlgorithmMode.COCKTAIL, "Cocktail Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new cocktailHandler());
	}

	public static class cocktailHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.COCKTAIL);
			} else {
				_selectedMode.remove(AlgorithmMode.COCKTAIL);
			}
		}
	}

	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.COCKTAIL);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.COCKTAIL);
		}
	}

	@Override
	public ArrayList<Integer> returnSortedList() {
		this.sortAlgorithm();
		return _numToSort;
	}
}
