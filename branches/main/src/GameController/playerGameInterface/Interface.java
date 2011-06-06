package playerGameInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import playerComponents.Player;
import playerComponents.PlayerLocalProcess;
import playerComponents.PlayerOutput;
import playerComponents.PlayerType;

import utilityObjects.Constants;
import utilityObjects.Logger;
import utilityObjects.XmlUtils;

public class Interface 
{
	private Integer _next_player_id;
	private HashMap<Integer, Player> _player_id_hash;
	
	private Player _initialize_player(Node data, PlayerType playerType, 
			Node timeOutNode)
	{
		Player ret;
		Integer timeOutTurn;
		Integer timeOutPlayer;
		
		switch(playerType)
		{
		case PlayerLocalProcess:
			String processLocation = XmlUtils.GetNodeValue(data, 
					Constants.LOCAL_PROCESS_LOCATION);
			timeOutTurn = Integer.parseInt(XmlUtils.GetNodeValue(timeOutNode,
					Constants.PLAYER_LOCAL_PROCESS));
			timeOutPlayer = timeOutTurn * Integer.parseInt(XmlUtils.GetNodeValue(timeOutNode,
					Constants.PLAYER_TIME_OUT_MULTIPLIER));
			ret = new PlayerLocalProcess(processLocation, timeOutTurn, 
					timeOutPlayer);													
			break;
		case PlayerUndefined:
			ret = null;
			break;
		case PlayerHuman:
		case PlayerNetworkProcess:
		default:
			Logger.LogError("Player type " + playerType.toString() + " is not supported " +
					"as of now");
			ret = null;
		}
		
		return ret;
	}
	
	private Integer _get_next_player_id()
	{
		return _next_player_id++;
	}
	
	private PlayerType _get_player_type(String playerType)
	{
		if (playerType.equals(Constants.PLAYER_HUMAN))
			return PlayerType.PlayerHuman;
		else if (playerType.equals(Constants.PLAYER_LOCAL_PROCESS))
			return PlayerType.PlayerLocalProcess;
		else if (playerType.equals(Constants.PLAYER_NETWORK_PROCESS))
			return PlayerType.PlayerNetworkProcess;
		else
			Logger.LogError("Player type " + playerType +
					"is not a valid type");
			return PlayerType.PlayerUndefined;
	}
	
	public Interface()
	{
		_next_player_id = 0;
		_player_id_hash = new HashMap<Integer, Player>();
	}
	
	public ArrayList<Integer> InitializeGame(String configFile)
	{
		ArrayList<Integer> playerIDs = null;
		String playerType;
		
		NodeList timeOutNodeList, playerNodeList;
		Node timeOutNode, playerNode;		
		
		int numberPlayers = 0;
		
		try
		{
			File inputFile = new File(configFile);
			Element data = XmlUtils.ParseXml(inputFile);
			
			Logger.LogMessage("Config file" + configFile + "successfully parsed");
			
			timeOutNodeList = data.getElementsByTagName(Constants.TIME_OUT);
			timeOutNode = timeOutNodeList.item(0);
			
			Logger.LogMessage("TimeOut node successfully Obtained");
			
			playerNodeList = data.getElementsByTagName(Constants.PLAYER);
			numberPlayers = playerNodeList.getLength();
			
			Logger.LogMessage("All player nodes obtained. Processing each player");
			
			for(int i=0; i<numberPlayers; i++)
			{
				playerNode = playerNodeList.item(i);
				
				playerType = XmlUtils.GetNodeValue(playerNode,
						Constants.PLAYER_TYPE);
				
				Player player = _initialize_player(playerNode,
						_get_player_type(playerType), timeOutNode);
				
				if(player != null)
					_player_id_hash.put(_get_next_player_id(), player);				
			}					
		}
		catch(Exception e)
		{
			Logger.LogError(e.getMessage());
		}
		
		Logger.LogMessage("Total number of players in config file:" + 
				numberPlayers);
		Logger.LogMessage("Total number of players successfully initialized:" + 
				_player_id_hash.size());
		
		playerIDs = new ArrayList<Integer>(_player_id_hash.keySet());
		return playerIDs;
	}
	
	public boolean SendData(Integer playerID, ArrayList<String> data)
	{
		return _player_id_hash.get(playerID).InputToPlayer(data);		
	}
	
	public PlayerOutput GetData(Integer playerID)
	{
		return _player_id_hash.get(playerID).OutputFromPlayer();				
	}
	
	public boolean ConnectPlayer(Integer playerID)
	{
		return _player_id_hash.get(playerID).ConnectPlayer();
	}
	
	public void DisconnectPlayer(Integer playerID)
	{
		_player_id_hash.get(playerID).DisconnectPlayer();
	}
}
