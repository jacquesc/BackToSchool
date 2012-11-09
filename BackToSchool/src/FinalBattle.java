import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

//import Battle.ButtonListener;


public class FinalBattle extends JPanel {	
	// global variables
	JButton button1;
	boolean attackPressed;
	ImageIcon background;

	Graphics graphics;

	// Student variables
	ImageIcon student;
	int studentX;
	int studentY;
	int playerHealth;
	JLabel playerHealthLabel;
	JLabel creativityLabel;
	JLabel quantReasoningLabel;
	JLabel scientRigorLabel;
	Timer timer;
	Player player;

	// Attack Menu variables
	JLabel defaultAttackLabel;
	JLabel specializedAttackLabel;
	JButton optionAButton;
	JButton optionBButton;
	ImageIcon A;
	ImageIcon B;
	boolean optionA;
	boolean optionB;
	boolean specialAttack;

	// Boss variables
	ImageIcon humBoss;
	ImageIcon sciBoss;
	ImageIcon mathBoss;
	ImageIcon attack;
	ImageIcon scribble;
	int attackX;
	int attackY;
	int humBossX;
	int humBossY;
	int sciBossX;
	int sciBossY;
	int mathBossX;
	int mathBossY;
	int xSpeed;
	int humBossHealth;
	int sciBossHealth;
	int mathBossHealth;
	Timer bossTimer;
	JLabel humHealthLabel;
	JLabel mathHealthLabel;
	JLabel sciHealthLabel;
	JLabel humBossType;
	JLabel humBossName;
	JLabel mathBossType;
	JLabel mathBossName;
	JLabel sciBossType;
	JLabel sciBossName;
	boolean anyBossTurn;
	boolean humBossTurn;
	boolean sciBossTurn;
	boolean mathBossTurn;


	public FinalBattle(Player player)
	{
		this.player = player;
		this.setPreferredSize(new Dimension(800, 600));// setting the size
		this.setBackground(Color.white);// color of background
		background = new ImageIcon("art/battle/battle.jpg");
		playerHealth=100;
		mathBossHealth=100;
		sciBossHealth=100;
		humBossHealth=100;
		optionA=true;
		optionB=false;

		//adding the attack button
		button1 = new JButton("Attack");
		//button1.addActionListener(new ButtonListener());
		button1.setBounds(400,510,100,30);

		//attacking menu
		defaultAttackLabel = new JLabel("Default Attack");
		defaultAttackLabel.setBounds(450,405,100,30);
		specializedAttackLabel = new JLabel("Special Attack");
		specializedAttackLabel.setBounds(450,455,160,30);
		scribble = new ImageIcon("art/battle/scribble_sprite.png");
		
		//adding option A button
		optionAButton = new JButton();
		optionAButton.addActionListener(new AButtonListener());
		optionAButton.setIcon(new ImageIcon("art/battle/A_sprite.png"));
		optionAButton.setBounds(388,398,50,40); 
		optionAButton.setBackground(null);
		optionAButton.setOpaque(false);
		optionAButton.setBorder(null);
		
		// adding option B button
		optionBButton = new JButton();
		optionBButton.addActionListener(new BButtonListener());
		optionBButton.setIcon(new ImageIcon("art/battle/B_sprite.png"));
		optionBButton.setBounds(388,450,50,40);
		optionBButton.setBackground(null);
		optionBButton.setOpaque(false);
		optionBButton.setBorder(null);

		setLayout(null);

		//----------------------Player Variables--------------------------------//
		student = new ImageIcon("art/characters/student_leftside.png"); // loading image
		studentX=600;// x coordinate for student
		studentY=200;// y coordinate for student
		attackPressed=false;

		// initializing variables
		playerHealthLabel = new JLabel(playerHealth+"%");
		creativityLabel = new JLabel("Creativity: "+player.getCreativity());
		quantReasoningLabel = new JLabel("Quantative Reasoning: "+player.getQuantReasoning());
		scientRigorLabel = new JLabel("Scientific Rigor: "+player.getSciRigor());


		//setting location of statistics
		playerHealthLabel.setBounds(670,340,100,100);
		creativityLabel.setBounds(620,380,150,100);
		quantReasoningLabel.setBounds(620,420,150,100);
		scientRigorLabel.setBounds(620,460,150,100);
		//---------------------End of Player Variables---------------------------//
		
		//---------------------Bosses Variables------------------------------------//
		humBoss = new ImageIcon("art/battle/Finalhumboss.png");
		sciBoss = new ImageIcon("art/battle/Finalscience_boss.png");
		mathBoss = new ImageIcon("art/battle/Finalmath_boss.png");
		//--------------------End of Bosses Variables
		

		// adding components to the jpanel
		//this.add(bossName);
		//this.add(bossType);
		this.add(button1);
		this.add(optionAButton);
		this.add(optionBButton);
		//this.add(bossHealthLabel);
		this.add(playerHealthLabel);
		this.add(creativityLabel);
		this.add(quantReasoningLabel);
		this.add(scientRigorLabel);
		this.add(specializedAttackLabel);
		this.add(defaultAttackLabel);

		setVisible(true);

	}

	// paint the images and graphics
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		graphics=g;
		background.paintIcon(this,g,0,0);
		student.paintIcon(this, g, studentX, studentY);
		humBoss.paintIcon(this, g, 0, 0);
		sciBoss.paintIcon(this, g, 100,100);
		mathBoss.paintIcon(this, g, 0, 210);
		
		if(optionA)
			scribble.paintIcon(this, g, 395, 400);
		else if(optionB)
			scribble.paintIcon(this,g,395,452);
	}

	// action listener for the Option A Button
	private class AButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(!attackPressed){
				//System.out.println("Pressed A");
				optionA=true;
				optionB=false;
				specialAttack=false;
				repaint();
			}
		}
	}

	// action listener for the Option B Button
	private class BButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(!attackPressed){
				//System.out.println("Pressed B");
				optionA=false;
				optionB=true;
				specialAttack=true;
				repaint();
			}
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Back To School: Battle Mode");
		FinalBattle fBattle= new FinalBattle(new Player());
		frame.setSize(800,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.add(battle);
		frame.setContentPane(fBattle);
		frame.pack();

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new FinalBattle(new Player()); // Let the constructor do the job
			}
		});
		frame.setVisible(true);
	}


}
