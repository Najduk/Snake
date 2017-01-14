package pl.wat.wcy.snakefx.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Callback;
import pl.wat.wcy.snakefx.view.FXMLFile;

import java.io.IOException;

public class FxmlFactory {


	private final Callback<Class<?>, Object> controllerInjector;

	public FxmlFactory(final Callback<Class<?>, Object> injector) {
		controllerInjector = injector;
	}

	public Parent getFxmlRoot(final FXMLFile file) {
		final FXMLLoader loader = new FXMLLoader(file.url());

		loader.setControllerFactory(controllerInjector);

		try {
			loader.load();
		} catch (final IOException e) {
			throw new IllegalStateException("Can't load FXML file [" + file.url() + "]", e);
		}

		return loader.getRoot();
	}
}
