package TicTacToe;

import Utility.Instructions;
import Utility.PlayerTypes;

public class TicTacToe {
    private Board board;
    private Player playerServer;
    private Player playerClient;

    public TicTacToe(){

    }

    public void StartGame(){
        board = new Board();
        playerServer = new Player(PlayerTypes.Server);
        playerClient = new Player(PlayerTypes.Client);

    }

    public void SetPlayerSides(String serverChar, String clientChar){
        if(serverChar.equalsIgnoreCase("X")){
            playerServer.SetSide(GameElements.X);
            playerClient.SetSide(GameElements.O);
        }
        else{
            playerServer.SetSide(GameElements.O);
            playerClient.SetSide(GameElements.X);
        }
    }

    public GameElements GetServerSide(){
        return playerServer.GetSide();
    }

    public GameElements GetClientSide(){
        return playerClient.GetSide();
    }

    public boolean IsServerGoingToStart(){
        //return false;
       return (int) ( Math.random() * 2 + 1) == 1; // will return either 1 or 2
    }
    /*
    public String ChooseSides(){
        int randomNum = (int) ( Math.random() * 2 + 1); // will return either 1 or 2
        if(randomNum == 1){
            playerServer = new Player(PlayerTypes.Server,GameElements.X);
            playerClient = new Player(PlayerTypes.Client,GameElements.O);
        }
        else{
            playerServer = new Player(PlayerTypes.Server,GameElements.O);
            playerClient = new Player(PlayerTypes.Client,GameElements.X);
        }
        return Instructions.InstructionForSides(playerServer,playerClient);
    }

*/
        public String AnnounceResult(PlayerTypes t) {
            return Instructions.PrintWinner(t.name());
        }

        public boolean Play(PlayerTypes type, int i, int j){
        if (board.boardArr[i][j] != GameElements.None.GetValue()) {
            return false;
        }
        if(i > 2 || i < 0 || j > 2 || j<0){
            return false;
        }

        Player p = null;
        if(type == PlayerTypes.Server){
            p = playerServer;
        }
        else{
            p = playerClient;
        }

        board.SetBoardElement(i,j,p.GetSide().GetValue());
        return true;
    }

    public String GetBoard2D(){
        return board.GetBoard2D();
    }

    public PlayerTypes CalculateWinnerCondition(){
        if((board.boardArr[0][0] == GameElements.X.GetValue() && board.boardArr[0][1] == GameElements.X.GetValue() && board.boardArr[0][2] == GameElements.X.GetValue())
            ||
            (board.boardArr[0][0] == GameElements.X.GetValue() && board.boardArr[1][0] == GameElements.X.GetValue() && board.boardArr[2][0] == GameElements.X.GetValue())
            ||
            (board.boardArr[0][0] == GameElements.X.GetValue() && board.boardArr[1][1] == GameElements.X.GetValue() && board.boardArr[2][2] == GameElements.X.GetValue())
            ||
            (board.boardArr[0][1] == GameElements.X.GetValue() && board.boardArr[1][1] == GameElements.X.GetValue() && board.boardArr[2][1] == GameElements.X.GetValue())
            ||
            (board.boardArr[0][2] == GameElements.X.GetValue() && board.boardArr[1][2] == GameElements.X.GetValue() && board.boardArr[2][2] == GameElements.X.GetValue())
            ||
            (board.boardArr[1][0] == GameElements.X.GetValue() && board.boardArr[1][1] == GameElements.X.GetValue() && board.boardArr[1][2] == GameElements.X.GetValue())
            ||
            (board.boardArr[2][0] == GameElements.X.GetValue() && board.boardArr[2][1] == GameElements.X.GetValue() && board.boardArr[2][2] == GameElements.X.GetValue())
            ||
            (board.boardArr[0][2] == GameElements.X.GetValue() && board.boardArr[1][1] == GameElements.X.GetValue() && board.boardArr[2][0] == GameElements.X.GetValue())
        ){
            if(playerServer.GetSide() == GameElements.X){
                return playerServer.GetPlayerType();
            }
            else if(playerClient.GetSide() == GameElements.X){
                return playerClient.GetPlayerType();
            }

        }
        else if((board.boardArr[0][0] == GameElements.O.GetValue() && board.boardArr[0][1] == GameElements.O.GetValue() && board.boardArr[0][2] == GameElements.O.GetValue())
                ||
                (board.boardArr[0][0] == GameElements.O.GetValue() && board.boardArr[1][0] == GameElements.O.GetValue() && board.boardArr[2][0] == GameElements.O.GetValue())
                ||
                (board.boardArr[0][0] == GameElements.O.GetValue() && board.boardArr[1][1] == GameElements.O.GetValue() && board.boardArr[2][2] == GameElements.O.GetValue())
                ||
                (board.boardArr[0][1] == GameElements.O.GetValue() && board.boardArr[1][1] == GameElements.O.GetValue() && board.boardArr[2][1] == GameElements.O.GetValue())
                ||
                (board.boardArr[0][2] == GameElements.O.GetValue() && board.boardArr[1][2] == GameElements.O.GetValue() && board.boardArr[2][2] == GameElements.O.GetValue())
                ||
                (board.boardArr[1][0] == GameElements.O.GetValue() && board.boardArr[1][1] == GameElements.O.GetValue() && board.boardArr[1][2] == GameElements.O.GetValue())
                ||
                (board.boardArr[2][0] == GameElements.O.GetValue() && board.boardArr[2][1] == GameElements.O.GetValue() && board.boardArr[2][2] == GameElements.O.GetValue())
                ||
                (board.boardArr[0][2] == GameElements.O.GetValue() && board.boardArr[1][1] == GameElements.O.GetValue() && board.boardArr[2][0] == GameElements.O.GetValue())
        ){
            if(playerServer.GetSide() == GameElements.O){
                return playerServer.GetPlayerType();
            }
            else if(playerClient.GetSide() == GameElements.O){
                return playerClient.GetPlayerType();
            }

        }
        else if (board.IsBoardFull()) {
            return PlayerTypes.None;
        }

        return  null;
    }

    private class Board {
        private int[][] boardArr;
        private int x;
        private int y;


        public Board(){
            x = 3;
            y = 3;
            CreateBoard();
        }

        private void CreateBoard(){
            boardArr = new int[x][y];
            for(int i = 0; i < boardArr.length; i++){
                for(int j = 0; j < boardArr[i].length; j++){
                    boardArr[i][j] = GameElements.None.GetValue();
                }
            }
        }

        private boolean IsBoardItemAvailable(int i, int j){
            if(boardArr[i][j] == GameElements.None.GetValue()) return false;
            return true;
        }

        public void SetBoardElement(int i, int j, int newMove){
            if(IsBoardItemAvailable(i,j)){
                System.out.println("Board is not available, choose another coordination!");
                return;
            }
            boardArr[i][j] = newMove;
        }

        public boolean IsBoardFull(){
            for(int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    if(boardArr[i][j] == GameElements.None.GetValue()){
                        return false;
                    }
                }
            }
            return true;
        }

        public String GetBoard2D(){
            String board2D = "";

            for(int i = 0; i < x; i++){
                for(int j = 0; j < y; j++){
                    if(boardArr[i][j] == GameElements.X.GetValue()){
                        board2D += "X";
                    }
                    else if(boardArr[i][j] == GameElements.O.GetValue()){
                        board2D += "O";
                    }
                    else{
                        board2D += " ";
                    }
                    if(j != 2){
                        board2D += "|";
                    }
                }

                if(i != 2){
                    board2D += "\n";
                    board2D += "------";
                }

                board2D += "\n";

            }
            return board2D;
        }


    }

}
