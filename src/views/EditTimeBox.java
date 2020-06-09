package views;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditTimeBox {
	static LocalTime time = null;
	
	public static LocalTime display(String title,LocalTime taskTime) {
		Stage box = new Stage();
		box.initModality(Modality.APPLICATION_MODAL);
		box.setTitle(title);
		box.getIcons().add(new Image("file:resources/image/pencil.png"));
		box.setMinWidth(250);
		Label label = new Label("Please enter a valid Time code \nmax : 23:59:59 / min : 00:00:00");
		
		//Create text input
		TextField input = new TextField(taskTime.format(DateTimeFormatter.ISO_TIME));
		Button confirm = new Button("Ok");
		confirm.setOnAction(e->{
			time = LocalTime.parse(input.getText());
			box.close();
		});
		Button cancel = new Button("Cancel");
		cancel.setOnAction(e->{
			box.close();
		});
		
		VBox layout = new VBox();
		layout.getChildren().addAll(label,input,confirm,cancel);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		scene.getStylesheets().add("file:resources/css/application.css");
		box.setScene(scene);
		box.showAndWait();
		
		return time;
	}
	
	public static void clear() {
		time = null;
	}
}
