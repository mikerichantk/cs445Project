import java.io.*;
import java.net.*;
import java.util.Arrays;
/**
 * example of creating a server receiving/sending data over TCP.
 * process must be explicitly stopped to shut down server
 * Socket Lab, java version
 * @author Tammy VanDeGrift
 * @version Spring 2021
 */
public class TCPServer {
    /** starts a server that receives/sends data over TCP.
     * uses port 9999 for communication
     */
    public static void main(String[] args) throws IOException {
        // hold message and reply
        String clientMessage, serverReply, currReply, waiterReply;
        String[] arrayOfWords = new String[200];
        Arrays.fill(arrayOfWords, "INVALID");
        int currentWord = 0;
        int numWords = 0;
        boolean activeGame = true;
        boolean first = true;
        int currentTurn = 0;
        try {
            // create socket connection to port 9999
            ServerSocket accepting = new ServerSocket(9999);

            System.out.println("Hello World");

            // Waits for two players to join the game
            Socket connectionSocket = accepting.accept();
            Socket pattySocket = accepting.accept();

            // Creates Instance variables and sets player number 
            // and player turn for first player
            BufferedReader clientIn = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream clientOut = new DataOutputStream(connectionSocket.getOutputStream());
            clientOut.writeBytes("0x00\n");
            clientOut.writeBytes("You are player 1\n");

            // Creates Instance variables and sets player number 
            // and player turn for second player
            BufferedReader pattyIn = new BufferedReader(new InputStreamReader(pattySocket.getInputStream()));
            DataOutputStream pattyOut = new DataOutputStream(pattySocket.getOutputStream());
            pattyOut.writeBytes("0x01\n");
            pattyOut.writeBytes("You are player 2\n");

            // Declare variables to use to distinguish current player player
            // and the waiter
            DataOutputStream curr, waiter;

            // Writes to the players who's current turn is it
            pattyOut.writeBytes("0x00\n");
            clientOut.writeBytes("0x00\n");
            
            curr = clientOut;
            waiter = pattyOut;

            clientMessage = "";

            if (arrayOfWords[currentWord] == "INVALID" && first){
                serverReply = "Welcome to the Word Chain Game. Player 1 will now add a word to the chain \n";
                clientOut.writeBytes(serverReply);
                pattyOut.writeBytes(serverReply);

                clientMessage = clientIn.readLine();

                //serverReply = "Congrats on starting the chain. Start next turn. Word: \n";
                //clientOut.writeBytes(serverReply);
                arrayOfWords[currentWord] = clientMessage;
                numWords++;
                first = false;
                serverReply = "Player 1 has played the first word which is: " + clientMessage + "\n";
                pattyOut.writeBytes(serverReply);
                currentTurn = 1;
                pattyOut.writeBytes("0x01\n");
                clientOut.writeBytes("0x01\n");
                curr = pattyOut;
                waiter = clientOut;
            }

            // wait for clients to make connections
            while(activeGame) {
                // clientOut.writeBytes("Welcome to the word-chain game! Please provide a word to start the game! NOTE: The max size a chain can be is 200 words. \n");
   
                // get message from client and captilatize the letters
                if(currentTurn == 0){
                    serverReply = "Currently Playing: Player 1\n";
                    clientOut.writeBytes(serverReply);
                    pattyOut.writeBytes(serverReply);
                    clientMessage = clientIn.readLine();
                    System.out.println("Here.");
                }
                else{
                    serverReply = "Currently Playing: Player 2\n";
                    clientOut.writeBytes(serverReply);
                    pattyOut.writeBytes(serverReply);
                    clientMessage = pattyIn.readLine();
                }

                // entered new word and end turn
                // need to tell opponent the new word
                if(arrayOfWords[currentWord] == "INVALID"){
                    System.out.println("Before: " + arrayOfWords[currentWord]);
                    currReply = "Good Job! New word added. Start next turn. Word: \n";
                    // send client reply 
                    curr.writeBytes(currReply);
                    waiterReply = "Opponent has added: " + clientMessage + "\n";
                    waiter.writeBytes(waiterReply);
                    arrayOfWords[currentWord] = clientMessage;
                    System.out.println("After: " + arrayOfWords[currentWord]);
                    currentWord = 0;
                    if(currentTurn == 0){
                        currentTurn = 1;
                        pattyOut.writeBytes("0x01\n");
                        clientOut.writeBytes("0x01\n");
                        curr = pattyOut;
                        waiter = clientOut;
                    }
                    else{
                        currentTurn = 0;
                        pattyOut.writeBytes("0x00\n");
                        clientOut.writeBytes("0x00\n");
                        curr = clientOut;
                        waiter = pattyOut;
                    }
                }
                // entered last word of chain correctly
                // need to say that the opponent got the whole chain correct and is adding a new word
                else if(clientMessage.equals(arrayOfWords[currentWord]) && arrayOfWords[currentWord+1] == "INVALID"){
                    System.out.println("Attempted Word: " + clientMessage);
                    currReply = "Round passed! Now, add a new word to the chain. Word: \n"; 
                    waiterReply = "Opponent has gotten sequence of words correct! They will now add a word\n";
                    currentWord++;
                    // send client reply 
                    curr.writeBytes(currReply);
                    waiter.writeBytes(waiterReply);
                }
                // entered a word in the chain correctly but there are more words left in the chain
                // need to say that they got a word correct but there are still more words
                else if(clientMessage.equals(arrayOfWords[currentWord])){
                    System.out.println("Attempted Word: " + clientMessage);
                    currReply = "Correct. Please provide the next word in the chain.\n"; 
                    currentWord++;
                    // send client reply 
                    curr.writeBytes(currReply);
                    waiterReply = "Opponent has gotten next word in the chain! On to next!\n";
                    waiter.writeBytes(waiterReply);
                }
                else {
                    int winner = (currentTurn + 1) % 2;
                    serverReply = "G A M E : O V E R ! Your guess was incorrect. Player " + winner + " wins\n";
                    // send client reply 
                    clientOut.writeBytes(serverReply);
                    pattyOut.writeBytes(serverReply);
                    currentWord = 0;
                    activeGame = false;
                }
            }
            // close
            connectionSocket.close();
            pattySocket.close();
            accepting.close();
        }
        catch (Exception e) {
            System.out.println("An error occurred while creating server socket or reading/writing data to/from client.");
        }
        
    }
}
