package unilib;

import com.thoughtworks.xstream.XStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.time.LocalDateTime;

public class EventoDiNavigazione implements Serializable
{
	public String nomeApplicazione;
	public String ipClient;
	public DataOraEvento data;
	public DataOraEvento ora;
	public String nomeEvento;
	
	public String toXML()
	{
		XStream xs = new XStream();
		xs.alias("EventoDiNavigazione", EventoDiNavigazione.class);
		xs.registerConverter(new DataOraEventoConverter());
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + xs.toXML(this);
	}
	
	public static void invia(String evento)
	{
		ParametriDiConnessione pdc = ParametriDiConfigurazione.ottieniConfigurazione().parametriDiConnessione;
		EventoDiNavigazione eventoDiNavigazione = new EventoDiNavigazione(evento);
		try (Socket socket = new Socket(pdc.ipServer, pdc.portaServer);
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
			dos.writeUTF(eventoDiNavigazione.toXML());
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
	}
	
	private EventoDiNavigazione(String evento)
	{
		nomeApplicazione = "UniLib";
		nomeEvento = evento;
		ParametriDiConnessione pdc = ParametriDiConfigurazione.ottieniConfigurazione().parametriDiConnessione;
		ipClient = pdc.ipClient;
		LocalDateTime dataCorrente = LocalDateTime.now();
		data = new DataOraEvento("yyyy-MM-dd", dataCorrente);
		ora = new DataOraEvento("HH:mm:ss", dataCorrente);
	}
}
