// Define the package this class belongs to
package Pieces;

// Import necessary classes
import board.Board;

// Define the public class Rook, which extends the Piece class
public class Rook extends Piece {
    private Board board; // Reference to the chessboard, used to check moves

    // Constructor for initializing a Rook piece
    public Rook(char c, int row, int col, Board board) {
        super(c, 'R', row, col); // Call the superclass constructor with the character for Rook ('R')
        this.board = board; // Store the reference to the board
    }

    // Override the possibleMoves method to define how the Rook can move
    @Override
    public boolean possibleMoves(int oldRow, int oldCol, int newRow, int newCol) {
        // Rook moves vertically or horizontally
        if (oldRow != newRow && oldCol != newCol) {
            return false; // Rooks do not move diagonally
        }

        // Determine the step direction for iteration over squares
        int stepRow = Integer.signum(newRow - oldRow); // Will be -1, 0, or 1, depending on the direction
        int stepCol = Integer.signum(newCol - oldCol); // Will be -1, 0, or 1, depending on the direction

        // Check each square between the current position and the target position
        int currentRow = oldRow + stepRow;
        int currentCol = oldCol + stepCol;
        while (currentRow != newRow || currentCol != newCol) {
            Piece pieceAtCurrent = board.getPiece(currentRow, currentCol);
            if (pieceAtCurrent != null) {
                return false; // There is a piece in the way, so the move is invalid
            }
            // Increment the row and column indices to move towards the target
            currentRow += stepRow;
            currentCol += stepCol;
        }

        // Check the final destination square for a piece of the same color
        Piece pieceAtDestination = board.getPiece(newRow, newCol);
        if (pieceAtDestination != null && pieceAtDestination.getColor() == getColor()) {
            return false; // Cannot capture a piece of the same color
        }

        return true; // Valid move if no pieces are blocking the path and no same-color capture
    }
}  // End of Rook class
