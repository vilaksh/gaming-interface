package playerComponents;

import java.util.ArrayList;

abstract public class Player implements Runnable
{
	protected int _current_timer;
	protected int _player_turn_timeout;
	protected int _player_alive_timeout;
		
	protected boolean _is_timedout;
	
	public void run()
	{
		try
		{
			Thread.sleep(_current_timer);
			_is_timedout = true;
		}
		catch (InterruptedException e)
		{
			_is_timedout = false;
		}
	}
	
	abstract public boolean ConnectPlayer();	
	abstract public boolean InputToPlayer(ArrayList<String> input);	
	abstract public PlayerOutput OutputFromPlayer();
	abstract public void DisconnectPlayer();
}
