package Sort;


import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TextInputOrganizer {
	private BorderPane _root;
	private Stage _currStage;
	private TextArea _numField;
	private Label _label;
	private boolean _caughtException;
	private ArrayList<Integer> _numToSort;

	public TextInputOrganizer() {
		_root = new BorderPane();
		_root.setFocusTraversable(false);
		_caughtException = false;
		this.setUpTextInput();
	}

	public void setUpTextInput() {
		_numField = new TextArea("Please type the number to sort. Split the numbers with ','");
		_numField.setFocusTraversable(false);
		VBox vbox = new VBox();
		_numField.minHeight(Constants.APP_HEIGHT - 50);
		_numField.prefWidth(Constants.APP_WIDTH);
		_root.setCenter(vbox);
		Button b1 = new Button("Submit!");
		_label = new Label("Please Input positive integers above. Any negative values will not be considered");
		_label.setWrapText(true);
		vbox.getChildren().addAll(_numField, b1, _label);
		b1.setPadding(new Insets(10, 50, 10, 50));
		b1.setOnAction(new clickHandler());
		
	}

	public Pane getRoot() {
		return _root;
	}
	
	public void setTextFieldText(String str) {
		_numField.setText(str);
	}

	public void setStage(Stage stage) {
		_currStage = stage;
	}
	
	public class clickHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			_caughtException = false;
			_numToSort = new ArrayList<Integer>();
			String string = _numField.getText();
			PaneOrganizer.setTextAreaText(string);
			String currNum = "";
			int currNumLength = 0;
			ArrayList<String> numString = new ArrayList<String>();
			for (int i = 0; i < string.length(); i++) {
				if (string.charAt(i) == ',' || string.charAt(i) == ' ') {
					if (currNumLength != 0) {
						numString.add(currNum);
					}
					currNumLength = 0;
					currNum = "";
				}
				else {
					currNum += Character.toString(string.charAt(i));
					currNumLength ++;
				}
			}
			if (currNumLength != 0) {
				numString.add(currNum);
			}
			ArrayList<Integer> errorIndex = new ArrayList<Integer>();
			for (int i = 0; i < numString.size();i ++) {
				try {
					int k = Integer.parseInt(numString.get(i));
					if (k >= 0) {
						_numToSort.add(k);
					}
				} catch(Exception e){
					_caughtException = true;
					errorIndex.add(i);
				}

			}
			if (_caughtException) {
				String errorString = "The ";
				for (int i: errorIndex) {
					errorString += i + 1;
					errorString += "th, ";
				}
				errorString = errorString.substring(0, errorString.length() - 2);
				_label.setText(errorString +  " word does not seem to be an integer. \nPlease check again and resubmit.");
				_numToSort.clear();
			}else {
				PaneOrganizer.sortUsingInputNum(_numToSort);
				_currStage.close();
			}
		}
	}
}
