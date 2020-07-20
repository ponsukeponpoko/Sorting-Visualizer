package Sort;

import java.util.ArrayList;
import java.util.List;
import Sort.Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

public class ShellSort extends SortingAlgorithms {
	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private List<Swap> _swap;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;

	public ShellSort(int numOfBars, ArrayList<Integer> numToSort) throws InvalidInputException {
		if (numToSort == null) {
			throw new InvalidInputException("You can not sort array with no numbers");
		}
		_numOfBars = numOfBars;
		_numToSort = numToSort;
		_swap = new ArrayList<>();
	}

	/**
	 * In order to visualize shell sort, I have reviewed the psuedocode of shell
	 * sort on:
	 * https://stackoverflow.com/questions/51034432/java-how-to-swap-2-array-elements-and-use-tostring-to-output-each-swap-of-shell.
	 * 
	 * @return
	 */

	public List<Swap> sortAlgorithm() {
		if (_numOfBars == 0) {
			return null;
		}
		this.sort();
		this.errorCheck(AlgorithmMode.SHELL, _numToSort);
		return _swap;
	}

	@Override
	public void sort() {
		int gap = _numOfBars / 2;
		do {
			boolean swapped = true;
			do {
				swapped = false;
				for (int i = 0; i < _numOfBars - gap; i++) {
					if (_numToSort.get(i) > _numToSort.get(i + gap)) {
						int temp = _numToSort.get(i);
						_numToSort.set(i, _numToSort.get(i + gap));
						_numToSort.set(i + gap, temp);
						_swap.add(new Swap(i, _numToSort.get(i), gap, true));
						swapped = true;
					} else {
						_swap.add(new Swap(i, _numToSort.get(i), gap, false));
					}
				}
			} while (swapped == true);
			gap = gap / 2;
		} while (gap > 0);
	}

	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = ShellSort.makeCheckBox(menu, AlgorithmMode.SHELL, "Shell Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new shellHandler());
	}

	public static class shellHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.SHELL);
			} else {
				_selectedMode.remove(AlgorithmMode.SHELL);
			}
		}
	}

	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.SHELL);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.SHELL);
		}
	}

	@Override
	public ArrayList<Integer> returnSortedList() {
		this.sortAlgorithm();
		return _numToSort;
	}
}
