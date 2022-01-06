import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.concurrent.TimeUnit;
import static javax.swing.JOptionPane.showMessageDialog;

public class GridLayoutGame1 extends JFrame implements ActionListener{
    private Container canvas;
    private JButton[][] grids;
    private int row=0, col=0;
    private ImageIcon[] icon = {new ImageIcon("icon.jpg"), new ImageIcon("player1.jpg"), new ImageIcon("player2.jpg")};
    private int player, level=0;
    private int startPlayer;
    private int rows, cols;
    //private boolean allowAction = true;

    
    public GridLayoutGame1(int rows, int cols, int startPlayer, int level){
        super("Game 1");
        this.rows =rows;
        this.cols =cols;
        this.player= startPlayer;
        this.level=level;
        this.startPlayer= startPlayer;
        grids = new JButton[rows][cols];
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        canvas= getContentPane();
        canvas.setLayout(new GridLayout(rows,cols));
        for (int i = 0; i<rows; i++){
            for (int j = 0; j<cols; j++){
                grids[i][j]=new JButton();
                if ((i+j)%2!=0) grids[i][j].setBackground(Color.BLACK);
                canvas.add(grids[i][j]);
                grids[i][j].addActionListener(this);
            }
        }
        grids[row][col].setIcon(icon[0]);
        setSize((500/rows)*cols, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void processClick(int i, int j){
        if (i<row || j<col || i>row+1 || j>col+1 || i+j==row+col) return;
        try {TimeUnit.MILLISECONDS.sleep(1);} catch (InterruptedException ex) {}
        grids[row][col].setIcon(null);
        grids[i][j].setIcon(icon[player]);
        row=i; col=j;

        if (row==rows-1 && col==cols-1) {
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
    
    public static void main ( String[] args ){
        GridLayoutGame1 g  = new GridLayoutGame1(15,18,1, 2) ;
        //g.setSize( 500, 500 );   
        g.setVisible( true );        
    }
}