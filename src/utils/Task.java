package utils;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;

import controllers.RunAction;

public class Task implements Serializable{
	/**
	 * generate serialVersionUID
	 */
	private static final long serialVersionUID = -4398769223546518088L;
	private String name;
	private transient Timer time;
	private LocalTime timeAmount;
	private boolean status;
	/**
	 * constructeur d'une tâche 
	 * @param name : nom de la tâche
	 * @param status : status de la tâche
	 */
	public Task(String name, boolean status) {
		this.name = name;
		this.status = status;
		time = new Timer();
		timeAmount = LocalTime.MIN;
	}
	/**
	 * permet de récupérer le nom d'une tâche
	 * @return nom de la tâche
	 */
	public String getName() {
		return name;
	}
	/**
	 * permet de définir un nom à la tâche
	 * @param name : nom à définir
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * permet de récupérer le statut d'une tâche
	 * @return status de la tâche
	 */
	public boolean isStatus() {
		return status;
	}
	/**
	 * permet de donner un status à la tâche
	 * @param status : status à définir
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}
	/**
	 * permet de récupérer le timer d'une tâche
	 * @return le timer de la tâche
	 */
	public Timer getTime() {
		return time;
	}
	/**
	 * permet d'initialiser un timer
	 */
	public void initTimer() {
		this.time = new Timer();
	}
	/**
	 * permet de recupérer le temps passé sur une tâche
	 * @return timeAmount
	 */
	public LocalTime getTimeAmount() {
		return timeAmount;
	}
	/**
	 * permet d'ajouter une seconde au temps passé sur la tâche
	 */
	public void plusOnesecond() {
		timeAmount = timeAmount.plusSeconds(1);
	}
	/**
	 * renvoie le temps passé au format HH:MM:SS
	 * @return une chaine de caractère du temps passé
	 */
	public String getTimeamountString() {
		return timeAmount.format(DateTimeFormatter.ISO_TIME);
	}
	/**
	 * défini le temps passé sur une tâche
	 * @param timeAmount : temps à définir
	 */
	public void setTimeAmount(LocalTime timeAmount) {
		this.timeAmount = timeAmount;
	}
	/**
	 * Permet de start un timer
	 * @param ra : controller de l'action du timer
	 */
	public void startTimer(RunAction ra) {
		initTimer();
		time.schedule(new TaskTimer(this,ra), 0, 1000);
	}
	/**
	 * permet de stop entièrement un timer
	 */
	public void stopTimer() {
		time.cancel();
	}
	
}
