package unilib;

import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AreaModifica extends GridPane // 01
{
	private final UniLib contenitore;
	private final Studente studente;
	private final TextField codiceEsame = new TextField();
	private final TextField insegnamento = new TextField();
	private final CheckBox inf = new CheckBox("ING-INF/05");
	private final TextField crediti = new TextField();
	private final TextField voto = new TextField();
	private final DatePicker data = new DatePicker();
	
	public AreaModifica(UniLib contenitore, Studente studente)
	{
		this.contenitore = contenitore;
		this.studente = studente;
		
		ColumnConstraints col1 = new ColumnConstraints();
		ColumnConstraints col2 = new ColumnConstraints();
		col1.setPercentWidth(50); col2.setPercentWidth(50);
		getColumnConstraints().addAll(col1, col2);
		
		Button bottoneSalva = new Button("SALVA");
		Button bottoneAnnulla = new Button("ANNULLA");
		HBox contenitoreBottoneSalva = new HBox();
		HBox contenitoreBottoneAnnulla = new HBox();
		Label labelCodiceEsame = new Label("Codice Esame:");
		Label labelInsegnamento = new Label("Insegnamento:");
		Label labelCrediti = new Label("Crediti:");
		Label labelVoto = new Label("Voto:");
		Label labelData = new Label("Data:");
		
		bottoneSalva.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
		bottoneAnnulla.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
		
		labelCodiceEsame.setPadding(new Insets(12, 0, 0, 0));
		codiceEsame.setPadding(new Insets(6, 6, 6, 6));
		labelInsegnamento.setPadding(new Insets(12, 0, 0, 0));
		insegnamento.setPadding(new Insets(6, 6, 6, 6));
		labelCrediti.setPadding(new Insets(12, 0, 0, 0));
		crediti.setPadding(new Insets(6, 6, 6, 6));
		labelVoto.setPadding(new Insets(12, 0, 0, 0));
		voto.setPadding(new Insets(6, 6, 6, 6));
		labelData.setPadding(new Insets(12, 0, 0, 0));
		contenitoreBottoneSalva.setAlignment(Pos.BASELINE_CENTER);
		contenitoreBottoneAnnulla.setAlignment(Pos.BASELINE_CENTER);
		contenitoreBottoneSalva.setPadding(new Insets(12, 0, 4, 0));
		contenitoreBottoneAnnulla.setPadding(new Insets(4, 0, 0, 0));
		setPadding(new Insets(12, 12, 12, 12));
		setHgap(18);
		setMaxWidth(250);
		setMinWidth(250);
		
		data.setVisible(false);
		data.setManaged(false);
		
		bottoneSalva.setOnAction(this::gestisciClickBottoneSalva);
		bottoneAnnulla.setOnAction(this::gestisciClickBottoneAnnulla);
		codiceEsame.setOnKeyTyped(this::gestisciInserimentoCodiceEsame);
		
		contenitoreBottoneSalva.getChildren().add(bottoneSalva);
		contenitoreBottoneAnnulla.getChildren().add(bottoneAnnulla);
		add(labelCodiceEsame, 0, 0, 2, 1);
		add(codiceEsame, 0, 1, 2, 1);
		add(labelInsegnamento, 0, 2, 2, 1);
		add(insegnamento, 0, 3, 2, 1);
		add(inf, 0, 4, 2, 1);
		add(labelCrediti, 0, 5);
		add(crediti, 0, 6);
		add(labelVoto, 1, 5);
		add(voto, 1, 6);
		add(labelData, 0, 7, 2, 1);
		add(data, 0, 8, 2, 1);
		add(contenitoreBottoneSalva, 0, 10, 2, 1);
		add(contenitoreBottoneAnnulla, 0, 11, 2, 1);
		
		AreaModifica.setHalignment(contenitoreBottoneSalva, HPos.CENTER);
		AreaModifica.setHalignment(contenitoreBottoneAnnulla, HPos.CENTER);
	}
	
	public void mostraSelettoreData() // 02
	{
		VBox dataSkinBox = new VBox();
		DatePickerSkin dataSkin = (DatePickerSkin) data.getSkin();
		dataSkinBox.getChildren().add(dataSkin.getPopupContent());
		add(dataSkinBox, 0, 9, 2, 1);
		AreaModifica.setHalignment(dataSkinBox, HPos.CENTER);
	}
	
	public void cancellaCampi()
	{
		codiceEsame.clear();
		insegnamento.clear();
		inf.setSelected(false);
		crediti.clear();
		voto.clear();
		data.setValue(LocalDate.now());
	}
	
	public void caricaEsame(Esame esame)
	{
		codiceEsame.setText(esame.getCodiceEsame());
		insegnamento.setText(esame.getInsegnamento());
		inf.setSelected(esame.isInf());
		if (esame.getCrediti() > 0)
			crediti.setText(Integer.toString(esame.getCrediti()));
		else
			crediti.clear();
		voto.setText(esame.getVoto());
		if (esame.getData() != null)
			data.setValue(esame.getData());
		else
			data.setValue(LocalDate.now());
	}
	
	public Esame ottieniInputEsame()
	{
		return new Esame(codiceEsame.getText(),
			insegnamento.getText(), inf.isSelected(),
			Integer.parseInt(crediti.getText().isEmpty() ? "0" : crediti.getText()),
			voto.getText(), data.getValue()
		);
	}
	
	private void gestisciClickBottoneSalva(ActionEvent evento)
	{
		Esame esameSelezionato = contenitore.ottieniEsameSelezionato();
		if (esameSelezionato != null)
			studente.modificaEsame(esameSelezionato, ottieniInputEsame());
		else
			studente.aggiungiEsame(ottieniInputEsame());
		contenitore.gestisciEventoSalva();
		cancellaCampi();
	}
	
	private void gestisciClickBottoneAnnulla(ActionEvent evento)
	{
		contenitore.gestisciEventoAnnulla();
		cancellaCampi();
	}
	
	private void gestisciInserimentoCodiceEsame(KeyEvent evento)
	{
		if (!evento.getCharacter().matches("^[ -~]$") || // 03
			codiceEsame.getText().length() < 4)
			return;
		Esame esame = Esame.ottieniEsame(codiceEsame.getText() + evento.getCharacter()); // 04
		if (esame == null)
			return;
		insegnamento.setText(esame.getInsegnamento());
		inf.setSelected(esame.isInf());
		crediti.setText(Integer.toString(esame.getCrediti()));
	}
}

/* COMMENTI:
 * (01) GridPane: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/GridPane.html
 *
 * (02) Il selettore della data (DatePickerSkin.getPopupContent()) può essere
 * mostrato solo dopo aver caricato la finestra (prima non è definito) con
 * primaryStage.show(). Quindi questo metodo verrà richiamato da UniLib al
 * momento opportuno.
 *
 * (03) matcha tutti i caratteri ASCII visibili.
 *
 * (04) Quando questo evento viene richiamato, la stringa ritornata da
 * codiceEsame.getText() non contiene ancora l'ultimo carattere inserito.
 */
