package utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class TaskList implements Serializable{
	/**
	 * default serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Task> list;
	/**
	 * constructeur de la TaskList
	 */
	public TaskList() {
		this.list = new ArrayList<Task>();
	}
	/**
	 * permet de retourner la list
	 * @return list de tâche
	 */
	public ArrayList<Task> getList() {
		return list;
	}
	/**
	 * permet de définir une list
	 * @param list : liste de tâche à définir
	 */
	public void setList(ArrayList<Task> list) {
		this.list = list;
	}
	/**
	 * permet d'ajouter une tâche à la liste
	 * @param t : tâche à ajouter
	 * @return : résultat de l'ajout
	 */
	public boolean add(Task t) {
		return list.add(t);
	}
	/**
	 * permet d'ajouter à un endroit précis de la liste
	 * @param i : index où il faut ajouter
	 * @param t : tâche à ajouter
	 */
	public void add(int i,Task t) {
		list.add(i,t);
	}
	/**
	 * enlever toute les tâches de la liste
	 */
	public void removeAll() {
		list.removeAll(list);
	}

	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream ois)
			throws IOException, ClassNotFoundException {
		this.list = (ArrayList<Task>)ois.readObject();

	}
	
	private void writeObject(ObjectOutputStream oos) throws IOException{
		oos.writeObject(this.list);
	}
	
}
