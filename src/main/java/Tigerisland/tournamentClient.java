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
                break;
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
                    break;
                }
                //If server says goodbye then everything is over or we got kicked
                if (serverMessage.equals("THANK YOU FOR PLAYING! GOODBYE")) {
                    clientSocket.close();
                    System.out.println("THANK YOU FOR PLAYING! GOODBYE");
                    break;
                }
                if (serverMessage.equals("END OF CHALLENGES")) {
                    System.out.println("Challenges over!");
                    break;
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
            while ((serverMessage = incomingMessage.readLine()) != null) { //This will parse inbound messages from server

                System.out.println("S: " + serverMessage + "\n");
                //This is for our move to be created
                if (serverMessage.startsWith("MAKE YOUR MOVE IN GAME")) { //Here we check if the game has asked us to make a move
                    String[] split = serverMessage.split(" "); //begin parsing with splitting the string into pieces
                    gid = split[5]; //6th word that the server gives us which is the game ID
                    moveNumber = split[10]; //11th word that the server gives us which is the move number
                    tileDrawn = split[12]; //13th word that the server gives us which is the tile type
                    String[] tileSplit = tileDrawn.split("[+]"); //parse the tile terrains
                    tileToAI = tileSplit[0] + " " + tileSplit[1]; //The tile arguements given to AI

                    if (gid.equals("A")) { //If the game ID is the first one
                        game1AI.setServerMessage(tileToAI);  //send to thread for AI
                        userMoveInformation = game1AI.placeAIMove(); //Tell game1's AI to make a move on client's side (place tile, Build)
                        outgoingMessage.println("GAME " + gid + " MOVE " + moveNumber + " PLACE "
                                + tileDrawn + " AT " + userMoveInformation); //Tell Server what we did
                        //Debug
                        System.out.println("GAME " + gid + " MOVE " + moveNumber + " PLACE "
                                + tileDrawn + " AT " + userMoveInformation); //Output for debugging

                        opponentMoves(); //Handler for opponent move
                    }
                    else if (gid.equals("B")) { //This is the second game
                        game2AI.setServerMessage(tileToAI);  //send to thread for AI
                        userMoveInformation = game2AI.placeAIMove(); //Tell game2's AI to make a move on client's side (place tile, Build)
                        outgoingMessage.println("GAME " + gid + " MOVE " + moveNumber + " PLACE "
                                + tileDrawn + " AT " + userMoveInformation); //Tell Server what we did
                        //Debug
                        System.out.println("GAME " + gid + " MOVE " + moveNumber + " PLACE "
                                + tileDrawn + " AT " + userMoveInformation); //Debug output

                        opponentMoves();
                    }
                    break;
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
            while ((serverMessage = incomingMessage.readLine()) != null) { // Receive server messages for other client's move
                //Will be told forfeited by server if other team makes an illegal move
                System.out.println("S: " + serverMessage + "\n"); //Debug, we need to see what the server told us
                if(serverMessage.startsWith("GAME")){
                    String[] split = serverMessage.split(" ");
                    gid = split[1]; // get a new game ID
                    opponentspid = split[5];  //get a player ID for our opponent
                    if(split[6].equals("FORFEITED:")) { //When the other player forfeits
                        waitForTournamentToBegin(); // if current match is over start a new one
                    }
                    else if(split[6].equals("LOST:")){ //When the other player lost
                        opponentMoves(); //recursively call opponentMoves to handle the next server message
                    }
                    if(split[2].equals("OVER")){ //Resetting the game if they have ended
                        if(gid.equals("A")){
                            game1AI.helper.map.resetGame(); // reset game 1
                            opponentMoves(); //recursively call opponentMoves to handle the next server message
                        }
                        else if(gid.equals("B")){
                            game2AI.helper.map.resetGame(); // reset game 2
                            waitForTournamentToBegin(); //Start a new game
                        }
                    }
                    else if(gid.equals("A") && opponentspid.equals(ourPid)) {
                        opponentMoves(); //handle server repeat messages
                    }
                    else if(gid.equals("B") && opponentspid.equals(ourPid)) {
                        opponentMoves(); //handle server repeat messages
                    }
                    else if(gid.equals("A") && !opponentspid.equals(ourPid) && !split[6].equals("LOST") ) {
                        game1AI.setServerMessage(serverMessage);  //game 1 for opponent
                        game1AI.placeOpponentMove();
                        winTheTournament();
                    }
                    else if(gid.equals("B") && !opponentspid.equals(ourPid) && !split[6].equals("LOST")){
                        game2AI.setServerMessage(serverMessage);  //game 2 for opponent
                        game2AI.placeOpponentMove();
                        winTheTournament();
                    }


                    //break;
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