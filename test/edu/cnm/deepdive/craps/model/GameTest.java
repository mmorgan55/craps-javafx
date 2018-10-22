package edu.cnm.deepdive.craps.model;

import static org.junit.jupiter.api.Assertions.*;

import edu.cnm.deepdive.craps.model.Game.State;
import org.junit.jupiter.api.Test;

class GameTest {

  @Test
  void testComeOutLoss() {

    for (int roll : new int[]{2, 3, 12}) {
      assertSame(State.LOSS, State.COME_OUT.roll(roll, 0));
    }
  }

  @Test
  void tesComeOutWin() {
    for (int roll : new int[]{7, 11}) {
      assertSame(State.WIN, State.COME_OUT.roll(roll, 0));
    }
  }

  @Test
  void testPointWin() {
    for (int point : new int[]{4, 5, 6, 7, 8, 9, 10}) {
      for (int roll = 2; roll <= 12; roll++) {
        if (roll != 7 && roll != point) {
          assertSame(State.POINT, State.POINT.roll(roll, point));
        }
      }
    }
  }

  @Test
  void testPointLoss() {
    int roll = 7;
    int point = 6;
    assertSame(State.LOSS, State.POINT.roll(roll, point));

    roll = 12;
    assertNotSame(State.LOSS, State.POINT.roll(roll, point));

    roll = 2;
    assertNotSame(State.LOSS, State.POINT.roll(roll, point));

    roll = 3;
    assertNotSame(State.LOSS, State.POINT.roll(roll, point));
  }


}