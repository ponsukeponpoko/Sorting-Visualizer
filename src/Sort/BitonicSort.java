package Sort;

import java.util.ArrayList;
import java.util.List;
import Sort.Exceptions.InvalidInputException;
import Sort.Exceptions.NotPowerOfTwoException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

public class BitonicSort extends SortingAlgorithms {
	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private List<Swap> _swap;
	private Swap _lastSwap;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;

	public BitonicSort(int numOfBars, ArrayList<Integer> numToSort) throws InvalidInputException, NotPowerOfTwoException {
		if (numToSort == null) {
			throw new InvalidInputException("You can not sort array with no numbers");
		}
		System.out.println("sort");
		_numOfBars = numOfBars;
		if (this.checkIfPowerOfTwo(numOfBars) == false) {
			throw new NotPowerOfTwoException("In order to sort numbers using bitonic sort, th input array size must be a power of 2.");
		}
		_numToSort = numToSort;
		
		_swap = new ArrayList<>();
	}

	public List<Swap> sortAlgorithm() {
		if (_numOfBars == 0) {
			return null;
		}
		this.sort();
		this.errorCheck(AlgorithmMode.BITONIC, _numToSort);
		return _swap;
	}

	@Override
	public void sort() {
		this.recursiveSort(0, _numOfBars, true);
		_swap.add(_lastSwap);
	}

	private void recursiveSort(int low, int count, boolean bool) {
		int k;
		if (count > 1) {
			k = count / 2;
			this.recursiveSort(low, k, true);
			this.recursiveSort(low + k, k, false);
			this.merge(low, count, bool);
		}
	}

	private void merge(int low, int count, boolean bool) {
		int k;
		int i;
		if (count > 1) {
			k = count / 2;
			for (i = low; i < low + k; i++) {
				if (bool == (_numToSort.get(i) > _numToSort.get(i + k))) {
					_lastSwap = new Swap(i, _numToSort.get(i), k, true);
					_swap.add(_lastSwap);
					int temp = _numToSort.get(i);
					_numToSort.set(i, _numToSort.get(i + k));
					_numToSort.set(i + k, temp);
				}
			}
			this.merge(low, k, bool);
			this.merge(low + k, k, bool);
		}
	}

	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = BitonicSort.makeCheckBox(menu, AlgorithmMode.BITONIC, "Bitonic Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new bitonicHandler());
	}

	public static class bitonicHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.BITONIC);
			} else {
				_selectedMode.remove(AlgorithmMode.BITONIC);
			}
		}
	}

	public static class visualizeHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Sort.setMode(AlgorithmMode.BITONIC);
			PaneOrganizer.updateCurrentModeLabel(AlgorithmMode.BITONIC);
		}
	}

	@Override
	public ArrayList<Integer> returnSortedList() {
		this.sortAlgorithm();
		System.out.println("sort");
		return _numToSort;
	}
}
