package edu.cnm.deepdive.craps.controller;

import edu.cnm.deepdive.craps.model.Game;
import edu.cnm.deepdive.craps.model.Game.Roll;
import java.security.SecureRandom;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;

public class Controller {

  private Game game;
  private boolean running;
  private String tallyFormat;

  @FXML
  private MenuItem next;
  @FXML
  private MenuItem fast;
  @FXML
  private MenuItem pause;
  @FXML
  private MenuItem reset;
  @FXML
  private ListView rolls;
  @FXML
  private Text tally;

  @FXML
  private void initialize() {
    tallyFormat = tally.getText();
    reset(null);
  }

  @FXML
  private void next(ActionEvent actionEvent) {
    game.play();
    updateTally(game.getWins(), game.getLosses());
    updateRolls(game.getRolls());
  }

  @FXML
  private void fast(ActionEvent actionEvent) {
    running = true;
    // TODO Update menu items enabled/disabled
    // TODO Instantiate and start Runner
  }

  @FXML
  private void pause(ActionEvent actionEvent) {
    running = false;
    // TODO Release Runner instance
  }

  @FXML
  private void reset(ActionEvent actionEvent) {
    game = new Game(new SecureRandom());
    updateTally(game.getWins(), game.getLosses());
    updateRolls(game.getRolls());
  }

  private void updateTally(int wins, int losses) {
    int total = wins + losses;
    double percentage = (total != 0) ? (100.0 * wins / total) : 0;
    tally.setText(String.format(tallyFormat, wins, total, percentage));
  }

  private void updateRolls(List<Roll> rolls) {
    // TODO Update Rolls ListView.
  }

}
