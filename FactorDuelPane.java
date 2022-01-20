import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.concurrent.TimeUnit;
import static javax.swing.JOptionPane.showMessageDialog;

public class FactorDuelPane extends JPanel implements ActionListener{
    private JButton[] btnNumber;

    //private int row=0, col=0;
    private ImageIcon[] icon = {new ImageIcon("icon.jpg"), new ImageIcon("player1.jpg"), new ImageIcon("player2.jpg")};
    private int player, level=0;
    private int startPlayer;
    private int rows, cols;
    private int[] selected;
    private int[] numOfNextMove;

    private int lastSelection=-2;
    private int[] strartingNumers = {12,13,14,15,16,18,19,20,21,22,24,25,26,27,28,30,32,33,34,35,36,38,39,40,42,44,45,48,50,52,54,56,60,63,64};

    public FactorDuelPane(int rows, int cols, int startPlayer, int level){
        this.rows =rows;
        this.cols =cols;
        this.player= startPlayer;
        this.level=level;
        this.startPlayer= startPlayer;
        btnNumber = new JButton[rows * cols];
        selected = new int[rows * cols];
        numOfNextMove= new int[rows * cols];
        resetNumOfNextMove();
        
        // int c = strartingNumers[(int)(Math.random()*(strartingNumers.length))];
        // selected[c]=1;

        //for (int i=0; i<rows;i++) for (int j=0; j<cols;j++) nextAllowed[i][j]=1;
        setLayout(new GridLayout(rows,cols));

        for (int i = 0; i<rows * cols ; i++){
            btnNumber[i]=new JButton();
            btnNumber[i].setText(""+(i+1));
            add(btnNumber[i]);
            btnNumber[i].addActionListener(this);
        }
        setPreferredSize(new Dimension(50*cols, 50*rows));
        setVisible(true);
        // select the initial number to start with
        int c = strartingNumers[(int)(Math.random()*(strartingNumers.length))];
        System.out.println(c);
        processClick(c);

    }

    private void processClick(int i){
        // if user clicked previously chosen grid 
        if (selected[i]==1) return;
        //user clicked a wrong butto, lose 
        else if ((lastSelection+1)%(i+1) !=0 && (i+1)%(lastSelection+1) != 0) {
            // handle lose -toDO
            return;
        } 
        // choose a grid that has no further move, winner found 
        else if (numOfNextMove[i]==0) {
            btnNumber[i].setBackground(Color.RED);
            showMessageDialog(this, "Player "+player+" won!");
            return;
        } else {
            btnNumber[i].setBackground(Color.BLACK);
            selected[i]=1;
            resetNumOfNextMove();
            lastSelection=i;
            player=3-player;
        }
        //try {TimeUnit.MILLISECONDS.sleep(1);} catch (InterruptedException ex) {}
        repaint();
    }
    
    private void resetNumOfNextMove(){
        for (int i =0; i<numOfNextMove.length; i++){
            numOfNextMove[i]=0;
            for (int j =0; j<selected.length; j++){
                if (selected[j]==0 && i != j && ((i+1)%(j+1)==0||(j+1)%(i+1)==0)) numOfNextMove[i]++; 
            } 
        }

    }

    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        for (int i = 0; i<rows * cols; i++) 
            if (source == btnNumber[i]) processClick(i);
        return;
    }
    
}