package serverlogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ServerLogger
{
	public static void main(String[] args)
	{
		try (ServerSocket ss = new ServerSocket(8888)) {
			while (true) {
				EventoDiNavigazione evento = EventoDiNavigazione.ricevi(ss);
				if (evento == null)
					continue;
				System.out.println(evento.toString());
				Files.write(Paths.get("eventi.log.xml"),
					(evento.toXML() + "\n<!--#####-->\n").getBytes(),
					StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			}
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		} finally {
			System.out.println("STOP");
		}
	}
}
