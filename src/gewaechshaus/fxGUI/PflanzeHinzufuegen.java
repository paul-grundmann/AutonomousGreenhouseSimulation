package gewaechshaus.fxGUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class PflanzeHinzufuegen extends GridPane {

	public PflanzeHinzufuegen() {
		super();
		this.setAlignment(Pos.CENTER_RIGHT);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));

		// Komponenten laden
		Label titel = new Label();
		titel.setText("Pflanze Hinzufügen");

		Button nachArtErnten = new Button("Eine Art ernten");
		Button nachReifegradErnten = new Button("Nach Reifegrad ernten");
		Button einePflanzeErnten = new Button("Eine einzige Pflanze ernten");
		Button alleErnten = new Button("Alle ernten");

		// Event-Listener setzen

		// Komponenten ins Grid einfügen
		this.add(nachArtErnten, 0, 0, 1, 1);
		this.add(nachReifegradErnten, 1, 0, 1, 1);
		this.add(einePflanzeErnten, 0, 1, 1, 1);
		this.add(alleErnten, 1, 1, 1, 1);
	}

}
