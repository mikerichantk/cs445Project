# cs445Project

Memory Chain Game is a two player game.
Both players must be on the same network.
Both players will have to be in a voice chat with eachother.

This project has three components:

- TCPServer.java
- TCPClient.java
- TCPSecondClient.java

To play the game one player needs two terminals open.
The third player needs one terminal open.

The player with two terminals open will run

	javac TCPServer.java

in one terminal to compile the server.

Then in the other terminal they will run

	javac TCPClient.java

and the other player will run the code

	javac TCPSecondClient.java

which will result in all the code being compiled.

Now here is an important sequence because
it must be done in the correct order.

Player one will run the code in the terminal that compiled the server code

	java TCPServer.java

then in the other terminal run

	java TCPClient.java

and finally the second player will run

	java TCPSecondClient.java

and the game is started.

The first player will have to communicate
whose turn it is which will appear on the server.

GAME PLAY:

- player one will type a word
- player one will tell the word to player two over voice chat

- player two will type the word player one said
- player two will a new word
- player two will tell the new word to player one

- player one will type the two previous words
- player one will a new word
- player one will tell the new word to player two

This pattern goes on until someone cannot complete the chain
and add a new word.