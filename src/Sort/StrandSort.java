package Sort;

import java.util.ArrayList;
import java.util.List;
import Sort.Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

public class StrandSort extends SortingAlgorithms{
	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private List<VisualChange> _visualChange;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;
	private int _counter;
	private boolean _isVisualizing;
	private ArrayList<Integer> _sortedList;
	private VisualChange _lastChange;
	

	public StrandSort(int numOfBars, ArrayList<Integer> numToSort, boolean isVisualizing) throws InvalidInputException{
		if(numToSort == null) {
			throw new InvalidInputException("You can not sort array with no numbers");
		}
		_numOfBars = numOfBars;
		_numToSort = numToSort;
		_isVisualizing = isVisualizing;
		_sortedList = new ArrayList<>();
		_visualChange = new ArrayList<>();
	}

	public List<VisualChange> sortAlgorithm() {
		if (_numOfBars == 0) {
			return null;
		}
		this.sort();
		this.errorCheck(AlgorithmMode.STRAND, _numToSort);
		_visualChange.add(_lastChange);
		return _visualChange;

	}
	
	@Override
	public void sort() {
		_counter = 0;
//		if (_isVisualizing) {
//			for (int i = 0; i < _numToSort.size(); i++) {
//				_visualChange.add(new VisualChange(i, 0, true));
//			}
//		}
		this.recursiveSort();
		_numToSort = new ArrayList<>();
		for (int i = 0; i < _numOfBars; i ++) {
			_numToSort.add(_sortedList.get(i));
		}
	}
	
	public void recursiveSort() {
		if (_numToSort.isEmpty()) {
			return;
		}
		ArrayList<Integer> subList = new ArrayList<Integer>();
		subList.add(_numToSort.get(0));
		_numToSort.remove(0);
		int index = 0;
		for (int i = 0; i < _numToSort.size(); i ++) {
			if (_numToSort.get(i) > subList.get(index)) {
				subList.add(_numToSort.get(i));
				_numToSort.remove(i);
				i --;
				index ++;
			}
		}
		if (_counter == 0) {
			for (int i = 0; i < subList.size(); i ++) {
				_sortedList.add(subList.get(i));
				_lastChange = new VisualChange(i, subList.get(i), true);
				_visualChange.add(_lastChange);
				_counter ++;
			}
		}
		else {
			int endIndex = subList.size() - 1;
			int startIndex = 0;
			while(!subList.isEmpty()) {
				if (subList.get(endIndex) > _sortedList.get(startIndex)) {
					startIndex ++;
				}else {
					if (_isVisualizing) {
						for (int i = _sortedList.size(); i > startIndex; i--) {
							_visualChange.add(new VisualChange(i, _sortedList.get(i - 1), true));
						}
						_lastChange = new VisualChange(startIndex, subList.get(endIndex), true);
						_visualChange.add(_lastChange);
					}
					_sortedList.add(startIndex, subList.get(endIndex));
					subList.remove(endIndex);
					endIndex --;
					startIndex = 0;
				}
			}
		}
		this.recursiveSort();
	}

	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = StrandSort.makeCheckBox(menu, AlgorithmMode.STRAND, "Strand Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new strandHandler());
	}

	public static class strandHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.STRAND);
			} else {
				_selectedMode.remove(AlgorithmMode.STRAND);
			}
		}
	}
	
	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.STRAND);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.STRAND);
		}
	}
	
	@Override
	public ArrayList<Integer> returnSortedList(){
		this.sortAlgorithm();
		return _numToSort;
	}
}
