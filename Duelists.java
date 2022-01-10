import java.awt.* ;
import javax.swing.*;
import java.awt.event.*;

public class Duelists extends JFrame implements ActionListener {

    int level;

    JPanel top = new JPanel();
    JPanel mid = new JPanel();
    JPanel gameSelectionPane = new JPanel();
    JPanel gameConfig = new JPanel();
    JPanel gameArea = new JPanel();
    JButton go = new JButton("Start Game");

    String[] games = {"Mapping", "Factor Duel"};
    String[][] playerList = {{"Computer", "You"},{"Player 1", "Player 2"}};
    String[] gameSizeLabel = {"8 x 8", "8 x 9", "8 x 10", "9 x 9", "9 x 10", "9 x 11", "10 x 10", "10 x 11", "11 x 11", "12 x 12", "13 x 13"};
    int[][]  gameSize      = {{8 , 8} ,{8 , 9} ,{8 , 10} ,{9 , 9} ,{9 , 10} ,{9 , 11} ,{10 , 10} ,{10 , 11}, {11, 11},  {12, 12},  {13, 13}};;
    String[] levelOptions = {"Easy","Medium","Hard", "Evil"};

    JComboBox cbxGame= new JComboBox(games);
    JComboBox cbxFirstPlayer = new JComboBox();
    JComboBox cbxSize = new JComboBox(gameSizeLabel);
    JComboBox cbxLevel = new JComboBox(levelOptions);

    JLabel lblGame = new JLabel("Game:");
    JLabel lblFirstPlayer = new JLabel("First Player:");
    JLabel lblSize = new JLabel("Board Size:");
    JLabel lblLevel = new JLabel("Hardness:");
    

    public Duelists() {
        cbxGame.addActionListener(this);
        gameSelectionPane.add(cbxGame);
        gameSelectionPane.setVisible(true);
        go.addActionListener(this);
        cbxLevel.addActionListener(this);
        cbxFirstPlayer.setModel(new DefaultComboBoxModel(playerList[cbxGame.getSelectedIndex()]));

        top.add(lblGame);           top.add(gameSelectionPane);
        top.add(lblFirstPlayer);    top.add(cbxFirstPlayer);
        top.add(lblSize);           top.add(cbxSize);
        top.add(lblLevel);          top.add(cbxLevel);
        top.add(go);

        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);
        gameArea = new MappingPane(gameSize[cbxSize.getSelectedIndex()][0],gameSize[cbxSize.getSelectedIndex()][1],1,2);
        //gameArea = new  FactorDuelPane(8,8,1, 2);
        gameArea.setVisible(true);

        mid.add(gameArea);
        mid.setPreferredSize(new Dimension(800, 600));
        add(mid);

        setResizable(false);

    }
    public void actionPerformed(ActionEvent e){
        if (e.getSource()==cbxGame){
            boolean visible =(cbxGame.getSelectedIndex()==0)? true: false;
            cbxFirstPlayer.setModel(new DefaultComboBoxModel(playerList[cbxGame.getSelectedIndex()]));
            lblSize.setVisible(visible);            cbxSize.setVisible( visible);
            lblLevel.setVisible(visible);           cbxLevel.setVisible(visible);
        } else if (e.getSource()==cbxLevel){
            level = cbxLevel.getSelectedIndex()+1;
            //System.out.printf("You just selected level%d. %n", level);
        } else if (e.getSource()== go){
            mid.remove(gameArea);
            repaint();
            if (cbxGame.getSelectedIndex()==0){
                gameArea = new MappingPane(gameSize[cbxSize.getSelectedIndex()][0],gameSize[cbxSize.getSelectedIndex()][1],1,2);
            } else {
                gameArea = new  FactorDuelPane(8,10,1, 2);
            } 
            gameArea.setVisible(true);
            mid.add(gameArea,BorderLayout.SOUTH);
            pack();
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
