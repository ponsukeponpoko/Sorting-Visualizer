package Sort;

import java.util.ArrayList;
import java.util.List;

import Sort.Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

public class ThreeWayQuickSort extends SortingAlgorithms{
	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private List<Swap> _swap;
	private int _i;
	private int _j;
	private int _left;
	private int _right;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;

	public ThreeWayQuickSort(int numOfBars, ArrayList<Integer> numToSort) throws InvalidInputException {
		if (numToSort == null) {
			throw new InvalidInputException("You can not sort array with no numbers");
		}
		_numOfBars = numOfBars;
		_numToSort = numToSort;
		_i = 0;
		_j = 0;
		_swap = new ArrayList<>();
	}

	public List<Swap> sortAlgorithm() {
		if (_numOfBars == 0) {
			return null;
		}
		this.sort();
		this.errorCheck(AlgorithmMode.THREEWAY_QUICK, _numToSort);
		return _swap;
	}
	
	@Override
	public void sort() {
		this.recursiveSort(0, _numOfBars - 1);
	}

	private void recursiveSort(int low, int high) {
		if (low >= high) {
			return;
		}
		this.calculatePartition(low, high);
		this.recursiveSort(low, _i);
		this.recursiveSort(_j, high);
	}

	private void calculatePartition(int low, int hi) {
		_left = low;
		_right = hi;
		if(_right - _left <= 1) {
			if(_numToSort.get(_right) < _numToSort.get(_left)) {
				int temp = _numToSort.get(_right);
				_numToSort.set(_right, _numToSort.get(_left));
				_numToSort.set(_left, temp);
				_swap.add(new Swap(_left, _numToSort.get(_left), Math.abs(_right - _left), true));
			}
			else {
				_swap.add(new Swap(_left, _numToSort.get(_left), Math.abs(_right - _left), false));
			}
			_i = _left;
			_j = _right;
			return;
		}
		int mid = _left;
		int pivot = _numToSort.get(_right);
		while(mid <= _right) {
			if (_numToSort.get(mid) < pivot) {
				int temp = _numToSort.get(_left);
				_numToSort.set(_left, _numToSort.get(mid));
				_numToSort.set(mid, temp);
				_swap.add(new Swap(mid, _numToSort.get(mid), -1 * Math.abs(mid - _left), true));
				_left ++;
				mid ++;
			}
			else if(_numToSort.get(mid) == pivot) {
				_swap.add(new Swap(mid, _numToSort.get(mid), Math.abs(_right - _left), false));
				mid ++;
			}
			else {
				int temp = _numToSort.get(mid);
				_numToSort.set(mid, _numToSort.get(_right));
				_numToSort.set(_right, temp);
				_swap.add(new Swap(mid, _numToSort.get(mid), Math.abs(_right - mid), true));
				_right --;
			}
			_i = _left - 1;
			_j = mid;
		}
	}
	
	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = ThreeWayQuickSort.makeCheckBox(menu, AlgorithmMode.THREEWAY_QUICK, "Threeway Quick Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new threeWayQuickHandler());
	}
	
	public static class threeWayQuickHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.THREEWAY_QUICK);
			} else {
				_selectedMode.remove(AlgorithmMode.THREEWAY_QUICK);
			}
		}
	}
	
	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.THREEWAY_QUICK);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.THREEWAY_QUICK);
		}
	}
	
	@Override
	public ArrayList<Integer> returnSortedList() {
		this.sortAlgorithm();
		return _numToSort;
	}
}
