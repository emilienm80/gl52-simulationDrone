package simulationDrones;

/**
 * 
 * @author hugo
 *
 */
public class Objective {

	/**
	 * 2D Position of the objectiv
	 */
	private int x,y;
	
	/**
	 * Constructor of an objective
	 * @param x coordinate of the objective in the x direction 
	 * @param y coordinate of the objective in the y direction
	 */
	public Objective(int x, int y) {
		this.setX(x);
		this.setY(y);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
}
