import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.util.concurrent.TimeUnit;
import static javax.swing.JOptionPane.showMessageDialog;

public class FactorDuelPane extends JPanel implements ActionListener{
    private JButton[] btnNumber; //buttons representing the 80 numbers 

    //private int row=0, col=0;
    private int player;
    private int rows, cols;
    private int[] selected; //tracking whether each number is selected or not
    private int[] numOfNextMoves; //track the number of next moves for each number (based on current situation)
    private int lastSelection=-2; //stores the last player's selection
    // all starting numbers to begin with, selected by program
    private int[] startingNumbers = {12,13,14,15,16,18,19,20,21,22,24,25,26,27,28,30,32,33,34,35,36,38,39,40,42,44,45,48,50,52,54,56,60,63,64};
    private JPanel pInfo = new JPanel();
    private JPanel pGame = new JPanel();
    private JPanel pWest = new JPanel();
    private JPanel pEast = new JPanel();
    
    private JLabel lblWest1 = new JLabel("Player 1");
    private JLabel lblEast1 = new JLabel("Player 2"); 
    private JLabel lblWest2 = new JLabel("?");
    private JLabel lblEast2 = new JLabel(""); 

    
    private JTextArea txtInfo = new JTextArea("The timer should be here in the pInfo pane");

    // the default size is 8 X 10, starting from player 1, with level =1 
    public FactorDuelPane() {
        this(8,10, 1, 1);        
    }

    // the "level" parameter is for futher enhancement
    public FactorDuelPane(int rows, int cols, int startPlayer, int level){
        // initialize variables
        this.rows       = rows;
        this.cols       = cols;
        this.player     = startPlayer;
        btnNumber       = new JButton[rows * cols];
        selected        = new int[rows * cols];
        numOfNextMoves  = new int[rows * cols];
        //this.setBackground(Color.BLACK);

        // UI initialization
        setLayout(new BorderLayout());
        pInfo.setPreferredSize(new Dimension(50*cols, 100));
        pInfo.add(txtInfo);

        lblWest1.setFont(new Font("Serif", Font.BOLD, 12));
        lblWest2.setFont(new Font("Serif", Font.BOLD, 40));
        lblEast1.setFont(new Font("Serif", Font.BOLD, 12));
        lblEast2.setFont(new Font("Serif", Font.BOLD, 40));

        pWest.setLayout( new BoxLayout( pWest, BoxLayout.Y_AXIS ) );
        pWest.add(Box.createRigidArea(new Dimension(1, 70)));       
        pWest.add(lblWest1);
        pWest.add(Box.createRigidArea(new Dimension(0, 70)));       
        pWest.add(lblWest2);
        

        pEast.setLayout( new BoxLayout( pEast, BoxLayout.Y_AXIS ) );
        pEast.add(Box.createRigidArea(new Dimension(0, 70)));       
        pEast.add(lblEast1);
        pEast.add(Box.createRigidArea(new Dimension(0, 70)));       
        pEast.add(lblEast2);
        

        txtInfo.setFont(new Font("Serif", Font.BOLD, 20));
        txtInfo.setForeground(Color.RED);
        txtInfo.setOpaque(false);

        pGame.setLayout(new GridLayout(rows,cols));
        pGame.setPreferredSize(new Dimension(50*cols, 50*rows));

        for (int i = 0; i < rows * cols ; i++){
            btnNumber[i] = new JButton();
            btnNumber[i].setText("" + (i + 1)); //displayed number is 1 more than the index
            pGame.add(btnNumber[i]);
            btnNumber[i].addActionListener(this);
        }

        
        
        
        
        
        
        
        // randomly select starting number from the allowed list 
        int startingIndex = startingNumbers[(int)(Math.random()*(startingNumbers.length))]-1;
        //txtInfo.setText(""+(startingIndex+1));
        btnNumber[startingIndex].setBackground(Color.BLACK);
        btnNumber[startingIndex].setEnabled(false);
        selected[startingIndex] = 100; //computer selected first move
        lastSelection=startingIndex+1;
        System.out.printf("Initial number is %d", startingIndex+1);

        calculateNumOfNextMoves();

        add(pInfo, BorderLayout.NORTH);
        add(pWest, BorderLayout.WEST);
        add(pEast, BorderLayout.EAST);
        add(pGame, BorderLayout.CENTER);

        setVisible(true);
    }

    private void processClick(int i){
        if (numOfNextMoves[i]==0) {// winner found
            btnNumber[i].setBackground(Color.RED);
            showMessageDialog(this, "Player "+player+" won!");
            this.setVisible(false);;
            revalidate();
            return;
        } else { // valid click and not finished yet
            if (player==1) {
                lblWest2.setText(""+(i+1));
                lblEast2.setText("?");
            } else {
                lblWest2.setText("?");
                lblEast2.setText(""+(i+1));
            }
        }
        btnNumber[i].setBackground(Color.BLACK);
        btnNumber[i].setEnabled(false);
        selected[i]=player;
        lastSelection=i+1;
        player = 3-player;
        calculateNumOfNextMoves();
    
        repaint();
    }
    
    // based on the current selection, calculate number of moves for each grid.
    private void calculateNumOfNextMoves(){
        for (int i=0; i<numOfNextMoves.length; i++){
            numOfNextMoves[i]=0;
            for (int j = 0; j < rows * cols; j++){
                if (selected[j] == 0 && i != j && ((i+1)%(j+1)==0||(j+1)%(i+1)==0)) 
                    numOfNextMoves[i]++; 
            } 
        }
    }

    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        // locate which button is clicked
        for (int i = 0; i<rows * cols; i++) { // not valid click
            if (source == btnNumber[i]){
                if ((lastSelection)%(i+1) !=0 && (i+1)%(lastSelection) != 0){
                    showMessageDialog(this, "Player "+ player +" lost!");
                    this.setVisible(false);
                    return;
                } else { // valid click
                    System.out.printf("New Selected number is %d%n", i);
                    processClick(i);
                }
            }
        }
        return;
    }   
}