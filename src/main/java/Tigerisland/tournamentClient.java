package Tigerisland;

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
    private BufferedReader incomingMessage;
    private PrintWriter outgoingMessage;
    private String username;
    private String password;
    private String ourPid, opponentspid;
    private String gid;
    private String moveNumber;
    private String tileDrawn;
    private String tileToAI;
    private String userMoveInformation;

    private AI game1AI, game2AI;


    public tournamentClient(String serverIP, int serverPort) { //update serverIP
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        try {
            //establish new socket connection
            clientSocket = new Socket(this.serverIP, this.serverPort);
            //open up a print writer on the socket
            outgoingMessage = new PrintWriter(clientSocket.getOutputStream(), true);
            //open up a buffer reader on the socket
            incomingMessage = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverIP);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("I/O error when connecting!");
            System.exit(1);
        }
    }

    public void tournamentAuthentication(String tournamentPassword, String username, String password) throws IOException {
        //this.username = username;
        //this.password = password;
        String serverMessage;

        while ((serverMessage = incomingMessage.readLine()) != null) {
            System.out.println("S: " + serverMessage + "\n");
            if (serverMessage.startsWith("WELCOME")) {
                //Send out the required line to enter
                outgoingMessage.println("ENTER THUNDERDOME " + tournamentPassword);
                tournamentLogin(username, password);
            }
            if (serverMessage.equals("THANK YOU FOR PLAYING! GOODBYE")) {
                clientSocket.close();
                System.out.println("FAILED TO LOGIN! NOT GOOD");
                break;  //Break while loop. THINGS ARE NOT GOOD
            }

        }

    }

    //After this the game begins
    public void tournamentLogin(String username, String password) throws IOException {
        String serverMessage;

        while ((serverMessage = incomingMessage.readLine()) != null) {

            System.out.println("S: " + serverMessage + "\n");
            if (serverMessage.startsWith("TWO")){

                //Send out user name and password
                outgoingMessage.println("I AM " + username + " " + password);

                System.out.println("Authentication!!");
            }
            if (serverMessage.startsWith("WAIT FOR THE TOURNAMENT")) {
                //Send message to array to parse it using split function
                String[] split = serverMessage.split(" ");
                //get pid from the server message
                ourPid = split[6];
                //Print to console we are about to beat some players!!!
                System.out.println("Login was a success. PID: " + ourPid);
                waitForTournamentToBegin();
            }

            if (serverMessage.equals("THANK YOU FOR PLAYING! GOODBYE")) {
                clientSocket.close();
                System.out.println("FAILED TO LOGIN! NOT GOOD");
                break;  //Break while loop. THINGS ARE NOT GOOD
            }
        }
    }

    //Waiting for a new match
    public void waitForTournamentToBegin() {
        String serverMessage = "";

//        System.out.println(serverMessage);
//        game1AI = new AI();
//        game2AI = new AI();
        System.out.println("************************");
        System.out.println("Created the new game AIs");
        System.out.println("************************");

        try {
            while ((serverMessage = incomingMessage.readLine()) != null) {

                System.out.println("S: " + serverMessage + "\n");
                //If match is going to start go to correct function
                if (serverMessage.startsWith("NEW MATCH")) { //Should only get called once
                    System.out.println("************************");
                    System.out.println(serverMessage);
                    System.out.println("************************");
                    game1AI = new AI();
                    game1AI.helper.map.placeStartingTile();
                    game2AI = new AI();
                    game2AI.helper.map.placeStartingTile();
                    winTheTournament();
                }
                //If server says goodbye then everything is over or we got kicked
                if (serverMessage.equals("THANK YOU FOR PLAYING! GOODBYE")) {
                    clientSocket.close();
                    System.out.println("THANK YOU FOR PLAYING! GOODBYE");
                    break;
                }
                if (serverMessage.equals("END OF CHALLENGES")) {
                    System.out.println("Challenges over!");
                }
            }
        } catch (IOException e) {
            System.err.println("I/O error!");
            System.exit(1);
        }
    }

    //This take in all the move messages
    public void winTheTournament() {
        String serverMessage;

        try {
            while ((serverMessage = incomingMessage.readLine()) != null) {

                System.out.println("S: " + serverMessage + "\n");
                //This is for our move to be created
                if (serverMessage.startsWith("MAKE YOUR MOVE IN GAME")) {
                    String[] split = serverMessage.split(" ");
                    gid = split[5];
                    moveNumber = split[10];
                    tileDrawn = split[12];
                    String[] tileSplit = tileDrawn.split("[+]");
                    tileToAI = tileSplit[0] + " " + tileSplit[1];

                    if (gid.equals("A")) {
                        game1AI.setServerMessage(tileToAI);
                        userMoveInformation = game1AI.placeAIMove();
                        outgoingMessage.println("GAME " + gid + " MOVE " + moveNumber + " PLACE "
                                + tileDrawn + " AT " + userMoveInformation);

                        System.out.println("GAME " + gid + " MOVE " + moveNumber + " PLACE "
                                + tileDrawn + " AT " + userMoveInformation + "\n");
                        
                        opponentMoves();
                    }
                    else if (gid.equals("B")) {
                        game2AI.setServerMessage(tileToAI);
                        userMoveInformation = game2AI.placeAIMove();
                        outgoingMessage.println("GAME " + gid + " MOVE " + moveNumber + " PLACE "
                                + tileDrawn + " AT " + userMoveInformation);

                        System.out.println("GAME " + gid + " MOVE " + moveNumber + " PLACE "
                                + tileDrawn + " AT " + userMoveInformation + "\n"); // output

                        opponentMoves();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("I/O error when connecting!");
            System.exit(1);
        }

    }

    public void opponentMoves() {
        String serverMessage;

        try {
            while ((serverMessage = incomingMessage.readLine()) != null) {

                System.out.println("S: " + serverMessage + "\n");
                if(serverMessage.startsWith("GAME")){
                    String[] split = serverMessage.split(" ");
                    gid = split[1];
                    opponentspid = split[5];
                    if(split[6].equals("FORFEITED")) {
                        waitForTournamentToBegin();
                    }
                    else if(split[6].equals("LOST:") || split[6].equals("FORFEITED:")){
                        opponentMoves();
                    }
                    else if(split[2].equals("OVER") && gid.equals("A")) {
                        //game1AI.helper.map.resetGame();
                        opponentMoves();
                    }
                    else if(split[2].equals("OVER") && gid.equals("B")){
                        //game2AI.helper.map.resetGame(); // reset game 2
                        waitForTournamentToBegin(); //Start a new game
                    }
                    else if(gid.equals("A") && opponentspid.equals(ourPid)) {
                        opponentMoves();
                    }
                    else if(gid.equals("B") && opponentspid.equals(ourPid)) {
                        opponentMoves();
                    }
                    else if(gid.equals("A") && !opponentspid.equals(ourPid) && !split[6].equals("LOST") ) {
                        game1AI.setServerMessage(serverMessage);  //game 1 for opponent
                        game1AI.placeOpponentMove();
                        System.out.println("****Game A****\n");
                        game1AI.helper.map.printCurrentPlayerScores();
                        winTheTournament();
                    }
                    else if(gid.equals("B") && !opponentspid.equals(ourPid) && !split[6].equals("LOST")){
                        game2AI.setServerMessage(serverMessage);  //game 2 for opponent
                        game2AI.placeOpponentMove();
                        System.out.println("****Game B****\n");
                        game2AI.helper.map.printCurrentPlayerScores();
                        winTheTournament();
                    }

                }
                //If server says goodbye then everything is over or we got kicked
                else if(serverMessage.equals("THANK YOU FOR PLAYING! GOODBYE")){
                    clientSocket.close();
                    System.out.println("THANK YOU FOR PLAYING! GOODBYE");
                    break;
                }

            }
        } catch (IOException e) {
            System.err.println("I/O error when connecting!");
            System.exit(1);
        }

    }
}