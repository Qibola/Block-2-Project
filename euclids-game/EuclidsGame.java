import java.util.LinkedList;
import java.util.List;

public final class EuclidsGame {

    public static final int UTILITY_MAX_PLAYER = -1;
    public static final int UTILITY_MIN_PLAYER = 1;

    /**
     * Euclid's game
     * Rules:
     * 2 players start with 2 arbitrary natural number
     * Player1 subtracts an arbitrary positive multiple of the smaller number, which results in another positive number
     * Player2 does the same with the resulting numbers.
     * A player who manages to reach a value of 0 at the end of his move wins.
     * Problem formulation (Assumption Player1 maximizes, Player2 minimizes, and there are no numbers == 0):
     * Player1 wins if resulting node == 0 and layer = 2n*1 (max-layer)
     * Player2 wins if resulting node == 0 and layer = 2n (min-layer)
     * @param number1 first number
     * @param number2 second number
     */
    public EuclidsGame(int number1, int number2) {
        // MIN_PLAYER because of getSuccessors
        this.initialState = new State(Player.MAX_PLAYER, number1, number2);
        this.lookupTable = new LookupTable();
    }

    private String getPlayer(Player player) {
        return player == Player.MAX_PLAYER ? "Player1" : "Player2";
    }

    private Player nextPlayer(Player player) {
        return player == Player.MAX_PLAYER ? Player.MIN_PLAYER : Player.MAX_PLAYER;
    }

    private void minimaxDecision(State state) {
        Player player = state.getPlayer();
        State nextState = state;
        State stateBefore = nextState;
        if (terminalTest(nextState)) {
            System.out.println(getPlayer(player) + " wins");
            return;
        }
        while (!terminalTest(nextState)) {
            State bestState = null;
            int bestUtility = Integer.MIN_VALUE;
            for (State successor : getSuccessors(nextState)) {
                if (player == Player.MAX_PLAYER) {
                    // Won't get better
                    if (bestUtility == 1) {
                        break;
                    }
                    // Lookup state
                    int result = minValue(successor);
                    // Maximize resulting utilities
                    if (bestUtility < result || bestState == null) {
                        bestState = successor;
                        bestUtility = result;
                    }
                } else {
                    // Won't get better
                    if (bestUtility == -1) {
                        break;
                    }
                    int result = maxValue(successor);
                    // Maximize resulting utilities
                    if (bestUtility > result || bestState == null) {
                        bestState = successor;
                        bestUtility = result;
                    }
                }
            }
            nextState = bestState;
            /*System.out.println(stateBefore.getNumber1() + " " + stateBefore.getNumber2() + " -> "
                    + nextState.getNumber1() + " " + nextState.getNumber2() + " (" + getPlayer(player) + "'s move)");*/
            stateBefore = nextState;
            player = nextPlayer(player);
        }
        System.out.println(getPlayer(nextPlayer(player)) + " wins");
    }

    /**
     * Calculate max-value based on
     * @return utility as an integer
     */
    private int maxValue(State state) {
        if (terminalTest(state)) {
            return calculateUtility(state);
        }
        int v = Integer.MIN_VALUE;
        State vState = null;

        // Lookup state
        if (this.lookupTable.contains(state)) {
            return this.lookupTable.get(state);
        }
        for (State s: getSuccessors(state)) {
            // Pruning: There are no result > v = UTILITY_MIN_PLAYER
            if (v == UTILITY_MIN_PLAYER) break;
            int result = minValue(s);
            // Maximize minValue(s)
            if (v < result) {
                v = result;
                vState = s;
            }
        }
        // Add State to lookup table
        this.lookupTable.put(vState, v);
        return v;
    }

    private int minValue(State state) {
        if (terminalTest(state)) {
            return calculateUtility(state);
        }
        int v = Integer.MAX_VALUE;
        State vState = null;

        // Lookup state
        if (this.lookupTable.contains(state)) {
            return this.lookupTable.get(state);
        }
        for (State s: getSuccessors(state)) {
            // Pruning: There are no result < v = UTILITY_MAX_PLAYER
            if (v == UTILITY_MAX_PLAYER) break;
            int result = maxValue(s);
            // Minimize maxValue(s)
            if (v > result) {
                v = result;
                vState = s;
            }
        }
        // Add State to lookup table
        this.lookupTable.put(vState, v);
        return v;
    }

    private boolean terminalTest(State state) {
        return state.getNumber1() == 0 || state.getNumber2() == 0;
    }

    private int calculateUtility(State state) {
        if (state.getPlayer() == Player.MAX_PLAYER) {
            return UTILITY_MAX_PLAYER;
        } else {
            return UTILITY_MIN_PLAYER;
        }
    }

    private List<State> getSuccessors(State state) {
        // Properties of the Game of Euclid by Cole and Davie, 1969
        // (1) The ending state of the game is (gcd(n, m), 0)
        // (2) For 1 < a/b < ϕ, the only move is (a, b) → (b, a'), where b/a 0 > ϕ. ϕ = Golden ratio

        LinkedList<State> successors = new LinkedList<>();
        LinkedList<State> lockedSuccessors = new LinkedList<>();
        int largerNumber = state.getNumber1();
        int smallerNumber = state.getNumber2();
        if (largerNumber < smallerNumber) {
            largerNumber = state.getNumber2();
            smallerNumber = state.getNumber1();
        }
        Player nextPlayer = nextPlayer(state.getPlayer());
        // Modulo optimization (no need to check the other state)
        int rest = largerNumber % smallerNumber;
        if (rest == 0) {
            successors.add(new State(nextPlayer, 0, smallerNumber));
            return successors;
        }
        // Best move optimization (Choose a move, in which the current player would win in his next turn)
        // If X % Y == 1 with X > Y, X_rest should be 1 + smallnumber
        // meaning Y has to calculate X_rest - smallnumber -> 1 smallnumber
        if (rest == 1 && largerNumber != 1 + smallerNumber) {
            int tmpRest = 1 + smallerNumber;
            successors.add(new State(nextPlayer, tmpRest, smallerNumber));
            return successors;
        }

        // Calculate sequence based on the properties above
        // Calculate rest = x * smallerNumber which results in Floor(rest / smallerNumber) = 1
        // (meaning the next player is restricted to one move)
        // AND try to keep the amount of 1 sequence odd for MAX_PLAYER BUT even for MIN_PLAYER
        for (int i = Math.floorDiv(largerNumber, smallerNumber); i >= 1; i--) {
            int tmp = i * smallerNumber;
            int tmpRest = largerNumber - tmp;
            // e. g. 500 13 -> 19 13 or 13 19
            if (Math.floorDiv(tmpRest, smallerNumber) == 1 || Math.floorDiv(smallerNumber, tmpRest) == 1) {
                lockedSuccessors.add(new State(nextPlayer, tmpRest, smallerNumber));
            } else {
                successors.add(new State(nextPlayer, tmpRest, smallerNumber));
            }
        }
        if (lockedSuccessors.size() > 0) {
            return lockedSuccessors;
        }
        return successors;
    }

    public void perfectGame() {
        minimaxDecision(this.initialState);
    }

    private State initialState;
    private LookupTable lookupTable;

}
