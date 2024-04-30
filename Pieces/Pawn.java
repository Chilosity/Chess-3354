// Define the package this class belongs to
package Pieces;

// Import necessary classes
import board.Board;

// Define the public class Pawn, which extends the Piece class
public class Pawn extends Piece {
    private Board board; // Reference to the chessboard, used to check moves

    // Constructor for initializing a Pawn piece
    public Pawn(char color, int row, int col, Board board) {
        super(color, 'P', row, col); // Call the superclass constructor with the character for Pawn ('P')
        this.board = board; // Store the reference to the board
    }

    // Override the possibleMoves method to define how the Pawn can move
    @Override
    public boolean possibleMoves(int oldRow, int oldCol, int newRow, int newCol) {
        int rowDiff = newRow - oldRow; // Calculate the difference in rows
        int colDiff = Math.abs(newCol - oldCol); // Calculate the absolute difference in columns

        // Check if the move is within the pawn's movement capabilities
        if (colDiff == 0) { // Movement along the same column (straight move)
            // Check if the new row is greater than the old row for white pawns
            if (getColor() == 'w' && rowDiff > 0) {
                // Check if the pawn is moving one square forward
                if (rowDiff == 1) {
                    // Check if there's a piece in the destination
                    Piece pieceAtDestination = board.getPiece(newRow, newCol);
                    return pieceAtDestination == null; // Valid move if no piece is in the destination
                }
                // Check if the pawn is moving two squares forward from its starting position
                else if (rowDiff == 2 && oldRow == 1) {
                    // Check if there's a piece in the intermediate square and the destination
                    Piece pieceAtIntermediate = board.getPiece(oldRow + 1, oldCol);
                    Piece pieceAtDestination = board.getPiece(newRow, newCol);
                    return pieceAtIntermediate == null && pieceAtDestination == null; // Valid if both squares are empty
                }
            }
            // Check if the new row is less than the old row for black pawns
            else if (getColor() == 'b' && rowDiff < 0) {
                // Check if the pawn is moving one square forward
                if (rowDiff == -1) {
                    // Check if there's a piece in the destination
                    Piece pieceAtDestination = board.getPiece(newRow, newCol);
                    return pieceAtDestination == null; // Valid move if no piece is in the destination
                }
                // Check if the pawn is moving two squares forward from its starting position
                else if (rowDiff == -2 && oldRow == 6) {
                    // Check if there's a piece in the intermediate square and the destination
                    Piece pieceAtIntermediate = board.getPiece(oldRow - 1, oldCol);
                    Piece pieceAtDestination = board.getPiece(newRow, newCol);
                    return pieceAtIntermediate == null && pieceAtDestination == null; // Valid if both squares are empty
                }
            }
        }
        // Check for valid diagonal capture
        else if ((rowDiff == 1 && colDiff == 1 && getColor() == 'w') || (rowDiff == -1 && colDiff == 1 && getColor() == 'b')) {
            // Check if there's an opponent's piece in the destination
            Piece pieceAtDestination = board.getPiece(newRow, newCol);
            if (pieceAtDestination != null && pieceAtDestination.getColor() != getColor()) {
                return true; // Valid capture move
            }
        }

        return false; // Return false if none of the conditions for a valid move are met
    }
}
