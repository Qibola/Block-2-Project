public class State {

    public State(Player player, int number1, int number2) {
        this.player = player;
        this.number1 = number1;
        this.number2 = number2;
    }

    public Player getPlayer() {
        return player;
    }

    public int getNumber1() {
        return number1;
    }
    public int getNumber2() {
        return number2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        if (number1 != state.number1) return false;
        return number2 == state.number2;
    }

    @Override
    public int hashCode() {
        int result = number1;
        result = 31 * result + number2;
        return result;
    }

    @Override
    public String toString() {
        return "State{" +
                "player=" + player +
                ", number1=" + number1 +
                ", number2=" + number2 +
                '}';
    }

    private Player player;
    private int number1;
    private int number2;
}
