package Utility;

public enum PlayerTypes {
    Server(0),
    Client(1);
    private final int value;

    PlayerTypes(final int newValue) {
        value = newValue;
    }

    public int GetValue() { return value; }
}
