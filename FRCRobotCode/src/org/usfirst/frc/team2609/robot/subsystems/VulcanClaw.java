package org.usfirst.frc.team2609.robot.subsystems;

import org.usfirst.frc.team2609.enums.BallDoorState;
import org.usfirst.frc.team2609.enums.VulcanClawState;
import org.usfirst.frc.team2609.enums.VulcanDeployState;
import org.usfirst.frc.team2609.robot.OI;
import org.usfirst.frc.team2609.robot.Robot;
import org.usfirst.frc.team2609.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class VulcanClaw extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	DoubleSolenoid doubleSolenoid1 = RobotMap.vulcanDeploy;
	DoubleSolenoid doubleSolenoid2 = RobotMap.vulcanClaw;
	 
	public static boolean getGearSensor(){
		if(SmartDashboard.getBoolean("Disable claw", false)){
			return OI.gearSensor.get();
		}else{
			return false; // no gear as far as we know
		}
	}
	
    public void toggleDeployClaw() {
    	System.out.println("For the love of Dean Kamen");
    	System.out.println("pls no");
    	//TODO: fix this spaghetti using the R03 method. 
    	if (doubleSolenoid1.get()==DoubleSolenoid.Value.kReverse && doubleSolenoid2.get()==DoubleSolenoid.Value.kReverse) {
//			Robot.autoClaw.close();
    		doubleSolenoid1.set(DoubleSolenoid.Value.kForward);
    		System.out.println("vulcanDeploy up "+ doubleSolenoid1.get());
    	}
    	else if (doubleSolenoid1.get() == DoubleSolenoid.Value.kForward && doubleSolenoid2.get()==DoubleSolenoid.Value.kReverse){
//			Robot.autoClaw.close();
    		doubleSolenoid1.set(DoubleSolenoid.Value.kReverse);
			System.out.println("vulcanDeploy down "+ doubleSolenoid1.get());
    	}
    	else if(doubleSolenoid1.get()==DoubleSolenoid.Value.kOff && doubleSolenoid2.get()==DoubleSolenoid.Value.kReverse){
//			Robot.autoClaw.close();
			doubleSolenoid1.set(DoubleSolenoid.Value.kReverse);	
    	}
    	else {
    		//doubleSolenoid1.set(DoubleSolenoid.Value.kReverse);
    		System.out.println("Unknown state, do not move!");
    	}    	
    }
    public void toggleClaw() {
    	System.out.println("pls no");
    	if (doubleSolenoid2.get() == DoubleSolenoid.Value.kReverse){
//				Robot.autoClaw.close();
    			doubleSolenoid2.set(DoubleSolenoid.Value.kForward);
    			System.out.println("vulcanClaw open "+ doubleSolenoid2.get());
    	}
    	else if (doubleSolenoid2.get() == DoubleSolenoid.Value.kForward){
//			Robot.autoClaw.close();
			doubleSolenoid2.set(DoubleSolenoid.Value.kReverse);
			System.out.println("vulcanClaw close "+ doubleSolenoid2.get());
    	}
    	else {
//			Robot.autoClaw.close();
    		doubleSolenoid2.set(DoubleSolenoid.Value.kReverse);
    		System.out.println("vulcanClaw close "+ doubleSolenoid2.get());
    	}    	
    }
    
    public void closeClaw() {
    	Robot.r03.StayInsideR03(VulcanClawState.CLOSED);
    }
    
    public void openClaw() {
    	Robot.r03.StayInsideR03(VulcanClawState.OPEN);
    }
    
    public void downClaw() {
    	Robot.r03.StayInsideR03(VulcanDeployState.DOWN);
    }
    
    public void upClaw() {
    	Robot.r03.StayInsideR03(VulcanDeployState.UP);
    }
    
    public VulcanClawState getClawState(){
    	switch(doubleSolenoid2.get()){
    	case kForward:
    		return VulcanClawState.OPEN;
    	case kReverse:
    		return VulcanClawState.CLOSED;
    	case kOff:
    		return VulcanClawState.NEUTRAL;
    	default:
    		return VulcanClawState.CLOSED;
    	}
    }
    public VulcanDeployState getDeployState(){
    	switch(doubleSolenoid1.get()){
    	case kForward:
    		return VulcanDeployState.DOWN;
    	case kReverse:
    		return VulcanDeployState.UP;
    	case kOff:
    		return VulcanDeployState.NEUTRAL;
    	default:
    		return VulcanDeployState.UP;
    	}
    }
    public DoubleSolenoid.Value setClaw(VulcanClawState desiredState){
    	switch(desiredState){
    	case OPEN:
    		return DoubleSolenoid.Value.kForward;
    	case CLOSED:
    		return DoubleSolenoid.Value.kReverse;
    	case NEUTRAL:
    		return DoubleSolenoid.Value.kOff;
    	default:
    		return DoubleSolenoid.Value.kOff;
    	}
    }
    public DoubleSolenoid.Value setClaw(VulcanDeployState desiredState){
    	switch(desiredState){
    	case UP:
    		return DoubleSolenoid.Value.kReverse;
    	case DOWN:
    		return DoubleSolenoid.Value.kForward;
    	case NEUTRAL:
    		return DoubleSolenoid.Value.kOff;
    	default:
    		return DoubleSolenoid.Value.kReverse;
    	}
    }
    // TODO: Do setclaw void with arg of DoubleSolenoid.Value
    // TODO: do lookup based on DoubleSolenoid.Value or Vulcan*State
    public void forceSetClaw(DoubleSolenoid.Value desiredValue){
    	doubleSolenoid2.set(desiredValue);
    }
    public void forceSetDeployClaw(DoubleSolenoid.Value desiredValue){
    	doubleSolenoid1.set(desiredValue);
    }
    
    public void gearRollerSetSpeed(double gearRollerSpeed){
    	RobotMap.gearRoller.set(gearRollerSpeed);
    }
    
    public void gearPushOut(){
    	RobotMap.gearPusher.set(DoubleSolenoid.Value.kReverse);
    }

    public void gearPushIn(){
    	RobotMap.gearPusher.set(DoubleSolenoid.Value.kForward);
    }
    
    public void gearPushDisable(){
    	RobotMap.gearPusher.set(DoubleSolenoid.Value.kOff);
    }
    
    
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

