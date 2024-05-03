// Define the package this class belongs to
package board;

// Import necessary classes for handling pieces
import Pieces.*;

// Define the public class Board to represent the chess game board
public class Board {
    // Declare a 2D array to represent the 8x8 chessboard
    Piece[][] board = new Piece[8][8];

    // Constructor for initializing the Board
    public Board() {
        // Initialize all positions on the board to null (empty)
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = null;
            }
        }

        // Place white pieces at their initial positions
        board[0][0] = new Rook('w', 0, 0, this); // Place a white Rook at A1
        board[0][1] = new Knight('w', 0, 1, this); // Place a white Knight at B1
        board[0][2] = new Bishop('w', 0, 2, this); // Place a white Bishop at C1
        board[0][3] = new Queen('w', 0, 3, this); // Place the white Queen at D1
        board[0][4] = new King('w', 0, 4, this); // Place the white King at E1
        board[0][5] = new Bishop('w', 0, 5, this); // Place another white Bishop at F1
        board[0][6] = new Knight('w', 0, 6, this); // Place another white Knight at G1
        board[0][7] = new Rook('w', 0, 7, this); // Place another white Rook at H1
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn('w', 1, i, this); // Place white Pawns on the second row
        }

        // Place black pieces at their initial positions
        board[7][0] = new Rook('b', 7, 0, this); // Place a black Rook at A8
        board[7][1] = new Knight('b', 7, 1, this); // Place a black Knight at B8
        board[7][2] = new Bishop('b', 7, 2, this); // Place a black Bishop at C8
        board[7][3] = new Queen('b', 7, 3, this); // Place the black Queen at D8
        board[7][4] = new King('b', 7, 4, this); // Place the black King at E8
        board[7][5] = new Bishop('b', 7, 5, this); // Place another black Bishop at F8
        board[7][6] = new Knight('b', 7, 6, this); // Place another black Knight at G8
        board[7][7] = new Rook('b', 7, 7, this); // Place another black Rook at H8
        for (int i = 0; i < 8; i++) {
            board[6][i] = new Pawn('b', 6, i, this); // Place black Pawns on the seventh row
        }
    }

    // Method to move a piece on the board
    public void movePiece(Piece piece, int newRow, int newCol) {
        // Retrieve the current row and column of the piece
        int prevRow = piece.getRow();
        int prevCol = piece.getCol();

        // Check if the intended move is valid
        if (isValidMove(piece, newRow, newCol)) {
            // Move the piece to the new position
            board[newRow][newCol] = piece;
            piece.setPosition(newRow, newCol);

            // Set the previous position to null (empty)
            board[prevRow][prevCol] = null;
        } else {
            // Print an error message if the move is not valid
            System.out.println("Invalid move!");
        }
    }

    // Helper method to check if a move is valid
    private boolean isValidMove(Piece piece, int newRow, int newCol) {
        // Ensure the new position is within the bounds of the board
        if (newRow < 0 || newRow >= 8 || newCol < 0 || newCol >= 8) {
            return false;
        }
        // Check if the piece allows this move
        return piece.possibleMoves(piece.getRow(), piece.getCol(), newRow, newCol);
    }

    // Method to get the piece at a specific board position
    public Piece getPiece(int row, int col) {
        // Return null if the specified position is out of board bounds
        if (row < 0 || row >= 8 || col < 0 || col >= 8) {
            return null;
        }
        return board[row][col];
    }

    // Method to print the board to the console
    public void printBoard() {
        // Print column headers
        System.out.println("  a  b  c  d  e  f  g  h");
        // Print a separator line
        System.out.println("  ------------------------");
        for (int i = 7; i >= 0; i--) {
            // Print row number at the start of each row
            System.out.print((i + 1) + "|");
            for (int j = 0; j < 8; j++) {
                // Get the piece at the current position
                Piece piece = board[i][j];
                if (piece == null) {
                    // Print checkerboard pattern for empty squares
                    System.out.print(((i + j) % 2 == 0) ? "## " : "   ");
                } else {
                    // Print the piece's name
                    System.out.print(piece.getName() + " ");
                }
            }
            // Print row number at the end of each row
            System.out.println("|" + (i + 1));
        }
        // Print a separator line
        System.out.println("  ------------------------");
        // Print column headers again
        System.out.println("  a  b  c  d  e  f  g  h");
    }
}  // End of Board class
