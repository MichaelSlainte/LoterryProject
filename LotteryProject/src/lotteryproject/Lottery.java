/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lotteryproject;

/**
 * @author aleksandro
 * @author michael
 * @author nuno
 */
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author aleksandro
 * @author michael
 * @author nuno
 */
public class Lottery {

    //declaring variables that are going to be used globaly, for more than one method
    //and variables that need to keep information in RAM until the user resolves exit the program
    private final int randomNumbers[] = new int[6];
    private final int userNumbers[] = new int[6];
    private final int history[][] = new int[100][4];
    private final int historyPerGame[][] = new int[3][2];
    private int gameNum = 0;
    private int gameLine = 0;
    private int totalWon = 0;
    private int linesWon = 0;
    private int partialResult = 0;
    private boolean showPartialResult = false;

    //standard constructor
    public void Lottery() {

    }

    //method menu is always called to initialize a new game or to give the user options to proceed
    public void menu() {
        Scanner input = new Scanner(System.in);
        char option;
        char optionUpper;

        //if this is the first game show this menu
        if (gameNum == 0) {
            System.out.println("This is the Green luck game, choose an option"
                    + "\nP - play"
                    + "\nE - exit");
            option = input.next().charAt(0);
            optionUpper = Character.toUpperCase(option);
            switch (optionUpper) {
                case 'P':
                    this.userGame();
                    break;
                case 'E':
                    System.out.println("\nThanks for playing! See you next time :)");
                    break;
                default:
                    System.out.println("Invalid option\n");
                    this.menu();
            }

            //if this is still into 3 lines of each game show this menu
        } else if (gameLine != 0 && gameLine < 3) {
            System.out.println("\nWhat do you want to do now?"
                    + "\nP - play another line"
                    + "\nE - exit game");
            option = input.next().charAt(0);
            optionUpper = Character.toUpperCase(option);
            switch (optionUpper) {
                case 'P':
                    this.userGame();
                    break;
                case 'E':
                    //show result for current game
                    partialResult++;
                    this.result();
                    break;
                default:
                    System.out.println("Invalid option\n");
                    this.menu();
            }

            //if the user have already played 3 lines, show this menu
        } else {

            if (showPartialResult) {
                partialResult++;
                this.result();

            } else {

                System.out.println("\nWhat do you want to do now?"
                        + "\nP - play another game"
                        + "\nH - see history of games"
                        + "\nE - exit");
                option = input.next().charAt(0);
                optionUpper = Character.toUpperCase(option);
                switch (optionUpper) {
                    case 'P':
                        this.userGame();
                        break;
                    case 'E':
                        System.out.println("\nThanks for playing! See you next time :)");
                        break;
                    case 'H':
                        this.result();
                        break;
                    default:
                        System.out.println("Invalid option\n");
                        this.menu();

                }
            }
        }

    }

    //method generateNumbers() is used to generate lottery random numbers
    public void generateNumbers() {

        //create array with sorted numbers - lottery
        Random rand = new Random();
        int num;
        for (int i = 0; i < randomNumbers.length; i++) {
            //rand + 1 garantees 0 will never be stored
            num = rand.nextInt(40) + 1;
            for (int j = 0; j < randomNumbers.length; j++) {
                //compare number to not store duplicates
                if (num == randomNumbers[j] && j != i) {
                    num = rand.nextInt(40) + 1;
                } else {
                    randomNumbers[i] = num;
                }
            }
        }
    }

    //method userGame is used to store the numbers user are going to input while palying the game
    public void userGame() {

        //variable that's going to store the numbers typed by user
        int noZero = 0;

        Scanner input = new Scanner(System.in);

        //verify if that's the first game or if the user has already played 3 lines
        if (gameNum == 0 || gameLine == 0) {

            //set game line back to 0 to start a new game
            gameLine = 0;

            //generate lottery numbers for new game
            this.generateNumbers();

            //store game number to show in history
            gameNum++;

            //gets first line of user numbers
            System.out.println("\nEnter the numbers (1-40) for line " + (gameLine + 1));

            for (int i = 0; i < userNumbers.length; i++) {
                noZero = input.nextInt();
                //user cannot type <=0 or >40
                if (noZero <= 0 || noZero > 40) {
                    System.out.println("Valid numbers between 1-40, no duplicates");
                    i--;
                } else {
                    //user cannot type duplicates or numbers outside range
                    int j;
                    for (j = 0; j < i; j++) {

                        while (userNumbers[j] == noZero || noZero <= 0 || noZero > 40) {
                            System.out.println("Valid numbers between 1-40, no duplicates");
                            noZero = input.nextInt();
                            j = 0;
                        }
                    }
                    //passed all conditions, number enters array
                    userNumbers[i] = noZero;
                }
            }
            //gets second and third line of user numbers
        } else {
            //get other lines of user numbers
            System.out.println("\nEnter the numbers (1-40) for line " + (gameLine + 1));

            for (int i = 0; i < userNumbers.length; i++) {
                noZero = input.nextInt();
                //user cannot type <=0 or >40
                if (noZero <= 0 || noZero > 40) {
                    System.out.println("Valid numbers between 1-40, no duplicates");
                    i--;
                } else {
                    //user cannot type duplicates or numbers outside range
                    int j;
                    for (j = 0; j < i; j++) {

                        while (userNumbers[j] == noZero || noZero <= 0 || noZero > 40) {
                            System.out.println("Valid numbers between 1-40, no duplicates");
                            noZero = input.nextInt();
                            j = 0;
                        }
                    }
                    //passed all conditions, number enters array
                    userNumbers[i] = noZero;
                }
            }
        }
        //store line number to show to user
        gameLine++;

        //compare user number with lottery numbers
        this.compare();

        //set showPartialResult for when it goes back to the menu
        if (gameLine == 3) {
            showPartialResult = true;
        }

        //call menu to give options to the user
        this.menu();

    }

    //method compare will compare lottery numbers with user numbers and store the values won
    public void compare() {

        //declare variables that are going to be used localy
        int guessed = 0;
        int random = 0;
        int user = 0;

        //compare user numbers with lottery numbers
        for (int i = 0; i < randomNumbers.length; i++) {
            random = this.randomNumbers[i];

            for (int j = 0; j < userNumbers.length; j++) {
                user = this.userNumbers[j];

                if (random == user) {
                    guessed++;
                }
            }
        }

        int numbersGuessedPerLine[] = {gameLine, guessed};

        for (int bla = 0; bla < historyPerGame[0].length; bla++) {
            historyPerGame[(gameLine - 1)][bla] = numbersGuessedPerLine[bla];
        }

        //store values won if the user has guessed numbers
        if (guessed != 0) {
            switch (guessed) {
                case 3:
                    totalWon = 9;
                    linesWon++;
                    break;
                case 4:
                    totalWon = 54;
                    linesWon++;
                    break;
                case 5:
                    totalWon = 1000;
                    linesWon++;
                    break;
                case 6:
                    totalWon = 5000;//symbolic value for won the lottery
                    linesWon++;
                    break;
                default:
                    break;
            }
        }

        //filling up array history of games
        int gameHistory[] = {gameNum, gameLine, linesWon, totalWon};

        for (int col = 0; col < gameHistory.length; col++) {
            history[(gameNum - 1)][col] = gameHistory[col];
        }

    }

    //method result will show results stored to the user at anytime they want it
    public void result() {

        if (partialResult != 0) {
            System.out.println("\nResult Game #" + gameNum);
            System.out.print("Line | Numbers Guessed\n");
            for (int i = 0; i < gameLine; i++) {

                for (int j = 0; j < historyPerGame[0].length; j++) {
                    System.out.print("" + historyPerGame[i][j]);
                    System.out.print("        ");

                }
                System.out.println("");
                //if the user guesses 6 numbers, print You won the lottery
                if (historyPerGame[i][1] == 6) {
                    System.out.println("You won the lottery!!!");

                }
            }
            partialResult = 0;
            gameLine = 0;
            showPartialResult = false;
        } else {
            System.out.println("\n***Game History***");
            System.out.print("Game# | L.Played | L. Won | Total Won\n");
            for (int lin = 0; lin < (gameNum); lin++) {
                for (int col = 0; col < history[0].length; col++) {
                    System.out.print("" + history[lin][col]);
                    System.out.print("        ");

                }
                System.out.println("");

            }

        }
        this.menu();
    }
}
