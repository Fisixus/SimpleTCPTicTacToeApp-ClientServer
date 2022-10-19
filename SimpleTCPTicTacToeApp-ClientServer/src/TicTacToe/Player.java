package TicTacToe;

import Utility.PlayerTypes;

public class Player {
    private GameElements side;
    private PlayerTypes playerType;

    public Player(PlayerTypes type){
        this.playerType = type;
    }

    public void SetSide(GameElements e){
        this.side = e;
    }

    public GameElements GetSide(){
        return side;
    }
}
