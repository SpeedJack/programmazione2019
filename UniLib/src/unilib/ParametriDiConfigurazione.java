package unilib;

import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
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

public class ParametriDiConfigurazione implements Serializable
{
	public final String nomeIstituto;
	public final Studente studente;
	public final CorsoDiLaurea corsoDiLaurea;
	public final ParametriDiConnessione parametriDiConnessione;
	private static ParametriDiConfigurazione istanzaSingleton;
	
	private ParametriDiConfigurazione()
	{
		this("???", null, null, null);
	}
	
	private ParametriDiConfigurazione(String nomeIstituto, Studente studente, CorsoDiLaurea cdl, ParametriDiConnessione pdc)
	{
		this.nomeIstituto = nomeIstituto;
		this.studente = studente;
		corsoDiLaurea = cdl;
		parametriDiConnessione = pdc;
	}
	
	public static ParametriDiConfigurazione ottieniConfigurazione()
	{
		if (istanzaSingleton == null)
			try {
				istanzaSingleton = caricaConfigurazione();
			} catch (FileNotFoundException ex) {
				System.err.println(ex.getMessage());
			}
		return istanzaSingleton;
	}
	
	private static boolean validaXML()
	{
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Document d = db.parse(new File("config.xml"));
			Schema s = sf.newSchema(new StreamSource(new File("config.xsd")));
			s.newValidator().validate(new DOMSource(d));
		} catch (SAXException ex) {
			System.err.println("config.xml NON valido: " + ex.getMessage());
			return false;
		} catch (ParserConfigurationException | IOException ex) {
			System.err.println(ex.getMessage());
			return false;
		}
		return true;
	}
	
	private static ParametriDiConfigurazione caricaConfigurazione() throws FileNotFoundException
	{
		validaXML();
		XStream xs = new XStream();
		FileReader fr = new FileReader("config.xml");
		xs.alias("ParametriDiConfigurazione", ParametriDiConfigurazione.class);
		xs.aliasField("Studente", ParametriDiConfigurazione.class, "studente");
		xs.aliasAttribute(Studente.class, "votoTesi", "votoTesi");
		xs.aliasAttribute(CorsoDiLaurea.class, "nome", "nome");
		xs.aliasAttribute(CorsoDiLaurea.class, "valoreLode", "valoreLode");
		xs.aliasAttribute(CorsoDiLaurea.class, "creditiTotali", "creditiTotali");
		xs.aliasField("ParametriDiConnessione", ParametriDiConfigurazione.class, "parametriDiConnessione");
		return (ParametriDiConfigurazione) xs.fromXML(fr);
	}
}
