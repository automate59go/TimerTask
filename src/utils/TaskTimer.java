package utils;

import java.util.TimerTask;

import controllers.RunAction;
import javafx.application.Platform;

public class TaskTimer extends TimerTask{
	private Task task;
	RunAction ra;
	
	public TaskTimer(Task task,RunAction ra) {
		this.task = task;
		this.ra = ra;
	}


	@Override
	public void run() {
		task.plusOnesecond();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ra.getLabel().setText(task.getTimeamountString());
			}
		});
		if(task.isStatus()) {
			cancel();
		}
	}

}
