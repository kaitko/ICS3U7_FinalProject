import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.util.concurrent.TimeUnit;
import static javax.swing.JOptionPane.showMessageDialog;
/**
 * @author Kaitlyn & Uthara
 * ICS3U7 Final Assignment
 * Duelists
 * 01/28/2022
 */
public class FactorDuelPane extends JPanel implements ActionListener{

	private JButton[] btnNumber; //buttons representing the 80 numbers 
	private int player;
	private int rows, cols;
	private int[] selected; 		//tracking whether each number is selected or not
	private int[] numOfNextMoves; 	//track the number of next moves for each number (based on current situation)
	private int lastSelection = -2; //stores the last player's selection
	// all possible starting numbers to begin with, selected by program
	private int[] startingNumbers = {12,13,14,15,16,18,19,20,21,22,24,25,26,27,28,30,32,33,34,35,36,38,39,40,42,44,45,48,50,52,54,56,60,63,64};
	private JPanel pInfo = new JPanel();
	private JPanel pGame = new JPanel();
	private JPanel pWest = new JPanel();
	private JPanel pEast = new JPanel();

	private JLabel lblWest1 = new JLabel("Player 1");
	private JLabel lblEast1 = new JLabel("Player 2"); 
	private JLabel lblWest2 = new JLabel("?");
	private JLabel lblEast2 = new JLabel(""); 

	//For timer:
	JLabel timeTaken = new JLabel("Time taken: ");
	JTextField time = new JTextField(13);
	private long start = 0L;
	private long end = 0L;
	long microseconds = 0L;

	//Fonts:
	Font timerFont = new Font("Times New Roman", Font.BOLD, 30);
	Font timeDisplayFont = new Font("Times New Roman", Font.PLAIN, 28);


	/**
	 * Constructor
	 * The default size is 8 X 10, starting from player 1, with level = 1
	 */
	public FactorDuelPane() {
		this(8,10, 1, 1);        
	}//end of FactorDuelPane() constructor


	/**
	 * Constructor
	 * the "level" parameter is for further enhancement
	 * @param rows
	 * @param cols
	 * @param startPlayer
	 * @param level
	 */
	public FactorDuelPane(int rows, int cols, int startPlayer, int level){

		// initialize variables
		this.rows       = rows;
		this.cols       = cols;
		this.player     = startPlayer;
		btnNumber       = new JButton[rows * cols];
		selected        = new int[rows * cols];
		numOfNextMoves  = new int[rows * cols];

		// UI initialization
		setLayout(new BorderLayout());
		pInfo.setPreferredSize(new Dimension(50*cols, 70));

		//Timer:
		time.setEditable(false);
		pInfo.add(timeTaken); 
		pInfo.add(time);
		start = System.currentTimeMillis();  
		timeTaken.setFont(timerFont);
		time.setFont(timerFont);

		//change style and format
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

		pGame.setLayout(new GridLayout(rows,cols));
		pGame.setPreferredSize(new Dimension(50*cols, 50*rows));


		//fill board with buttons
		for (int i = 0; i < rows * cols ; i++){
			btnNumber[i] = new JButton();
			btnNumber[i].setText("" + (i + 1)); //displayed number is 1 more than the index
			pGame.add(btnNumber[i]);
			btnNumber[i].addActionListener(this);
		}

		// randomly select starting number from the allowed list 
		int startingIndex = startingNumbers[(int)(Math.random()*(startingNumbers.length))] - 1;
		
		btnNumber[startingIndex].setBackground(Color.BLACK);
		btnNumber[startingIndex].setEnabled(false);
		
		selected[startingIndex] = 100; //to mark the grid as selected
		lastSelection = startingIndex + 1;

		calculateNumOfNextMoves();

		//LAYOUT OF THINGS:   
		add(pInfo, BorderLayout.NORTH);
		add(pWest, BorderLayout.WEST);
		add(pEast, BorderLayout.EAST);
		add(pGame, BorderLayout.CENTER); 

		setVisible(true);
	}

	/**
	 * Check for winner, timer, button color handling, resetting lastSelection and current player.
	 * @param Integer i
	 * @return void
	 */
	private void processClick(int i){
		if (numOfNextMoves[i] == 0) {// winner found
			btnNumber[i].setBackground(Color.yellow);
			showMessageDialog(this, "Player " + player + " won!");

			this.setVisible(false);;
			revalidate();
			return;
		} else { // valid click and not finished yet
			if (player == 1) {
				lblWest2.setText("" + (i + 1));
				lblEast2.setText("?");

			} else {
				lblWest2.setText("?");
				lblEast2.setText("" + (i + 1));

			}
		}

		end = System.currentTimeMillis();
		time.setText(calculateTimeTaken(start,end)); //setting timer stats

		btnNumber[i].setBackground(Color.BLACK); //changing background button
		btnNumber[i].setEnabled(false);
		selected[i] = player;
		lastSelection = i + 1;
		player = 3 - player; //changing turn to other player

		calculateNumOfNextMoves();
		repaint();

		start = System.currentTimeMillis(); //timer
	}//end of processClick()

	/**
	 * Based on the current selection, calculate number of moves for each grid.
	 * Purpose for checking whether there are more moves; determining a winner
	 * @return void
	 */
	private void calculateNumOfNextMoves(){
		for (int i = 0; i < numOfNextMoves.length; i++){
			numOfNextMoves[i] = 0;
			for (int j = 0; j < rows * cols; j++){
				//check if is a factor or multiple
				if (selected[j] == 0 && i != j && ((i + 1) % (j + 1) == 0 || (j + 1) % (i + 1) == 0))
					numOfNextMoves[i]++; 
			} 
		}
	}//end of calculateNumOfNextMoves()

	/**
	 * Action event handler
	 */
	public void actionPerformed(ActionEvent e){
		Object source = e.getSource();
		
		// locate which button is clicked
		for (int i = 0; i<rows * cols; i++) { // not valid click
			if (source == btnNumber[i]){ 
				if ((lastSelection) % (i + 1) != 0 && (i + 1) % (lastSelection) != 0) { //not a factor or multiple of lastSelection
					showMessageDialog(this, "Player "+ player +" lost!");
					this.setVisible(false);
					return;
				} else { // valid click
					processClick(i);
				}
			}
		}
		return;
	}//end of actionPerformed

	/**
	 * Calculates how much time taken, and sets it into a JTextLabel
	 * @param start is milliseconds between the time at start of move and midnight 1/1/1970
	 * @param end is milliseconds between the time after the move and midnight 1/1/1970
	 * @return time taken casted as a String
	 */
	public String calculateTimeTaken(Long start, Long end) {
		Long milliseconds = (end-start);
		long minutes = (milliseconds/60000)%60;
		long seconds = (milliseconds/1000)%60;    	

		return String.valueOf(minutes) + " minutes "+ String.valueOf(seconds) + " seconds ";
	}//end of calculatetimeTaken
}