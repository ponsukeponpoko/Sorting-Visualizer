package Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Sort.Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

public class AmericanFlagSort extends SortingAlgorithms {
	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private int _radix;
	private boolean _isVisualizing;
	private List<VisualChange> _visualChange;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;
	private VisualChange _finalChange;

	public AmericanFlagSort(int numOfBars, ArrayList<Integer> numToSort, boolean isVisualizing)
			throws InvalidInputException {
		if (numToSort == null) {
			throw new InvalidInputException("You can not sort array with no numbers");
		}
		_numOfBars = numOfBars;
		_numToSort = numToSort;
		_isVisualizing = isVisualizing;
		_visualChange = new ArrayList<>();
	}

	public List<VisualChange> sortAlgorithm() {
		_radix = 10;
		if (_numOfBars == 0) {
			return null;
		}
		this.sort();
		_visualChange.add(_finalChange);
		this.errorCheck(AlgorithmMode.AMERICAN_FLAG, _numToSort);
		return _visualChange;

	}

	@Override
	public void sort() {
		int maxVal = _numToSort.get(0);
		for (int i = 0; i < _numOfBars; i++) {
			if (_numToSort.get(i) > maxVal) {
				maxVal = _numToSort.get(i);
			}
		}
		int maxDigit = (int) Math.log10(maxVal) + 1;
		int max = 1;
		for (int i = 0; i < maxDigit - 1; i++) {
			max *= 10;
		}
		this.americanFlagSort(0, _numOfBars, max);
	}

	private void americanFlagSort(int start, int length, int divisor) {
		if (_isVisualizing) {
			for (int i = start; i < length; i++) {
				_visualChange.add(new VisualChange(i, _numToSort.get(i), false));
			}
		}
		int[] offset = new int[_radix];
		int[] count = new int[_radix];
		Arrays.fill(offset, 0);
		Arrays.fill(count, 0);
		int digit = 0;
		for (int i = start; i < length; i++) {
			int temp = _numToSort.get(i);
			int currDigit = (temp / divisor) % _radix;
			count[currDigit]++;
		}
		offset[0] = start;
		for (int i = 1; i < _radix; i++) {
			offset[i] = count[i - 1] + offset[i - 1];
		}
		for (int b = 0; b < _radix; b++) {
			while (count[b] > 0) {
				int origin = offset[b];
				int from = origin;
				int tempNum = _numToSort.get(from);
				_visualChange.add(new VisualChange(from, _numToSort.get(from) - 1, true));
				_numToSort.set(from, _numToSort.get(from) - 1);
				do {
					digit = (tempNum / divisor) % _radix;
					int to = offset[digit]++;
					count[digit]--;
					int temp = _numToSort.get(to);
					_finalChange = new VisualChange(to, tempNum, true);
					_visualChange.add(_finalChange);
					_numToSort.set(to, tempNum);
					tempNum = temp;
					from = to;
				} while (from != origin);
			}
		}
		if (divisor > 1) {
			for (int i = 0; i < _radix; i++) {
				int begin;
				if (i > 0) {
					begin = offset[i - 1];
				} else {
					begin = start;
				}
				int end = offset[i];
				if (end - begin > 1) {
					this.americanFlagSort(begin, end, divisor / 10);
				}
			}
		}
	}

	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = BogoSort.makeCheckBox(menu, AlgorithmMode.AMERICAN_FLAG, "American Flag Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new americanFlagHandler());
	}

	public static class americanFlagHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.AMERICAN_FLAG);
			} else {
				_selectedMode.remove(AlgorithmMode.AMERICAN_FLAG);
			}
		}
	}

	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.AMERICAN_FLAG);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.AMERICAN_FLAG);
		}
	}

	@Override
	public ArrayList<Integer> returnSortedList() {
		this.sortAlgorithm();
		return _numToSort;
	}
}
