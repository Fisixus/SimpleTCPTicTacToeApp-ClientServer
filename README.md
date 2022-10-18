# SimpleTCPTicTacToeApp-ClientServer
A Java client-server application that allows two players to play a tic-tac-toe game over the network. The application will use TCP socket programming. Information on the tic-tac-toe game can be found here:

https://www.exploratorium.edu/brain_explorer/tictactoe.html Links to an external site.

The game starts with each player choosing a character as a symbol. The server will check if the chosen symbols are the same and ask the player on the server side to change the symbol.
The server will randomly choose the first player.
The players' picks are communicated, and both the client and server will track the game's progress. Furthermore, both will announce the winner or tie at the end of the game.
Name the client program (TTTClient) and the server program (TTTServer).
