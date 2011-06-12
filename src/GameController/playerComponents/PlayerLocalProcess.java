package playerComponents;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import utilityObjects.Logger;

public class PlayerLocalProcess extends Player
{
	private Process _player_process;
	private String _process_location;
	
	private BufferedReader _output_from_process;
	private BufferedReader _error_from_process;
	private PrintWriter _input_to_process;	
	
	private boolean _verify_process_valid()
	{
		try 
		{
			return !_error_from_process.ready();			
		} 
		catch (IOException e) 
		{
			Logger.LogError(e.getMessage());
		}
		return false;
	}	
	
	private String _internal_read()
	{
		StringBuilder line = new StringBuilder();
		
		int i;
		char c;
		
		try
		{
			while(_output_from_process.ready())
			{
				i = _output_from_process.read();
				if( i == -1 )
					return line.toString();
				
				c = (char) i;				
				if(c == '\n' || c == '\r')
				{
					if(line.length() == 0)
						return null;
					return line.toString();
				}
				line.append(c);								
			}
		}
		catch (IOException e)
		{
			Logger.LogError(e.getMessage());
		}
		
		if(line.length() == 0)
		{
			return null;
		}
		else
		{
			return line.toString();
		}
	}
	
	public PlayerLocalProcess(String processLocation, int timeOut,
			int playerAliveTimeOut)
	{
		_process_location = processLocation;
		_player_turn_timeout = timeOut;
		_player_alive_timeout = playerAliveTimeOut;
	}
	
	public boolean ConnectPlayer()
	{
		boolean ret = true;
		
		Logger.LogMessage("Connecting Player " + _process_location);
		
		try
		{
			_player_process = Runtime.getRuntime().exec(_process_location);
			Logger.LogMessage("Created the player process" + _process_location);
			
			_output_from_process = new BufferedReader
			(new InputStreamReader(_player_process.getInputStream())); 			
			Logger.LogMessage("Created reader for player's output stream");
			
			_error_from_process = new BufferedReader
			(new InputStreamReader(_player_process.getErrorStream()));
			Logger.LogMessage("Created reader for player's error stream");	
			
			_input_to_process = new PrintWriter(_player_process.getOutputStream());
			Logger.LogMessage("connecting to player's input stream");			
		}
		catch(Exception e)
		{
			ret = false;
			Logger.LogError("Unable to initialize player process" +
					_process_location);
			Logger.LogError(e.getMessage());
		}
		
		return ret;
	}	
	
	public void DisconnectPlayer()
	{
		Logger.LogMessage("Disconnecting player " + _process_location);
		
		if(_player_process == null)
		{
			Logger.LogError("The player process " + _process_location +
					" has already been destroyed");
			return;
		}
		
		try 
		{
			_output_from_process.close();
			_error_from_process.close();
			_input_to_process.close();
		} 
		catch (IOException e) 
		{
			Logger.LogError("Some error occured while closing the output streams" +
					" from " + _process_location );
			Logger.LogError(e.getMessage());
		}		
				
		_player_process.destroy();
		_player_process = null;
		
		Logger.LogMessage("All stream of player process " + _process_location +
				" successfully closed and process destroyed");
	}
	
	public boolean InputToPlayer(ArrayList<String> input)
	{ 
		Logger.LogMessage("Sending input to process " + _process_location);
		
		if(!_verify_process_valid())
		{
			Logger.LogError("Player process " + _process_location
					+" seems to be in some error state");
			return false;
		}
		
		for(int i=0; i<input.size(); i++)
		{
			_input_to_process.println(input.get(i));
			_input_to_process.flush();
		}
		
		Logger.LogMessage("Sending input to process " + _process_location + 
				" successful");					
		
		return true;
	}
	
	public PlayerOutput OutputFromPlayer()
	{
		PlayerOutput ret = new PlayerOutput();
		String line;
		
		Logger.LogMessage("Reading output from player process " + _process_location);
		
		if(!_verify_process_valid())
		{
			Logger.LogError("Player process " + _process_location
					+" seems to be in some error state, terminating player");
			ret.outputFromPlayer = null;
			ret.outputType = PlayerOutputType.PlayerDead;
			this.DisconnectPlayer();
		
			return ret;
		}
		
		try 
		{
			Thread timeOut = new Thread(this);
			_current_timer = _player_turn_timeout;
			_is_timedout = false;
			
			timeOut.start();
			while(!_output_from_process.ready() && !_is_timedout);		
									
			if(_is_timedout)
			{
				Logger.LogWarning("Player " + _process_location + " timed out. " +
						"Verifying if player is still alive");
				
				timeOut = new Thread(this);
				
				_current_timer = _player_alive_timeout;
				_is_timedout = false;
				
				timeOut.start();				
				while(!_output_from_process.ready() && !_is_timedout);
				
				if(_is_timedout)
				{
					Logger.LogError("Player " + _process_location + " is dead");
					
					ret.outputFromPlayer = null;
					ret.outputType = PlayerOutputType.PlayerDead;
					this.DisconnectPlayer();
					return ret;
				}
				timeOut.interrupt();
				
				ret.outputFromPlayer.add(_internal_read());
				ret.outputType = PlayerOutputType.TimedOut;
				
				Logger.LogWarning("Player " + _process_location + " is alive, " +
						"but timed out for this turn" );
				
				return ret;
			}
			timeOut.interrupt();
			
			//line = _internal_read();
			//while (line != null)
			//{
				ret.outputFromPlayer.add(_output_from_process.readLine());
				//line = _internal_read();
			//}
			ret.outputType = PlayerOutputType.CorrectOutput;
			
			Logger.LogMessage("Output successfully read from player " + 
					_process_location);			
		} 
		catch (Exception e) 
		{
			Logger.LogError(e.getMessage());
		}

		return ret;
	}
}
