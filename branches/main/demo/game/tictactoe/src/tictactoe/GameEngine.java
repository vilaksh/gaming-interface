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

	private boolean CheckAndSendGameStatus() {
		ArrayList<String> inputArray = new ArrayList<String>();
		// Send Game status..
		if (!isGameOver()) {
			inputArray.clear();
			inputArray.add("c");
			SendPlayerInput(inputArray); // automatically increments the
			return false;
		} else {
			if (_i_player_won == 0) {
				inputArray.add("w");
			} else if (_i_player_won == 1) {
				inputArray.add("l");
			} else {
				inputArray.add("d");
			}
			SendPlayerInput(inputArray);
			return true;
		}

	}

	@Override
	public boolean ExecuteGame() {
		// Send initial data to player 1

		// send initial data to player 2

		ArrayList<String> outArray = new ArrayList<String>();
		ArrayList<String> inArray = new ArrayList<String>();

		while (true) {

			int x, y;

			// get input from current player
			if (CheckAndSendGameStatus())
				return true;

			outArray.clear();
			for(int i = 0; i < 9; i++)
			{
				outArray.add(((Integer)matrix[i]).toString());
			}
			SendPlayerInput(outArray);
			SendPlayerInput(outArray);
			
			_get_next_player(); //bcoz SendPlayerInput auto increments..
			
			// assuming i get both the inputs i.e. x,y coordinate together.. is
			// this ok?
			PlayerOutput out1 = GetPlayerOutput();
			if (out1.outputType != PlayerOutputType.CorrectOutput) {
				_player_won_coz_err = 1;
			} else {
				inArray = out1.outputFromPlayer;
				x = Integer.parseInt(inArray.get(0));
				y = Integer.parseInt(inArray.get(1));

				_gb_board.updateBoard(x, y, Owner.Player1);
			}
			
			// get input from current player
			if (CheckAndSendGameStatus())
				return true;

			out1 = GetPlayerOutput();
			if (out1.outputType != PlayerOutputType.CorrectOutput) {
				_player_won_coz_err = 1;
			} else {
				inArray = out1.outputFromPlayer;

				x = Integer.parseInt(inArray.get(0));
				y = Integer.parseInt(inArray.get(1));

				_gb_board.updateBoard(x, y, Owner.Player1);
			}
			
			
		}
	}

}
