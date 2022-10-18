package TicTacToe;

public enum GameElements {
    None(0),
    X(1),
    O(2);
    private final int value;

    GameElements(final int newValue) {
        value = newValue;
    }

    public int GetValue() { return value; }

}
