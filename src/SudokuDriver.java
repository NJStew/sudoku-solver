import java.util.Scanner;

/**
 * Driver class for Sudoku Solver text game.
 * 
 * @author Noah Stewart
 *
 */
public class SudokuDriver {
  static String difficultyChoice = ""; //Difficulty of Sudoku Board.
  static String heartsLeft = "<3 <3 <3"; //The player's remaining hearts.
  static String playAgain = ""; //Whether player would like to play again or not.
  static Scanner scanner = new Scanner(System.in);
  static SudokuSolver sudokuBoard = new SudokuSolver();
  static String playerCommand = ""; //Command input by player.
  
  /**
   * Starting menu of Sudoku Solver that re-initializes variables upon a replay.
   */
  public static void startMenu() {
    heartsLeft = "<3 <3 <3"; //Re-initialization of heartsLeft.
    playAgain = ""; //Re-initialization of playAgain.
    playerCommand = ""; //Re-initialization of playerCommand.
    difficultyChoice = ""; //Re-initialization of difficultyChoice.
    System.out.println("-----------------------------------------------------------------------");
    System.out.println(
        "   _____           _       _             _____       _                \r\n" + 
        "  / ____|         | |     | |           / ____|     | |               \r\n" + 
        " | (___  _   _  __| | ___ | | ___   _  | (___   ___ | |_   _____ _ __ \r\n" + 
        "  \\___ \\| | | |/ _` |/ _ \\| |/ / | | |  \\___ \\ / _ \\| \\ \\ / / _ \\ '__|\r\n" + 
        "  ____) | |_| | (_| | (_) |   <| |_| |  ____) | (_) | |\\ V /  __/ |   \r\n" + 
        " |_____/ \\____|\\____|\\___/|_|\\_\\\\____| |_____/ \\___/|_| \\_/ \\___|_| ");
    System.out.println("-----------------------------------------------------------------------");
    System.out.println();
    System.out.println("Press Enter to play:");
    scanner.nextLine();
    System.out.println("Select difficulty. Type easy, medium or hard: ");
    //Loop to catch incorrect input.
    while (!difficultyChoice.toLowerCase().equals("easy")) {
      difficultyChoice = scanner.nextLine().toLowerCase();
      if (!difficultyChoice.equals("easy")) {
        System.out.println("Please enter valid choice. Type easy, medium or hard: ");
      }
    }
    System.out.println();
    System.out.println("Instructions:");
    System.out.println("To place numbers use the coordinates found above and on the side of the grids.");
    System.out.println("Guess format example: 1 at 3,3 or choice at top,side");
    System.out.println("If three of your guesses are incorrect, it's game over.");
    System.out.println("Commands: ");
    System.out.println("Hint: h");
    System.out.println("View Remaining Hearts: v");
    System.out.println("Reveal board: r");
    System.out.println("Quit: q");
    System.out.println();
    sudokuBoard.createBoard(difficultyChoice);
    sudokuBoard.printBoard(sudokuBoard.board);
  }
  
  /**
   * Checks to see what the player input and handles the corresponding command.
   */
  public static void userInput() {
    //Loop to hand incorrect input.
    playerCommand = scanner.nextLine().toLowerCase();
    playerCommand = playerCommand.replaceAll(" ", "");
    while (!playerCommand.equals("h") && !playerCommand.equals("r") && !playerCommand.equals("q") && !playerCommand.equals("v") && validateCommand() != true) {
      if (!playerCommand.equals("h") && !playerCommand.equals("r") && !playerCommand.equals("q") && !playerCommand.equals("v") && validateCommand() != true) {
        System.out.println("Please enter valid command");
        playerCommand = scanner.nextLine().toLowerCase();
        playerCommand = playerCommand.replaceAll(" ", "");
      }
    }
    //Checks if command is hint. If so, places a hint onto the board and prints where it was placed.
    if (playerCommand.equals("h")) {
      sudokuBoard.giveHint();
      sudokuBoard.printBoard(sudokuBoard.board);
      return;
    }
    //Checks if command is view hearts. If so, shows remaining hearts.
    else if (playerCommand.equals("v")) {
      System.out.println();
      System.out.println("Hearts Remaining: " + heartsLeft);
      System.out.println();
      sudokuBoard.printBoard(sudokuBoard.board);
      return;
    }
    //Checks if command is reveal board. If so, reveals game board and ends game.
    else if (playerCommand.equals("r")) {
      sudokuBoard.autoComplete(sudokuBoard.board);
      System.out.println();
      System.out.println("The correct board was: ");
      sudokuBoard.printBoard(sudokuBoard.board);
      heartsLeft = "Game Over";
      gameOver();
      return;
    }
    //Checks if command was quit. If so, quits game.
    else if (playerCommand.equals("q")) {
      System.out.println();
      System.out.println("Thanks for playing!");
      return;
    }
    //Checks to see if command was a valid placement command by calling validateCommand(). If so, attempts to place number into board.
    else if (validateCommand() == true) {
      //Checks if number placement was incorrect. If so, player loses a heart and board gets re-printed.
      if (sudokuBoard.placeNum(sudokuBoard.board, Character.getNumericValue(playerCommand.charAt(5)), Character.getNumericValue(playerCommand.charAt(3)), Character.getNumericValue(playerCommand.charAt(0))) == false) {
        System.out.println();
        System.out.println("Incorrect!");
        lostHeart();
        sudokuBoard.printBoard(sudokuBoard.board);
      }
      //If number placement was correct prints correct and the new board.
      else {
        System.out.println();
        System.out.println("Correct!");
        System.out.println();
        sudokuBoard.printBoard(sudokuBoard.board);
      }
    }
  }
  
  /**
   * Checks to see if userCommand was a valid placement command.
   * 
   * @return true if command is valid, false otherwise.
   */
  public static boolean validateCommand() {
    //Checks if userCommand is the expected length. If not, returns false.
    if (playerCommand.length() != 6) {
      return false;
    }
    //Checks if the number being placed in the board is within the appropriate range. If not, returns false.
    else if (Character.getNumericValue(playerCommand.charAt(0)) < 0 && Character.getNumericValue(playerCommand.charAt(0)) > 9) {
      return false;
    }
    //Checks if userCommand contains at and a comma to seperate number coordinates. If not, returns false.
    else if (playerCommand.charAt(1) != 'a' && playerCommand.charAt(2) != 't' && playerCommand.charAt(3) != ',') {
      return false;
    }
    //Checks if the first coordinate position is within the appropriate range. If not, returns false.
    else if (Character.getNumericValue(playerCommand.charAt(3)) < 0 && Character.getNumericValue(playerCommand.charAt(3)) > 9) {
      return false;
    }
    //Checks if the second coordinate position is within the appropriate range. If not, returns false.
    else if (Character.getNumericValue(playerCommand.charAt(5)) < 0 && Character.getNumericValue(playerCommand.charAt(5)) > 9) {
      return false;
    }
    return true;
  }
  
  /**
   * Method for handling the loss of hearts every time an incorrect placement is made.
   */
  public static void lostHeart() {
    System.out.println();
    //Checks if player has 3 hearts left. If so, removes one heart.
    if (heartsLeft.equals("<3 <3 <3")) {
      heartsLeft = "<3 <3";
    }
    //Checks if player has 2 hearts left. If so, removes one heart.
    else if (heartsLeft.equals("<3 <3")) {
      heartsLeft = "<3";
    }
    //If player has one heart left changes hearts to Game Over and returns for game to be ended.
    else {
      heartsLeft = "Game Over";
      System.out.println(heartsLeft);
      return;
    }
    System.out.println("Hearts Remaining: ");
    System.out.println(heartsLeft);
    System.out.println();
  }
  
  /**
   * Method for handling when a player loses or wins.
   */
  public static void gameOver() {
    System.out.println();
    System.out.println(heartsLeft);
    System.out.println("Would you like to play again? Yes or No?");
    playAgain = scanner.nextLine().toLowerCase();
    //Loop to catch incorrect input. 
    while (!playAgain.equals("yes") && !playAgain.equals("no")) {
      if (!playAgain.equals("yes") && !playAgain.equals("no")) {
        System.out.println("Please enter valid choice. Type yes or no: ");
        playAgain = scanner.nextLine().toLowerCase();
      }
    }
    //Checks if player selected not to play again. If so, prints thanks for playing.
    if (playAgain.equals("no")) {
      System.out.println();
      System.out.println("Thanks for playing!");
      playerCommand = "q";
    }
    //Checks if player selected yes to play again. If so, prints loading another game.
    else if (playAgain.equals("yes")) {
      System.out.println();
      System.out.println("Loading another game...");
    }
    return;
  }
  
  /**
   * Main method for calling all previous methods and playing the Sudoku Game.
   */
  public static void main(String[] args) {
    //Initializes new game
    while (!playerCommand.equals("q")) {
      startMenu();
      //Begins player input to game
      while (!playAgain.equals("yes")) {
        //Checks if player has lost game.
        if (heartsLeft.equals("Game Over")) {
          //Calls gamerOver() to check if player would like to play again.
          gameOver();
          //If player does not want to play again, ends game.
          if (playAgain.equals("no")) {
            return;
          }
          //If player does want to play again, resets game.
          else if (playAgain.equals("yes")) {
            playAgain = "yes";
          }
        }
        //Checks if player has won the game. If so, prints you won asks player if they would like to play again.
        else if (sudokuBoard.boardComplete() == true && !heartsLeft.equals("Game Over")) {
          System.out.println("Congratulations!");
          heartsLeft = "You won!";
          gameOver();
        }
        //Takes input from player.
        else {
          userInput();
          //Checks if player input was to quit.
          if (playerCommand.equals("q")) {
            return;
          }
        }
      }
    }
  }
}