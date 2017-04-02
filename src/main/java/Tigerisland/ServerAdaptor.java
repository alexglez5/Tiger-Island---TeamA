package Tigerisland;

import java.io.*;
import java.net.*;

/**
 * Created by vincentibarrola on 4/2/17.
 */
public class ServerAdaptor {
    //todo
    /*
    - TCP Session Connection
        - Need client socket (Waiting for port/hostname)
            - Will read from the server
            - WIll write out to the server
    - Beginning code
        - After connection is established
            - Wait for welcome message
            - Send password
            - Wait for slogan
            - Send username and password
            - Wait for pid
    */
    private static int serverPort = 1234; //This needs to be changed when information is obtained

    public static void main(String serverIP) throws IOException{ //update serverIP

        try (
            //establish new socket connection
        Socket clientSocket = new Socket(serverIP, serverPort);
            //open up a print writer on the socket
        PrintWriter outgoingMessage = new PrintWriter(clientSocket.getOutputStream(), true);
            //open up a buffer reader on the socket
        BufferedReader incomingMessage = new BufferedReader(new InputStreamReader (clientSocket.getInputStream()));
        ) {/*Add code here for message*/}
        catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverIP);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("I/O error when connecting!");
            System.exit(1);
        }
    }


}
