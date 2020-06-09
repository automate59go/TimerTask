package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.Task;

public class RunAction implements EventHandler<ActionEvent>{
	private Button button;
	private Task task;
	private Label time;
	private MainController main;
	private boolean isrun = false;
	
	public RunAction(Button button, Task task,Label time,MainController mc) {
		this.button = button;
		this.task = task;
		this.time = time;
		this.main = mc;
	}

	@Override
	public void handle(ActionEvent event) {
		
		// TODO Auto-generated method stub
		if (!isrun) {
			isrun = true;
			task.setStatus(false);
			//actionner le timer
			task.startTimer(this);
			button.setGraphic(new ImageView(new Image("file:resources/image/stop.png")));
		}else{
			isrun = false;
			//arrêter le timer
			task.setStatus(true);
			task.getTime().cancel();
			button.setGraphic(new ImageView(new Image("file:resources/image/play.png")));
			main.refreshMainTimer();
		}
	}
	
	public Label getLabel() {
		return time;
	}
	

}
