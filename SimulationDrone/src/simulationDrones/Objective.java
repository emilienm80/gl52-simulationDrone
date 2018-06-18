package simulationDrones;

/**
 * 
 * @author hugo
 *
 */
public class Objective {


	/**
	 * 3D position of the objective
	 */
	private Vect3 position;
	
	/**
	 * Constructor of an objective
	 * @param x coordinate of the objective in the x direction 
	 * @param y coordinate of the objective in the y direction
	 */
	public Objective(int x, int y) {
		setPosition(new Vect3(x,y,0));
	}
	
	public Objective(int x, int y, int z) {
		setPosition(new Vect3(x,y,z));
	}
	
	public Objective(Vect3 pos) {
		this.position = pos;
	}
	
	public Objective(Objective o) {
		this.position = new Vect3(o.position);
	}

	public Vect3 getPosition() {
		return position;
	}

	public void setPosition(Vect3 position) {
		this.position = position;
	}
	
	
	
}
