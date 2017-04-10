package Tigerisland.AIHelpers;

import Tigerisland.AI;

import java.util.Scanner;

/**
 * Created by Alexander Gonzalez on 4/10/2017.
 */
public class TestMessages {
    public static void main(String[] args){
        AI ai = new AI();
        ai.helper.map.placeStartingTile();

        System.out.println("fuck this:");
        Scanner input  = new Scanner(System.in);
        while(input.hasNextLine()){
            String serverMessage = input.nextLine();
            ai.setServerMessage(serverMessage);
            System.out.println(ai.placeAIMove());
            ai.helper.map.printGameBoard();
            String opponentMessage = input.nextLine();
            ai.setServerMessage(opponentMessage);
            ai.placeOpponentMove();
            ai.helper.map.printGameBoard();
            System.out.println("fuck this:");
        }
    }
}
