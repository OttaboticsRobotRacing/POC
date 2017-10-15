package org.usfirst.frc.team2609.robot.subsystems;

public class GearPath {
	public double gyro; // input
	public double angleToTarget; // input
	public double distanceToTarget; // input
	public double endDistanceToTarget = 24; // input
	public static double distanceToDrive; // being calculated in calc()
	public static double angleToDrive; // being calculated in calc()
	double fi;
	
	public void calc(){
		if (angleToTarget>0){
			fi = 90-(angleToTarget+gyro);
		}
		else if (angleToTarget<0){
			fi = 90+(angleToTarget+gyro);
		}
		else{
			if (gyro>0){
				fi = 90-(angleToTarget+gyro);
			}
			else if (gyro<0){
				fi = 90+(angleToTarget+gyro);
			}
			else{
				fi = 0;
			}
		}
		System.out.println("fi: " + Double.toString(fi));
		System.out.println(fi);
		double X = (Math.cos(Math.toRadians(fi)))*distanceToTarget;
		System.out.println("X: " + Double.toString(X));
		System.out.println(X);
		double Y = Math.sin(Math.toRadians(fi))*distanceToTarget;
		System.out.println("Y: " + Double.toString(Y));
		System.out.println(Y);
		double Y2 = Y-endDistanceToTarget;
		System.out.println("Y2: " + Double.toString(Y2));
		System.out.println(Y2);
		distanceToDrive = Math.sqrt(Math.pow(X, 2)+Math.pow(Y2, 2));
		System.out.println("distanceToDrive: " + Double.toString(distanceToDrive));
		System.out.println(distanceToDrive);
		if (angleToTarget>0){
			angleToDrive = 90-Math.toDegrees(Math.asin(Y2/distanceToDrive));
		}
		else{
			angleToDrive = -(90-Math.toDegrees(Math.asin(Y2/distanceToDrive)));
		}
		System.out.println("angleToDrive: " + Double.toString(angleToDrive));
		System.out.println(angleToDrive);
		
	}
}
