package Tigerisland;

import java.io.IOException;

public class tournamentClientMain {
    //Will be taking in the arguments from command line based off assumption
    public static void main(String[] args){
        String serverIP;
        int port;	//Doens't mean anything will get from command line
        String tournamentPassword;
        String username;
        String password;

        if(args.length != 5){
            //Print to console and return to console for fixing
            System.out.println("Invalid number of arguments to connect!!");
            return;
        }

        serverIP = args[0];
        port = Integer.parseInt(args[1]);
        tournamentPassword = args[2];
        username = args[3];
        password = args[4];

//        System.out.println(serverIP);
//        System.out.println(port);
//        System.out.println(tournamentPassword);
//        System.out.println(username);
//        System.out.println(password);

        tournamentClient clientSocket = new tournamentClient(serverIP, port);
        try{ //This starts everything up for the tournament!
            clientSocket.tournamentAuthentication(tournamentPassword, username, password);
        }catch(Exception e){
            System.err.println("Cannot login!");
        }

    }

}
