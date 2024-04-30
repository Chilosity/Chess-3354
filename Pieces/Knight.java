// Define the package this class belongs to
package Pieces;

// Import necessary classes
import board.Board;

// Define the public class Knight, which extends the Piece class
public class Knight extends Piece {
    private Board board; // Reference to the chessboard, used to check moves

    // Constructor for initializing a Knight piece
    public Knight(char c, int row, int col, Board board) {
        super(c, 'N', row, col); // Call the superclass constructor with the character for Knight ('N')
        this.board = board; // Store the reference to the board
    }

    // Override the possibleMoves method to define how the Knight can move
    @Override
    public boolean possibleMoves(int oldRow, int oldCol, int newRow, int newCol) {
        int rowDiff = Math.abs(newRow - oldRow); // Calculate the absolute difference in rows
        int colDiff = Math.abs(newCol - oldCol); // Calculate the absolute difference in columns

        // Check if the move conforms to the valid movement pattern of a knight
        // Knights move in an "L" shape: two squares in one direction and one square perpendicular
        if ((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2)) {
            // Check if there is a collision with a piece of the same color at the destination
            Piece pieceAtDestination = board.getPiece(newRow, newCol);
            if (pieceAtDestination == null || pieceAtDestination.getColor() != getColor()) {
                // Valid move: Either the destination is empty or contains an opponent's piece
                return true;
            }
        }
        // If the conditions are not met, the move is invalid
        return false; // Invalid move due to collision with a piece of the same color or incorrect movement pattern
    }
}  // End of Knight class
