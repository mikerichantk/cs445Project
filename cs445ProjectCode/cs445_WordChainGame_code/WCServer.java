import java.io.*;
import java.net.*;
import java.util.Arrays;

/**
 * @author Word-Chain Team
 * @version Spring 2021
 */
public class WCServer 
{
    public static void main(String[] args) throws IOException 
    {
        // hold message and reply
        String clientMessage, serverReply, pattyMessage;
        String[] arrayOfWords = new String[200];
        Arrays.fill(arrayOfWords, "INVALID");
        UserInterface ui = new UserInterface(); 
        int currentWord = 0;
        boolean activeGame = true;
        boolean first = true;
        int currentTurn = 0;
        UserInterface myGUI = new UserInterface(); 
        try {
            // create socket connection to port 9999
            ServerSocket accepting = new ServerSocket(9999);

            System.out.println("Hello World");
            Socket connectionSocket = accepting.accept();
            Socket pattySocket = accepting.accept();

            BufferedReader clientIn = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream clientOut = new DataOutputStream(connectionSocket.getOutputStream());

            BufferedReader pattyIn = new BufferedReader(new InputStreamReader(pattySocket.getInputStream()));
            DataOutputStream pattyOut = new DataOutputStream(pattySocket.getOutputStream());

            System.out.println("Game will now begin!");

            // wait for clients to make connections
            while(activeGame) {
                if(currentTurn == 0){
                    System.out.println("Turn: Player 1");
                    clientMessage = clientIn.readLine();
                }
                else{
                    System.out.println("Turn: Player 2");
                    clientMessage = pattyIn.readLine();
                }
                // only accessed by current turn 0
                if(arrayOfWords[currentWord] == "INVALID" && first){
                    arrayOfWords[currentWord] = clientMessage;
                    currentWord = 0;
                    first = false;
                    System.out.println("First Word added: " + clientMessage);
                    currentTurn = 1;
                }
                else if(arrayOfWords[currentWord] == "INVALID"){
                    arrayOfWords[currentWord] = clientMessage;
                    System.out.println("New word added at end of chain: " + clientMessage);
                   // System.out.println("After: " + arrayOfWords[currentWord]);
                    currentWord = 0;
                    if(currentTurn == 0){
                        currentTurn = 1;
                    }
                    else{
                        currentTurn = 0;
                    }
                }
                else if(clientMessage.equals(arrayOfWords[currentWord]) && arrayOfWords[currentWord+1] == "INVALID"){
                    currentWord++;
                    System.out.println("Player has successfully guessed the chain and will add a new word" );
                }
                else if(clientMessage.equals(arrayOfWords[currentWord])){
                    currentWord++;
                    System.out.println("Player  has successfully guessed the next word" );
                }
                else {
                    // send client reply 
                    if(currentTurn == 0){
                        System.out.println("Player 2 wins!");
                    }
                    else{
                        System.out.println("Player 1 wins!");
                    }
                    currentWord = 0;
                    activeGame = false;
                }

            }
            // close
            connectionSocket.close();
            pattySocket.close();
        }
        catch (Exception e) {
            System.out.println("An error occurred while creating server socket or reading/writing data to/from client.");
        }
        
    }
}
