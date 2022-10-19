package Utility;

import TicTacToe.Player;

public class Instructions {
    public static final String INSTRUCTION_FOR_SELECTING_SIDE = "Please press X for playing X, press O for playing O!";
    public static final String INSTRUCTION_FOR_COORDINATE = "Please press your coordinate!" +
            "\n" +
            "Ex. For left top corner press '00', for right bottom corner press '22'!";

    public static String PrintChangeSide(String clientChar, String serverChar){
        return "Client selected:"+clientChar+", therefore your new character:"+serverChar;
    }
    public static String PrintSides(String serverChar, String clientChar){
        return "Client:"+clientChar+", Server:"+serverChar;
    }

    public static String PrintStartingSide(String startingSide){
        return startingSide + " is going to start the game!";
    }

}
