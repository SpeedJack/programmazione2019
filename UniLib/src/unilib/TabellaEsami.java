package unilib;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TabellaEsami extends TableView
{
	public TabellaEsami()
	{
		TableColumn codiceEsameCol = new TableColumn("Codice");
		TableColumn insegnamentoCol = new TableColumn("Insegnamento");
		TableColumn creditiCol = new TableColumn("Crediti");
		TableColumn votoCol = new TableColumn("Voto");
		TableColumn dataCol = new TableColumn("Data");
		
		codiceEsameCol.setCellValueFactory(new PropertyValueFactory<>("codiceEsame"));
		insegnamentoCol.setCellValueFactory(new PropertyValueFactory<>("insegnamento"));
		creditiCol.setCellValueFactory(new PropertyValueFactory<>("crediti"));
		votoCol.setCellValueFactory(new PropertyValueFactory<>("voto"));
		dataCol.setCellValueFactory(new PropertyValueFactory<>("data"));
		
		getColumns().addAll(codiceEsameCol, insegnamentoCol, creditiCol,
			votoCol, dataCol);
	}
	
	public boolean haEsameSelezionato()
	{
		return !getSelectionModel().isEmpty();
	}
	
	public Esame ottieniEsameSelezionato()
	{
		return haEsameSelezionato() ?(Esame)getSelectionModel().getSelectedItem() : null;
	}
	
	public void deselezionaEsame()
	{
		getSelectionModel().clearSelection();
	}
	
	public void selezionaEsame(String codiceEsame)
	{
		for (Object riga: getItems())
			if (((Esame)riga).getCodiceEsame().equals(codiceEsame))
			{
				deselezionaEsame();
				getSelectionModel().select(riga);
				return;
			}
	}
}
