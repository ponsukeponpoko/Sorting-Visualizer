package Sort;

import java.util.ArrayList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;

public abstract class SortingAlgorithms{
	private static CheckBox _checkBox;

	public SortingAlgorithms() {

	}

	public abstract void sort();

	public abstract ArrayList<Integer> returnSortedList();
	
	public static MenuItem makeVisualizerMenu(Menu menu, AlgorithmMode mode, String checkBoxName) {
		MenuItem algorithmName = new MenuItem(checkBoxName);
		menu.getItems().add(algorithmName);
		return algorithmName;
	}

	public static CheckBox makeCheckBox(Menu menu, AlgorithmMode mode, String checkBoxName) {
		Menu algorithmSelecter = menu;
		_checkBox = new CheckBox(checkBoxName);
		_checkBox.setTextFill(Color.BLACK);
		CustomMenuItem sortSelecter = new CustomMenuItem(_checkBox);
		sortSelecter.setHideOnClick(false);
		algorithmSelecter.getItems().add(sortSelecter);
		return _checkBox;
	}

	public boolean checkIfSorted(ArrayList<Integer> numToSort) {
		int currMax = 0;
		for (int i = 0; i < numToSort.size(); i++) {
			if (currMax > numToSort.get(i)) {
				return false;
			} else {
				currMax = numToSort.get(i);
			}
		}
		return true;
	}

	public void errorCheck(AlgorithmMode mode, ArrayList<Integer> numToSort) {
		int currMax = 0;
		for (int i = 0; i < numToSort.size(); i++) {
			if (currMax > numToSort.get(i)) {
				System.out.println("ERROR in " + mode.toString());
			} else {
				currMax = numToSort.get(i);
			}
		}
	}
	
	public boolean checkIfPowerOfTwo(int num) {
		if (num == 0) {
			return false;
		}
		int numOfBars = num;
		while (numOfBars != 1) {
			numOfBars /= 2;
			if(numOfBars%2 != 0 && numOfBars != 1) {
				return false;
			}
		}
		return true;
	}
	
}
