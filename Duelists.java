import java.awt.* ;
import javax.swing.*;
import java.awt.event.*;

public class Duelists extends JFrame implements ActionListener {

    int level;
    
 
    //FOR GRAPHICS:
    JPanel title = new JPanel(new BorderLayout());
    JPanel displayP = new JPanel(new BorderLayout());
    
    JButton display = new JButton("Show game");
    JLabel text = new JLabel("Duelist");  
    JLabel displayText = new JLabel("Welcome to Duelist!");
    Font font2 = new Font("Times New Roman", Font.PLAIN, 20);
    Font font = new Font("Times New Roman", Font.BOLD, 90);
    final Color GOLD = new Color(255,204,51);
    
    ImageIcon math = new ImageIcon("math2.jpg");
    JLabel image = new JLabel(math);

    JPanel top = new JPanel();
    JPanel mid = new JPanel();
    JPanel gameSelectionPane = new JPanel();
    JPanel gameConfig = new JPanel();
    JPanel gameArea = new JPanel();
    JButton go = new JButton("Select the Game");

    String[] games = {"Mapping", "Factor Duel"};
    JLabel lblGame = new JLabel("Game:");
    JComboBox cbxGame= new JComboBox(games);

    public Duelists() {
        cbxGame.addActionListener(this);
        gameSelectionPane.add(cbxGame);
        gameSelectionPane.setVisible(true);
        go.addActionListener(this);
        top.add(lblGame); top.add(gameSelectionPane);top.add(go);
        
      //Graphics:
        title.setPreferredSize(new Dimension(800,600));
        title.setBackground(GOLD);
        
        text.setFont(font);
        title.add(text);
        displayText.setFont(font2);
        displayP.add(displayText);
        displayP.add(image);
        
        displayP.setPreferredSize(new Dimension(600,500));
        displayP.add(display, BorderLayout.SOUTH);
        displayP.setBackground(Color.black);
        displayText.setForeground(Color.WHITE);
        
        title.add(displayP, BorderLayout.SOUTH);
        display.addActionListener(this);
        title.setVisible(true);
        
        mid.add(title);

        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);
        top.setVisible(false);

        gameArea.setVisible(false);

        mid.add(gameArea);
        mid.setPreferredSize(new Dimension(800, 650));
        add(mid);
        setResizable(false);

    }
    public void actionPerformed(ActionEvent e){
        if (e.getSource()== go){
            mid.remove(gameArea); repaint();
            if (cbxGame.getSelectedIndex()==0) {
                gameArea = new MappingPane();
            } else {
                gameArea = new  FactorDuelPane(8,10,1, 2);
            }

            gameArea.setVisible(true);
            mid.add(gameArea,BorderLayout.SOUTH);
            pack();
        }
        else if (e.getSource()== display) {
        	top.setVisible(true);
        	title.setVisible(false);
            gameArea.setVisible(true);
        }

    }

    public static void main ( String[] args ){
        JFrame duelists = new Duelists();
        duelists.setDefaultCloseOperation( EXIT_ON_CLOSE );
        duelists.pack();
        duelists.setLocationRelativeTo(null);
        duelists.setVisible( true );        
    }
}
