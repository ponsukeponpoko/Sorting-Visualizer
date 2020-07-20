package Sort;

import java.util.ArrayList;
import java.util.List;
import Sort.Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

public class CycleSort extends SortingAlgorithms {
	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private List<VisualChange> _visualChange;
	private VisualChange _currFinalChange;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;

	public CycleSort(int numOfBars, ArrayList<Integer> numToSort) throws InvalidInputException {
		if (numToSort == null) {
			throw new InvalidInputException("You can not sort array with no numbers");
		}
		_numOfBars = numOfBars;
		_numToSort = numToSort;
		_visualChange = new ArrayList<>();
	}

	public List<VisualChange> sortAlgorithm() {
		if (_numOfBars == 0) {
			return null;
		}
		this.sort();
		this.errorCheck(AlgorithmMode.CYCLE, _numToSort);
		return _visualChange;
	}

	@Override
	public void sort() {
		for (int start = 0; start <= _numOfBars - 2; start++) {
			int item = _numToSort.get(start);
			int pos = start;
			for (int i = start + 1; i < _numOfBars; i++) {
				if (_numToSort.get(i) < item) {
					pos++;
				}
			}
			if (pos == start) {
				continue;
			}
			while (item == _numToSort.get(pos)) {
				pos += 1;
			}
			if (pos != start) {
				int temp = item;
				item = _numToSort.get(pos);
				_currFinalChange = new VisualChange(pos, temp, true);
				_visualChange.add(_currFinalChange);
				_numToSort.set(pos, temp);
			}
			while (pos != start) {
				pos = start;
				for (int i = start + 1; i < _numOfBars; i++) {
					if (_numToSort.get(i) < item) {
						pos += 1;
					}
				}

				while (item == _numToSort.get(pos)) {
					pos += 1;
				}
				if (item != _numToSort.get(pos)) {
					int temp = item;
					item = _numToSort.get(pos);
					_currFinalChange = new VisualChange(pos, temp, true);
					_visualChange.add(_currFinalChange);
					_numToSort.set(pos, temp);
				}
			}
		}
		_visualChange.add(_currFinalChange);
	}

	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = CycleSort.makeCheckBox(menu, AlgorithmMode.CYCLE, "Cycle Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new cycleHandler());
	}

	public static class cycleHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.CYCLE);
			} else {
				_selectedMode.remove(AlgorithmMode.CYCLE);
			}
		}
	}

	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.CYCLE);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.CYCLE);
		}
	}

	@Override
	public ArrayList<Integer> returnSortedList() {
		this.sortAlgorithm();
		return _numToSort;
	}
}
