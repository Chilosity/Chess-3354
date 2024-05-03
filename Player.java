// Define the package this class belongs to
package player;

// Import necessary classes
import Board;
import Pieces.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Define the public class Player to represent a chess player
public class Player {
    private char color; // Character representing the player's color: 'w' for white, 'b' for black
    private List<Piece> availablePieces; // List to store the pieces available to the player

    // Constructor to initialize a Player with a specified color
    public Player(char color) {
        this.color = color; // Set the player's color
        this.availablePieces = new ArrayList<>(); // Initialize the list of available pieces
    }

    // Method to add a piece to the player's collection of available pieces
    public void addPiece(Piece piece) {
        availablePieces.add(piece); // Add the specified piece to the list
    }

    // Method for the player to perform a move on the board
    public void makeMove(Board board) {
        // Initialize a Scanner object to read from standard input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your move (e.g., 'E2 E4'): "); // Prompt the user to enter a move
        String move = scanner.nextLine(); // Read the move as a line of text
        scanner.close();  // Close the scanner to avoid resource leaks

        // Split the move string into source and destination positions
        String[] moveParts = move.split(" ");
        if (moveParts.length < 2) {
            System.out.println("Invalid move format. Please enter positions as two parts (e.g., 'E2 E4').");
            return;  // Return early if the move format is incorrect
        }
        String sourcePosition = moveParts[0]; // Extract the source position from the move string
        String destPosition = moveParts[1]; // Extract the destination position from the move string

        // Convert algebraic notation positions to row and column indices
        int sourceRow = 8 - (sourcePosition.charAt(1) - '0');
        int sourceCol = sourcePosition.charAt(0) - 'A';
        int destRow = 8 - (destPosition.charAt(1) - '0');
        int destCol = destPosition.charAt(0) - 'A';

        // Retrieve the piece at the specified source position
        Piece piece = board.getPiece(sourceRow, sourceCol);
        if (piece != null) { // Check if there is a piece at the source
            if (piece.possibleMoves(sourceRow, sourceCol, destRow, destCol)) { // Check if the move is possible
                board.movePiece(piece, destRow, destCol); // Execute the move on the board
                System.out.println("Move successful.");
            } else {
                System.out.println("Move invalid. Try again."); // Inform the user the move was invalid
            }
        } else {
            System.out.println("No piece at the source position."); // Inform the user there is no piece at the source
        }
    }
}  // End of Player class
