// Define the package this class belongs to
package Pieces;

// Import necessary classes
import board.Board;

// Define the public class Bishop, which extends the Piece class
public class Bishop extends Piece {
    private Board board; // Reference to the chessboard, used to check moves

    // Constructor for initializing a Bishop piece
    public Bishop(char c, int row, int col, Board board) {
        super(c, 'B', row, col); // Call the superclass constructor with the character for Bishop ('B')
        this.board = board; // Store the reference to the board
    }

    // Override the possibleMoves method to define how the Bishop can move
    @Override
    public boolean possibleMoves(int oldRow, int oldCol, int newRow, int newCol) {
        int rowDiff = Math.abs(newRow - oldRow); // Calculate the absolute difference in rows
        int colDiff = Math.abs(newCol - oldCol); // Calculate the absolute difference in columns

        // Check if the move follows the diagonal pattern of equal row and column differences
        if (rowDiff == colDiff) {
            // Early check: Ensure the destination square does not have a piece of the same color
            Piece pieceAtDestination = board.getPiece(newRow, newCol);
            if (pieceAtDestination != null && pieceAtDestination.getColor() == this.getColor()) {
                return false; // Cannot capture your own piece
            }

            // Check for collisions along the diagonal path
            int rowDirection = (newRow > oldRow) ? 1 : -1; // Determine the direction of movement for rows
            int colDirection = (newCol > oldCol) ? 1 : -1; // Determine the direction of movement for columns
            int currentRow = oldRow + rowDirection;
            int currentCol = oldCol + colDirection;
            while (currentRow != newRow && currentCol != newCol) {
                Piece pieceAtPosition = board.getPiece(currentRow, currentCol);
                if (pieceAtPosition != null) {
                    return false; // Collision detected: Another piece is blocking the path
                }
                // Move along the diagonal path
                currentRow += rowDirection;
                currentCol += colDirection;
            }
            // No collisions detected along the path, the move is valid
            return true;
        }

        // If the row and column differences are not equal, it's not a valid diagonal move
        return false;
    }
}  // End of Bishop class
