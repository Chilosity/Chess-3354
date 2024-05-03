// Define the package this class belongs to
package gui;

// Import necessary classes for GUI components
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import board.Board;
import Pieces.*;

// Define the public class ChessGUI to handle the graphical user interface of the chess game
public class ChessGUI {
  
    // Unicode characters for displaying chess pieces in the GUI
    private static final String[] UNICODE_PIECES = {
        "\u2654", "\u2655", "\u2656", "\u2657", "\u2658", "\u2659",
        "\u265A", "\u265B", "\u265C", "\u265D", "\u265E", "\u265F"
    };

    private Board board; // Reference to the game board
    private JLabel[][] squares = new JLabel[8][8]; // Labels for each square on the chessboard
    private JPanel panelSelected = null; // Panel that is currently selected
    private JLabel labelSelected = null; // Label that is currently selected
    private JLabel turnIndicator; // Label to indicate which player's turn it is
    boolean turn = false; // false for White's turn, true for Black's turn
  
    // Constructor to initialize the ChessGUI with a given board
    public ChessGUI(Board b) {
        board = b;
        turnIndicator = new JLabel("White's turn", SwingConstants.CENTER); // Initial turn label
        turnIndicator.setFont(new Font("Arial", Font.BOLD, 20)); // Set font for the turn indicator
    }
  
    // Method to display the main GUI for the chess game
    public void showChessGUI() {
        JFrame frame = new JFrame("Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(turnIndicator, BorderLayout.NORTH);

        JPanel chessBoardPanel = new JPanel(new GridLayout(8, 8));
        chessBoardPanel.setPreferredSize(new Dimension(400, 400));
        Color lightColor = new Color(176,196,222); // Light color for chessboard squares
        Color darkColor = new Color(70,130,180); // Dark color for chessboard squares

        // Populate the chessboard with panels and labels for each square
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JLabel square = new JLabel(getPieceUnicode(row, col), JLabel.CENTER);
                square.setFont(new Font("MS Gothic", Font.BOLD, 60));
                JPanel panelSquare = new JPanel(new BorderLayout());
                panelSquare.setBackground((row + col) % 2 == 0 ? lightColor : darkColor);
                panelSquare.add(square);
                panelSquare.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                chessBoardPanel.add(panelSquare);

                // Add mouse listener to handle click events on the square
                panelSquare.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        handleMouseClick(square, panelSquare);
                    }
                });

                squares[row][col] = square; // Store the label in the array for later access
            }
        }

        // Configure and display the main window
        frame.add(chessBoardPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        updateBoardGUI(); // Update the display to match the current board state
    }

    // Method to handle mouse clicks on chessboard squares
    private void handleMouseClick(JLabel clickedSquare, JPanel clickedPanel) {
        if (panelSelected == null) {
            // Select the piece if there's no currently selected piece and the clicked square is not empty
            if (!clickedSquare.getText().isEmpty() && turn) {
                labelSelected = clickedSquare;
                panelSelected = clickedPanel;
                panelSelected.setBorder(BorderFactory.createLineBorder(Color.RED));  // Highlight the selected panel
              
              highlightLegalMoves(labelSelected); // Highlight legal moves for the selected piece
            }
        } else {
            // Convert square positions to row and column indices
            int sourceCol = getColFromSquare(labelSelected);
            int sourceRow = getRowFromSquare(labelSelected);
            int destCol = getColFromSquare(clickedSquare);
            int destRow = getRowFromSquare(clickedSquare);

            // Get the piece at the source position
            Piece piece = board.getPiece(sourceRow, sourceCol);

            // Check if the piece belongs to the current player's color
            if ((turn && piece != null && piece.getColor() == 'w') || (!turn && piece != null && piece.getColor() == 'b')) {
                // Check if the move is valid according to the piece's possible moves
                if (isValidMove(sourceRow, sourceCol, destRow, destCol)) {
                    // Move the piece
                    clickedSquare.setText(labelSelected.getText());
                    labelSelected.setText("");
                    panelSelected.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Reset original border color

                    // Update the board with the new piece position
                    board.movePiece(piece, destRow, destCol);

                    // Toggle the turn
                    turn = !turn;

                    // Update the turn indicator
                    updateTurnIndicator();

                    // Reset selection after the move
                    labelSelected = null;
                    panelSelected = null;
                  
                  resetSquareColors(); // Reset the colors of all squares
                } else {
                    // Display an error message for invalid move
                    JOptionPane.showMessageDialog(null, "Invalid move. Please try again.");
                    panelSelected.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Reset original border color
                    labelSelected = null;
                    panelSelected = null;
                  resetSquareColors();
                    
                }
            } else {
                // Display an error message if the selected piece belongs to the opposite color
                JOptionPane.showMessageDialog(null, "Invalid move. Please select a piece of your color.");
                panelSelected.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Reset original border color
                labelSelected = null;
                panelSelected = null;
                resetSquareColors();
            }
        }
    }
    
    // Method to update the turn indicator based on whose turn it is
    private void updateTurnIndicator() {
        if (turn) {
            turnIndicator.setText("Black's turn");
        } else {
            turnIndicator.setText("White's turn");
        }
    }

    // Method to validate if a move is possible for a given piece
    private boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol) {
        Piece piece = board.getPiece(sourceRow, sourceCol);
        if (piece != null) {
            // Check if the piece can move to the destination square
            return piece.possibleMoves(sourceRow, sourceCol, destRow, destCol);
        }
        return false; // No piece found at the source square
    }
    
    // Method to highlight possible legal moves for a selected piece
    private void highlightLegalMoves(JLabel selectedSquare) {
        // Convert square positions to row and column indices
        int sourceCol = getColFromSquare(selectedSquare);
        int sourceRow = getRowFromSquare(selectedSquare);

        // Get the piece at the source position
        Piece piece = board.getPiece(sourceRow, sourceCol);

        // Loop through all squares and highlight legal moves
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                // Check if the move is valid according to the piece's possible moves
                if (isValidMove(sourceRow, sourceCol, row, col)) {
                    JPanel squarePanel = (JPanel) squares[row][col].getParent();
                    drawCircleInSquare(squarePanel, Color.GRAY); // Visual indication of possible moves
                }
            }
        }
    }

    // Method to visually indicate possible moves on the chessboard
    private void drawCircleInSquare(JPanel squarePanel, Color color) {
        int squareSize = 50; 
        int diameter = squareSize / 2;
        
        Graphics2D g2d = (Graphics2D) squarePanel.getGraphics();
        g2d.setColor(color);
        g2d.fillOval((squarePanel.getWidth() - diameter) / 2, (squarePanel.getHeight() - diameter) / 2, diameter, diameter);
    }
 
    // Method to reset the color of all squares to their default state
    private void resetSquareColors() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JPanel squarePanel = (JPanel) squares[row][col].getParent();
                squarePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Reset the border color
            }
        }
    }

    // Utility methods to get row and column indices from square labels
    private int getRowFromSquare(JLabel square) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (squares[row][col] == square) {
                    return row;
                }
            }
        }
        return -1; // Square not found
    }

    private int getColFromSquare(JLabel square) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (squares[row][col] == square) {
                    return col;
                }
            }
        }
        return -1; // Square not found
    }
    
    // Method to update the board GUI to reflect the current state of the game
    public void updateBoardGUI(){
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece != null) {
                    squares[row][col].setText(getPieceUnicode(row, col)); // Update the text to show the piece's Unicode
                } else {
                    squares[row][col].setText(""); // Clear the square if no piece is present
                }
            }
        }
    }
    
    // Method to retrieve the Unicode character for a piece based on its position and type
    private static String getPieceUnicode(int row, int col) {
        if (row == 0 || row == 7) {
            int offset = (row == 0) ? 6 : 0; // Determine offset for white or black pieces
            switch (col) {
                case 0:
                case 7:
                    return UNICODE_PIECES[2 + offset]; // Rook
                case 1:
                case 6:
                    return UNICODE_PIECES[4 + offset]; // Knight
                case 2:
                case 5:
                    return UNICODE_PIECES[3 + offset]; // Bishop
                case 3:
                    return (row == 0) ? UNICODE_PIECES[7] : UNICODE_PIECES[1]; // Queen
                case 4:
                    return (row == 0) ? UNICODE_PIECES[6] : UNICODE_PIECES[0]; // King
            }
        } else if (row == 1 || row == 6) {
            return (row == 1) ? UNICODE_PIECES[11] : UNICODE_PIECES[5]; // Pawns
        }
        return ""; // Empty space for non-piece areas
    }

}
