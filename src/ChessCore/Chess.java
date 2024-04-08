
package ChessCore;
import ChessCore.Pieces.Bishop;
import ChessCore.Pieces.King;
import ChessCore.Pieces.Knight;
import ChessCore.Pieces.Pawn;
import ChessCore.Pieces.Piece;
import ChessCore.Pieces.Queen;
import ChessCore.Pieces.Rook;
import java.util.Stack;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.JOptionPane;


/**
 *
 * @author ADMIN
 */
public class Chess extends javax.swing.JFrame {
       public ClassicChessGame game;
       private JPanel chessboardPanel;
       private Square selectedSquare;
       private Stack<ChessBoard> boardStack;
    public Chess() {
         game = new ClassicChessGame();
         boardStack = new Stack<>();
         boardStack.push(new ChessBoard(game.getBoard()));
        initComponents();
         myInitComponents();
    }
    private void undoMove() {
        
        if (!boardStack.isEmpty()) {
           
            boardStack.pop();
            if (game.getWhoseTurn()==Player.BLACK)
            {
                game.setWhoseTurn(Player.WHITE);
            }else 
            {
                game.setWhoseTurn(Player.BLACK);
            }
            if (!boardStack.isEmpty()) {
                game.setBoard(new ChessBoard(boardStack.peek()));
                updateChessboardGUI();
            }
        }
    }
    private void myInitComponents() {

      



        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel(); // Create a new JPanel to hold components
        mainPanel.setLayout(new BorderLayout());
        
         chessboardPanel = new JPanel();
        chessboardPanel.setLayout(new GridLayout(8, 8));
    
       
        for ( int row = 0; row < 8; row++) {
            
            for (int col = 0; col < 8; col++) {
                JButton squareButton = new JButton();
                
               
                if ((row + col) % 2 == 0) {
                    squareButton.setBackground(Color.WHITE);
                } else {
                    squareButton.setBackground(Color.BLACK);
                }
                
                
                squareButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                final int finalRow = row+1;
                final int finalCol = col+1;
                ImageIcon icon = null;
                //pawns
                if (row == 1) {
                    
                    icon = new ImageIcon(getClass().getResource("WhitePawn.png"));
                } else if (row == 6) {
                    
                    icon = new ImageIcon(getClass().getResource("BlackPawn.png"));
                    //White peices
                } else if (row ==0 && (col == 0 || col ==7))
                {
                    icon = new ImageIcon(getClass().getResource("WhiteRook.png"));
                }else if (row ==0 && (col == 1 || col ==6))
                {
                    icon = new ImageIcon(getClass().getResource("WhiteKnight.png"));
                }else if (row ==0 && (col == 2 || col ==5))
                {
                    icon = new ImageIcon(getClass().getResource("WhiteBishop.png"));
                }else if (row ==0 && (col == 3))
                {
                    icon = new ImageIcon(getClass().getResource("WhiteQueen.png"));
                }
                else if (row ==0 && (col == 4))
                {
                    icon = new ImageIcon(getClass().getResource("WhiteKing.png"));
                
                } 
                //Black peices
                else if (row ==7 && (col == 0 || col ==7))
                {
                    icon = new ImageIcon(getClass().getResource("BlackRook.png"));
                }else if (row ==7 && (col == 1 || col ==6))
                {
                    icon = new ImageIcon(getClass().getResource("BlackKnight.png"));
                }else if (row ==7 && (col == 2 || col ==5))
                {
                    icon = new ImageIcon(getClass().getResource("BlackBishop.png"));
                }else if (row ==7 && (col == 3))
                {
                    icon = new ImageIcon(getClass().getResource("BlackQueen.png"));
                }
                else if (row ==7 && (col == 4))
                {
                    icon = new ImageIcon(getClass().getResource("BlackKing.png"));
                }
        
                
                if (icon != null) {
                    Image image = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    icon = new ImageIcon(image);
                    squareButton.setIcon(icon);
                }
                
                squareButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        resetButtonColors();
                        System.out.println("Button clicked at row: " + finalRow + ", column: " + finalCol);
                       
                        Square clickedSquare = new Square(BoardFile.values()[finalCol - 1], BoardRank.values()[finalRow - 1]);

                        if (selectedSquare == null) {
                            // First press
                            selectedSquare = clickedSquare;
                             List<Square> validMoves = game.getAllValidMovesFromSquare(selectedSquare);
                            highlightValidMoves(validMoves);
                        }else {
                            // Second press
                            Move move = new Move(selectedSquare, clickedSquare); 
                            if(game.getPieceAtSquare(selectedSquare) instanceof Pawn &&((clickedSquare.getRank()==BoardRank.FIRST)||(clickedSquare.getRank()==BoardRank.EIGHTH))){
                             String p= JOptionPane.showInputDialog("Promote Pawn To : 1:Queen   2:Knight   3:Rook   4:Bishop");                                
                            Move prom=null;
                             if(Integer.parseInt(p)==1 ){
                               prom = new Move(selectedSquare, clickedSquare,PawnPromotion.Queen); 
                               if (game.isValidMove(prom)) {
                                game.makeMove(prom);
                                updateChessboardGUI();
                                 selectedSquare = null;
                                if(game.isGameEnded()){
                        JOptionPane.showMessageDialog(chessboardPanel, game.getGameStatus());
                    }
                                 boardStack.push(new ChessBoard(game.getBoard()));
                               
                                 return;
                                 
                               }}
                                if(Integer.parseInt(p)==2){
                               prom = new Move(selectedSquare, clickedSquare,PawnPromotion.Knight); 
                               if (game.isValidMove(prom)) {
                                game.makeMove(prom);
                                updateChessboardGUI();
                                 selectedSquare = null;
                                 if(game.isGameEnded()){
                        JOptionPane.showMessageDialog(chessboardPanel, game.getGameStatus());
                    }
                                  boardStack.push(new ChessBoard(game.getBoard()));
                               
                                 return;
                               }
                               } if(Integer.parseInt(p)==3){
                               prom = new Move(selectedSquare, clickedSquare,PawnPromotion.Rook); 
                               if (game.isValidMove(prom)) {
                                game.makeMove(prom);
                                updateChessboardGUI();
                                if(game.isGameEnded()){
                        JOptionPane.showMessageDialog(chessboardPanel, game.getGameStatus());
                    }
                                 boardStack.push(new ChessBoard(game.getBoard()));
                               
                                 selectedSquare = null;
                                 return;
                               }
                               }if(Integer.parseInt(p)==4){
                               prom = new Move(selectedSquare, clickedSquare,PawnPromotion.Bishop); 
                               if (game.isValidMove(prom)) {
                                game.makeMove(prom);
                                updateChessboardGUI();
                                if(game.isGameEnded()){
                        JOptionPane.showMessageDialog(chessboardPanel, game.getGameStatus());
                    }
                                 boardStack.push(new ChessBoard(game.getBoard()));
                               
                                 selectedSquare = null;
                                 return;
                               }
                               }
                            
                            }
                            if (game.isValidMove(move)) {
                                game.makeMove(move);
                                updateChessboardGUI();
                    if(game.isGameEnded()){
                        JOptionPane.showMessageDialog(chessboardPanel, game.getGameStatus());
                    }
                     boardStack.push(new ChessBoard(game.getBoard()));
                               
                                // Reset the selected square after making the move
                                selectedSquare = null;
                            } else {
                                System.out.println("move not valid");
                                selectedSquare = null;
                            }
                        }

                    }

                   
                });
    
                
                chessboardPanel.add(squareButton);
            }
        }
    
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undoMove();
            }
        });
        
        chessboardPanel.setPreferredSize(new java.awt.Dimension(400, 400));
        
        mainPanel.add(chessboardPanel, BorderLayout.CENTER);
        mainPanel.add(undoButton, BorderLayout.SOUTH);
    
        setContentPane(mainPanel);
        pack();
    }
    public void updateChessboardGUI() {
        for (Component component : chessboardPanel.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                int buttonIndex = chessboardPanel.getComponentZOrder(button);
                int row = buttonIndex / 8;
                int col = buttonIndex % 8;
    
                Square square = new Square(BoardFile.values()[col], BoardRank.values()[row]);
                Piece piece = game.getPieceAtSquare(square);
    
                updateSquareIcon(button, piece);
            }
        }
    }
    
    private void updateSquareIcon(JButton button, Piece piece) {
        if (piece != null) {
            String iconName = piece.getOwner() == Player.WHITE ? "White" : "Black";
            if (piece instanceof Pawn) {
                iconName += "Pawn.png";
            } else if (piece instanceof Rook) {
                iconName += "Rook.png";
            } else if (piece instanceof Knight) {
                iconName += "Knight.png";
            } else if (piece instanceof Bishop) {
                iconName += "Bishop.png";
            } else if (piece instanceof Queen) {
                iconName += "Queen.png";
            } else if (piece instanceof King) {
                iconName += "King.png";
            }
            ImageIcon icon = new ImageIcon(getClass().getResource(iconName));
            Image image = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            button.setIcon(icon);
        } else {
            // Empty square
            button.setIcon(null);
        }
    }
    
    private void resetButtonColors() {
        for (Component component : chessboardPanel.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                int buttonIndex = chessboardPanel.getComponentZOrder(button);
                int row = buttonIndex / 8;
                int col = buttonIndex % 8;
                button.setBackground((row + col) % 2 == 0 ? Color.WHITE : Color.BLACK);
            }
        }
    }
    
    private void highlightValidMoves(List<Square> validMoves) {
        System.out.println("Valid Moves: " + validMoves);
        for (Square square : validMoves) {
            int col = square.getFile().ordinal();
            int row = square.getRank().ordinal();
            int buttonIndex = row * 8 + col;
    
            if (buttonIndex >= 0 && buttonIndex < chessboardPanel.getComponentCount()) {
                Component component = chessboardPanel.getComponent(buttonIndex);
                if (component instanceof JButton) {
                    JButton button = (JButton) component;
                    button.setBackground(Color.GREEN);
                }
            }
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1008, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 611, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    /**
     * @param args the command line arguments
     */
     public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Chess.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new Chess().setVisible(true);
        });
    }

    // Variables declaration - do not modify                     
    // End of variables declaration                   
}