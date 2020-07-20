package Sort;

import java.util.ArrayList;

import Sort.Exceptions.InvalidInputException;
import Sort.Exceptions.NullModeException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PaneOrganizer2 {
	private BorderPane _root;
	private Label _label1;
	private SortGraph _graph;
	private ArrayList<AlgorithmMode> _selectedMode;
	private Menu _algorithmSelecter;
	private boolean _currentlyMills;
	private MenuItem _changeMillsAndNanoSec;
	private Stage _currentStage;

	public PaneOrganizer2() {
		_root = new BorderPane();
		_label1 = new Label();
		// changes the label's font, color, and its size.
		Pane sortPane = new Pane(); // creates a new pane.
		_graph = new SortGraph(sortPane, _label1);
		_root.setCenter(sortPane);
		_root.setCenter(sortPane);
		_selectedMode = new ArrayList<AlgorithmMode>();
		_currentlyMills = true;
		this.setUpMenu();
		this.setUpLabel();
	}

	public Pane getRoot() {
		return _root;
	}

	public void setUpLabel() {
		HBox box = new HBox();
		_root.setBottom(box);
		box.getChildren().add(_label1);
		_label1.setText("No Mode Selected");
	}

	public void setUpMenu() {
		VBox menubarBox = new VBox();
		_root.setTop(menubarBox);
		MenuBar menubar = new MenuBar();
		menubarBox.getChildren().add(menubar);
		Menu quitMenu = new Menu("Options");
		Menu saveMenu = new Menu("File");
		Menu makeGraphMenu = new Menu("Make Graph!");
		_algorithmSelecter = new Menu("Select algorithms");
		Menu graphOptionMenu = new Menu("Graph Option");
		menubar.getMenus().addAll(quitMenu, saveMenu, _algorithmSelecter, graphOptionMenu, makeGraphMenu);
		MenuItem quit = new MenuItem("Quit sort analyzer");
		quit.setOnAction(new quitHandler());
		MenuItem quitAll = new MenuItem("Quit sort visualizer and analyzer");
		quitAll.setOnAction(new quitAllHandler());
		quitMenu.getItems().addAll(quit, quitAll);
		MenuItem save = new MenuItem("Save");
		saveMenu.getItems().add(save);
		save.setOnAction(new SaveHandler());
		MenuItem start = new MenuItem("Make / Update Graph!");
		makeGraphMenu.getItems().add(start);
		start.setOnAction(new StartGraphHandler());
		MenuItem rangeOfArrayTextInput = new MenuItem("Range of array to sort: 1000");
		TextField text = new TextField();
		rangeOfArrayTextInput.setGraphic(text);
		MenuItem rangeOfArraySlider = new MenuItem("Range of array to sort: 1000");
		Slider slider = new Slider(0, 2000, 1000);
		rangeOfArraySlider.setGraphic(slider);
		_changeMillsAndNanoSec = new MenuItem("Change time unit to nano sec");
		_changeMillsAndNanoSec.setOnAction(new convertUnitHandler());
		graphOptionMenu.getItems().addAll(rangeOfArraySlider, rangeOfArrayTextInput, _changeMillsAndNanoSec);
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			// Referenced:
			// https://stackoverflow.com/questions/22780369/make-a-label-update-while-dragging-a-slider
			public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
				rangeOfArraySlider.textProperty().setValue("Range of array to sort: " + String.valueOf(newValue.intValue()));
				rangeOfArrayTextInput.textProperty().setValue("Range of array to sort: " + String.valueOf(newValue.intValue()));
				_graph.setArrayRange(newValue.intValue());
				text.setText(String.valueOf(newValue.intValue()));
			}
		});
		text.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		    	rangeOfArraySlider.textProperty().setValue("Range of array to sort: " + newValue);
		    	rangeOfArrayTextInput.textProperty().setValue("Range of array to sort: " + newValue);
		    	int newval = Integer.parseInt(newValue);
		    	slider.setValue(newval);
		    	_graph.setArrayRange(newval);
		    }
		});
		this.makeAlgorithmCheckBox();
	}

	public void setStage(Stage stage) {
		_currentStage = stage;
	}

	public class convertUnitHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			_graph.changeYAxisUnit(_currentlyMills);
			_currentlyMills = !_currentlyMills;
			_changeMillsAndNanoSec.setText("Change time unit to milli sec");
		}
	}

	public void makeAlgorithmCheckBox() {
		AmericanFlagSort.addToCheckList(_algorithmSelecter, _selectedMode);
		BogoSort.addToCheckList(_algorithmSelecter, _selectedMode);
		BozoSort.addToCheckList(_algorithmSelecter, _selectedMode);
		BubbleSort.addToCheckList(_algorithmSelecter, _selectedMode);
		BubbleSort2.addToCheckList(_algorithmSelecter, _selectedMode);
		BucketSort.addToCheckList(_algorithmSelecter, _selectedMode);
		CocktailSort.addToCheckList(_algorithmSelecter, _selectedMode);
		CombSort.addToCheckList(_algorithmSelecter, _selectedMode);
		CountSort.addToCheckList(_algorithmSelecter, _selectedMode);
		CycleSort.addToCheckList(_algorithmSelecter, _selectedMode);
		HeapSort.addToCheckList(_algorithmSelecter, _selectedMode);
		HoareQuickSort.addToCheckList(_algorithmSelecter, _selectedMode);
		HybridQuickSort.addToCheckList(_algorithmSelecter, _selectedMode);
		InsertionSort.addToCheckList(_algorithmSelecter, _selectedMode);
		IntroSort.addToCheckList(_algorithmSelecter, _selectedMode);
		LomutoQuickSort.addToCheckList(_algorithmSelecter, _selectedMode);
		LSDRadixSort.addToCheckList(_algorithmSelecter, _selectedMode);
		MergeSort.addToCheckList(_algorithmSelecter, _selectedMode);
		OddEvenSort.addToCheckList(_algorithmSelecter, _selectedMode);
		OddEvenMergeSort.addToCheckList(_algorithmSelecter, _selectedMode);
		PancakeSort.addToCheckList(_algorithmSelecter, _selectedMode);
		PigeonholeSort.addToCheckList(_algorithmSelecter, _selectedMode);
		SelectionSort.addToCheckList(_algorithmSelecter, _selectedMode);
		ShellSort.addToCheckList(_algorithmSelecter, _selectedMode);
		SleepSort.addToCheckList(_algorithmSelecter, _selectedMode);
		SlowSort.addToCheckList(_algorithmSelecter, _selectedMode);
		StoogeSort.addToCheckList(_algorithmSelecter, _selectedMode);
		StrandSort.addToCheckList(_algorithmSelecter, _selectedMode);
		TimSort.addToCheckList(_algorithmSelecter, _selectedMode);
		ThreewayMergeSort.addToCheckList(_algorithmSelecter, _selectedMode);
		ThreeWayQuickSort.addToCheckList(_algorithmSelecter, _selectedMode);
	}

	public class quitHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			_currentStage.close();
		}
	}

	public class quitAllHandler implements EventHandler<ActionEvent> {
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

	private class SaveHandler implements EventHandler<ActionEvent> {
		// @Override
		public void handle(ActionEvent event) {
			_graph.save();
		}
	}

	public class StartGraphHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			try {
				_graph.setUpGraph(_selectedMode);
			} catch (InvalidInputException | NullModeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
