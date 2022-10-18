package TicTacToe;

public class Board {
    private int[][] board;
    private int x;
    private int y;

    public Board(){
        x = 3;
        y = 3;
        CreateBoard();
    }

    private void CreateBoard(){
        board = new int[x][y];
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                board[i][j] = GameElements.None.GetValue();
            }
        }
    }

    private boolean IsBoardItemAvailable(int i, int j){
        if(board[i][j] == GameElements.None.GetValue()) return false;
        return true;
    }

    public void SetBoardElement(int i, int j, int newMove){
        if(IsBoardItemAvailable(i,j)){
            System.out.println("Board is not available, choose another coordination!");
            return;
        }
        board[i][j] = newMove;
    }

    public void PrintBoard(){
        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                if(board[i][j] == GameElements.X.GetValue()){
                    System.out.print("X");
                    System.out.print("|");
                }
                else if(board[i][j] == GameElements.O.GetValue()){
                    System.out.print("O");
                    System.out.print("|");
                }
                else{
                    System.out.print(" ");
                    System.out.print("|");
                }
            }
            System.out.println("------");
        }
    }


}
