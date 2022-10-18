package TicTacToe;

//For winning condition, I am using magicSquare https://mathworld.wolfram.com/MagicSquare.html
public class TicTacToe {
    private Board board;
    private Player player1;
    private Player player2;

    public TicTacToe(){
        board = new Board();
    }

    public void Play(Player player, int i, int j, int newMove){
        if(player.GetSide().GetValue() != newMove){
            System.out.println("Wrong Move!");
            return;
        }
        board.SetBoardElement(i,j,newMove);
    }

    public Player CalculateWinnerCondition(){
        return  null;
    }



}
