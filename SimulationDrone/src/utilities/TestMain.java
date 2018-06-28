package utilities;

import java.util.Vector;

import physics.collisions.CollisionTools;
import physics.collisions.colliders.Sphere;

public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//test1();
		
		test2();

	}
	
	public static void test2()
	{
		Sphere s1=new Sphere(new Vect3(1,1,0), 1);
		Sphere s2=new Sphere(new Vect3(4,4,0), 2);
		
		boolean spheresIntersect=CollisionTools.intersect(s1, s2);
		System.out.println("intersect before moving : "+spheresIntersect);
		
		CollisionTools.stickSpheres(s1, s2, s1.getRadius(), s2.getRadius());
		
		System.out.println("s1 center : "+s1.getCenter());
		System.out.println("s2 center : "+s2.getCenter());
	}
	
	public static void test1()
	{
		Sphere s1=new Sphere(new Vect3(1,1,0), 1);
		Sphere s2=new Sphere(new Vect3(4,4,0), 2);
		Vect3 v1=new Vect3(1,0,0);
		Vect3 v2=new Vect3(0,-1,0);
		
		boolean spheresIntersect=CollisionTools.intersect(s1, s2);
		
		System.out.println("intersect before moving : "+spheresIntersect);
		
		long t1=System.nanoTime();
		long t, t2;
		
		//for(int i=0;i<a;i++)

		Vector<Vect3> res=CollisionTools.getSpheresCentersAtCollision(s1, s2, v1, v2);
		Vect3 s1Center=res.get(0);
		Vect3 s2Center=res.get(1);
		
		t2=System.nanoTime();
		t=t2-t1;
		
		System.out.println("s1 center : "+s1Center);
		System.out.println("s2 center : "+s2Center);
		
<<<<<<< HEAD:SimulationDrone/src/simulationDrones/TestMain.java
		System.out.println("\nTime : "+t/1000.0+" s");
		
		//getSpheresCentersAtCollision rated at an average execution time around 0.2s
=======
		System.out.println("\nTime : "+t/1000.0+" µs");
		
		//getSpheresCentersAtCollision rated at an average execution time around 0.2�s
>>>>>>> 66bfa454a0470580197d63bc24c994801b458b86:SimulationDrone/src/utilities/TestMain.java
	}

}
