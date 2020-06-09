package utils;

public class SubTask extends Task{
	/**
	 * generate serialVersionUID
	 */
	private static final long serialVersionUID = 7295046082269532410L;
	private Task parent;
	
	/**
	 * Constructeur d'une sous tâche
	 * @param name : nom de la tâche
	 * @param status : status de la tâche
	 * @param parent : tâche parent de la sous tâche
	 */
	public SubTask(String name, boolean status, Task parent) {
		super(name, status);
		this.parent = parent;
	}
	/**
	 * permet de récupérer le parent d'une sous-tâche
	 * @return : parent de la tache
	 */
	public Task getParent() {
		return parent;
	}
	/**
	 * permet de définir le parent d'une sous-tâche
	 * @param parent : parent à définir
	 */
	public void setParent(Task parent) {
		this.parent = parent;
	}
	
}
