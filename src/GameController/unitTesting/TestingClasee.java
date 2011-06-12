package unitTesting;


import java.util.ArrayList;

import playerComponents.PlayerOutput;
import playerComponents.PlayerOutputType;
import playerGameInterface.Interface;

public class TestingClasee {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		/*
		Process _player_process = Runtime.getRuntime().exec("C:\\viji\\spoj problema\\Test.exe");
		
		BufferedReader read = new BufferedReader
		(new InputStreamReader(_player_process.getInputStream()));
		
		PrintWriter p = new PrintWriter(_player_process.getOutputStream());
		
		p.println("123");
		p.flush();
		Thread.sleep(1500);
		int r;
		
		System.out.println("out");
		while(read.ready())
		{
			r = read.read();
			System.out.println("Test" + r);			
		}
		
		p.println("123");
		p.flush();
		
		System.out.println("out");
		while(read.ready())
		{
			r = read.read();
			System.out.println("Test" + r);			
		}
		
		System.out.println(read.ready());
		p.println("-1");
		p.flush();
		_player_process.waitFor();
		System.out.println(read.ready());
		read.close();
		_player_process.getOutputStream().close();
		_player_process.destroy();
		*/
		
		Interface intface = new Interface();
		
		ArrayList<Integer> players = intface.InitializeGame(
				"D:\\Projects\\gaming-interface\\branches\\main\\src\\GameController\\TestData\\ConfigFile.xml");
		
		ArrayList<String> input = new ArrayList<String>();
		PlayerOutput output;
		
		intface.ConnectPlayer(0);
		
		input.add("123");
		intface.SendData(0, input);
		output = intface.GetData(0);
		
		if(output.outputType == PlayerOutputType.CorrectOutput)
		{
		for (String string : output.outputFromPlayer) {
			System.out.println(string);
		}}
		
		input.clear();
		input.add("123");
		intface.SendData(0, input);
		output = intface.GetData(0);
		
		if(output.outputType == PlayerOutputType.CorrectOutput)
		{
		for (String string : output.outputFromPlayer) {
			System.out.println(string);
		}}
		
		input.clear();
		input.add("-1");
		intface.SendData(0, input);
		output = intface.GetData(0);
		
		if(output.outputType == PlayerOutputType.CorrectOutput)
		{
		for (String string : output.outputFromPlayer) {
			System.out.println(string);
		}}
		
		
		intface.DisconnectPlayer(0);
	}
/*
	private class InputProcess implements Runnable
	{
		private BufferedReader _reader;
		
		public boolean _is_available;
		public ArrayList<String> _data;
		
		public InputProcess(InputStreamReader reader)
		{
			_data = new ArrayList<String>();
			_is_available = false;
			_reader = new BufferedReader(_reader);
		}

		@Override
		public void run()
		
		{
			try {
				String line = _reader.readLine();
				while(line!=null)
				{
					_data.add(line);
					line = _reader.readLine();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
	}*/
	
}
