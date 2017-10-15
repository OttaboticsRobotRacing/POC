package org.usfirst.frc.team2609.robot.subsystems;

import java.io.File;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

public class TankBench {

    public static void main(String[] args) {
    	long time = System.currentTimeMillis();
        Trajectory.Config configHigh = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.01, 10, 8.5, 50.0);
        Trajectory.Config configLow = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_LOW, 0.01, 10, 8.5, 50.0);
        Trajectory.Config configFast = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_FAST, 0.01, 10, 8.5, 50.0);
        Waypoint[] points = new Waypoint[] {
                new Waypoint(-2, 114/12, Pathfinder.d2r(60)),
                new Waypoint(0, 0, 0)
        };
        long highGenTime = System.currentTimeMillis();
        Trajectory Hightrajectory = Pathfinder.generate(points, configHigh);
        highGenTime = System.currentTimeMillis() - highGenTime;
        long lowGenTime = System.currentTimeMillis();
        Trajectory Lowtrajectory = Pathfinder.generate(points, configLow);
        lowGenTime = System.currentTimeMillis() - lowGenTime;
        long fastGenTime = System.currentTimeMillis();
        Trajectory Fasttrajectory = Pathfinder.generate(points, configFast);
        fastGenTime = System.currentTimeMillis() - fastGenTime;

        // Wheelbase Width = 28inches
        long highModTime = System.currentTimeMillis();
        TankModifier Highmodifier = new TankModifier(Hightrajectory).modify(28.5);
        highModTime = System.currentTimeMillis() - highModTime;
        long lowModTime = System.currentTimeMillis();
        TankModifier Lowmodifier = new TankModifier(Lowtrajectory).modify(28.5);
        lowModTime = System.currentTimeMillis() - lowModTime;
        long fastModTime = System.currentTimeMillis();
        TankModifier Fastmodifier = new TankModifier(Fasttrajectory).modify(28.5);
        fastModTime = System.currentTimeMillis()-fastModTime;

//         Do something with the new Trajectories...
        Trajectory left = Highmodifier.getLeftTrajectory();
        Trajectory right = Highmodifier.getRightTrajectory();
//        File leftF = new File("left.csv");
//        Pathfinder.writeToCSV(leftF, left);
//        File rightF = new File("right.csv");
//        Pathfinder.writeToCSV(rightF, right);
        double[][] rightpos = right.getPositions();
        double[][] leftpos = left.getPositions();
//        for (int i = 0; i < rightpos.length; i++){
////    		System.out.print("vel:" + rightpos[i][0]+ " ");
////    		System.out.print("accel:" + rightpos[i][1] + " ");
////    		System.out.print("dt:" + rightpos[i][2] + " ");
////    		System.out.print("\n");
//        	System.out.print("{");
//        	System.out.print(rightpos[i][0]);
//        	System.out.print(",");
//        	System.out.print(rightpos[i][1]);
//        	System.out.print(",");
//        	System.out.print(rightpos[i][2]);
//        	System.out.print("}, \n");
//    	} 
        for (int i = 0; i < rightpos.length; i++){
//    		System.out.print("vel:" + rightpos[i][0]+ " ");
//    		System.out.print("accel:" + rightpos[i][1] + " ");
//    		System.out.print("dt:" + rightpos[i][2] + " ");
//    		System.out.print("\n");
        	System.out.print("{");
        	System.out.print(leftpos[i][0]);
        	System.out.print(",");
        	System.out.print(leftpos[i][1]);
        	System.out.print(",");
        	System.out.print(leftpos[i][2]);
        	System.out.print("}, \n");
    	}
        System.out.println("This took "+ (highModTime+highGenTime) + "ms");
//        System.out.println("highGenTime: " + highGenTime);
//        System.out.println("highModTime: " + highModTime);
//        System.out.println("highSumTime: " + (highModTime+highGenTime));
//        
//        System.out.println("lowGenTime: " + lowGenTime);
//        System.out.println("lowModTime: " + lowModTime);
//        System.out.println("lowSumTime: " + (lowModTime+lowGenTime));
//        
//        System.out.println("fastGenTime: " + fastGenTime);
//        System.out.println("fastModTime: " + fastModTime);
//        System.out.println("fastSumTime: " + (fastModTime+fastGenTime));
        
    }

}