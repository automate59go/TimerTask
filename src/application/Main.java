package application;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

import controllers.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	private Stage primaryStage;
	
	@Override
	public void start(final Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			
			Platform.setImplicitExit(false);
			javax.swing.SwingUtilities.invokeLater(this::addAppToTray);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample.fxml"));
			BorderPane root = loader.load();
			MainController controller = (MainController) loader.getController();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			primaryStage.setTitle("TimerTask");
			primaryStage.getIcons().add(new Image("file:resources/image/task.png"));
			
			controller.initialisation();
			primaryStage.setOnHidden(e->{
				controller.save();
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addAppToTray() {
        try {
        	/*
        	 * code utilisé : https://gist.github.com/jewelsea/e231e89e8d36ef4e5d8a
        	 */
            // ensure awt toolkit is initialized.
            java.awt.Toolkit.getDefaultToolkit();

            // app requires system tray support, just exit if there is no support.
            if (!java.awt.SystemTray.isSupported()) {
                System.out.println("No system tray support, application exiting.");
                Platform.exit();
            }

            // set up a system tray icon.
            java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();
            java.awt.Image image = ImageIO.read(new File("resources/image/task.png"));
            java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(image);

            // if the user double-clicks on the tray icon, show the main app stage.
            trayIcon.addActionListener(event -> Platform.runLater(this::showStage));

            // if the user selects the default menu item (which includes the app name), 
            // show the main app stage.
            java.awt.MenuItem openItem = new java.awt.MenuItem("Open App");
            openItem.addActionListener(event -> Platform.runLater(this::showStage));

            // the convention for tray icons seems to be to set the default icon for opening
            // the application stage in a bold font.
            java.awt.Font defaultFont = java.awt.Font.decode(null);
            java.awt.Font boldFont = defaultFont.deriveFont(java.awt.Font.BOLD);
            openItem.setFont(boldFont);

            // to really exit the application, the user must go to the system tray icon
            // and select the exit option, this will shutdown JavaFX and remove the
            // tray icon (removing the tray icon will also shut down AWT).
            java.awt.MenuItem exitItem = new java.awt.MenuItem("Exit");
            exitItem.addActionListener(event -> {
                Platform.exit();
                tray.remove(trayIcon);
            });

            // setup the popup menu for the application.
            final java.awt.PopupMenu popup = new java.awt.PopupMenu();
            popup.add(openItem);
            popup.addSeparator();
            popup.add(exitItem);
            trayIcon.setPopupMenu(popup);

            // add the application tray icon to the system tray.
            tray.add(trayIcon);
        } catch (java.awt.AWTException | IOException e) {
            System.out.println("Unable to init system tray");
            e.printStackTrace();
        }
    }
	
    private void showStage() {
        if (primaryStage != null) {
        	primaryStage.show();
        	primaryStage.toFront();
        }
    }
	
	public static void main(String[] args) {
		launch(args);
	}
}
