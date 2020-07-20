package Sort;

import java.util.ArrayList;
import java.util.List;
import Sort.Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

public class BucketSort extends SortingAlgorithms {
	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private boolean _isVisualizing;
	private List<VisualChange> _visualChange;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;

	public BucketSort(int numOfBars, ArrayList<Integer> numToSort, boolean isVisualizing) throws InvalidInputException {
		if (numToSort == null) {
			throw new InvalidInputException("You can not sort array with no numbers");
		}
		_numOfBars = numOfBars;
		_numToSort = numToSort;
		_isVisualizing = isVisualizing;
		_visualChange = new ArrayList<>();
	}

	public List<VisualChange> sortAlgorithm() {
		if (_numOfBars == 0) {
			return null;
		}
		this.sort();
		this.errorCheck(AlgorithmMode.BUCKET, _numToSort);
		return _visualChange;
	}

	@Override
	public void sort() {
		if (_isVisualizing) {
			for (int i = 0; i < _numOfBars; i++) {
				_visualChange.add(new VisualChange(i, _numToSort.get(i), false));
			}
		}
		ArrayList<ArrayList<Integer>> buckets = new ArrayList<ArrayList<Integer>>();
		int maxVal = _numToSort.get(0);
		for (int i = 0; i < _numOfBars; i++) {
			if (_numToSort.get(i) > maxVal) {
				maxVal = _numToSort.get(i);
			}
		}
		maxVal++;
		for (int i = 0; i < maxVal; i++) {
			buckets.add(new ArrayList<>());
		}
		for (int i = 0; i < _numOfBars; i++) {
			buckets.get(_numToSort.get(i)).add(_numToSort.get(i));
		}
		int counter = 0;
		for (int i = 0; i < buckets.size(); i++) {
			for (int j = 0; j < buckets.get(i).size(); j++) {
				_visualChange.add(new VisualChange(counter, buckets.get(i).get(j), true));
				_numToSort.set(counter, buckets.get(i).get(j));
				counter++;
			}
		}
		_visualChange.add(new VisualChange(counter - 1,
				buckets.get(buckets.size() - 1).get(buckets.get(buckets.size() - 1).size() - 1), true));
	}

	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = BucketSort.makeCheckBox(menu, AlgorithmMode.BUCKET, "Bucket Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new bucketHandler());
	}

	public static class bucketHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.BUCKET);
			} else {
				_selectedMode.remove(AlgorithmMode.BUCKET);
			}
		}
	}

	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.BUCKET);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.BUCKET);
		}
	}

	@Override
	public ArrayList<Integer> returnSortedList() {
		this.sortAlgorithm();
		return _numToSort;
	}
}
