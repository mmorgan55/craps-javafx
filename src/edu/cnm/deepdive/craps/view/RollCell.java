package edu.cnm.deepdive.craps.view;

import edu.cnm.deepdive.craps.model.Game.Roll;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class RollCell extends ListCell<Roll> {

  private static final String ROLL_RESOURCE = "res/roll.fxml";

  private ResourceBundle bundle;

  public RollCell(ResourceBundle bundle) {
    this.bundle = bundle;
  }

  @Override
  protected void updateItem(Roll item, boolean empty) {
    super.updateItem(item, empty);
    setText(null);
    if (empty) {
      setGraphic(null);
    } else {
      try {
        ClassLoader classLoader = getClass().getClassLoader();
        FXMLLoader fxmlLoader = new FXMLLoader(classLoader.getResource(ROLL_RESOURCE), bundle);
        Controller controller = new Controller();
        fxmlLoader.setController(controller);
        Parent root = fxmlLoader.load();
        controller.setRoll(item);
        setGraphic(root);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private static class Controller {

    private static final String DIE_FACE_FORMAT = "res/face_%d.png";

    private static Image[] faces;

    static {
      ClassLoader loader = Controller.class.getClassLoader();
      faces = new Image[6];
      for (int i = 0; i < faces.length; i++) {
        faces[i] = new Image(loader.getResourceAsStream(String.format(DIE_FACE_FORMAT, i + 1)));
      }
    }

    private String totalFormat;
    @FXML
    private ImageView die0;
    @FXML
    private ImageView die1;
    @FXML
    private Text total;

    @FXML
    private void initialize() {
      totalFormat = total.getText();
    }

    private void setRoll(Roll roll) {
      int[] dice = roll.getDice();
      die0.setImage(faces[dice[0] - 1]);
      die1.setImage(faces[dice[1] - 1]);
      total.setText(String.format(totalFormat, dice[0] + dice[1]));
    }
  }
}
