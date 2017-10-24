package org.usfirst.frc.team2609.robot.subsystems;

public class GearPath {
	public double gyro; // input
	public double angleToTarget; // input
	public double distanceToTarget = 48; // input
	public double endDistanceToTarget = 24; // input
	public static double distanceToDrive; // being calculated in calc()
	public static double angleToDrive; // being calculated in calc()
	
	public void calc(){
		double X = Math.cos(angleToTarget)*distanceToTarget;
		System.out.println("X: " + Double.toString(X));
		System.out.println(X);
		double fi = gyro+angleToTarget;
		System.out.println("fi: " + Double.toString(fi));
		System.out.println(fi);
		double Y = Math.sin(fi)*distanceToTarget;
		System.out.println("Y: " + Double.toString(Y));
		System.out.println(Y);
		double Y2 = Y-endDistanceToTarget;
		System.out.println("Y2: " + Double.toString(Y2));
		System.out.println(Y2);
		distanceToDrive = Math.sqrt(Math.pow(X, 2)+Math.pow(Y, 2));
		System.out.println("distanceToDrive: " + Double.toString(distanceToDrive*33.33));
		System.out.println(distanceToDrive*33.33);
		angleToDrive = Math.asin(Y2/distanceToDrive);
		System.out.println("angleToDrive: " + Double.toString(angleToDrive));
		System.out.println(angleToDrive);
		
	}
}
