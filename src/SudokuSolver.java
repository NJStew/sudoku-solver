import java.util.concurrent.ThreadLocalRandom;

/**
 * Class for performing operations on a Sudoku board.
 * 
 * @author Noah Stewart
 *
 */
public class SudokuSolver {
  public int[][] board;
  
  /**
   * Initializes Sudoku board based on the input difficulty.
   * 
   * @param difficulty - Level of difficulty of board to be created
   */
  public void createBoard(String difficulty) {
    if (difficulty.equals("easy")) {
      board = new int[][] {
        {0, 0, 2, 0, 0, 0, 5, 0, 0},
        {0, 1, 0, 7, 0, 5, 0, 2, 0}, 
        {4, 0, 0, 0, 9, 0, 0, 0, 7},
        {0, 4, 9, 0, 0, 0, 7, 3, 0},
        {8, 0, 1, 0, 3, 0, 4, 0, 9},
        {0, 3, 6, 0, 0, 0, 2, 1, 0},
        {2, 0, 0, 0, 8, 0, 0, 0, 4},
        {0, 8, 0, 9, 0, 2, 0, 6, 0},
        {0, 0, 7, 0, 0, 0, 8, 0, 0}
      };
    }
    else if (difficulty.equals("medium")) {
      board = new int[][] {
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 7, 9, 0, 5, 0, 1, 8, 0},
        {8, 0, 0, 0, 0, 0, 0, 0, 7},
        {0, 0, 7, 3, 0, 6, 8, 0, 0},
        {4, 5, 0, 7, 0, 8, 0, 9, 6},
        {0, 0, 3, 5, 0, 2, 7, 0, 0},
        {7, 0, 0, 0, 0, 0, 0, 0, 5},
        {0, 1, 6, 0, 3, 0, 4, 2, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0}
      };
    }
    else if (difficulty.equals("hard")) {
      board = new int[][] {
        {0, 0, 0, 0, 0, 3, 0, 1, 7}, 
        {0, 1, 5, 0, 0, 9, 0, 0, 8},
        {0, 6, 0, 0, 0, 0, 0, 0, 0}, 
        {1, 0, 0, 0, 0, 7, 0, 0, 0}, 
        {0, 0, 9, 0, 0, 0, 2, 0, 0}, 
        {0, 0, 0, 5, 0, 0, 0, 0, 4}, 
        {0, 0, 0, 0, 0, 0, 0, 2, 0}, 
        {5, 0, 0, 6, 0, 0, 3, 4, 0}, 
        {3, 4, 0, 2, 0, 0, 0, 0, 0} 
      };
    }
  }
  
  /**
   * Prints contents of board.
   * 
   * @param sudokuBoard - The board being printed.
   */
  public void printBoard(int[][] sudokuBoard) {
    System.out.println("1 2 3  4 5 6  7 8 9"); 
    System.out.println("_ _ _  _ _ _  _ _ _"); 
    for (int i = 0; i < sudokuBoard.length; i++) {
      //Creates line break between top, middle, and bottom grids.
      if (i % 3 == 0 && i != 0) {
        System.out.println();
      }
      for (int j = 0; j < sudokuBoard.length + 1; j++) {
        //Creates line break to format 9x9 area.
        if (j == 9) {
          System.out.print("|" + "  " + (i + 1));
          System.out.println();
        }
        else {
          //Creates a space to format 3x3 grids.
          if (j % 3 == 0 && j != 0) {
            System.out.print(" ");
          }
          System.out.print(sudokuBoard[i][j] + " ");
        }
      }
    }
  }
  
  /**
   * Places a number into board if it is a valid placement.
   * 
   * @param sudokuBoard - board for number to be placed in.
   * @param row - row for num to be placed in.
   * @param col - column for num to be placed in.
   * @param num - number to be placed in row and column.
   * @return true if number was successfully placed, false otherwise.
   */
  public boolean placeNum(int[][] sudokuBoard, int row, int col, int num) {
    //Check if num placement is valid.
    if (validMove(sudokuBoard, row - 1, col - 1, num) == true) {
      sudokuBoard[row - 1][col - 1] = num;
      return true;
    }
    else {
      return false;
    }
  }
  
  /**
   * Method for checking if a Sodoku move is valid.
   * 
   * @param sudokuBoard - the current board
   * @param row - the row of the sudokuBoard[][]
   * @param col - the column of the sudokuBoard[][]
   * @param num - the number to be placed in the sudokuBoard[][].
   * @return false if move is invalid, true otherwise.
   */
  private boolean validMove(int[][] sudokuBoard, int row, int col, int num) {
    //Checks if column contains same int.
    for (int i = 0; i < sudokuBoard.length; i++) {
      if (sudokuBoard[row][i] == num) {
        return false;
      }
    }
    //Checks if row contains same int.
    for (int i = 0; i < sudokuBoard.length; i++) {
      if (sudokuBoard[i][col] == num) {
        return false;
      }
    }
    //Checks if 3x3 grid contains the same int.
    for (int i = 0; i < sudokuBoard.length / 3; i ++) {
      for (int j = 0; j < sudokuBoard.length / 3; j++) {
        if (sudokuBoard[((row / 3) * 3) + i][((col / 3) * 3) + j] == num) {
          return false;
        }
      }
    }
    return true;
  }
  
  /**
   * Auto completes board by cycling through every position, putting an integer into it, 
   * calling validMove(), and recursively calling itself.
   * 
   * @param sudokuBoard - the board to be auto completed.
   * @return true when board is solved.
   */
  public boolean autoComplete(int[][] sudokuBoard) {
    for (int i = 0; i < sudokuBoard.length; i++) {
      for (int j = 0; j < sudokuBoard.length; j++) {
        if (sudokuBoard[i][j] == 0) {
          for (int k = 1; k < 10; k++) {
            //Checks if move is valid before placing it into board.
            if (validMove(sudokuBoard, i, j, k) == true) {
              sudokuBoard[i][j] = k;
              if (autoComplete(sudokuBoard)) {
                return true;
              }
              else {
                sudokuBoard[i][j] = 0;
              }
            }
          }
          return false;
        }
      }
    }
    return true;
  }
  
  /**
   * Randomly selects one correctly placed number to be put in board as a hint and prints the coordinates
   * of where it was placed.
   */
  public void giveHint() {
    int[][] hintBoard = new int[9][9];
    //Copies board elements to hintBoard.
    for (int i = 0; i < hintBoard.length; i++) {
      for (int j = 0; j < hintBoard.length; j++) {
        hintBoard[i][j] = board[i][j];
      }
    }
    autoComplete(hintBoard);
    int randRow = 1;
    int randCol = 1;
    //Finds a random position on the board that does not have a number placed.
    while (board[randRow][randCol] != 0) {
      randRow = ThreadLocalRandom.current().nextInt(0, 9);
      randCol = ThreadLocalRandom.current().nextInt(0, 9);
      //Places random correct number from hintBoard to board.
      if (board[randRow][randCol] == 0) {
        board[randRow][randCol] = hintBoard[randRow][randCol];
        System.out.println();
        System.out.println("Hint placed at: " + (randCol + 1) + "," + (randRow + 1));
        return;
      }
    }
  }
  
  /**
   * Checks if board has been completed.
   * 
   * @return true if board is complete, false otherwise.
   */
  public boolean boardComplete() {
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board.length; j++) {
        if (board[i][j] == 0) {
          return false;
        }
      }
    }
    return true;
  }
}
