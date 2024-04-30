// Define the package this class belongs to
package Pieces;

// Import necessary classes
import board.Board;

// Define the public class Queen, which extends the Piece class
public class Queen extends Piece {
    private Board board; // Reference to the chessboard, used to check moves

    // Constructor for initializing a Queen piece
    public Queen(char c, int row, int col, Board board) {
        super(c, 'Q', row, col); // Call the superclass constructor with the character for Queen ('Q')
        this.board = board; // Store the reference to the board
    }

    // Override the possibleMoves method to define how the Queen can move
    @Override
    public boolean possibleMoves(int oldRow, int oldCol, int newRow, int newCol) {
        int rowDiff = Math.abs(newRow - oldRow); // Calculate the absolute difference in rows
        int colDiff = Math.abs(newCol - oldCol); // Calculate the absolute difference in columns

        // Check if the target square has a piece of the same color
        Piece pieceAtDestination = board.getPiece(newRow, newCol);
        if (pieceAtDestination != null && pieceAtDestination.getColor() == this.getColor()) {
            return false; // Cannot capture your own piece
        }

        // Check for valid horizontal or vertical movement
        if (oldRow == newRow || oldCol == newCol) {
            int stepRow = (oldRow == newRow) ? 0 : Integer.signum(newRow - oldRow); // Determine the direction of the row movement
            int stepCol = (oldCol == newCol) ? 0 : Integer.signum(newCol - oldCol); // Determine the direction of the column movement
            for (int i = 1; i < (oldRow == newRow ? colDiff : rowDiff); i++) {
                int currentRow = oldRow + stepRow * i;
                int currentCol = oldCol + stepCol * i;
                if (board.getPiece(currentRow, currentCol) != null) {
                    return false; // Collision with a piece along the path
                }
            }
            return true; // No collision, valid move
        }
        // Check for valid diagonal movement
        else if (rowDiff == colDiff) {
            int stepRow = Integer.signum(newRow - oldRow); // Determine the direction of the diagonal row movement
            int stepCol = Integer.signum(newCol - oldCol); // Determine the direction of the diagonal column movement
            for (int i = 1; i < rowDiff; i++) {
                int currentRow = oldRow + stepRow * i;
                int currentCol = oldCol + stepCol * i;
                if (board.getPiece(currentRow, currentCol) != null) {
                    return false; // Collision with a piece along the diagonal path
                }
            }
            return true; // No collision, valid move
        }

        return false; // Invalid move, if none of the valid conditions are met
    }
}  // End of Queen class
