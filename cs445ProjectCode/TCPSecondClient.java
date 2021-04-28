import java.io.*;
import java.net.*;

/**
 * Example of creating a client and connection using TCP
 * Socket Lab, example with Java
 * @author Tammy VanDeGrift 
 * @version Spring 2021
 */
public class TCPSecondClient {
    
        /**
     * creates a connection on the client side for sending data over TCP
     * reads from standard input stream and sends that data to the server
     * uses port 9999 on the localhost
     */
    public static void main(String[] args) throws IOException {
        // create Strings to store message and reply
        String message, reply, turn;
        boolean correctWord = true;
        boolean currentTurn = false;
        System.out.println("Welcome to the Word-Chain Game! Please provide a word to begin the game. Be aware the max chain is 200 words.");
        try { 
<<<<<<< Updated upstream
=======

            Socket clientSocket = new Socket("localhost", 9999);
            // create streams for reading data from server
            BufferedReader serverIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream serverOut = new DataOutputStream(clientSocket.getOutputStream());

            // retrieves the turn client has
            System.out.println("Here");
            turn = serverIn.readLine();

            // Get who's turn is it
            // Set current Turn true if this client's turn
            String getTurn = serverIn.readLine();
            if (getTurn.equals(turn)){
                currentTurn = true;
            }

>>>>>>> Stashed changes
            while(correctWord){

                // get data from server
                reply = serverIn.readLine();
                // Checks if player's turns have switched
                if (reply.equals("0x01\n") && !reply.equals(turn)){
                    currentTurn = false;
                }
                else if (reply.equals("0x00\n") && !reply.equals(turn)){
                    currentTurn = false;
                }
                while (!currentTurn){
                    reply = serverIn.readLine();
                    if (reply.equals(turn)){
                        currentTurn = true;
                        System.out.println("Now it's your turn");
                    }
                    System.out.println(reply);
                }

                System.out.println(reply);
                
                // print reply from server
                // System.out.println("REPLY RECEIVED: " + reply);

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
<<<<<<< Updated upstream
                Socket clientSocket = new Socket("localhost", 9999);
                DataOutputStream serverOut = new DataOutputStream(clientSocket.getOutputStream());
                
                // create stream for reading data from server
                BufferedReader serverIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                
                // send data to server
                serverOut.writeBytes(message + '\n');
                
                // get data from server
                reply = serverIn.readLine();
                
                // print reply from server
                System.out.println("REPLY RECEIVED: " + reply);
            
                clientSocket.close();
            }            
	
=======
                
                // get message from user
                message = userIn.readLine();

                
                // send data to server
                serverOut.writeBytes(message + '\n');
            }            
           clientSocket.close();
>>>>>>> Stashed changes
        }
        catch(ConnectException e) {
            System.out.println("Error connecting to server. Check that server is running and accepting connections.");
        }
        catch(Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
