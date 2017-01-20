package pl.wat.wcy.snakefx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.wat.wcy.snakefx.inject.DependencyInjector;
import pl.wat.wcy.snakefx.util.FxmlFactory;
import pl.wat.wcy.snakefx.util.KeyboardHandler;
import pl.wat.wcy.snakefx.util.PopupDialogHelper;
import pl.wat.wcy.snakefx.view.FXMLFile;
import pl.wat.wcy.snakefx.viewmodel.ViewModel; 


public class Launcher extends Application {

    public static void main(final String... args) {
        Application.launch(Launcher.class, args);
    }


    @Override
    public void start(final Stage primaryStage) {
        DependencyInjector dependencyInjector = new DependencyInjector();

        FxmlFactory fxmlFactory = new FxmlFactory(dependencyInjector);
        Scene scene = new Scene(fxmlFactory.getFxmlRoot(FXMLFile.MAIN));
        scene.setOnKeyPressed(dependencyInjector.get(KeyboardHandler.class));


        PopupDialogHelper popupDialogHelper = new PopupDialogHelper(fxmlFactory);


        ViewModel viewModel = dependencyInjector.get(ViewModel.class);

        Stage aboutStage = popupDialogHelper.createModalDialog(viewModel.aboutWindowOpen, primaryStage, FXMLFile.ABOUT);

        Stage highScoreStage = popupDialogHelper.createModalDialog(viewModel.highscoreWindowOpen, primaryStage, FXMLFile.HIGHSCORE);

        popupDialogHelper.createModalDialog(viewModel.newHighscoreWindowOpen, highScoreStage, FXMLFile.NEW_HIGHSCORE);

        primaryStage.setTitle("SnakeFX");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
