package nl.yogibear;

import lombok.*;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;

@Data
class Recipe {
    public Recipe(int idValue) {
        this.idValue = idValue;
    }
    private int idValue;
    Recipe previousMarble;
    Recipe nextMarble;

    boolean current;
}

@Data
class Game {

    Recipe marble;
    Recipe currentMarble;
    final long[] players;

    public Game(int numPlayers, int numMarbles) {

        players = new long[numPlayers + 1];
        Recipe m = new Recipe(0);
        m.setPreviousMarble(m);
        m.setNextMarble(m);
        m.setCurrent(true);
        this.currentMarble = m;
        this.marble = m;
        for (int i = 0; i < players.length; i++) {
            players[i] = 0;
        }
    }

    public void insertMarble(int id) {
        marble.setCurrent(false);

        Recipe previous = marble.getNextMarble();
        Recipe next = marble.getNextMarble().getNextMarble();

        Recipe current = new Recipe(id);
        current.setNextMarble(next);
        current.setPreviousMarble(previous);
        current.setCurrent(true);
        this.currentMarble = current;

        this.marble = current;

        previous.setNextMarble(current);
        next.setPreviousMarble(current);
    }

    public long getHighScore() {
        long result = 0;
        for (int i = 0; i < players.length; i++) {
            if (players[i] > result) result = players[i];
        }
        return result;
    }

    public void scoreMarble(int id, int player) {
        marble.setCurrent(false);
        for (int x = 0; x < 7; x++) {
            this.marble = this.marble.getPreviousMarble();
        }
        players[player] = players[player] + id + marble.getIdValue();
        marble.getNextMarble().setCurrent(true);
        this.currentMarble = marble.getNextMarble();

        marble.getPreviousMarble().setNextMarble(marble.getNextMarble());
        marble.getNextMarble().setPreviousMarble(marble.getPreviousMarble());

        setCurrentMarble();
    }

    public void setCurrentMarble() {
        this.marble = this.currentMarble;
    }

    public Recipe getMarble(Recipe m, int id) {
        if (m.getIdValue() == id) {
            return (m);
        } else {
            return (getMarble(m.getNextMarble(), id));
        }
    }
}

public class Day_9 {

    final static int PLAYERS = 426;
    final static int MARBLES = 7205800;

    public static void main(String[] args) throws IOException {
        LocalTime start = LocalTime.now();

        Game game = new Game(PLAYERS, MARBLES);
        int player = 0;

        for (int x=1; x<=MARBLES; x++) {
            if (player == PLAYERS ) player = 0;

            if ((x % 23) == 0 ){
                game.scoreMarble(x, player);
            } else {
                game.insertMarble(x);
            }

            player++;
        }

        System.out.println("The highscore is : " + game.getHighScore());

        LocalTime finish = LocalTime.now();
        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());
    }

}



