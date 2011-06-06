package gameComponents;

import java.util.ArrayList;

import playerComponents.PlayerOutput;
import playerGameInterface.Interface;

public abstract class Game 
{
	protected ArrayList<Integer> _player_id;
	protected Interface _player_controller;
	
	protected abstract Integer _get_next_player();
	protected abstract Integer _get_current_player();
	
	public PlayerOutput GetPlayerOutput()
	{
		Integer playerId = _get_current_player();
		return _player_controller.GetData(playerId);
	}
	
	public boolean SendPlayerInput(ArrayList<String> inputToPlayer)
	{
		Integer playerId = _get_next_player();
		return _player_controller.SendData(playerId, inputToPlayer);
	}
	 
	public abstract boolean ExecuteGame(); 
	
	public Game(String configFile)
	{
		_player_controller = new Interface();
		_player_id = _player_controller.InitializeGame(configFile);
	}	
}
