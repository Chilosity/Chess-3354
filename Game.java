// Import the Scanner class for reading user inputs
import java.util.Scanner;

// Import custom classes for the game components
import Pieces.*;
import Player;
import Board;
import ChessGUI;

// Define the public class Game
public class Game
{
  // The main method, where the program execution begins
  public static void main(String[] args) 
  {
    // Instantiate the Board object to hold the state of the chess game
    Board board = new Board();

    // Create an instance of the GUI, passing the board object to visualize the game
    ChessGUI gui = new ChessGUI(board);
  
    // Display the GUI
    gui.showChessGUI();
  
    // Initialize the Scanner object to read from the standard input
    Scanner scanner = new Scanner(System.in);
  
    // Variable to keep track of whose turn it is; true for white, false for black
    boolean whiteTurn = true;
    
    // Infinite loop to keep the game running until manually exited
    while (true) {
      // Refresh the GUI to reflect the current board state
      gui.updateBoardGUI();

      // Determine the current player's color based on turn
      char currentPlayerColor = whiteTurn ? 'w' : 'b';

      // Prompt the player to enter a move or type 'exit' to end the game
      System.out.print("Enter your move (or 'exit' to end the game): ");
      String move = scanner.nextLine();

      // Check if the input is "exit" to end the game
      if (move.equals("exit")) {
          System.out.println("Exiting the game. Goodbye!");
          break; // Exit the loop and end the game
      }
      
      // Split the entered move into source and destination positions
      String[] moveParts = move.split(" ");
      String sourcePosition = moveParts[0];
      String destPosition = moveParts[1];
      
      // Validate move format (should be two characters: one letter, one number)
      if (sourcePosition.length() != 2 || destPosition.length() != 2) {
          System.out.println("Invalid move format. Please enter positions as a letter followed by a number, e.g., 'E4 E5'.");
          continue; // Skip the rest of the loop and prompt for another input
      }

      // Convert the source and destination positions from algebraic notation to board indices
      int sourceCol = sourcePosition.charAt(0) - 'A';
      int sourceRow = sourcePosition.charAt(1) - '1';
      int destCol = destPosition.charAt(0) - 'A';
      int destRow = destPosition.charAt(1) - '1';
      

      // Retrieve the piece at the specified source position
      Piece piece = board.getPiece(sourceRow, sourceCol);
      
      // Ensure the correct player is moving and the piece at the source is valid
      if (piece != null && piece.getColor() == currentPlayerColor) {
          // Check if the move is legal for the selected piece
          if (piece.possibleMoves(sourceRow, sourceCol, destRow, destCol)) {
              // Make the move on the board
              board.movePiece(piece, destRow, destCol);
              // Toggle the turn to the next player
              whiteTurn = !whiteTurn;
          } else {
              // Inform player the move was illegal
              System.out.println("Invalid move. Please try again.");
          }
      } else {
          // Inform player it's not their turn or no piece is at the source
          System.out.println("It is not your turn or no valid piece at source. Please try again.");
          continue;
      }
    }

    // Close the scanner to prevent resource leaks
    scanner.close();
  }
}
