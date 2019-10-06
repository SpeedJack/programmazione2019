package unilib;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AreaLibretto extends VBox
{
	private final UniLib contenitore;
	private final Studente studente;
	private final Label nomeIstituto = new Label("???");
	private final Label nomeStudente = new Label("???");
	private final Label corsoDiLaurea = new Label("???");
	private final TabellaEsami tabellaLibretto = new TabellaEsami();
	
	public AreaLibretto(UniLib contenitore, Studente studente)
	{
		this.contenitore = contenitore;
		this.studente = studente;
		
		Button bottoneElimina = new Button("ELIMINA");
		HBox contenitoreBottone = new HBox();
		
		nomeIstituto.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
		nomeStudente.setStyle("-fx-font-size: 18px;");
		corsoDiLaurea.setStyle("-fx-font-size: 18px; -fx-font-style: italic;");
		setStyle("-fx-border-style: solid; -fx-border-color: -fx-background grey; -fx-border-width: 1");
		bottoneElimina.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
		
		contenitoreBottone.setAlignment(Pos.BASELINE_RIGHT);
		contenitoreBottone.setPadding(new Insets(12, 0, 0, 0));
		nomeIstituto.setPadding(new Insets(6, 0, 6, 0));
		nomeStudente.setPadding(new Insets(6, 0, 6, 0));
		corsoDiLaurea.setPadding(new Insets(6, 0, 12, 0));
		setPadding(new Insets(12, 12, 12, 12));
		setAlignment(Pos.TOP_CENTER);
		setMinWidth(300);
		
		nomeStudente.setText(studente.nome + " (" + studente.matricola + ")");
		tabellaLibretto.setItems(studente.libretto);
		
		bottoneElimina.setOnAction(this::gestisciClickBottoneElimina);
		
		contenitoreBottone.getChildren().add(bottoneElimina);
		getChildren().addAll(nomeIstituto, nomeStudente, corsoDiLaurea,
			tabellaLibretto, contenitoreBottone);
	}
	
	public void aggiungiSelezioneListener() // 01
	{
		tabellaLibretto.getSelectionModel().selectedItemProperty().addListener((valoriOsservabili, vecchiaSelezione, nuovaSelezione) -> {
			if (!tabellaLibretto.isPressed() && tabellaLibretto.haEsameSelezionato())
				tabellaLibretto.deselezionaEsame();
			else if (tabellaLibretto.isPressed())
				contenitore.gestisciEventoSelezione(tabellaLibretto.ottieniEsameSelezionato());
		});
	}
	
	public void setNomeIstituto(String nome)
	{
		nomeIstituto.setText(nome);
	}
	
	public void setNomeCorsoDiLaurea(String nome)
	{
		corsoDiLaurea.setText(nome);
	}
	
	public void ricaricaEsami() // 02
	{
		tabellaLibretto.setItems(studente.caricaEsami());
		deselezionaEsame();
	}
	
	public Esame ottieniEsameSelezionato() // 02
	{
		return tabellaLibretto.ottieniEsameSelezionato();
	}
	
	public String ottieniCodiceEsameSelezionato() // 02
	{
		Esame esame = tabellaLibretto.ottieniEsameSelezionato();
		return esame == null ? null : esame.getCodiceEsame();
	}
	
	public void selezionaEsame(String codiceEsame) // 02
	{
		tabellaLibretto.selezionaEsame(codiceEsame);
	}
	
	public void deselezionaEsame() // 02
	{
		tabellaLibretto.deselezionaEsame();
	}
	
	private void gestisciClickBottoneElimina(ActionEvent evento)
	{
		Esame esame = ottieniEsameSelezionato();
		if (esame == null)
			return;
		studente.eliminaEsame(esame);
		ricaricaEsami();
		contenitore.gestisciEventoElimina();
		deselezionaEsame();
	}
}

/* COMMENTI:
 * (01) Possiamo aggiungere il listener solo dopo che UniLib ha caricato la
 * InputCache, così da evitare che la selezione della riga della TabellaEsami
 * salvata in cache non faccia riempire i campi di AreaModifica. Questo metodo
 * sarà quindi chiamato da UniLib al momento opportuno.
 *
 * (02) Metodi wrapper per TabellaEsami.
 */