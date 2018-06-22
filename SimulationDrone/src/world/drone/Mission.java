package simulationDrones;

import java.util.ArrayList;

/**
 * 
 * @author hugo
 *
 */
public class Mission {
	
	/**
	 * 
	 */
	private Priority priority;
	
	/**
	 * 
	 */
	private ArrayList<Objective> objectives;
	
	/**
	 * Default constructor, priority set to low, and no objectives
	 */
	public Mission() {
		setPriority(Priority.Low);
		setObjectives(new ArrayList<Objective>());
	}
	
	/**
	 * Constructor to use when more than 1 objective
	 * @param obj ArrayList containing the objectives of the mission
	 * @param prior	the priority of the mission
	 */
	public Mission(ArrayList<Objective> obj, Priority prior) {
		setObjectives(obj);
		setPriority(prior);
	}
	
	/**
	 * Constructor to use when only 1 objective
	 * @param obj the objective of the mission
	 * @param prior the priority of the mission
	 */
	public Mission(Objective obj, Priority prior) {
		objectives = new ArrayList<Objective>();
		objectives.add(obj);
		setPriority(prior);
	}
	
	/**
	 * deep copy constructor
	 */
	public Mission(Mission m) {
		this();
		for (Objective obj : m.objectives) {
			objectives.add(new Objective(obj));
		}
		this.priority=m.priority;
	}

	public ArrayList<Objective> getObjectives() {
		return objectives;
	}

	public void setObjectives(ArrayList<Objective> objectives) {
		this.objectives = objectives;
	}
	
	/**
	 * Adds one objective at the end of the list of objectives
	 * @param obj the objective to add
	 */
	public void addObjective(Objective obj) {
		this.objectives.add(obj);
	}
	
	/**
	 * Adds multiple objectives at the end of the list of objectives
	 * @param obj the ArrayList containing the objectives
	 */
	public void addObjectives(ArrayList<Objective> obj) {
		this.objectives.addAll(obj);
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	
}
