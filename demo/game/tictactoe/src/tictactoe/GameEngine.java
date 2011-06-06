package tictactoe;

import gameComponents.Game;

public class GameEngine extends Game 
{
	public GameEngine(String configFile) 
	{
		super(configFile);
	}

	@Override
	protected Integer _get_next_player() 
	{
		return null;
	}

	@Override
	protected Integer _get_current_player() 
	{
		return null;
	}

	@Override
	public boolean ExecuteGame() 
	{
		return false;
	}
	
}
