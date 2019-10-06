package serverlogger;

import com.thoughtworks.xstream.XStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

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
	
	@Override
	public String toString()
	{
		return "EVENTO DI NAVIGAZIONE\n" +
			"Nome Applicazione: " + nomeApplicazione + "\n" +
			"IP Client: " + ipClient + "\n" +
			"Data: " + data.valore + " (formato: " + data.formato + ")\n" +
			"Ora: " + ora.valore + " (formato: " + ora.formato + ")\n" +
			"Evento: " + nomeEvento + "\n#####";
	}
	
	public static EventoDiNavigazione ricevi(ServerSocket ss)
	{
		String xml;
		try (Socket socket = ss.accept();
			DataInputStream dis = new DataInputStream(socket.getInputStream())) {
			xml = dis.readUTF();
			if (!validaXML(xml))
				return null;
			return fromXML(xml);
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
			return null;
		}
	}
	
	private static EventoDiNavigazione fromXML(String xml)
	{
		XStream xs = new XStream();
		xs.alias("EventoDiNavigazione", EventoDiNavigazione.class);
		xs.registerConverter(new DataOraEventoConverter());
		return (EventoDiNavigazione)xs.fromXML(xml);
	}
	
	private static boolean validaXML(String xml)
	{
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Document d = db.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
			Schema s = sf.newSchema(new StreamSource(new File("evento.xsd")));
			s.newValidator().validate(new DOMSource(d));
		} catch (SAXException ex) {
			System.err.println("Ricevuto XML di EventoDiNavigazione NON valido: " + ex.getMessage());
			return false;
		} catch (ParserConfigurationException | IOException ex) {
			System.err.println(ex.getMessage());
			return false;
		}
		return true;
	}
	
	private EventoDiNavigazione() { }
}
