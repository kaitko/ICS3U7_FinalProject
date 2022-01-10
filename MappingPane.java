import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.concurrent.TimeUnit;
import static javax.swing.JOptionPane.showMessageDialog;

public class MappingPane extends JPanel implements ActionListener{
    private JButton[][] grids;
    private int row=0, col=0;
    private ImageIcon[] icon = {new ImageIcon("icon.jpg"), new ImageIcon("player1.jpg"), new ImageIcon("player2.jpg")};
    private int player, level=0;
    private int startPlayer;
    private int rows, cols;

    
    public MappingPane(int rows, int cols, int startPlayer, int level){
        setPreferredSize(new Dimension(40*cols, 40*rows));
        this.rows =rows;
        this.cols =cols;
        this.player= startPlayer;
        this.level=level;
        this.startPlayer= startPlayer;
        grids = new JButton[rows][cols];
        setLayout(new GridLayout(rows,cols));
        for (int i = 0; i<rows; i++){
            for (int j = 0; j<cols; j++){
                grids[i][j]=new JButton();
                if ((i+j)%2!=0) grids[i][j].setBackground(Color.BLACK);
                add(grids[i][j]);
                grids[i][j].addActionListener(this);
            }
        }
        grids[row][col].setIcon(icon[0]);
        setVisible(true);
    }

    private void processClick(int i, int j){
        if (i<row || j<col || i>row+1 || j>col+1 || i+j==row+col) return;
        try {TimeUnit.MILLISECONDS.sleep(1);} catch (InterruptedException ex) {}
        grids[row][col].setIcon(null);
        grids[i][j].setIcon(icon[player]);
        row=i; col=j;

        if (row==rows-1 && col==cols-1) { //found a winner
            showMessageDialog(this, "Player "+player+" won!");
            grids[row][col].setIcon(null);
            row=0; col=0; 
            grids[0][0].setIcon(icon[0]);
            player=startPlayer;
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