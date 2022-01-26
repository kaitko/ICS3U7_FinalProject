import java.io.*;
import java.awt.* ;
import javax.swing.*;
import java.awt.event.*;

/**
 * @author Kaitlyn & Uthara
 * ICS3U7 Final Assignment
 * Duelists
 * 01/28/2022
 */
public class Duelists extends JFrame implements ActionListener {

    //FOR GRAPHICS: MORE RECENT
    JPanel title = new JPanel(new BorderLayout());
    JPanel displayP = new JPanel(new BorderLayout());

    //Gif
    ImageIcon math = new ImageIcon("SwordFight.gif"); 
    JLabel image = new JLabel(math);

    //GUI construction
    JPanel mid = new JPanel();
    JPanel gameSelectionPane = new JPanel();
    JPanel gameArea = new JPanel();
    JPanel menuOptions = new JPanel();
    JButton go = new JButton("Select the Game");
    JButton goHome = new JButton("Return to Main Menu");

    //Game Selection
    String[] games = {"Mapping", "Factor Duel"};
    JLabel lblGame = new JLabel("Game:");
    JComboBox cbxGame= new JComboBox(games);
   
    //Instructions
    JPanel iPanel = new JPanel();
    JPanel iPanelB = new JPanel();
    JButton instructions = new JButton("Instructions");
    JButton instF = new JButton("Factor Duel");
    JButton instM = new JButton("Mapping");
    JTextArea instructionsTEXT = new JTextArea();
    JTextField inHEADER = new JTextField("Instructions");
    private String fileName = "Instructions.txt";
    String line;
    JButton home = new JButton("Main menu");
    JButton back = new JButton("Return to game");
    
    //Fonts
    Font instructionsTitle = new Font("RuslanDisplay", Font.BOLD, 30);
    Font instructionsFont = new Font("Times New Roman", Font.PLAIN, 15);
    Font nameF = new Font("Times New Roman", Font.PLAIN, 17);
    
    /**
     * Constructor for Duelists class
     */
    public Duelists() {
        cbxGame.addActionListener(this);
        gameSelectionPane.add(cbxGame); cbxGame.setForeground(Color.black);
        gameSelectionPane.setVisible(true);
        go.addActionListener(this);
        
        //create separate panel for better formatting and looks: DONE
        menuOptions.add(lblGame); lblGame.setForeground(Color.white);
        menuOptions.add(gameSelectionPane);
        menuOptions.add(go); 
  
      //Graphics: Title Page
        title.setPreferredSize(new Dimension(800,500));
        displayP.add(image);
        
        displayP.setPreferredSize(new Dimension(800,500));
        displayP.setBackground(Color.black); //change for debugging
        title.add(displayP, BorderLayout.CENTER); //originally center
        title.setVisible(true);
        mid.add(title);
      
        menuOptions.add(instructions);  menuOptions.add(home); 
        menuOptions.add(goHome);        goHome.addActionListener(this);
        
        menuOptions.setBackground(Color.BLACK);//change for debugging
        mid.add(menuOptions, BorderLayout.SOUTH);

        //Instructions:
        instructions.addActionListener(this);
        iPanel.setPreferredSize(new Dimension(750, 600));
              
        iPanel.setLayout(new BorderLayout());
        iPanel.add(inHEADER, BorderLayout.NORTH); 
        
        iPanelB.add(home, BorderLayout.EAST); iPanelB.add(back, BorderLayout.WEST);
        iPanel.add(instructionsTEXT, BorderLayout.CENTER); 
        iPanel.add(iPanelB, BorderLayout.SOUTH);
        back.addActionListener(this);
        inHEADER.setFont(instructionsTitle); inHEADER.setEditable(false);

        home.addActionListener(this); //main menu button
        
        		//reading from text file for instructions menu
        		try {
        			BufferedReader br = new BufferedReader(new FileReader(fileName));
        			line = br.readLine();
   		
        			while(line !=null) {
          				instructionsTEXT.append(line + "\n");
             			line = br.readLine();
          				
        			}
        			br.close();
        		}
        		catch (IOException e) {
        			instructionsTEXT.setText("Uh oh, there's been an error on reading the file!");
             		}
        		
        //GUI of instructionsTEXT
        instructionsTEXT.setEditable(false);
        instructionsTEXT.setFont(instructionsFont);
        iPanel.setVisible(false);
        
        mid.add(iPanel, BorderLayout.CENTER);

        gameArea.setVisible(false);

        mid.add(gameArea);
        //mid.setBackground(Color.black); //CHANGE for debugging
        mid.setPreferredSize(new Dimension(800, 625));
        add(mid);
        setResizable(false);

    }//end of constructor
    
    /**
     * Action event handler
     */    
    public void actionPerformed(ActionEvent e){
        if (e.getSource()== go){
            mid.remove(gameArea); repaint();  
            if (cbxGame.getSelectedIndex()==0) { 
                gameArea = new MappingPane();
            } else {
                gameArea = new  FactorDuelPane(8,10,1, 2);
            }
            
            
            iPanel.setVisible(false);
            title.setVisible(false);
            gameArea.setVisible(true);
            mid.add(gameArea,BorderLayout.SOUTH);
            pack();
        }
        else if (e.getSource() == instructions) {
        	//when instruction button is displayed
        	iPanel.setVisible(true);
        	title.setVisible(false);
        	gameArea.setVisible(false);
        	menuOptions.setVisible(false);
        	
        }
        else if (e.getSource() == home) {
        	//when home is button is pressed
        	iPanel.setVisible(false);
        	gameArea.setVisible(false);
        	menuOptions.setVisible(true);
        	title.setVisible(true);
        }
        else if (e.getSource() == back) {
        	//when want to return to game after viewing instructions
        	iPanel.setVisible(false);
        	gameArea.setVisible(true);
        	menuOptions.setVisible(true);
        	title.setVisible(false);
        }
        
        else if (e.getSource() == goHome) {
        	iPanel.setVisible(false);
        	gameArea.setVisible(false);
        	menuOptions.setVisible(true);
        	title.setVisible(true);
        }
      
    }//end of listener

    /**
     * Main
     * @param args
     */
    public static void main ( String[] args ){
        JFrame duelists = new Duelists();
        duelists.setDefaultCloseOperation( EXIT_ON_CLOSE );
        duelists.pack();
        duelists.setLocationRelativeTo(null);
        duelists.setVisible( true );        
    }//end of main
}
