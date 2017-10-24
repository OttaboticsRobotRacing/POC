package org.usfirst.frc.team2609.robot.commands;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class Auto1 extends CommandGroup {

    public Auto1() {
        // Use requires() here to declare subsystem dependencies
        //requires(Robot.exampleSubsystem);
    	addSequential(new RingLED(true));
    	addSequential(new TimerDelay(0.5));
        addSequential(new GearLeft());
        addSequential(new RingLED(false));
    }
}
