/*
Amy Estep Coursework - Student 2014315, attempt Lvl 3
Note: in order to customise number of heaps and coins please input 'custom'
at the start of the program
 */
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SubtractASquare {

    private static Scanner in = new Scanner(System.in);
    private static ArrayList<Integer> heaps = new ArrayList<>();
    private static int pileChosen;
    public static final String P_1 = "Player 1";
    public static final String P_2 = "Player 2";
    public static final int DEFAULT_NUMBER_HEAPS = 3;
    public static final int DEFAULT_COINS_IN_HEAP = 13;
    public static final String INVALID_MSG = "Invalid input please try again";
//constants for 3 and 13 are required as they are magic numbers
    
    public static void main(String[] args) {
        populateHeapsDefault();
        runGame();
    }
// runGame calls the methods required for the game and checks every turn if a player has won

    public static void runGame() {
        boolean playerWin = false;
        do {
            checkPlayerInputPile(P_1);
            if (Collections.max(heaps) == 0) {
                System.out.println(P_1 + " wins!");
                exit(0);
            } else {
                checkPlayerInputPile(P_2);
                if (Collections.max(heaps) == 0) {
                    playerWin = true;
                    System.out.println(P_2 + " wins!");
                }
            }
        } while (!playerWin);
    }

//fills array with values, note: the constants are defined at the beginning of the program
    public static void populateHeapsDefault() {
        for (int i = 0; i < DEFAULT_NUMBER_HEAPS; i++) {
            heaps.add(DEFAULT_COINS_IN_HEAP);
        }
    }
//prompts user inputs for custom number of heaps and coins and overwrites the array

    public static void populateHeapsCustom() {
        final String HEAP_IN = "Input the number of heaps: ";
        final String COIN_PER_HEAP_IN = "Input the number of coins in each heap";
        int numberOfHeaps = promptIntGreaterThanZero(HEAP_IN);
        int numberOfCoinsInHeap = promptIntGreaterThanZero(COIN_PER_HEAP_IN);
        heaps.clear();
        for (int i = 0; i < numberOfHeaps; i++) {
            heaps.add(numberOfCoinsInHeap);
        }
        runGame();
    }

    public static int promptIntGreaterThanZero(String inputMessage) {
        int userInput;
        do {
            System.out.println(inputMessage);
            while (!in.hasNextInt()) {
                System.out.println("That is not an Integer");
                System.out.println(inputMessage);
                in.nextLine();
            }
            userInput = in.nextInt();
            if (userInput <= 0) {
                System.out.println("please input an integer greater than zero");
            }
        } while (!(userInput > 0));
        return userInput;

    }

    public static void checkPlayerInputPile(String player) {
        boolean integerInput = false;
        boolean validNum = false;
        boolean customActivated = false;
        do {
            System.out.print("Remaining coins: ");
            for (int i = 0; i < heaps.size() - 1; i++) {
                System.out.print(heaps.get(i) + ", ");
            }
            System.out.print(heaps.get(heaps.size() - 1) + "\n");
            System.out.print(player + ": choose a pile: ");
            String playerInput = in.next();
            if (playerInput.equals("custom") && !customActivated) {
                populateHeapsCustom();
                break;
            }
            try {
                pileChosen = Integer.parseInt(playerInput) - 1;
                if (pileChosen >= 0 && pileChosen < heaps.size()) {
                    validNum = true;
                    if (heaps.get(pileChosen) != 0) {
                        integerInput = true;
                    }
                } else {
                    System.out.println(INVALID_MSG);
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a number!");
            }
        } while (!integerInput && !validNum);
        checkPlayerInputCoins();
    }

    public static int checkPlayerInputCoins() {
        boolean integerInput = false;
        int numberOfCoinsChosen = 0;
        /*This do while loop checks for a valid input by changing boolean variable to true if the user input is
a square number, not equal to zero, less than or equal the number of coins in the pile that has been selected
and is an integer
         */
        do {
            System.out.print("Now choose a square number of coins: ");
            String inputValue = in.next();
            try {
                numberOfCoinsChosen = Integer.parseInt(inputValue);
                if (isSquare(numberOfCoinsChosen) && numberOfCoinsChosen <= heaps.get(pileChosen)) {
                    if (numberOfCoinsChosen != 0) {
                        integerInput = true;
                        heaps.set(pileChosen, (heaps.get(pileChosen) - numberOfCoinsChosen));
                    }
                } else {
                    System.out.println(INVALID_MSG);
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter an integer!");
            }
        } while (!integerInput);
        return numberOfCoinsChosen;
    }

    /*This method returns a boolean value true if input is a square number
     */
    public static boolean isSquare(int inputNumberCoins) {
        boolean isSquare = false;
        if (Math.sqrt(inputNumberCoins) % 1 == 0) {
            isSquare = true;
        }
        return isSquare;
    }
}
