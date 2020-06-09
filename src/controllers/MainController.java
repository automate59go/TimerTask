package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import utils.SubTask;
import utils.Task;
import utils.TaskList;
import views.EditTimeBox;
import views.SetNameBox;

public class MainController {
	TaskList taskArrayList = new TaskList();
	
	ObservableList<Task> taskList = FXCollections.observableArrayList();
	public static File file = new File("resources/save/save");
	@FXML
	private ListView<HBox> list;
	
	@FXML
	public Label mainTimer;
	
	@FXML
	public Button newTask;
	
    @FXML
    public void exitApplication(ActionEvent event) {
       Platform.exit();
    }
	
    public void initialisation() {
    	if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			if(loadsave(file) != null) {
				taskArrayList = loadsave(file);
				for (Task task : taskArrayList.getList()) {
					task.initTimer();
				}
			}
		}
  
        initList();
    }
    
    public void refreshMainTimer() {
    	LocalTime tmp = LocalTime.MIN;
    	for (Task task : taskArrayList.getList()) {
			tmp = tmp.plusHours(task.getTimeAmount().getHour());
			tmp = tmp.plusMinutes(task.getTimeAmount().getMinute());
			tmp = tmp.plusSeconds(task.getTimeAmount().getSecond());
		}
    	mainTimer.setText(tmp.format(DateTimeFormatter.ISO_TIME));
    }
	/*
	 * initialise la list
	 */
    public void initList() {
    	//taskArrayList.removeAll();
    	taskList = FXCollections.observableArrayList(taskArrayList.getList());
    	displayTask();
    }
    /**
     * fonction d'affiche / ré-affichage de la listview
     */
    public void displayTask() {
    	list.getItems().clear();
    	refreshMainTimer();
    	for (Task task : taskList) {
    		//reset timer 
    		task.setStatus(false);
    		task.stopTimer();
    		HBox hb = new HBox();
    		Label taskname = new Label(task.getName());
    		taskname.setPrefWidth(233);
    		Label time = new Label(task.getTimeamountString());
    		time.setAlignment(Pos.CENTER);
    		time.setPrefWidth(152);
    		time.setPadding(new Insets(0, 0, 0, 10));
    		Button run = new Button();
    		run.setGraphic(new ImageView(new Image("file:resources/image/play.png")));
    		run.setTooltip(new Tooltip("to run a Task"));
    		Button addSub = new Button();
    		addSub.setTooltip(new Tooltip("to add a SubTask"));
    		addSub.setGraphic(new ImageView(new Image("file:resources/image/add.png")));
	    		addSub.setOnAction(e->{
	    			addSubTask(task);
	    		});
	    	Button reset = new Button();
	    	reset.setGraphic(new ImageView(new Image("file:resources/image/refresh.png")));
	    	reset.setTooltip(new Tooltip("to reset a TaskTimer"));
	    	reset.setOnAction(e->{
	    		resetTimer(task);
	    	});
	    	Button edit = new Button();
	    	edit.setGraphic(new ImageView(new Image("file:resources/image/pencil.png")));
	    	edit.setTooltip(new Tooltip("to edit a TaskTimer"));
	    	edit.setOnAction(e->{
	    		EditTimeBox.clear();
	    		LocalTime tmp = EditTimeBox.display("Edit Timer", task.getTimeAmount());
	    		if (tmp != null) {
	    			task.setTimeAmount(tmp);
		    		displayTask();
				}
	    	});
	    	Button suppr = new Button();
	    	suppr.setGraphic(new ImageView(new Image("file:resources/image/trash.png")));
	    	suppr.setOnAction(e->{
	    		deleteTask(task);
	    	});
	    	suppr.setTooltip(new Tooltip("to delete a Task"));
	    	run.setOnAction(new RunAction(run, task,time,this));
	    	if (task instanceof SubTask) {
				taskname.setPadding(new Insets(0, 0, 0, 40));
			}
    		hb.getChildren().addAll(taskname,time,run,addSub,reset,edit,suppr);
    		list.getItems().add(hb);
		}
    }
    
	public void addTask(ActionEvent event) {
		SetNameBox.clear();
		String name = SetNameBox.display("New Task Name");
		if (name != null) {
			taskArrayList.add(new Task(name, false));
			taskList.removeAll(taskList);
			taskList = FXCollections.observableArrayList(taskArrayList.getList());
			displayTask();
		}
	}
	
	public void addSubTask(Task t) {
		SetNameBox.clear();
		String name = SetNameBox.display("New SubTask Name");
		if (name != null) {
			if (t instanceof SubTask) {
				SubTask tmp = (SubTask) t;
				taskArrayList.add(taskArrayList.getList().indexOf(t)+1, new SubTask(name, false,tmp.getParent()));
			}else {
				taskArrayList.add(taskArrayList.getList().indexOf(t)+1, new SubTask(name, false,t));
			}
			taskList.removeAll(taskList);
			taskList = FXCollections.observableArrayList(taskArrayList.getList());
			displayTask();
		}
	}
	
	public void resetTimer(Task task) {
		LocalTime tmp = LocalTime.parse("00:00:00");
		task.setTimeAmount(tmp);
		displayTask();
	}
	
	public void deleteTask(Task t) {
		ArrayList<Task> tmp = new ArrayList<>();
		tmp.add(t);
		
		if (!(t instanceof SubTask)) {
			for (Task task : taskArrayList.getList()) {
				if (task instanceof SubTask) {
					SubTask tmpTask = (SubTask) task;
					if (tmpTask.getParent() == t) {
						tmp.add(tmpTask);
					}
				}
			}
		}			
		taskArrayList.getList().removeAll(tmp);
		taskList = FXCollections.observableArrayList(taskArrayList.getList());
		displayTask();
	}

	static public TaskList loadsave(File tmpFile) {
		TaskList tmp = null;
		try(ObjectInputStream load = new ObjectInputStream(new FileInputStream(tmpFile))) {
			tmp =(TaskList)load.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tmp;
	}
	
	 public void save() {
		try(ObjectOutputStream save = new ObjectOutputStream(new FileOutputStream(file))) {
			save.writeObject(taskArrayList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
