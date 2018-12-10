package nl.yogibear;

import lombok.*;

public class Day_9 {

    private static void log(String logLine) {
        System.out.println(logLine);
    }

    class Marble {
        public Marble(int idValue) {
            this.idValue = idValue;
        }

        @Getter
        @Setter
        private int idValue;
        @Getter
        @Setter
        Marble previousMarble;
        @Getter
        @Setter
        Marble nextMarble;
        @Getter
        @Setter
        boolean current;
    }


    class Game {

        Marble marble;
        final long[] players;

        public Game(int numPlayers, int numMarbles) {

            players = new long[numPlayers + 1];
            Marble m = new Marble(0);
            m.setPreviousMarble(m);
            m.setNextMarble(m);
            m.setCurrent(true);
            for (int i =0; i<players.length; i++) {
                players[i] = 0;
            }

        }



        public void insertMarble(int id) {

            setCurrentMarble();
            marble.setCurrent(false);

            Marble n = marble.getNextMarble();
            Marble p = marble;

            Marble m = new Marble();
            m.setIdValue(id);
            m.setNextMarble(n);
            m.setPreviousMarble(p);
            m.setCurrent(true);

            n.setPreviousMarble(m);
            p.setNextMarble(m);

            setCurrentMarble();
        }

        public long getHighScore(){
            long result = 0;
            for (int i =0; i<players.length; i++) {
                if (players[i] > result) result = players[i];
            }
            return result;
        }

        public void scoreMarble(int id, int player) {


            setCurrentMarble();

            for (int x = 0; x < 6; x++) {
                this.marble = this.marble.getPreviousMarble();
            }

            players[player] = players[player] + id + marble.getIdValue();

            marble.getPreviousMarble().setNextMarble(marble.getNextMarble());
            marble.getNextMarble().setPreviousMarble(marble.getPreviousMarble());
        }



        public void setCurrentMarble() {
            this.marble = getCurrent(this.marble);
        }


        public Marble getCurrent(Marble m) {
            if (m.isCurrent()) {
                return (m);
            } else {
                return getCurrent(m.getNextMarble());
            }
        }

        public void findAndSetMarble (int id) {
            this.marble = getMarble(this.marble, id);
        }

        public Marble getMarble(Marble m, int id) {
            if (m.getIdValue() == id ) {
                 return (m);
            } else {
                 return (getMarble(m.getNextMarble(), id));
            }
        }

        final static int PLAYERS = 9;
        final static int MARBLES = 25;

        public static void main(String[] args) {

            Game game = new Game(PLAYERS, MARBLES);






        }

    }


}
