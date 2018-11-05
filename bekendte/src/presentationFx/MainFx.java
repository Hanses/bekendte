package presentationFx;

import javafx.application.Application;
import javafx.stage.Stage;
import logic.VennerOgBekendte;
import logic.VennerOgBekendteFactory;

public class MainFx extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		VennerOgBekendte venner = new VennerOgBekendteFactory().makeVennerOgBekendte();
		VenOversigt oversigt = new VenOversigt(stage, venner);
		oversigt.start();
	}

}
