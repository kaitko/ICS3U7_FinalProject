import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.concurrent.TimeUnit;
import static javax.swing.JOptionPane.showMessageDialog;

public class FactorDuelPane extends JPanel implements ActionListener{
    private JButton[][] grids;
    //private int row=0, col=0;
    private ImageIcon[] icon = {new ImageIcon("icon.jpg"), new ImageIcon("player1.jpg"), new ImageIcon("player2.jpg")};
    private int player, level=0;
    private int startPlayer;
    private int rows, cols;
    private int[][] nextAllowed;

    
    public FactorDuelPane(int rows, int cols, int startPlayer, int level){
        this.rows =rows;
        this.cols =cols;
        this.player= startPlayer;
        this.level=level;
        this.startPlayer= startPlayer;
        grids = new JButton[rows][cols];
        nextAllowed = new int[rows][cols];
        for (int i=0; i<rows;i++) for (int j=0; j<cols;j++) nextAllowed[i][j]=1;
        setLayout(new GridLayout(rows,cols));

        for (int i = 0; i<rows; i++){
            for (int j = 0; j<cols; j++){
                grids[i][j]=new JButton();
                //if ((i+j)%2!=0) grids[i][j].setBackground(Color.BLACK);
                grids[i][j].setText(""+(i*cols+j+1));
                add(grids[i][j]);
                grids[i][j].addActionListener(this);
            }
        }
        //grids[row][col].setIcon(icon[0]);
        setPreferredSize(new Dimension(50*cols, 50*rows));
        setVisible(true);
    }

    private void processClick(int i, int j){
        if (nextAllowed[i][j]==0) return;
        try {TimeUnit.MILLISECONDS.sleep(1);} catch (InterruptedException ex) {}
        grids[i][j].setBackground(Color.BLACK);
        int hasAllowed =0; 
        for (int m = 0; m<rows; m++) 
            for (int n = 0; n<cols; n++) {
                if (grids[m][n].getBackground()!=Color.black 
                    && ((m*cols+n+1)%(i*cols+j+1)==0 ||(i*cols+j+1)%(m*cols+n+1)==0))
                {
                    nextAllowed[m][n]=1;
                    grids[m][n].setToolTipText("Allowed");
                    hasAllowed++;

                } else nextAllowed[m][n]=0;
            }
        //grids[i][j].setIcon(icon[player]);
        //row=i; col=j;

        if (hasAllowed==0) {
            showMessageDialog(this, "Player "+player+" won!");
        } else player=3-player;
    }
    
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        for (int i = 0; i<rows; i++) 
            for (int j = 0; j<cols; j++) 
                if (source == grids[i][j]) processClick(i, j);
        return;
    }
    
}