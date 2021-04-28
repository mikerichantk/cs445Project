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
public class TCPServer 
{
    /** starts a server that receives/sends data over TCP.
     * uses port 9999 for communication
     */
    public static void main(String[] args) throws IOException 
    {
        // hold message and reply
        String clientMessage, serverReply;
        String[] arrayOfWords = new String[200];
        Arrays.fill(arrayOfWords, "INVALID");
        UserInterface ui = new UserInterface(); 
        int currentWord = 0;
        boolean activeGame = true;
        boolean reiteration = false; 
        try 
        {
            // create socket connection to port 9999
            ServerSocket accepting = new ServerSocket(9999);
            
            // wait for clients to make connections
            while(activeGame) 
            {
        
                Socket connectionSocket = accepting.accept();
                BufferedReader clientIn = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream clientOut = new DataOutputStream(connectionSocket.getOutputStream());
                // clientOut.writeBytes("Welcome to the word-chain game! Please provide a word to start the game! NOTE: The max size a chain can be is 200 words. \n");

                // get message from client and captilatize the letters
                clientMessage = clientIn.readLine();
                if(arrayOfWords[currentWord] == "INVALID")
                {
                    System.out.println("Before: " + arrayOfWords[currentWord]);
                    //serverReply = "Good Job! Now, reiterate the chain." + "\nWord: ";
                    serverReply = ui.printMustReiterateChain(clientMessage, currentWord);
                    reiteration = true; 
                    // send client reply 
                    clientOut.writeBytes(serverReply);
                    arrayOfWords[currentWord] = clientMessage;
                    System.out.println("After: " + arrayOfWords[currentWord]);
                    currentWord = 0;
                } 
                else if(reiteration)
                {
                    
                    while(!(arrayOfWords[currentWord + 1].equals("INVALID")))
                    {
                        Socket connectionSocket2 = accepting.accept();
                        BufferedReader clientIn2 = new BufferedReader(new InputStreamReader(connectionSocket2.getInputStream()));
                        DataOutputStream clientOut2 = new DataOutputStream(connectionSocket2.getOutputStream());
                        String clientMessage2 = clientIn2.readLine();
                        if(clientMessage.equals(arrayOfWords[currentWord]))
                        {
                            System.out.println("Attempted Word: " + clientMessage2);
                            //serverReply = "Correct!\n";
                            serverReply = ui.printAttemptedWordIsCorrect(clientMessage2, currentWord);
                            clientOut2.writeBytes(serverReply);
                            currentWord++;
                        }
                        else
                        {
                            serverReply = ui.printGameOver() + "\nYour guess was incorrect. The correct word was: " + arrayOfWords[currentWord];
                            // send client reply 
                            clientOut2.writeBytes(serverReply);
                        }
                        connectionSocket2.close();
                    }

                    if(clientMessage.equals(arrayOfWords[currentWord]))
                    {
                        System.out.println("Attempted Word: " + clientMessage);
                        serverReply = ui.printReiterationCorrect(arrayOfWords);
                        //serverReply = "Reiteration correct! Please provide the next word in the chain.\n"; 
                        currentWord++;
                        // send client reply 
                        clientOut.writeBytes(serverReply);
                    }
                    else
                    {
                        serverReply = ui.printGameOver() + "\nYour guess was incorrect. The correct word was: " + arrayOfWords[currentWord];
                        // send client reply 
                        clientOut.writeBytes(serverReply);
                    }
                } 
                else 
                {
                    serverReply = ui.printGameOver() + "\nYour guess was incorrect. The correct word was: " + arrayOfWords[currentWord];
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
