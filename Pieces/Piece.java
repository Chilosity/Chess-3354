// Define the package this class belongs to
package Pieces;

// Import necessary classes
import board.Board;

// Define the abstract class Piece
public abstract class Piece {
    private char color; // Color of the piece: 'w' for white, 'b' for black
    private char ID; // ID representing the type of the piece, e.g., 'P' for Pawn, 'K' for King, etc.
    private String name; // Full name of the piece, displayed on the board
    private int row; // Current row of the piece on the board
    private int col; // Current column of the piece on the board

    // Constructor for initializing a piece with specific attributes
    public Piece(char c, char d, int row, int col) {
        color = c; // Set the color of the piece
        ID = d; // Set the ID of the piece
        setName(); // Set the display name based on color and ID
        setPosition(row, col); // Set the position of the piece on the board
    }

    // Method to set the color of the piece and update its name accordingly
    public void setColor(char c) {
        color = c; // Update the color
        setName(); // Update the display name after changing the color
    }

    // Method to set the position of the piece
    public void setPosition(int row, int col) {
        this.row = row; // Set the row of the piece
        this.col = col; // Set the column of the piece
    }

    // Method to set the ID of the piece and update its name
    public void setID(char c) {
        ID = c; // Update the ID
        setName(); // Update the display name after changing the ID
    }

    // Method to update the display name of the piece based on its color and ID
    public void setName() {
        name = String.valueOf(color) + String.valueOf(ID); // Combine color and ID to form the display name
    }

    // Abstract method to determine if a move is possible; must be implemented in subclasses
    public abstract boolean possibleMoves(int origRow, int origCol, int destRow, int destCol);

    // Getter for the color of the piece
    public char getColor() {
        return color; // Return the color
    }

    // Getter for the current row of the piece
    public int getRow() {
        return row; // Return the current row
    }

    // Getter for the current column of the piece
    public int getCol() {
        return col; // Return the current column
    }

    // Getter for the ID of the piece
    public char getID() {
        return ID; // Return the ID
    }

    // Getter for the name of the piece
    public String getName() {
        return name; // Return the display name
    }
}
