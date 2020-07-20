package Sort;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Session;
import javax.mail.Transport;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BugReportOrganizer {
	private BorderPane _root;
	private Stage _currStage;
	private TextArea _textField;
	private Label _label;

	public BugReportOrganizer() {
		_root = new BorderPane();
		_root.setFocusTraversable(false);
		this.setUpTextInput();
	}

	public void setUpTextInput() {
		_textField = new TextArea("");
		_textField.setFocusTraversable(false);
		VBox vbox = new VBox();
		_textField.minHeight(Constants.APP_HEIGHT - 50);
		_textField.prefWidth(Constants.APP_WIDTH);
		_root.setCenter(vbox);
		Button b1 = new Button("Submit Bug(s)");
		_label = new Label(
				"Please list the errors you have encountered. If any stack trace is available, please paste them below.");
		_label.setWrapText(true);
		vbox.getChildren().addAll(_label, _textField, b1);
		b1.setPadding(new Insets(10, 50, 10, 50));
		b1.setOnAction(new clickHandler());

	}

	public Pane getRoot() {
		return _root;
	}

	public void setStage(Stage stage) {
		_currStage = stage;
	}

	public class clickHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			String recipient = "kenya.kimata@gmail.com";
			Properties properties = new Properties();
			properties.setProperty("mail.smtp.host", "smtp.gmail.com");
			properties.setProperty("mail.smtp.port", "465");
			properties.setProperty("mail.smtp.auth", "true");
			properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			properties.setProperty("mail.smtp.socketFactory.fallback", "false");
			properties.setProperty("mail.smtp.socketFactory.port", "465");
			Session session = Session.getDefaultInstance(properties, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("kenya.kimata@gmail.com", "Kenya0514");
				}
			});
			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(recipient));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
				message.setSubject("Error from SortVisualizer");
				message.setText(_textField.getText());
				Transport.send(message);
				Alert quitAlert = new Alert(AlertType.INFORMATION);
				quitAlert.setTitle("Bug report completed");
				quitAlert.setContentText("Thank you for submitting the bug report.");
				quitAlert.setHeaderText(null);
				quitAlert.showAndWait();
				if (quitAlert.getResult() == ButtonType.OK) {
					_currStage.close();
				}
			} catch (MessagingException mex) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setContentText(mex.toString());
			}
		}
	}
}
