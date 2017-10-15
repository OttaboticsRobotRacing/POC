package org.usfirst.frc.team2609.robot.commands;
import org.usfirst.frc.team2609.robot.Robot;
import org.usfirst.frc.team2609.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class autoClawOpen extends Command {

    public autoClawOpen() {
    }

    protected void initialize() {
//    	Robot.autoClaw.open();
    	RobotMap.hodor.set(DoubleSolenoid.Value.kReverse);
    	//Robot.LedControl.flash(true);
    	
    }

    protected void execute() {
    	//Robot.LedControl.setLed(255,235,59);
    }

    protected boolean isFinished() {
        return true;//timeSinceInitialized()>1;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
