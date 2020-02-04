package app;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class ScreenController {
	private HashMap<String, Pane> screenMap = new HashMap<>();
	private Scene main;

	public ScreenController(Scene main) {
		this.main = main;
	}

	protected <T> T getController(String name) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		try {
			Pane p = fxmlLoader.load(getClass().getResource(name).openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fxmlLoader.getController();
	}
	protected void addScreen(String name, Pane pane) {
		screenMap.put(name, pane);
	}

	protected void removeScreen(String name) {
		screenMap.remove(name);
	}

	protected void activate(String name) {
		main.setRoot(screenMap.get(name));
//  

	}
}