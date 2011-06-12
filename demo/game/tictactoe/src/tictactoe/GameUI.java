/**
 * 
 */
package tictactoe;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import playerGameInterface.Interface;

/**
 * @author AlokUSC
 * 
 */
public class GameUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel _l_title;

	private JPanel _p_status;

	private JLabel _l_player1_name;
	private JLabel _l_player1_symbol;

	private JLabel _l_player2_name;
	private JLabel _l_player2_symbol;

	private GameBoard _gb_board;
	private GameEngine _ge_engine;
	public GameUI() {
		// set the window to square with side = min(width,height)/2
		Dimension screenD = Toolkit.getDefaultToolkit().getScreenSize();
		int len = Math.min(screenD.width, screenD.height);
		setSize(len / 2, len / 2);

		// set the location to the center of the screen
		setLocationRelativeTo(getParent());

		setResizable(false);

		// Initialize the game UI
		initializeUI();
		
		// Initialize game engine
		initializeGame();
				
		
	}

	public void executeGame()
	{
		//execute Game
		_ge_engine.ExecuteGame();		
	}
	private boolean initializeGame() {
		
		_ge_engine = new GameEngine(".\\config\\ConfigFile.xml");
		_ge_engine.Initialize(_gb_board);
		return true;
	}

	private void initializeUI() {
		setLayout(new BorderLayout());
		setTitle("Tic Tac Toe");

		// North
		// Title
		_l_title = new JLabel("Tic Tac Toe", JLabel.CENTER);

		this.add(_l_title, BorderLayout.PAGE_START);

		// South
		// Player Status
		_p_status = new JPanel(new GridLayout(2, 4));
		_l_player1_name = new JLabel("Player 1 TODO 1", JLabel.RIGHT); // TODO:
																		// Get
																		// player
																		// name
																		// from
																		// the
																		// GameController
		_l_player1_symbol = new JLabel(
				new ImageIcon(
						".\\resources\\x.jpg")); // TODO:
																													// Use
																													// SVG
																													// images

		_l_player2_name = new JLabel("Player 2 TODO 2", JLabel.RIGHT); // TODO:
																		// Get
																		// player
																		// name
																		// from
																		// the
																		// GameController
		_l_player2_symbol = new JLabel(
				new ImageIcon(
						".\\resources\\o.jpg")); // TODO:
																												// Use
																												// SVG
																												// images

		_p_status.add(_l_player1_name);
		_p_status.add(_l_player1_symbol);

		_p_status.add(_l_player2_name);
		_p_status.add(_l_player2_symbol);

		this.add(_p_status, BorderLayout.PAGE_END);

		// Center
		_gb_board = new GameBoard();
		this.add(_gb_board, BorderLayout.CENTER);
		// Game Board
	}
}

class GameBoard extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	private Owner[][] posArr;
	private final int boardSize = 3;
	private Image _i_player1_symbol;
	private Image _i_player2_symbol;

	public GameBoard() {
		_i_player1_symbol = Toolkit
				.getDefaultToolkit()
				.getImage(
						".\\resources\\x.jpg"); // TODO:
																												// Use
																												// SVG
																												// images
		_i_player2_symbol = Toolkit
				.getDefaultToolkit()
				.getImage(
						".\\resources\\o.jpg"); // TODO:
																											// Use
																											// SVG
																											// images

		posArr = new Owner[boardSize][boardSize]; // is it ok to have this as
													// 3x3 or should be generic?
		for (int i = 0; i < boardSize; i++)
			for (int j = 0; j < boardSize; j++)
				posArr[i][j] = Owner.None;

	}

	private void drawBoard(Graphics2D g) {
		int height = getHeight();
		int width = getWidth();

		// Horizontal
		int hgap = 0;
		for (int i = 0, x = hgap, y = height / 3; i < boardSize - 1; i++) {
			g.drawLine(x, y, x + width - 2 * hgap, y);
			y += height / 3;
		}

		// verticals
		int vgap = 0;
		for (int i = 0, x = width / 3, y = vgap; i < boardSize - 1; i++) {
			g.drawLine(x, y, x, y + height - 2 * vgap);
			x += width / 3;
		}
	}

	private void drawSymbols(Graphics2D g) {
		int height = getHeight();
		int width = getWidth();

		int centerx;
		int centery;
		int imgwidth;
		int imgheight;

		centerx = width / 3 / 2;
		centery = height / 3 / 2;

		imgheight = 2 * height / 3 / 3;
		imgwidth = 2 * width / 3 / 3;

		for (int i = 0; i < boardSize; i++) {
			centerx = width / 3 / 2;
			for (int j = 0; j < boardSize; j++) {

				switch (posArr[i][j]) {
				case Player1:
					g.drawImage(_i_player1_symbol, centerx - imgwidth / 2,
							centery - imgheight / 2, imgwidth, imgheight, this);
					break;

				case Player2:
					g.drawImage(_i_player2_symbol, centerx - imgwidth / 2,
							centery - imgheight / 2, imgwidth, imgheight, this);
					break;

				}

				centerx += width / 3;
			}
			centery += height / 3;
		}

	}

	public void paint(Graphics g) {
		Graphics2D g2;
		g2 = (Graphics2D) g;
		drawBoard(g2);
		drawSymbols(g2);
	}

	public void updateBoard(int x,int y, Owner o)
	{
		posArr[x][y] = o;
		repaint();
	}
}
