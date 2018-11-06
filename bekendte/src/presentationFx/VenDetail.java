package presentationFx;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import logic.Ven;
import logic.VenType;
import logic.VennerOgBekendte;

public class VenDetail {

	private Stage stage;
	private VennerOgBekendte venner;
	private VenOversigt oversigt;
	private VenTypeConverter converter = new VenTypeConverter();
	
	private ChoiceBox<VenType> choiceType;
	private TextField textNavn;
	private TextField textEmail;
	private TextField textTelefon;
	private VenFx venFx = null;
	
	
	public VenDetail(Stage stage, VennerOgBekendte venner, VenOversigt oversigt) {
		this.stage = stage;
		this.venner = venner;
		this.oversigt = oversigt;
	}
	
	public VenDetail(Stage stage, VennerOgBekendte venner, VenOversigt oversigt, VenFx venFx) {
		this(stage, venner, oversigt);
		this.venFx = venFx;
	}
	
	public void start() {
		stage.setTitle("Ven Detail");

		BorderPane border = new BorderPane();
		border.setCenter(makeCenter());
		border.setBottom(makeBottom());
	    
		Scene scene = new Scene(border, 400, 250);
		stage.setScene(scene);
	    stage.show();
	}
	
	private Node makeCenter() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Label labelType = new Label("Type : ");
		grid.add(labelType, 0, 0);
	    GridPane.setHalignment(labelType, HPos.RIGHT);
	    choiceType = new ChoiceBox<>();	
	    choiceType.getItems().setAll(VenType.values());
	    choiceType.setConverter(converter);
	    grid.add(choiceType, 1, 0);
	    Label labelEmail = new Label("Email : ");
	    grid.add(labelEmail, 0, 1);
	    GridPane.setHalignment(labelEmail, HPos.RIGHT);
	    textEmail = new TextField();
	    grid.add(textEmail, 1, 1);
	    Label labelNavn = new Label("Navn : ");
	    grid.add(labelNavn, 0, 2);
	    GridPane.setHalignment(labelNavn, HPos.RIGHT);
	    textNavn = new TextField();
	    grid.add(textNavn, 1, 2);
	    Label labelTelefon = new Label("Telefon : ");
	    grid.add(labelTelefon, 0, 3);
	    GridPane.setHalignment(labelTelefon, HPos.RIGHT);
	    textTelefon = new TextField();
	    grid.add(textTelefon, 1, 3);
	    
	    if (venFx != null) {
	    	choiceType.setValue(converter.fromString(venFx.getType()));
		    textEmail.setEditable(false);
	    	textEmail.setText(venFx.getEmail());
	    	textNavn.setText(venFx.getNavn());
	    	textTelefon.setText(venFx.getTelefon());
	    }
		return grid;
	}

	private Node makeBottom() {
		FlowPane pane = new FlowPane();
		pane.setAlignment(Pos.BASELINE_RIGHT);
	    pane.setPadding(new Insets(15, 12, 15, 12));

	    Button buttonGem = new Button("Gem");
	    buttonGem.setPrefSize(100, 20);
	    buttonGem.setOnAction(e ->  gemVen());

	    Button buttonAnnuller = new Button("Annuller");
	    buttonAnnuller.setPrefSize(100, 20);
	    buttonAnnuller.setOnAction(e -> stage.close());
	    
	    pane.getChildren().addAll(buttonGem, buttonAnnuller);
		return pane;
	}
	
	private void gemVen() {
		if (venFx == null) {
			opretVen();
		} else {
			opdaterVen();
		}
	}
	
	private void opretVen() {
		Ven nyven = new Ven(choiceType.getValue(), textNavn.getText(), textEmail.getText(), textTelefon.getText());
		boolean rc = venner.opret(nyven);
		if (rc) {
			VenFx nyvenFx = new VenFx(nyven);
			oversigt.addVenToTable(nyvenFx);
			Alert alert = new Alert(AlertType.INFORMATION, "Ven " + nyven.getNavn() + " oprettet", ButtonType.OK);
			alert.showAndWait();
			stage.close();
		} else {
			Alert alert = new Alert(AlertType.ERROR, "Ven " + nyven.getNavn() + " ikke oprettet", ButtonType.OK);
			alert.showAndWait();
		}	
	}
	
	private void opdaterVen() {
		Ven nyven = new Ven(choiceType.getValue(), textNavn.getText(), textEmail.getText(), textTelefon.getText());
		boolean rc = venner.opdater(nyven);
		if (rc) {
			venFx.setType(converter.toString(choiceType.getValue()));
			venFx.setNavn(textNavn.getText());
			venFx.setEmail(textEmail.getText());
			venFx.setTelefon(textTelefon.getText());
			oversigt.refreshTable();
			Alert alert = new Alert(AlertType.INFORMATION, "Ven " + nyven.getNavn() + " opdateret", ButtonType.OK);
			alert.showAndWait();
			stage.close();
		} else {
			Alert alert = new Alert(AlertType.ERROR, "Ven " + nyven.getNavn() + " ikke opdateret", ButtonType.OK);
			alert.showAndWait();
		}
	}
}
