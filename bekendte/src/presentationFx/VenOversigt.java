package presentationFx;

import data.Container;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import logic.Ven;
import logic.VennerOgBekendte;

public class VenOversigt {

	private Stage stage;
	private VennerOgBekendte venner;
	
	private BorderPane border;
	private TextField soeg;
	private TableView<VenFx> tabel;

	public VenOversigt(Stage stage, VennerOgBekendte venner) {
		this.stage = stage;
		this.venner = venner;
	}

	public void start() {
		stage.setTitle("Oversigt over venner og bekendte");

		border = new BorderPane();
		border.setTop(makeTop());
		border.setBottom(makeBottom());

		Scene scene = new Scene(border, 500, 500);
		stage.setScene(scene);
		stage.show();
	}

	public void addVenToTable(VenFx venFx) {
		if (tabel != null) {
			tabel.getItems().add(venFx);
		}
	}

	public void removeVenFromTable(VenFx venFx) {
		tabel.getItems().remove(venFx);
	}
	
	public void refreshTable() {
		tabel.refresh();
	}

	private Node makeTop() {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10);

		Label labelSoeg = new Label("Indtast søgekriterie : ");
		soeg = new TextField();
		Button buttonSoeg = new Button("Søg");
		buttonSoeg.setOnAction(e -> procesSoeg());

		hbox.getChildren().addAll(labelSoeg, soeg, buttonSoeg);
		return hbox;
	}

	private Node makeBottom() {
		FlowPane pane = new FlowPane();
		pane.setAlignment(Pos.BASELINE_RIGHT);
		pane.setPadding(new Insets(15, 12, 15, 12));

		Button buttonOpret = new Button("Opret");
		buttonOpret.setPrefSize(100, 20);
		buttonOpret.setOnAction(e -> procesOpret());

		Button buttonOpdater = new Button("Vis/Opdater");
		buttonOpdater.setPrefSize(100, 20);
		buttonOpdater.setOnAction(e -> procesOpdater());

		Button buttonSlet = new Button("Slet");
		buttonSlet.setPrefSize(100, 20);
		buttonSlet.setOnAction(e -> procesSlet());
		
		pane.getChildren().addAll(buttonOpret, buttonOpdater, buttonSlet);
		return pane;
	}

	private void procesSoeg() {
		Container<Ven> fundneVenner = findVenner();
		if (fundneVenner.size() > 0) {
			border.setCenter(makeTabel(fundneVenner));
			tabel.setOnMouseClicked(e -> procesClick(e));
		}
	}

	private void procesOpret() {
		VenDetail detail = new VenDetail(new Stage(), venner, VenOversigt.this);
		detail.start();
	}

	private void procesOpdater() {
		if (tabel != null) {
			VenFx ven = tabel.getSelectionModel().getSelectedItem();
			if (ven != null) {
				VenDetail detail = new VenDetail(new Stage(), venner, VenOversigt.this, ven);
				detail.start();
			}
		}
	}

	private void procesSlet() {
		if (tabel != null) {
			VenFx venFx = tabel.getSelectionModel().getSelectedItem();
			if (venFx != null) {
				Alert alert = new Alert(AlertType.CONFIRMATION, "Slet ven ?", ButtonType.YES, ButtonType.NO);
				alert.showAndWait();
				if (alert.getResult() == ButtonType.YES) {
					boolean rc = venner.slet(venFx.getEmail());
					if (rc) {
						removeVenFromTable(venFx);
						alert = new Alert(AlertType.INFORMATION, "Ven Slettet", ButtonType.OK);
						alert.showAndWait();
					} else {
						alert = new Alert(AlertType.ERROR, "Ven ikke slettet", ButtonType.OK);
						alert.showAndWait();
					}
				}
			}
		}
	}

	private Container<Ven> findVenner() {
		return venner.find(soeg.getText());
	}

	@SuppressWarnings("unchecked")
	private TableView<VenFx> makeTabel(Container<Ven> fundneVenner) {
		tabel = new TableView<>();
		tabel.setItems(makeObservableList(fundneVenner));

		TableColumn<VenFx, String> typeCol = new TableColumn<>("Type");
		typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
		TableColumn<VenFx, String> navnCol = new TableColumn<>("Navn");
		navnCol.setCellValueFactory(new PropertyValueFactory<>("navn"));
		TableColumn<VenFx, String> emailCol = new TableColumn<>("Email");
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		TableColumn<VenFx, String> telefonCol = new TableColumn<>("Telefon");
		telefonCol.setCellValueFactory(new PropertyValueFactory<>("telefon"));

		tabel.getColumns().setAll(typeCol, navnCol, emailCol, telefonCol);
		return tabel;
	}

	private ObservableList<VenFx> makeObservableList(Container<Ven> fundneVenner) {
		ObservableList<VenFx> fundneVennerFx = FXCollections.observableArrayList();
		for (int i = 0; i < fundneVenner.size(); i++) {
			Ven ven = fundneVenner.getElement(i);
			VenFx venFx = new VenFx(ven);
			fundneVennerFx.add(venFx);
		}
		return fundneVennerFx;
	}

	private void procesClick(Event event) {
		if (event instanceof MouseEvent) {
			MouseEvent mouseevent = (MouseEvent) event;
			if (mouseevent.getClickCount() > 1) {
				procesOpdater();
			}
		}
	}
}
