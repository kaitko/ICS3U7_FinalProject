import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.concurrent.TimeUnit;
import static javax.swing.JOptionPane.showMessageDialog;

public class MappingPane extends JPanel implements ActionListener, Runnable {
	private int player, rows, cols, row=0, col=0;

	// configurations
	private ImageIcon[] icon = {new ImageIcon("icon.jpg"), new ImageIcon("player1.jpg"), new ImageIcon("player2.jpg")};
	private String[]    playerList = {"Bot", "Me"};
	private String[]    gameSizeLabel = {"8 x 8", "8 x 9", "8 x 10", "9 x 9", "9 x 10", "9 x 11", "10 x 10", "10 x 11", "11 x 11", "12 x 12", "13 x 13"};
	private int[][]     gameSize      = {{8 , 8} ,{8 , 9} ,{8 , 10} ,{9 , 9} ,{9 , 10} ,{9 , 11} ,{10 , 10} ,{10 , 11}, {11, 11},  {12, 12},  {13, 13}};;
	private String[]    levelOptions = {"Easy","Medium","Hard", "Evil"};

	// UI components
	// Game configuration panel
	private JPanel      pConfig = new JPanel(new FlowLayout());
	private JLabel      lblFirstPlayer = new JLabel("First Player:");
	private JComboBox   cbxFirstPlayer = new JComboBox(playerList);
	private JLabel      lblSize = new JLabel("Board Size:");
	private JComboBox   cbxSize = new JComboBox(gameSizeLabel);
	private JLabel      lblLevel = new JLabel("Hardness:");
	private JComboBox   cbxLevel = new JComboBox(levelOptions);
	private JButton     btnGO = new JButton("GO");
	// Game zone panel
	private JPanel      pGameZone = new JPanel();
	private JButton[][] btnGrids;

	/*
	 * Constructor
	 */
	public MappingPane(){
		setPreferredSize(new Dimension(800,650));
		btnGO.addActionListener(this);
		btnGrids = new JButton[rows][cols];
		pConfig.add(lblFirstPlayer);    pConfig.add(cbxFirstPlayer);
		pConfig.add(lblSize);           pConfig.add(cbxSize);
		pConfig.add(lblLevel);          pConfig.add(cbxLevel);
		pConfig.add(btnGO);
		add(pConfig);
		setVisible(true);
	}
	/*
	 * process is completed after allowed click by user
	 */
	private void processClick(int i, int j){
		btnGrids[row][col].setIcon(null); 		//remove previous icon display from grid
		btnGrids[i][j].setIcon(icon[player]); 	//set new icon
		row = i; col = j; 						//set current position
		//      pGameZone.setVisible(false); 	//debug
		//      pGameZone.setVisible(true); 	//debug

		if (!hasWinner()) { 						//check for winner, if none found
			player = 3 - player;					//switch player turn
			btnGrids[i][j].setIcon(icon[player]);	//set current icon

			if (player==1) {						//bot turn
				System.out.println("It's now the computer's turn");
				try {TimeUnit.MILLISECONDS.sleep(1000);} catch (InterruptedException ex) {}
				moveByBot();
			}  
		}
	}

	/*
	 * Handle bot moves
	 */
	private void moveByBot() {
		//disable buttons after initial valid click
		for (int i = 0; i<rows; i++) 
			for (int j = 0; j<cols; j++) 
				btnGrids[i][j].setEnabled(false);

		int x = 0, y = 0; 		//set variables for next move
		if (row == rows - 1) {	//if current player is at most bottom row
			x = row; 
			y = col + 1;
		}
		else if (col==cols-1) { //if current player is at most right column
			x=row+1; 
			y=col;
		}
		else {
			int[][] nextMove = new int[3][2];	//3 possible moves
			nextMove[0][0] = row;         
			nextMove[0][1] = col + 1; 
			nextMove[1][0] = row + 1;         
			nextMove[1][1] = col; 
			nextMove[2][0] = row + 1; 
			nextMove[2][1] = col + 1;
			//calculate whether to win or not based on chance related level selection
			int toWin = (int) (Math.random()*100) < cbxLevel.getSelectedIndex() * 25 + 25 ? 1: 0;
			//toWin=0;
			//System.out.printf("toWin=%d%n", toWin);
			
			for (int i = 0; i<3 ; i++) { //for all 3 possible moves
				x=nextMove[i][0]; 
				y=nextMove[i][1]; 
				if (((rows - x) * (cols - y) % 2) == toWin) //if move matches winning target 
					break;
			}
		}
		btnGrids[row][col].setIcon(null); //remove previous move icon
		btnGrids[x][y].setIcon(icon[player]); //replace new move icon
		row=x; col=y; //reset current position
		if (!hasWinner()) { //if no winner
			player = 3 - player;
			//enable buttons
			for (int i = 0; i<rows; i++) 
				for (int j = 0; j<cols; j++) 
					btnGrids[i][j].setEnabled(true);
		}
	}
/*
 * Check for winner
 */
	private boolean hasWinner(){
		if (row == rows - 1 && col == cols - 1) { //found a winner
			showMessageDialog(this, (player ==1? "Too bad, The Bot": "Congratulations! You") + " won!");//winner dialogue
			this.setVisible(false);
			return true;
		} else return false;
	}
/*
 * Listener event handler
 */
	public void actionPerformed(ActionEvent e){
		Object source = e.getSource();
		if (source == btnGO) {
			remove(pGameZone);
			btnGO.setText("Restart");;

			// read config
			rows = gameSize[cbxSize.getSelectedIndex()][0]; // read config
			cols = gameSize[cbxSize.getSelectedIndex()][1];
			player = cbxFirstPlayer.getSelectedIndex()+1;
			// Initialize the game zone with JButtons
			pGameZone = new JPanel(new GridLayout(rows,cols));
			btnGrids = new JButton[rows][cols];
			
			//creating buttons and placing in pGameZone
			for (int i = 0; i<rows; i++){
				for (int j = 0; j<cols; j++){
					btnGrids[i][j]=new JButton();
					
					if ((i+j)%2!=0) btnGrids[i][j].setBackground(Color.BLACK); //normal chess board grid
					//if (((rows-i)*(cols-j))%2 == 0 ) btnGrids[i][j].setBackground(Color.BLACK); //show winning grids
					btnGrids[i][j].addActionListener(this);
					pGameZone.add(btnGrids[i][j]);
				}
			}
			row=0; col=0; //initial starting position 
			btnGrids[0][0].setIcon(icon[0]); 
			pGameZone.setPreferredSize(new Dimension(40*cols, 40*rows));          
			pGameZone.setVisible(true);
			add(pGameZone); 
			revalidate();
			
			//if bot is starting player
			if (player==1) {
				System.out.println("It's now the Bot's turn");
				moveByBot();
			} 
		} else { //only check when player makes allowed move
			for (int i = row; i < Math.min(row+2, rows); i++) //current and next row 
				for (int j = col; j<Math.min(col+2, cols); j++) //current and next col
					if (source == btnGrids[i][j] && player == 2 && i+j != row+col) //checking if human's move is valid and not current grid
						processClick(i, j);
		}


	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}