package unilib;

import java.io.Serializable;

public class ParametriDiConnessione implements Serializable
{
	public final String ipClient;
	public final String ipServer;
	public final int portaServer;
	
	private ParametriDiConnessione()
	{
		this("127.0.0.1", "127.0.0.1", 8888);
	}
	
	private ParametriDiConnessione(String ipClient, String ipServer, int portaServer)
	{
		this.ipClient = ipClient;
		this.ipServer = ipServer;
		this.portaServer = portaServer;
	}
}
