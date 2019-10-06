package unilib;

import java.text.DecimalFormat;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class AreaStatistiche extends VBox
{
	private final Text media = new Text("--");
	private final Text votoLaurea = new Text("--");
	private final Text votoLaureaMinimo = new Text("--");
	private final Text votoLaureaMassimo = new Text("--");
	private final Text cfuRimanenti = new Text("???");
	private final GraficoVoti graficoVoti = new GraficoVoti();
	private final Statistiche statistiche;
	
	public AreaStatistiche(Statistiche statistiche)
	{
		this.statistiche = statistiche;
		
		Label titolo = new Label("Statistiche");
		Text testoMedia = new Text("Media: ");
		Text titoloVotoLaurea = new Text("\nPrevisione Voto di Laurea:");
		Text testoVotoLaureaAttuale = new Text("\nAttuale: ");
		Text testoVotoLaureaMinimo = new Text("\nMinimo: ");
		Text testoVotoLaureaMassimo = new Text("\nMassimo: ");
		Text testoPreCfuRimanenti = new Text("\n(");
		Text testoPostCfuRimanenti = new Text(" CFU rimanenti)");
		Label titoloGrafico = new Label("Grafico Voti");
		ScrollPane pannelloGraficoVoti = new ScrollPane(); // 01
		
		titolo.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
		testoMedia.setStyle("-fx-font-size: 16;");
		media.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
		titoloVotoLaurea.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
		testoVotoLaureaAttuale.setStyle("-fx-font-size: 16;");
		votoLaurea.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
		votoLaureaMinimo.setStyle("-fx-font-weight: bold;");
		votoLaureaMassimo.setStyle("-fx-font-weight: bold;");
		cfuRimanenti.setStyle("-fx-font-weight: bold;");
		titoloGrafico.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
		
		TextFlow corpoStatistiche = new TextFlow(
			testoMedia, media,
			titoloVotoLaurea,
			testoVotoLaureaAttuale, votoLaurea,
			testoVotoLaureaMinimo, votoLaureaMinimo,
			testoVotoLaureaMassimo, votoLaureaMassimo,
			testoPreCfuRimanenti, cfuRimanenti, testoPostCfuRimanenti
		);
		pannelloGraficoVoti.setContent(graficoVoti);
		pannelloGraficoVoti.setFitToHeight(true);
		
		titolo.setPadding(new Insets(6, 0, 0, 0));
		corpoStatistiche.setPadding(new Insets(6, 6, 0, 18));
		titoloGrafico.setPadding(new Insets(18, 0, 0, 6));
		pannelloGraficoVoti.setPadding(new Insets(6, 0, 0, 0));
		setPadding(new Insets(12, 12, 12, 12));
		setAlignment(Pos.TOP_CENTER);
		setMinWidth(150);
		setMaxWidth(300);
		
		mostraStatistiche();
		
		getChildren().addAll(titolo, corpoStatistiche,
			titoloGrafico, pannelloGraficoVoti);
		
	}
	
	public final void mostraStatistiche()
	{
		double m = statistiche.media();
		int vl = statistiche.votoLaurea();
		if (Double.isNaN(m))
			media.setText("--");
		else
			media.setText(new DecimalFormat("#.0#").format(m));
		if (vl == -1) {
			votoLaurea.setText("--");
			votoLaureaMinimo.setText("--");
			votoLaureaMassimo.setText("--");
		} else {
			votoLaurea.setText(Integer.toString(vl));
			votoLaureaMinimo.setText(Integer.toString(statistiche.votoLaureaMinimo()));
			votoLaureaMassimo.setText(Integer.toString(statistiche.votoLaureaMassimo()));
		}
		cfuRimanenti.setText(Integer.toString(statistiche.creditiRimanenti()));
		graficoVoti.setData(statistiche.mediaEsami());
	}
}

/* COMMENTI:
 * (01) ScrollPane: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/ScrollPane.html
 * Viene utilizzato per poter mostrare tutto il contenuto di GraficoVoti in uno
 * spazio orizzontale ristretto.
 */
