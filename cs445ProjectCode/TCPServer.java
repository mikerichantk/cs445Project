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
        String clientMessage, serverReply, pattyMessage;
        String[] arrayOfWords = new String[200];
        Arrays.fill(arrayOfWords, "INVALID");
        int currentWord = 0;
        boolean activeGame = true;
        boolean first = true;
        int currentTurn = 0;
        try {
            // create socket connection to port 9999
            ServerSocket accepting = new ServerSocket(9999);
            
            // wait for clients to make connections
            while(activeGame) {
        
                Socket connectionSocket = accepting.accept();
                Socket pattySocket = accepting.accept();

                BufferedReader clientIn = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream clientOut = new DataOutputStream(connectionSocket.getOutputStream());

                BufferedReader pattyIn = new BufferedReader(new InputStreamReader(pattySocket.getInputStream()));
                DataOutputStream pattyOut = new DataOutputStream(pattySocket.getOutputStream());
                // clientOut.writeBytes("Welcome to the word-chain game! Please provide a word to start the game! NOTE: The max size a chain can be is 200 words. \n");

                // get message from client and captilatize the letters
                if(currentTurn == 0){
                    clientMessage = clientIn.readLine();
                    System.out.println("Here.");
                }
                else{
                    clientMessage = pattyIn.readLine();
                }

                if(arrayOfWords[currentWord] == "INVALID" && first){
                    serverReply = "Congrats on starting the chain. Start next turn." + "\nWord: ";
                    clientOut.writeBytes(serverReply);
                    arrayOfWords[currentWord] = clientMessage;
                    currentWord = 0;
                    first = false;
                    serverReply = "Player one has played the first word which is: " + clientMessage;
                    pattyOut.writeBytes(serverReply);
                    currentTurn = 1;
                    
                }
                else if(arrayOfWords[currentWord] == "INVALID"){
                    System.out.println("Before: " + arrayOfWords[currentWord]);
                    serverReply = "Good Job! New word added. Start next turn." + "\nWord: ";
                    // send client reply 
                    clientOut.writeBytes(serverReply);
                    arrayOfWords[currentWord] = clientMessage;
                    System.out.println("After: " + arrayOfWords[currentWord]);
                    currentWord = 0;
                }
                else if(clientMessage.equals(arrayOfWords[currentWord]) && arrayOfWords[currentWord+1] == "INVALID"){
                    System.out.println("Attempted Word: " + clientMessage);
                    serverReply = "Round passed! Now, add a new word to the chain." + "\nWord: "; 
                    currentWord++;
                    // send client reply 
                    clientOut.writeBytes(serverReply);
                    if(currentTurn == 0){
                        currentTurn = 1;
                    }
                    else{
                        currentTurn = 0;
                    }
                }
                else if(clientMessage.equals(arrayOfWords[currentWord])){
                    System.out.println("Attempted Word: " + clientMessage);
                    serverReply = "Correct. Please provide the next word in the chain.\n"; 
                    currentWord++;
                    // send client reply 
                    clientOut.writeBytes(serverReply);
                }
                else {
                    serverReply = "G A M E : O V E R ! Your guess was incorrect. The correct word was: " + arrayOfWords[currentWord];
                    // send client reply 
                    clientOut.writeBytes(serverReply);
                    currentWord = 0;
                    activeGame = false;
            
                }
                    // close
                    connectionSocket.close();
            }
        }
        catch (Exception e) {
            System.out.println("An error occurred while creating server socket or reading/writing data to/from client.");
        }
        
    }
}
