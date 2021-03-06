package minigames.sudoku;


import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;

import main.BackToSchool;

public class SudokuGame extends JPanel implements ActionListener{

	private BufferedImage fourByfour_grid;
	private BufferedImage nineBynine_grid;
	private BufferedImage colorSq;
	private BufferedImage colorSq_thin;

	BackToSchool frame;

	// Game Related
	private int[][] currentAnswer;
	private int[][] setup;
	private SudokuSol gameSol;
	private String gameStatus; //in progress, right, wrong

	// Game Booleans
	private boolean isRunning;
	private boolean fourxfour = false;


	private Timer timer;
	private Clock gameTimer;
	private long timeLeft;

	// Coordinates
	private int sq_x;
	private int sq_y;

	private JButton exit;



	public SudokuGame(int day)
	{
		InputMap myInputMap = new InputMap();
		ActionMap myActionMap = new ActionMap();

		oneKey oneKey = new oneKey();
		twoKey twoKey = new twoKey();
		threeKey threeKey = new threeKey();
		fourKey fourKey = new fourKey();
		fiveKey fiveKey = new fiveKey();
		sixKey sixKey = new sixKey();
		sevenKey sevenKey = new sevenKey();
		eightKey eightKey = new eightKey();
		nineKey nineKey = new nineKey();

		delete delete = new delete();
		enter enter = new enter();

		up up = new up();
		down down = new down();
		left left = new left();
		right right = new right();

		myInputMap = this.getInputMap(WHEN_IN_FOCUSED_WINDOW);

		//Number Keys
		myInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0, false), "1");
		myInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0, false), "2");
		myInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_3, 0, false), "3");
		myInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_4, 0, false), "4");
		myInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_5, 0, false), "5");
		myInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_6, 0, false), "6");
		myInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_7, 0, false), "7");
		myInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_8, 0, false), "8");
		myInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_9, 0, false), "9");

		myInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0, false), "enter");
		myInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0,false), "delete");
		myInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE,0,false), "delete");
		// Arrow Keys
		myInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "up");
		myInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "down");
		myInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "left");
		myInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "right");

		myActionMap = this.getActionMap();
		myActionMap.put("1", oneKey);
		myActionMap.put("2", twoKey);
		myActionMap.put("3", threeKey);
		myActionMap.put("4", fourKey);
		myActionMap.put("5", fiveKey);
		myActionMap.put("6", sixKey);
		myActionMap.put("7", sevenKey);
		myActionMap.put("8", eightKey);
		myActionMap.put("9", nineKey);

		myActionMap.put("up", up);
		myActionMap.put("down", down);
		myActionMap.put("left", left);
		myActionMap.put("right", right);

		// Miscellaneous
		myActionMap.put("enter", enter);
		myActionMap.put("delete", delete);

		setLayout(null);

		gameSol = new SudokuSol(day);
		gameStatus = "In Progress"; // Default until <Enter> Pressed

		setup = gameSol.gridToShow();

		setBackground(new Color(58,54,55));

		exit = new JButton(new ImageIcon("art/buttons/exit_btn.jpg"));

		this.add(exit);
		exit.setBounds( 427, 250, 100, 30);
		exit.addActionListener(this);

		exit.setVisible(false);

		setFocusable(true);
		isRunning = true;

		if(day <= 6)
		{
			// FOUR BY FOUR GRID (LVL 1&2)
			fourxfour = true;
			sq_x = 20;
			sq_y = 13;

			if(day >=3)
			{
				gameTimer = new Clock(20);
			}
			else
			{
				gameTimer = new Clock(40);
			}

			gameTimer = new Clock(3); //testing

			currentAnswer = new int[4][4];
		}
		else
		{
			// NINE BY NINE GRID (LVL 3)
			fourxfour = false;
			sq_x = 15;
			sq_y = 16;
			gameTimer = new Clock(3); //testing
			//gameTimer = new Clock(150);
			currentAnswer = new int[9][9];

		}

		setup();
		gameTimer.start();
		gameLoop();

	}

	public void getFrame(BackToSchool frame)
	{
		this.frame = frame;
	}

	public void actionPerformed(ActionEvent e) {

		Object src = e.getSource();

		if(src == exit)
		{
			frame.switchPanel(BackToSchool.Screen.CAMPUS);
			
		}

	}


	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		timeLeft = gameTimer.timeRemaining();
		Font font = new Font("Arial", Font.BOLD, 50);
		Font markup = new Font("Arial", Font.PLAIN, 50);
		Font gameTxt = new Font("Arial", Font.PLAIN, 18);


		g.setColor(new Color(255,254,215));
		g.fillRect(415, 60, 120, 300);


		g.setFont(gameTxt);
		g.setColor(Color.BLACK);
		g.drawString("Timer", 420, 80);

		if(timeLeft == 0)
		{
			isRunning = false;
			gameStatus = "Game Over";
			exit.setVisible(true);

		}
		g.drawString(String.valueOf(timeLeft), 420, 100);

		g.drawString("Status", 420, 200);
		g.drawString(gameStatus, 420, 220);

		if(fourxfour)
		{
			g.drawImage(fourByfour_grid, 0, 0, null);
			g.drawImage(colorSq, sq_x, sq_y, null);


			g.setFont(font);

			int dx = 0;
			int posx = 50;
			int posy = 80;
			int dy = 0;

			// draw for setup
			for(int row = 0; row < 4; row++)
			{
				for(int col = 0; col < 4; col++)
				{	
					g.setColor(Color.BLACK);


					if(setup[row][col] == 0)
					{
						g.drawString("", posx+dx, posy+dy);
					}
					else
					{
						currentAnswer[row][col] = setup[row][col];
						g.drawString(String.valueOf(setup[row][col]), posx+dx, posy+dy);
					}
					dx += 95;
				}
				dx = 0;
				dy += 100;
			}

			dx = 0;
			dy = 0;

			// draw for updates
			for(int row = 0; row < 4; row++)
			{
				for(int col = 0; col < 4; col++)
				{
					if(setup[row][col] == 0 && currentAnswer[row][col] != 0)
					{
						g.setFont(markup);
						g.setColor(Color.RED);
						g.drawString(String.valueOf(currentAnswer[row][col]), posx+dx, posy+dy);
					}

					dx += 95;
				}
				dx = 0;
				dy += 100;

			}
		}
		else
		{
			g.drawImage(nineBynine_grid, 0, 0, null);	
			g.drawImage(colorSq, sq_x, sq_y, null);

			//Setup for Original
			font = new Font("Arial", Font.BOLD, 30);
			markup = new Font("Arial", Font.PLAIN, 30);

			int dx = 20;
			int posx = 0;
			int posy = 0;
			int dy = 50;

			for(int row = 0; row < 9; row++)
			{
				for(int col = 0; col < 9; col++)
				{
					g.setColor(Color.BLACK);


					if(setup[row][col] == 0)
					{
						g.drawString("", posx+dx, posy+dy);
					}
					else
					{
						g.setFont(font);
						currentAnswer[row][col] = setup[row][col];
						g.drawString(String.valueOf(setup[row][col]), dx, dy);
					}
					dx += 45;
				}
				dx = 20;
				dy += 43;
			}

			//Setup for Drawing Solutions

			dx = 20;
			dy = 50;

			for(int row = 0; row < 9; row++)
			{
				for(int col = 0; col < 9; col++)
				{
					if(setup[row][col] == 0 && currentAnswer[row][col] != 0)
					{
						g.setFont(markup);
						g.setColor(Color.RED);
						g.drawString(String.valueOf(currentAnswer[row][col]), posx+dx, posy+dy);
					}
					dx += 45;
				}
				dx = 20;
				dy += 43;

			}
		}

	}


	public void gameLoop()
	{
		timer = new Timer();
		int fps = 80;
		timer.schedule(new Loop(), 0, 1000/fps);

	}

	public class Loop extends java.util.TimerTask
	{
		public void run()
		{
			repaint(); // render

			if(!isRunning)
			{
				timer.cancel();
			}
		}
	}

	private void setup()
	{
		// Image Setup
		try {



			if(fourxfour)
			{
				fourByfour_grid = ImageIO.read(new File("art/sudoku/4x4grid.jpg"));
				colorSq = ImageIO.read(new File("art/sudoku/highlight_sq.jpg"));	
			}
			else
			{
				nineBynine_grid = ImageIO.read(new File("art/sudoku/9x9grid.jpg"));
				colorSq = ImageIO.read(new File("art/sudoku/highlight_sq9x9.jpg"));
			}

		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	private int[] getIndex()
	{
		int index_i = 0;
		int index_j = 0;

		if(fourxfour)
		{
			switch(sq_x)
			{
			case 20:
				index_j = 0;
				break;
			case 115:
				index_j = 1;
				break;
			case 210:
				index_j = 2;
				break;
			case 305:
				index_j = 3;
				break;
			default:
				break;
			}

			switch(sq_y)
			{
			case 13:
				index_i = 0;
				break;
			case 114:
				index_i = 1;
				break;
			case 215:
				index_i = 2;
				break;
			case 315:
				index_i = 3;
				break;
			default:
				break;
			}
		}
		else
		{
			switch(sq_x)
			{
			case 15:
				index_j = 0;
				break;
			case 58:
				index_j = 1;
				break;
			case 103:
				index_j = 2;
				break;
			case 148:
				index_j = 3;
				break;
			case 191:
				index_j = 4;
				break;
			case 234:
				index_j = 5;
				break;
			case 280:
				index_j = 6;
				break;
			case 323:
				index_j = 7;
				break;
			case 366:
				index_j = 8;
				break;
			default:
				break;
			}
			switch(sq_y)
			{
			case 16:
				index_i = 0;
				break;
			case 60:
				index_i = 1;
				break;
			case 104:
				index_i = 2;
				break;
			case 148:
				index_i = 3;
				break;
			case 192:
				index_i = 4;
				break;
			case 236:
				index_i = 5;
				break;
			case 281:
				index_i = 6;
				break;
			case 325:
				index_i = 7;
				break;
			case 369:
				index_i = 8;
				break;
			default:
				break;
			}
		}

		int[] arrayIndex = new int[2];
		arrayIndex[0] = index_i;
		arrayIndex[1] = index_j;

		return arrayIndex;
	}

	private class oneKey extends AbstractAction{
		public void actionPerformed(ActionEvent e)
		{

			if(timeLeft != 0)
			{
				currentAnswer[getIndex()[0]][getIndex()[1]] = 1;
				repaint();
			}

		}
	}
	private class twoKey extends AbstractAction{
		public void actionPerformed(ActionEvent e)
		{
			if(timeLeft != 0)
			{
				currentAnswer[getIndex()[0]][getIndex()[1]] = 2;
				repaint();

			}
		}
	}
	private class threeKey extends AbstractAction{
		public void actionPerformed(ActionEvent e)
		{
			if(timeLeft != 0)
			{
				currentAnswer[getIndex()[0]][getIndex()[1]] = 3;
				repaint();

			}
		}
	}
	private class fourKey extends AbstractAction{
		public void actionPerformed(ActionEvent e)
		{
			if(timeLeft != 0)
			{
				currentAnswer[getIndex()[0]][getIndex()[1]] = 4;
				repaint();

			}
		}
	}
	private class fiveKey extends AbstractAction{
		public void actionPerformed(ActionEvent e)
		{
			if(timeLeft != 0 && !fourxfour)
			{
				currentAnswer[getIndex()[0]][getIndex()[1]] = 5;
				repaint();

			}
		}
	}
	private class sixKey extends AbstractAction{
		public void actionPerformed(ActionEvent e)
		{
			if(timeLeft != 0 && !fourxfour)
			{
				currentAnswer[getIndex()[0]][getIndex()[1]] = 6;
				repaint();

			}
		}
	}
	private class sevenKey extends AbstractAction{
		public void actionPerformed(ActionEvent e)
		{
			if(timeLeft != 0 && !fourxfour)
			{
				currentAnswer[getIndex()[0]][getIndex()[1]] = 7;
				repaint();

			}
		}
	}
	private class eightKey extends AbstractAction{
		public void actionPerformed(ActionEvent e)
		{
			if(timeLeft != 0 && !fourxfour)
			{
				currentAnswer[getIndex()[0]][getIndex()[1]] = 8;
				repaint();

			}
		}
	}
	private class nineKey extends AbstractAction{
		public void actionPerformed(ActionEvent e)
		{
			if(timeLeft != 0 && !fourxfour)
			{
				currentAnswer[getIndex()[0]][getIndex()[1]] = 9;
				repaint();

			}
		}
	}

	private class enter extends AbstractAction{
		public void actionPerformed(ActionEvent e)
		{
			if(isRunning)
			{
				if(gameSol.compareAnswer(currentAnswer))
				{
					gameStatus = "Success!";
					gameTimer.timeStop();
					isRunning = false;
				}
				else
				{
					gameStatus = "Try Again!";
					if(gameTimer.timeRemaining() == 0)
					{
						isRunning = false;
					}
				}
			}

		}
	}

	private class up extends AbstractAction{
		public void actionPerformed(ActionEvent e)
		{
			if(fourxfour)
			{
				if(sq_y != 13)
				{
					if(sq_y == 113)
					{
						sq_y -= 100;
					}
					else
					{
						sq_y -= 101;
					}
				}
			}
			else
			{
				switch(sq_y)
				{
				case 180:
					sq_y -= 45;
					break;
				case 281:
					sq_y -= 45;
					break;
				default:
					if(sq_y <= 17)
					{
						break;
					}
					sq_y -= 44;
					break;
				}
				//System.out.println(sq_y);
			}

		}
	}
	private class down extends AbstractAction{
		public void actionPerformed(ActionEvent e)
		{
			if(fourxfour)
			{
				if(sq_y != 315)
				{
					if(sq_y == 215)
					{
						sq_y += 100;
					}
					else
					{
						sq_y += 101;
					}
				}
			}
			else
			{
				switch(sq_y)
				{
				case 16:
					sq_y += 44;
					break;
				case 180:
					sq_y += 45;
				case 236:
					sq_y += 45;
					break;
				default:
					if(sq_y >= 369)
					{
						break;
					}
					sq_y += 44;
					break;
				}
				//System.out.println(sq_y);
			}

		}
	}

	private class delete extends AbstractAction{
		public void actionPerformed(ActionEvent e)
		{
			currentAnswer[getIndex()[0]][getIndex()[1]] = 0;
			repaint();
		}
	}
	private class left extends AbstractAction{
		public void actionPerformed(ActionEvent e)
		{
			if(fourxfour)
			{
				if(sq_x != 20)
				{
					sq_x -= 95;
				}
			}
			else
			{
				switch(sq_x)
				{
				case 103:
					sq_x -= 45;
					break;
				case 148:
					sq_x -= 45;
					break;
				case 280:
					sq_x -= 46;
					break;
				default:
					if(sq_x <= 16)
					{
						break;
					}
					sq_x -= 43;
					break;
				}
				//System.out.println(sq_x);
			}

		}
	}
	private class right extends AbstractAction{
		public void actionPerformed(ActionEvent e)
		{
			if(fourxfour)
			{
				if(sq_x != 305)
				{
					sq_x += 95;
				}
			}
			else
			{
				switch(sq_x)
				{
				case 58:
					sq_x += 45;
					break;
				case 103:
					sq_x += 45;
					break;
				case 234:
					sq_x += 46;
					break;
				default:
					if(sq_x >= 336)
					{
						break;
					}
					sq_x += 43;
					break;
				}
				//System.out.println(sq_x);
			}
		}
	}


}


