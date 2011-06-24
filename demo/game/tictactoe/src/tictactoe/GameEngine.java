package tictactoe;

import java.util.ArrayList;

import playerComponents.PlayerOutput;
import playerComponents.PlayerOutputType;

import gameComponents.Game;

public class GameEngine extends Game {
	private int _i_current_player;
	private GameBoard _gb_board; // this is to draw..
	private int[] matrix;
	private int _i_player_won;
	int _player_won_coz_err;

	public GameEngine(String configFile) {
		super(configFile);

	}

	public Boolean Initialize(GameBoard gb) {
		if (_player_id.size() != 2)
			return false;

		_gb_board = gb;
		_i_current_player = -1;
		_i_player_won = -1;
		_player_won_coz_err = -1;
		matrix = new int[9];
		for (int i = 0; i < 9; i++) {
			matrix[i] = -1;
		}
		
		_player_controller.ConnectPlayer(_player_id.get(0));
		_player_controller.ConnectPlayer(_player_id.get(1));
		
		return true;
	}

	public void Clean()
	{
		
	}
	
	@Override
	protected Integer _get_next_player() {
		_i_current_player++;

		if (_i_current_player > 1)
			_i_current_player = 0;

		return _i_current_player;
	}

	@Override
	protected Integer _get_current_player() {
		return _i_current_player;
	}

	private boolean isGameOver() {
		boolean gameDraw = true;
		boolean gameOver = false;

		_i_player_won = -1;

		if (_player_won_coz_err != -1) {
			_i_player_won = _player_won_coz_err;
			return true;
		}
		for (int i = 0; i < 9; i++) {
			if (matrix[i] == -1) {
				gameDraw = false;
			}
		}

		for (int row = 0; row < 3; row++) {
			int idx = row * 3;
			if (matrix[idx] != -1 && matrix[idx] == matrix[idx + 1]
					&& matrix[idx + 1] == matrix[idx + 2]) {
				_i_player_won = matrix[idx];
				gameOver = true;
			}
		}

		for (int col = 0; col < 3; col++) {
			int idx = col;
			if (matrix[idx] != -1 && matrix[idx] == matrix[idx + 3]
					&& matrix[idx + 3] == matrix[idx + 6]) {
				_i_player_won = matrix[idx];
				gameOver = true;
			}
		}

		if (matrix[0] != -1 && matrix[0] == matrix[4] && matrix[4] == matrix[8]) {
			_i_player_won = matrix[0];
			gameOver = true;
		}

		if (matrix[2] != -1 && matrix[2] == matrix[4] && matrix[4] == matrix[6]) {
			_i_player_won = matrix[2];
			gameOver = true;
		}

		if (gameOver || gameDraw)
			return true;
		return false;
	}

	private boolean CheckAndSendGameStatus(int current_player) {
		ArrayList<String> inputArray1 = new ArrayList<String>();
		ArrayList<String> inputArray2 = new ArrayList<String>();
		// Send Game status..
		if (!isGameOver()) {
			inputArray1.clear();
			inputArray1.add("c");
			SendPlayerInput(current_player, inputArray1); // automatically increments the
			return false;
		} else {
			if (_i_player_won == current_player) {
				inputArray1.add("w");
				inputArray2.add("l");
			} else if (_i_player_won == 1 - current_player) {
				inputArray1.add("l");
				inputArray2.add("w");
			} else {
				inputArray1.add("d");
				inputArray2.add("d");
			}
			SendPlayerInput(current_player, inputArray1);
			SendPlayerInput(1-current_player, inputArray2);
			return true;
		}

	}

	@Override
	public boolean ExecuteGame() {
		// Send initial data to player 1
		ArrayList<String> inputArray1 = new ArrayList<String>();

		inputArray1.add("0");
		SendPlayerInput(0, inputArray1); 
		
		inputArray1.clear();
		inputArray1.add("1");
		SendPlayerInput(0, inputArray1); 

		inputArray1.clear();
		inputArray1.add("-1");
		SendPlayerInput(0, inputArray1); 


		
		inputArray1.clear();
		inputArray1.add("1");
		SendPlayerInput(1, inputArray1); 
		
		inputArray1.clear();
		inputArray1.add("0");
		SendPlayerInput(1, inputArray1); 

		inputArray1.clear();
		inputArray1.add("-1");
		SendPlayerInput(1, inputArray1); 

		
		boolean game_over = false;
		while (!game_over) {
			int current_player = _get_next_player();
			game_over = HandlePlayer(current_player);			
		}
		
		return true;
	}
	
	//returns true if game over..
	private boolean HandlePlayer(int current_player)
	{
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int x, y;
		ArrayList<String> outArray = new ArrayList<String>();
		ArrayList<String> inArray = new ArrayList<String>();


		
		// get input from current player
		if (CheckAndSendGameStatus(current_player))
			return true;

		//send board status
		outArray.clear();
		for(int i = 0; i < 9; i++)
		{
			outArray.add(((Integer)matrix[i]).toString());
		}
		SendPlayerInput(current_player, outArray);
		
		
		//Get user input and update board
		// assuming i get both the inputs i.e. x,y coordinate together.. is
		// this ok?
		PlayerOutput out1 = GetPlayerOutput(current_player);
		PlayerOutput out2 = GetPlayerOutput(current_player);
		if (out1.outputType != PlayerOutputType.CorrectOutput || out2.outputType != PlayerOutputType.CorrectOutput) {
			_player_won_coz_err = 1-current_player;
		} else {
			inArray = out1.outputFromPlayer;
			inArray.addAll(out2.outputFromPlayer);
			x = Integer.parseInt(inArray.get(0));
			y = Integer.parseInt(inArray.get(1));

			if(current_player == 0)
				_gb_board.updateBoard(x, y, Owner.Player1);
			else
				_gb_board.updateBoard(x, y, Owner.Player2);
			matrix[x*3+y] = current_player;
		}
		return false;
	}

}
