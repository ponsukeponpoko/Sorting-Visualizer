package Sort;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import Sort.Exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;

public class SleepSort extends SortingAlgorithms{
	private int _numOfBars;
	private ArrayList<Integer> _numToSort;
	private double _timeSlept;
	private static CheckBox _checkBox;
	private static ArrayList<AlgorithmMode> _selectedMode;
	private ArrayList<Integer> _sortedArray;

	public SleepSort(int numOfBars, ArrayList<Integer> numToSort) throws InvalidInputException {
		if (numToSort == null) {
			throw new InvalidInputException("You can not sort array with no numbers");
		}
		_numOfBars = numOfBars;
		_numToSort = numToSort;
		_timeSlept = 0;
		_sortedArray = new ArrayList<>();
	}
	
	/*
	 * Algorithm taken from: https://www.w3resource.com/java-exercises/sorting/java-sorting-algorithm-exercise-17.php
	 */

	public double sortAlgorithm() {
		if (_numOfBars == 0) {
			return 0;
		}
		this.sort();
		for (final int num : _numToSort) {
			_timeSlept += (double) (num * 1000);
		}
		this.errorCheck(AlgorithmMode.SLEEP, _sortedArray);
		return _timeSlept;
	}
	
	@Override
	public void sort() {
		final CountDownLatch doneSignal = new CountDownLatch(_numOfBars);
		for (final int num : _numToSort) {
			new Thread(new Runnable() {
				public void run() {
					doneSignal.countDown();
					try {
						doneSignal.await();
						Thread.sleep(num * 999);
						_sortedArray.add(num);
						System.out.println(num);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start(); //purposely  not starting thread for obvious reason
		}
	}
	
	public static void addToCheckList(Menu menu, ArrayList<AlgorithmMode> modes) {
		_checkBox = SleepSort.makeCheckBox(menu, AlgorithmMode.SLEEP, "Sleep Sort");
		_selectedMode = modes;
		_checkBox.setOnAction(new sleepHandler());
	}
	
	public static class sleepHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (_checkBox.isSelected()) {
				_selectedMode.add(AlgorithmMode.SLEEP);
			} else {
				_selectedMode.remove(AlgorithmMode.SLEEP);
			}
		}
	}
	
	@Override
	public ArrayList<Integer> returnSortedList() {
		this.sortAlgorithm();
		return _sortedArray;
	}
}
