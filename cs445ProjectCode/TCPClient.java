import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Example of creating a client and connection using TCP
 * Socket Lab, example with Java
 * @author Tammy VanDeGrift 
 * @version Spring 2021
 */
public class TCPClient {
    
    /**
     * creates a connection on the client side for sending data over TCP
     * reads from standard input stream and sends that data to the server
     * uses port 9999 on the localhost
     */
    public static void main(String[] args) throws IOException {
        // create Strings to store message and reply
        String message, reply;
        boolean correctWord = true;
        UserInterface myGUI = new UserInterface();
        try { 
            //System.out.println("Welcome to the word-chain game! Please provide a word to start the game! NOTE: The max size a chain can be is 200 words. \n");
	        myGUI.printInitialState();
            while(correctWord){
                // create reader to acquire text
                BufferedReader userIn = new BufferedReader(
                    new InputStreamReader(System.in));
            
                // get message from user
                message = userIn.readLine();
            
                // create socket connection on current machine at port 9999
                // and attach stream for writing data
                // connection is made to localhost (same machine), but could 
                // instead put in 32-bit or 128-bit unsigned number
                // for IP address or a string with the host name
                Socket clientSocket = new Socket("localhost", 9999);
                DataOutputStream serverOut = new DataOutputStream(clientSocket.getOutputStream());
                
                // create stream for reading data from server
                BufferedReader serverIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                
                // send data to server
                serverOut.writeBytes(message + '\n');
                
                // get data from server
                //reply = serverIn.readLine();
                Iterator<String> size = serverIn.lines().iterator();
                while(size.hasNext())
                {
                    System.out.println(size.next());
                }
                
                // print reply from server
                //System.out.println(reply);
                //myGUI.printServerReply(reply);
	    
	            clientSocket.close();
	    }            
	
        }
        catch(ConnectException e) {
            System.out.println("Error connecting to server. Check that server is running and accepting connections.");
        }
        catch(Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

    
}
