package Sort;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import Sort.Exceptions.InvalidInputException;
import Sort.Exceptions.NullModeException;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SortGraph {
	private Pane _root;
	private AlgorithmMode _currMode;
	private ArrayList<Integer> _numToSort;
	private int _numOfBars;
	private double _averageTimeCounter;
	private int _numOfSample;
	private int _sizeOfArray;
	private int _sizeOfArrayInterval;
	private ArrayList<Double> _averageTimeArray;
	private ArrayList<ArrayList<Double>> _listOfAverageTimeArray;
	private ArrayList<AlgorithmMode> _modesToSort;
	private LineChart<Number, Number> _lineChart;
	private Label _label;
	private int _maxSizeArray;
	private int _unit;
	private double _sleepSortTimer;

	public SortGraph(Pane root, Label label) {
		_label = label;
		_root = root;
		_averageTimeCounter = 0;
		_numOfSample = 15;
		_sizeOfArrayInterval = 5;
		_maxSizeArray = 1000;
		_unit = 1000000;
		_root.setFocusTraversable(true);
		_averageTimeArray = new ArrayList<Double>();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setUpGraph(ArrayList<AlgorithmMode> modes) throws InvalidInputException, NullModeException {
		if (_root.getChildren().contains(_lineChart)) {
			_root.getChildren().remove(_lineChart);
		}
		_modesToSort = modes;
		if (_modesToSort.size() == 0) {
			_label.setText("No Mode Selected!");
			throw new NullModeException("No Mode Selected!");
		}
		else {
			String textToShow = new String("Selected Modes: ");
			for (AlgorithmMode mode: modes) {
				textToShow += mode.toString() + ", ";
			}
			textToShow = textToShow.substring(0, textToShow.length() - 2);
			_label.setText(textToShow);
		}
		_listOfAverageTimeArray = new ArrayList<>(modes.size());
		for (int i = 0; i < modes.size(); i++) {
			_listOfAverageTimeArray.add(new ArrayList<>());
		}
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Number of indices to sort");
		yAxis.setLabel("Average time to sort (mills)");
		if (_unit == 1000) {
			yAxis.setLabel("Average time to sort (nano sec)");
		}
		xAxis.setAutoRanging(false);
		xAxis.setUpperBound(_maxSizeArray - 1);
		_lineChart = new LineChart<Number, Number>(xAxis, yAxis);
		_lineChart.setMaxWidth(Constants.APP_WIDTH);
		_lineChart.setPrefWidth(Constants.APP_WIDTH);
		_lineChart.setMaxHeight(Constants.APP_HEIGHT - Constants.GRAPH_HEIGHT_ADJUSTER);
		_lineChart.setPrefHeight(Constants.APP_HEIGHT - Constants.GRAPH_HEIGHT_ADJUSTER);
		_lineChart.setAnimated(false);
		_lineChart.setCreateSymbols(true);
		_lineChart.setTitle("How Long Does it Take to Sort?");
		for (int i = 0; i < modes.size(); i++) {
			_currMode = modes.get(i);
			XYChart.Series series = new XYChart.Series();
			series.setName(_currMode.toString() + " Sort");
			for (_sizeOfArray = 0; _sizeOfArray < _maxSizeArray; _sizeOfArray += _sizeOfArrayInterval) {
				_numOfBars = _sizeOfArray;
				for (int j = 0; j < _numOfSample; j++) {
					this.makeArrayToSort();
					long startTime = System.nanoTime();
					this.checkAlgorithm();
					long endTime = System.nanoTime();
					long timeTook = endTime - startTime;
					double time = (double) timeTook / _unit;
					_averageTimeCounter += time;
				}
				double averageTime = _averageTimeCounter / _numOfSample;
				_averageTimeArray.add(averageTime);
				_listOfAverageTimeArray.get(i).add(averageTime);
				series.getData().add(new XYChart.Data(_sizeOfArray, averageTime));
				_averageTimeCounter = 0;
			}
			_lineChart.getData().add(series);
			_averageTimeArray.clear();
		}

		_root.getChildren().add(_lineChart);
	}

	public void checkAlgorithm() throws InvalidInputException {
		switch (_currMode) {
		case PANCAKE:
			new PancakeSort(_numOfBars, _numToSort).sortAlgorithm();
			break;
		case BUBBLE:
			new BubbleSort(_numOfBars, _numToSort).sortAlgorithm();
			break;
		case BUBBLE2:
			new BubbleSort2(_numOfBars, _numToSort).sortAlgorithm();
			break;
		case LOMUTO_QUICK:
			new LomutoQuickSort(_numOfBars, _numToSort).sortAlgorithm();
			break;
		case HOARE_QUICK:
			new HoareQuickSort(_numOfBars, _numToSort).sortAlgorithm();
			break;
		case MERGE:
			new MergeSort(_numOfBars, _numToSort, false).sortAlgorithm();
			break;
		case INSERTION:
			new InsertionSort(_numOfBars, _numToSort).sortAlgorithm();
			break;
		case COMB:
			new CombSort(_numOfBars, _numToSort).sortAlgorithm();
			break;
		case HEAP:
			new HeapSort(_numOfBars, _numToSort).sortAlgorithm();
			break;
		case COCKTAIL:
			new CocktailSort(_numOfBars, _numToSort).sortAlgorithm();
			break;
		case SHELL:
			new ShellSort(_numOfBars, _numToSort).sortAlgorithm();
			break;
		case SLOW:
			new SlowSort(_numOfBars, _numToSort).sortAlgorithm();
			break;
		case THREEWAY_QUICK:
			new ThreeWayQuickSort(_numOfBars, _numToSort).sortAlgorithm();
			break;
		case INTRO:
			new IntroSort(_numOfBars, _numToSort).sortAlgorithm();
			break;
		case BOGO:
			new BogoSort(_numOfBars, _numToSort).sortAlgorithm();
			break;
		case STOOGE:
			new StoogeSort(_numOfBars, _numToSort).sortAlgorithm();
			break;
		case BOZO:
			new BozoSort(_numOfBars, _numToSort).sortAlgorithm();
			break;
		case SELECTION:
			new SelectionSort(_numOfBars, _numToSort).sortAlgorithm();
			break;
		case SLEEP:
			_sleepSortTimer = new SleepSort(_numOfBars, _numToSort).sortAlgorithm();
			if (_unit == 1000) {
				_averageTimeCounter += _sleepSortTimer;
			} else {
				_averageTimeCounter += _sleepSortTimer * 1000;
			}
			break;
		case PIGEONHOLE:
			new PigeonholeSort(_numOfBars, _numToSort, false).sortAlgorithm();
			break;
		case COUNT:
			new CountSort(_numOfBars, _numToSort).sortAlgorithm();
			break;
		case LSD_RADIX:
			new LSDRadixSort(_numOfBars, _numToSort, false).sortAlgorithm();
			break;
		case AMERICAN_FLAG:
			new AmericanFlagSort(_numOfBars, _numToSort, false).sortAlgorithm();
			break;
		case BUCKET:
			new BucketSort(_numOfBars, _numToSort, false).sortAlgorithm();
			break;
		case TIM:
			new TimSort(_numOfBars, _numToSort, false).sortAlgorithm();
			break;
		case THREEWAY_MERGE:
			new ThreewayMergeSort(_numOfBars, _numToSort, false).sortAlgorithm();
			break;
		default:
			break;
		}
	}

	public void setArrayRange(int newSize) {
		_maxSizeArray = newSize + 1;
	}

	public void changeYAxisUnit(boolean currentlyMills) {
		if (currentlyMills) {
			_unit = 1000;
		} else {
			_unit = 1000000;
		}
	}

	public void makeArrayToSort() {
		_numToSort = new ArrayList<Integer>();
		for (int i = 0; i < _numOfBars; i++) {
			int currInteger = (int) (Math.random() * 1000);
			_numToSort.add(currInteger);
		}
	}

	public void save() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showSaveDialog(new Stage());
		if (file != null) {
			try {
				PrintWriter writer = new PrintWriter(file);
				int currentArraySize = 1;

				for (int i = 0; i < _modesToSort.size(); i++) {
					AlgorithmMode currMode = _modesToSort.get(i);
					writer.println("DATA FOR " + currMode.toString() + " Sort:");
					writer.println("");
					for (int j = 0; j < _listOfAverageTimeArray.get(i).size(); j++) {
						writer.println("Array size: " + currentArraySize + ", Average time to sort: "
								+ _listOfAverageTimeArray.get(i).get(j));
						currentArraySize += _sizeOfArrayInterval;
					}
					writer.println("");
					writer.println("");
					currentArraySize = 0;
				}
				writer.println("END OF DOCUMENT");
				writer.close();
			} catch (IOException ex) {
				Logger.getLogger(SortGraph.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

}
