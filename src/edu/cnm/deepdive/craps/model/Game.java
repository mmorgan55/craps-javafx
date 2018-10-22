package edu.cnm.deepdive.craps.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * This class provides the game logic to play a game of craps.
 *
 */
public class Game {

  private final Object lock = new Object();

  private State state;
  private int point;
  private Random rng;
  private List<Roll> rolls;
  private int wins;
  private int losses;

  /**
   * This is the constructor that creates an instance of a craps game. Requires
   * that an instance of Random is passed.
   * @param rng Random used get a dice roll.
   */
  public Game(Random rng) {
    this.rng = rng;
    rolls = new LinkedList<>();
    wins = 0;
    losses = 0;
  }

  /**
   * Resets the instance of the game to the beginning of the game.
   */
  public void reset() {
    state = State.COME_OUT;
    point = 0;
    synchronized (lock) {
      rolls.clear();
    }
  }

  private State roll() {
    int[] dice = {
        1 + rng.nextInt(6),
        1 + rng.nextInt(6)
    };
    int total = dice[0] + dice[1];
    State state = this.state.roll(total, point);
    if (this.state == State.COME_OUT && state == State.POINT) {
      point = total;
    }
    this.state = state;
    synchronized (lock) {
      rolls.add(new Roll(dice, state));
    }
    return state;
  }

  /**
   * Plays a new game of craps after calling reset method. Returns whether the
   * ending State of the game was a winning or losing state.
   * @return State of the game after the game has finished.
   */
  public State play() {
    reset();
    while (state != State.WIN && state != State.LOSS) {
      roll();
    }
    if (state == State.WIN) {
      wins++;
    } else {
      losses++;
    }
    return state;
  }

  public State getState() {
    return state;
  }

  public List<Roll> getRolls() {
    synchronized (lock) {
      return new LinkedList<>(rolls);
    }
  }

  public int getWins() {
    return wins;
  }

  public int getLosses() {
    return losses;
  }

  /**
   * Class that rolls holds the instance of a roll of dice.
   */
  public static class Roll {

    private final int[] dice;
    private final State state;

    private Roll(int[] dice, State state) {
      this.dice = Arrays.copyOf(dice, 2);
      this.state = state;
    }

    public int[] getDice() {
      return Arrays.copyOf(dice, 2);
    }

    public State getState() {
      return state;
    }

    @Override
    public String toString() {
      return String.format("%s %s%n", Arrays.toString(dice), state);
    }
  }

  /**
   * Creates and provides logic for the game to determine what the state of
   * the game is after a roll has occurred.
   */
  public enum State {

    COME_OUT {
      /**
       * Provides the logic for the very first roll of the game.
       * @param total Total of the two dice rolled.
       * @param point Point value. Always 0 on initial roll.
       * @return The state of the game after the first roll.
       */
      @Override
      public State roll(int total, int point) {
        switch (total) {
          case 2:
          case 3:
          case 12:
            return LOSS;
          case 7:
          case 11:
            return WIN;
          default:
            return POINT;
        }
      }
    },
    WIN,
    LOSS,
    POINT {
      /**
       * Provides the logic for every roll after the initial roll.
       * @param total Total of the two dice rolled.
       * @param point Point that the game has stored after the first roll.
       * @return The State of the game after the first roll and any subsequent rolls.
       */
      @Override
      public State roll(int total, int point) {
        if (total == point) {
          return WIN;
        } else if (total == 7) {
          return LOSS;
        } else {
          return POINT;
        }
      }
    };

    /**
     * Base roll method. Returns the State of the instance after a roll.
     * @param total Total of the two dice rolled.
     * @param point Point value. Determined after the first roll of the game.
     * @return The instance of State after a roll.
     */
    public State roll(int total, int point) {
      return this;
    }
  }
}
