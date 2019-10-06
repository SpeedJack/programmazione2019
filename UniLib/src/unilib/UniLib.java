package unilib;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class UniLib extends Application
{
	private Studente studente;
	private AreaLibretto areaLibretto;
	private AreaStatistiche areaStatistiche;
	private AreaModifica areaModifica;
	
	@Override
	public void start(Stage primaryStage)
	{
		EventoDiNavigazione.invia("AVVIO");
		ParametriDiConfigurazione pdc = ParametriDiConfigurazione.ottieniConfigurazione();
		studente = pdc.studente;
		studente.caricaEsami();
		
		areaLibretto = new AreaLibretto(this, studente);
		areaLibretto.setNomeIstituto(pdc.nomeIstituto);
		areaLibretto.setNomeCorsoDiLaurea(pdc.corsoDiLaurea.nome);
		areaStatistiche = new AreaStatistiche(studente.ottieniStatistiche());
		areaModifica = new AreaModifica(this, studente);
		
		BorderPane root = new BorderPane(); // 01
		root.setCenter(areaLibretto);
		root.setLeft(areaStatistiche);
		root.setRight(areaModifica);
		Scene scene = new Scene(root, 865, 600);
		primaryStage.setTitle("UniLib");
		primaryStage.setScene(scene);
		primaryStage.setMinWidth(865);
		primaryStage.setMinHeight(565);
		primaryStage.show();
		areaModifica.mostraSelettoreData();

		InputCache cache = InputCache.deserializza();
		if (cache != null) {
			if (cache.codiceEsameSelezionato != null)
				areaLibretto.selezionaEsame(cache.codiceEsameSelezionato);
			areaModifica.caricaEsame(cache.ottieniInputEsame());
		}
		areaLibretto.aggiungiSelezioneListener();
	}
	
	@Override
	public void stop() // 02
	{
		InputCache cache = new InputCache(areaModifica.ottieniInputEsame(),
			areaLibretto.ottieniCodiceEsameSelezionato());
		cache.serializza();
		EventoDiNavigazione.invia("TERMINE");
	}
	
	public Esame ottieniEsameSelezionato() // 03
	{
		return areaLibretto.ottieniEsameSelezionato();
	}
	
	public void gestisciEventoSalva() // 03
	{
		EventoDiNavigazione.invia("SALVA");
		areaStatistiche.mostraStatistiche();
		areaLibretto.ricaricaEsami();
	}
	
	public void gestisciEventoAnnulla() // 03
	{
		EventoDiNavigazione.invia("ANNULLA");
		areaLibretto.deselezionaEsame();
	}
	
	public void gestisciEventoElimina() // 03
	{
		EventoDiNavigazione.invia("ELIMINA");
		areaStatistiche.mostraStatistiche();
		areaModifica.cancellaCampi();
	}
	
	public void gestisciEventoSelezione(Esame esame) // 03
	{
		if (esame == null) {
			areaModifica.cancellaCampi();
		} else {
			EventoDiNavigazione.invia("SELEZIONE");
			areaModifica.caricaEsame(esame);
		}
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}

/* COMMENTI:
 * (01) BorderPane: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/BorderPane.html
 *
 * (02) Il metodo stop() viene richiamato quando l'applicazione sta per
 * chiudersi.
 *
 * (03) Metodi per la sincronizzazione tra le varie aree. Questi metodi vengono
 * richiamati quando un tasto viene premuto o viene selezionato un elemento di
 * TabellaEsami in modo che: possano essere ricalcolate le statistiche; alla
 * selezione di un elemento di TabellaEsami si possano riempire i campi di
 * AreaModifica; possano essere rielencati gli esami di TabellaEsami quando il
 * database viene modificato. Ognuno di questi metodi invia anche un
 * EventoDiNavigazione a ServerLogger.
 */
