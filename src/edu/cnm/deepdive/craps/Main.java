package edu.cnm.deepdive.craps;

import edu.cnm.deepdive.craps.controller.Controller;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

  private static final String RESOURCE_PATH = "res/ui";
  private static final String MAIN_RESOURCE = "res/main.fxml";
  private static final String WINDOW_TITLE_KEY = "window_title";
  private static final String ICON_PATH = "res/icon.png";

  private ClassLoader classLoader;
  private ResourceBundle bundle;
  private FXMLLoader fxmlLoader;
  private Controller controller;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    setupLoaders();
    setupStage(stage, loadLayout());
  }

  @Override
  public void stop() throws Exception {
    controller.stop();
    super.stop();
  }

  private void setupLoaders() {
    classLoader = getClass().getClassLoader();
    bundle = ResourceBundle.getBundle(RESOURCE_PATH);
    fxmlLoader = new FXMLLoader(classLoader.getResource(MAIN_RESOURCE), bundle);
  }

  private Parent loadLayout() throws IOException {
    Parent root = fxmlLoader.load();
    controller = fxmlLoader.getController();
    return root;
  }

  private void setupStage(Stage stage, Parent root) {
    Scene scene = new Scene(root);
    stage.setTitle(bundle.getString(WINDOW_TITLE_KEY));
    stage.getIcons().addAll(new Image(classLoader.getResourceAsStream(ICON_PATH)));
    stage.setResizable(false);
    stage.setScene(scene);
    stage.show();
  }

}
