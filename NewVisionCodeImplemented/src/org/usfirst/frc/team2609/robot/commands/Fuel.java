package org.usfirst.frc.team2609.robot.commands;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class Fuel extends CommandGroup {

    public Fuel() {
        // Use requires() here to declare subsystem dependencies
        //requires(Robot.exampleSubsystem);
    	addSequential(new DriveEncoder(750, 0.6, 0));
    	addSequential(new TimerDelay(1));
        addSequential(new GyroTurn(0.3,45));
        addSequential(new EncReset());
        addSequential(new TimerDelay(1));
        addSequential(new DriveEncoder(500, 0.6, 45));
        addSequential(new TimerDelay(1));
        addSequential(new DriveEncoder(0, 0.6, 45));
    	addSequential(new TimerDelay(1));
        addSequential(new GyroTurn(0.3,0));
        addSequential(new TimerDelay(1));
        addSequential(new EncReset());
        addSequential(new DriveEncoder(-750, 0.6, 0));       
    }
}
