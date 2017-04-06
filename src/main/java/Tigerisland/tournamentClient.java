package Tigerisland;

import java.util.*;
import java.io.*;
import java.net.*;

/**
 * = done

 - TCP Session Connection *
 - Need client socket (Waiting for port/hostname) *
 - Will read from the server *
 - WIll write out to the server *
 - Beginning code *
 - After connection is established *
 - Wait for welcome message *
 - Send password *
 - Wait for slogan *
 - Send username and password *
 - Wait for pid *
 */
public class tournamentClient {
    //ADD AI to call functions

    private String serverIP;
    private int serverPort;
    private Socket clientSocket;
    private BufferedReader  incomingMessage;
    private PrintWriter outgoingMessage;
    private String username;
    private String password;
    private String pid;
    private String gid;
    private String moveNumber;
    private String tileDrawn;
    private String tileToAI;
    private String userMoveInformation;

    public tournamentClient(String serverIP, int serverPort){ //update serverIP
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        try {
            //establish new socket connection
            clientSocket = new Socket(this.serverIP, this.serverPort);
            //open up a print writer on the socket
            outgoingMessage = new PrintWriter(clientSocket.getOutputStream(), true);
            //open up a buffer reader on the socket
            incomingMessage = new BufferedReader(new InputStreamReader (clientSocket.getInputStream()));
        }catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverIP);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("I/O error when connecting!");
            System.exit(1);
        }
    }

    public void tournamentAuthentication(String tournamentPassword, String username, String password) throws Exception{
        //this.username = username;
        //this.password = password;
        String serverMessage;

        while((serverMessage = incomingMessage.readLine()) != null){
            if(serverMessage.startsWith("WELCOME")) {
                //Send out the required line to enter
                outgoingMessage.println("ENTER THUNDERDOME" + tournamentPassword);
                for(int i=1; i<150; i++){
                    System.out.println(i);
                }
                //  tournamentLogin(username, password);
            }
            if(serverMessage.equals("THANK YOU FOR PLAYING! GOODBYE")){
                System.out.println("FAILED TO LOGIN! NOT GOOD");
                break;  //Break while loop. THINGS ARE NOT GOOD
            }

        }
    }

    public void tournamentLogin(String username, String password) throws Exception{
        String serverMessage;

        while((serverMessage = incomingMessage.readLine()) != null){
            if(serverMessage.startsWith("TWO"))
                //Send out user name and password
                outgoingMessage.println("I AM" + username + " " + password);
            else if(serverMessage.startsWith("WAIT FOR THE TOURNAMENT")){
                //Send message to array to parse it using split function
                String[] split =serverMessage.split(" ");
                //get pid from the server message
                pid = split[6];
                //Print to console we are about to beat some players!!!
                System.out.println("Login was a success");
                waitForTournamentToBegin();
            }

            if(serverMessage.equals("THANK YOU FOR PLAYING! GOODBYE")){
                System.out.println("FAILED TO LOGIN! NOT GOOD");
                break;  //Break while loop. THINGS ARE NOT GOOD
            }

        }
    }

    public void waitForTournamentToBegin() {
        String serverMessage;

        try{
            while((serverMessage = incomingMessage.readLine()) != null){
                //If match is going to start go to correct function
                if(serverMessage.startsWith("NEW MATCH")){
                    makeMove();
                    break;
                }
                //If server says goodbye then everything is over or we got kicked
                if(serverMessage.equals("THANK YOU FOR PLAYING! GOODBYE")){
                    System.out.println("THANK YOU FOR PLAYING! GOODBYE");
                    break;
                }
                if(serverMessage.equals("END OF CHALLENGES")){
                    System.out.println("Challenges over!");
                    break;
                }
            }
        }catch(IOException e){
            System.err.println("I/O error when connecting!");
            System.exit(1);
        }
    }

    public void makeMove(){
        String serverMessage;

        try{
            while((serverMessage = incomingMessage.readLine()) != null){
                //If match is going to start go to correct function
                if(serverMessage.startsWith("MAKE YOUR MOVE IN GAME")){
                    String[] split = serverMessage.split(" ");
                    gid = split[5];
                    moveNumber = split[10];
                    tileDrawn = split[12];
                    String[] tileSplit = tileDrawn.split("[+]");
                    tileToAI = Arrays.toString(tileSplit);              //Check this!!!
                    //userMoveInformation = AI.moveMake(tileToAI);

                    outgoingMessage.println("GAME" + gid + moveNumber + "PLACE" + tileDrawn + "AT" + userMoveInformation);
                    break;
                }
                //If server says goodbye then everything is over or we got kicked
                if(serverMessage.equals("THANK YOU FOR PLAYING! GOODBYE")){
                    System.out.println("THANK YOU FOR PLAYING! GOODBYE");
                    break;
                }
            }
        }catch(IOException e){
            System.err.println("I/O error when connecting!");
            System.exit(1);
        }

    }
}
