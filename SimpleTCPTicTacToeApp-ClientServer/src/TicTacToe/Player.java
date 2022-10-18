package TicTacToe;

public class Player {
    private GameElements side;

    public Player(GameElements side){
        this.side = side;
    }

    public GameElements GetSide(){
        return side;
    }
}
