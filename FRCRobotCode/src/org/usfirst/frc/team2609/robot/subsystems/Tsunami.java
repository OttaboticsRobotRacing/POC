package org.usfirst.frc.team2609.robot.subsystems;
import org.usfirst.frc.team2609.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Tsunami extends Subsystem {

    public void initDefaultCommand() {

    }
    public static void pullUp(double speed){
//    	RobotMap.tsunamiMotor.changeControlMode(TalonControlMode.PercentVbus);
    	RobotMap.tsunamiMotor.set(speed*SmartDashboard.getNumber("climber speed", 0)); // - VE!!
    }
}

