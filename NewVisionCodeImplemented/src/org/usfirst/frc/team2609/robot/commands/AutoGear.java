package org.usfirst.frc.team2609.robot.commands;
import org.usfirst.frc.team2609.robot.subsystems.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoGear extends CommandGroup {

    public AutoGear(double endAngleToTarget) {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.

    	addSequential(new RingLED(true));
    	addSequential(new GearSwerve()); // calculate gearPath
    	addSequential(new GyroTurn(0.4, GearPath.angleToDrive));
    	addSequential(new DriveEncoder((GearPath.distanceToDrive)*33.33, 0.4, 0)); // TODO: convert feet to enc counts
    	addSequential(new GyroTurn(0.4, endAngleToTarget)); // end angle to target!
//    	addSequential(new DriveEncoder(400, 0.4, 0));
    }
}
