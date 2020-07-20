package Sort;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PaneOrganizer {
	private BorderPane _root;
	private Label _label1;
	private Label _label2;
	private Slider _slider;
	private Slider _slider2;
	private static Sort _sort;
	private Stage _currentStage;
	private Menu _algorithmSelecter;
	private static MenuItem _currentMode;
	private static String _currentTextFieldString;

	public PaneOrganizer() {
		_root = new BorderPane();
		_label1 = new Label();
		_label2 = new Label();
		_label1.setFont(new Font("Times New Roman", 15));
		_label2.setFont(new Font("Comic Sans MS", 20));
		_label2.setStyle("-fx-background-color: red; -fx-text-fill: white;");
		// changes the label's font, color, and its size.
		Pane sortPane = new Pane(); // creates a new pane.
		_sort = new Sort(sortPane);
		_root.setCenter(sortPane);
		_currentTextFieldString = null;
		this.setUpMenu();
		this.setUpInfo();
	}

	public void setUpInfo() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Welcome!");
		alert.setHeaderText("Tutorial");
		alert.setContentText(
				"Thank you very much for opening my sorting visualizer. You can follow the following steps in order to use the sorting visualizer: "
				+ "\n1. Please first select the algorithm mode. "
				+ "\n2. Please then visit the 'Graph Option' menu to select the size of numbers to sort. "
				+ "Or, if you have custom numbers to sort, please visit 'Input custom number to sort' menu under 'file' and type the numbers to sort."
				+ "If you input your own number, the graph will automatically update. However, if you want to sort random numbers, after selecting the size of numbers to sort, please select 'Make Graph' menu."
				+ "\n3. Now, please utilize the following keys in order to view the sorting visualizer: \n"
				+ "S: Start the sorting visualizer \n"
				+ "P: Pause the sorting process \n"
				+ "C: Clear the graph \n"
				+ "Q: Quit application \n \n"
				+ "You can always visit this information panel by clicking 'Tutorial' menu under 'Help'. Please enjoy!");

		alert.showAndWait();
	}

	public Pane getRoot() {
		return _root;
	}

	public void setUpMenu() {
		VBox menubarBox = new VBox();
		_root.setTop(menubarBox);
		MenuBar menubar = new MenuBar();
		menubarBox.getChildren().add(menubar);
		Menu quitMenu = new Menu("Options");// quitMenu
		Menu loadMenu = new Menu("File");
		_algorithmSelecter = new Menu("Select algorithms");
		Menu graphOptionMenu = new Menu("Graph Option");
		Menu makeGraphMenu = new Menu("Make Graph!");
		Menu helpMenu  = new Menu("Help");
		menubar.getMenus().addAll(quitMenu, loadMenu, _algorithmSelecter, graphOptionMenu, makeGraphMenu, helpMenu);

		MenuItem getInput = new MenuItem("Input custom number to sort");
		getInput.setOnAction(new inputHandler());
		loadMenu.getItems().add(getInput);

		MenuItem quit = new MenuItem("Quit Sort Visualizer");// quitMenu
		quit.setOnAction(new quitHandler());// quitMenu
		MenuItem quitAll = new MenuItem("Quit Sort Visualizer and Analyzer");// quitMenu
		quitAll.setOnAction(new quitAllHandler());// quitMenu
		MenuItem newWindow = new MenuItem("Open Sort Analyzer");// quitMenu
		newWindow.setOnAction(new newWindowHandler());// quitMenu
		MenuItem reportBug = new MenuItem("Report Bug");
		reportBug.setOnAction(new bugReportHandler());
		quitMenu.getItems().addAll(newWindow, quit, quitAll, reportBug);// quitMenu

		MenuItem start = new MenuItem("Make / Update Graph!");
		makeGraphMenu.getItems().add(start);
		start.setOnAction(new graphHandler());
		
		MenuItem tutorial = new MenuItem("Tutorial");
		helpMenu.getItems().add(tutorial);
		tutorial.setOnAction(new tutorialHandler());

		_currentMode = new MenuItem("No Mode selected!");
		Label label = new Label();
		_currentMode.setGraphic(label);
		MenuItem rangeOfArrayTextInput = new MenuItem("Number of numbers to sort: 500");
		TextField text = new TextField();
		rangeOfArrayTextInput.setGraphic(text);
		MenuItem rangeOfArraySlider = new MenuItem("Number of numbers to sort: 500");
		_slider = new Slider(0, 1000, 500);
		rangeOfArraySlider.setGraphic(_slider);
		MenuItem sortingSpeedSlider = new MenuItem("Sorting Speed(ms): 100");
		_slider2 = new Slider(0.05, 2000, 100);
		_slider2.setValue(500);
		sortingSpeedSlider.setGraphic(_slider2);
		graphOptionMenu.getItems().addAll(_currentMode, rangeOfArraySlider, rangeOfArrayTextInput, sortingSpeedSlider);
		_slider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			// Referenced:
			// https://stackoverflow.com/questions/22780369/make-a-label-update-while-dragging-a-slider
			public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
				rangeOfArraySlider.textProperty()
						.setValue("Number of numbers to sort: " + String.valueOf(newValue.intValue()));
				rangeOfArrayTextInput.textProperty()
						.setValue("Number of numbers to sort: " + String.valueOf(newValue.intValue()));
				text.setText(String.valueOf(newValue.intValue()));
			}
		});
		text.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				rangeOfArraySlider.textProperty().setValue("Number of numbers to sort: " + newValue);
				rangeOfArrayTextInput.textProperty().setValue("Number of numbers to sort: " + newValue);
				int newval = Integer.parseInt(newValue);
				_slider.setValue(newval);
			}
		});
		_slider2.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
				sortingSpeedSlider.textProperty()
						.setValue("Sorting Speed(ms): " + String.valueOf(newValue.doubleValue()));
				_slider2.setValue(newValue.doubleValue());
				_sort.setUpTimelineSpeed(newValue.doubleValue());
			}
		});
		this.makeAlgorithmList();
	}

	public static void updateCurrentModeLabel(AlgorithmMode mode) {
		_currentMode.textProperty().setValue(mode.toString() + " mode selected");
	}

	public void makeAlgorithmList() {
		AmericanFlagSort.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.AMERICAN_FLAG, "American Flag Sort")
				.setOnAction(new AmericanFlagSort.visualizeHandler());
		BitonicSort.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.BITONIC, "Bitonic Sort")
				.setOnAction(new BitonicSort.visualizeHandler());
		BogoSort.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.BOGO, "Bogo Sort")
				.setOnAction(new BogoSort.visualizeHandler());
		BozoSort.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.BOZO, "Bozo Sort")
				.setOnAction(new BozoSort.visualizeHandler());
		BubbleSort.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.BUBBLE, "Bubble Sort")
				.setOnAction(new BubbleSort.visualizeHandler());
		BubbleSort2.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.BUBBLE2, "Bubble Sort 2")
				.setOnAction(new BubbleSort2.visualizeHandler());
		BucketSort.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.BUCKET, "Bucket Sort")
				.setOnAction(new BucketSort.visualizeHandler());
		CocktailSort.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.COCKTAIL, "Cocktail Sort")
				.setOnAction(new CocktailSort.visualizeHandler());
		CombSort.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.COMB, "Comb Sort")
				.setOnAction(new CombSort.visualizeHandler());
		CountSort.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.COUNT, "Count Sort")
				.setOnAction(new CountSort.visualizeHandler());
		CycleSort.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.CYCLE, "Cycle Sort")
				.setOnAction(new CycleSort.visualizeHandler());
		HeapSort.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.HEAP, "Heap Sort")
				.setOnAction(new HeapSort.visualizeHandler());
		HoareQuickSort.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.HOARE_QUICK, "Hoare Quick Sort")
				.setOnAction(new HoareQuickSort.visualizeHandler());
		HybridQuickSort.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.HYBRID_QUICK, "Hybrid Quick Sort")
				.setOnAction(new HybridQuickSort.visualizeHandler());
		InsertionSort.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.INSERTION, "Insertion Sort")
				.setOnAction(new InsertionSort.visualizeHandler());
		IntroSort.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.INTRO, "Intro Sort")
				.setOnAction(new IntroSort.visualizeHandler());
		LomutoQuickSort.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.LOMUTO_QUICK, "Lomuto Quick Sort")
				.setOnAction(new LomutoQuickSort.visualizeHandler());
		LSDRadixSort.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.LSD_RADIX, "LSD Radix Sort")
				.setOnAction(new LSDRadixSort.visualizeHandler());
		MergeSort.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.MERGE, "Merge Sort")
				.setOnAction(new MergeSort.visualizeHandler());
		OddEvenSort.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.ODD_EVEN, "Odd-even Sort")
				.setOnAction(new OddEvenSort.visualizeHandler());
		OddEvenMergeSort
				.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.BATCHER_ODD_EVEN_MERGE, "Odd-even Merge Sort")
				.setOnAction(new OddEvenMergeSort.visualizeHandler());
		PancakeSort.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.PANCAKE, "Pancake Sort")
				.setOnAction(new PancakeSort.visualizeHandler());
		PigeonholeSort.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.PIGEONHOLE, "Pigeonhole Sort")
				.setOnAction(new PigeonholeSort.visualizeHandler());
		SelectionSort.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.SELECTION, "Selection Sort")
				.setOnAction(new SelectionSort.visualizeHandler());
		ShellSort.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.SHELL, "Shell Sort")
				.setOnAction(new ShellSort.visualizeHandler());
		// no Sleep Sort
		SlowSort.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.SLOW, "Slow Sort")
				.setOnAction(new SlowSort.visualizeHandler());
		StoogeSort.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.STOOGE, "Stooge Sort")
				.setOnAction(new StoogeSort.visualizeHandler());
		StrandSort.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.STRAND, "Strand Sort")
				.setOnAction(new StrandSort.visualizeHandler());
		ThreewayMergeSort.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.THREEWAY_MERGE, "Threeway Merge Sort")
				.setOnAction(new ThreewayMergeSort.visualizeHandler());
		ThreeWayQuickSort.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.THREEWAY_QUICK, "Threeway Quick Sort")
				.setOnAction(new ThreeWayQuickSort.visualizeHandler());
		TimSort.makeVisualizerMenu(_algorithmSelecter, AlgorithmMode.TIM, "Tim Sort")
				.setOnAction(new TimSort.visualizeHandler());

	}

	public void setStage(Stage stage) {
		_currentStage = stage;
	}

	public static void setTextAreaText(String str) {
		_currentTextFieldString = str;
	}

	public static void sortUsingInputNum(ArrayList<Integer> numToSort) {
		_sort.clearGraph();
		_sort.makeGraph(numToSort.size(), true, numToSort);
	}

	private class graphHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			_label1.setText(String.valueOf((int) _slider.getValue()));
			_sort.clearGraph();
			_sort.makeGraph((int) _slider.getValue(), false, null);
		}
	}

	public class quitHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			_currentStage.close();
		}
	}

	public class quitAllHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			Alert quitAlert = new Alert(AlertType.CONFIRMATION);
			quitAlert.setTitle("QUIT?");
			quitAlert.setContentText("Are you sure you want to quit?");
			quitAlert.setHeaderText(null);
			quitAlert.showAndWait();
			if (quitAlert.getResult() == ButtonType.OK) {
				Alert quitAlert2 = new Alert(AlertType.INFORMATION);
				quitAlert2.setTitle("THANK YOU");
				quitAlert2.setContentText("THANK YOU FOR VIEWING MY SORTING VISUALIZER!\n");
				quitAlert2.setHeaderText(null);
				quitAlert2.showAndWait();
				;
				System.exit(0);
			}
		}
	}

	public class newWindowHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			PaneOrganizer2 organizer = new PaneOrganizer2();
			Scene secondScene = new Scene(organizer.getRoot(), Constants.APP_WIDTH, Constants.APP_HEIGHT);
			// New window (Stage)
			Stage newWindow = new Stage();
			newWindow.setTitle("Sort Analyzer");
			newWindow.setScene(secondScene);
			newWindow.setX(200);
			newWindow.setY(100);
			newWindow.show();
			organizer.setStage(newWindow);
		}
	}

	public class inputHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			TextInputOrganizer organizer = new TextInputOrganizer();
			Scene scene = new Scene(organizer.getRoot(), Constants.INPUT_WINDOW_WIDTH, Constants.INPUT_WINDOW_HEIGHT);
			Stage newStage = new Stage();
			newStage.setScene(scene);
			newStage.show();
			organizer.setStage(newStage);
			if (_currentTextFieldString != null) {
				organizer.setTextFieldText(_currentTextFieldString);
			}
		}
	}

	public class bugReportHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			BugReportOrganizer organizer = new BugReportOrganizer();
			Scene scene = new Scene(organizer.getRoot(), Constants.INPUT_WINDOW_WIDTH, Constants.INPUT_WINDOW_HEIGHT);
			Stage newStage = new Stage();
			newStage.setScene(scene);
			newStage.show();
			organizer.setStage(newStage);
		}
	}
	
	public class tutorialHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			PaneOrganizer.this.setUpInfo();
		}
	}
}