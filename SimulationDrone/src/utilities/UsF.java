package utilities;

/**
 * stores utilities functions used throughout the project
 * @author Francis
 *
 */
public class UsF {

	public static double getRandomWithin(double min, double max)
	{
		return Math.random()*(max-min)+min;
	}
	
}
