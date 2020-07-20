package Sort;

import javafx.application.*;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class App extends Application {

	@Override
	public void start(Stage stage) {
		PaneOrganizer organizer = new PaneOrganizer();
		Scene scene = new Scene(organizer.getRoot(), Constants.APP_WIDTH, Constants.APP_HEIGHT);
		stage.setScene(scene);
		stage.setTitle("Sorting Visualizer!");
		stage.show();
		organizer.setStage(stage);
		// Instantiate top-level object, set up the scene, and show the stage here.
	}

	public static void main(String[] argv) {
		// launch is a static method inherited from Application.
		launch(argv);
	}
}
