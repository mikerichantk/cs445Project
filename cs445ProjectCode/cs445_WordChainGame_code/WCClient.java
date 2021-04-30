import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * @author Word-Chain Team 
 * @version Spring 2021
 */
public class WCClient {
    
    public static void main(String[] args) throws IOException {
        // create Strings to store message and reply
        String message, reply;
        boolean correctWord = true;
        UserInterface myGUI = new UserInterface(); 
        try { 
            myGUI.printInitialState();
            Socket clientSocket = new Socket("localhost", 9999);
            DataOutputStream serverOut = new DataOutputStream(clientSocket.getOutputStream());
            while(correctWord){
                // create reader to acquire text
                BufferedReader userIn = new BufferedReader(
                    new InputStreamReader(System.in));
                
                // get message from user
                message = userIn.readLine();

                // create stream for reading data from server
                BufferedReader serverIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                
                // send data to server
                serverOut.writeBytes(message + '\n');
	    
            }            
            clientSocket.close();
            
        }
        catch(ConnectException e) {
            System.out.println("Error connecting to server. Check that server is running and accepting connections.");
        }
        catch(Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

    
}
