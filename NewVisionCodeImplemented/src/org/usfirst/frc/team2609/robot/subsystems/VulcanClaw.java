package org.usfirst.frc.team2609.robot.subsystems;

import org.usfirst.frc.team2609.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class VulcanClaw extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	DoubleSolenoid doubleSolenoid1 = RobotMap.vulcanDeploy;
	DoubleSolenoid doubleSolenoid2 = RobotMap.vulcanClaw;
    
    public void toggleDeployClaw() {
    	System.out.println("2.get"+ doubleSolenoid2.get());
    	System.out.println("1.get"+doubleSolenoid1.get());

    	
    	if (doubleSolenoid1.get()==DoubleSolenoid.Value.kReverse && doubleSolenoid2.get()==DoubleSolenoid.Value.kReverse) {
    			doubleSolenoid1.set(DoubleSolenoid.Value.kForward);
    			System.out.println("vulcanDeploy up "+ doubleSolenoid1.get());
    	}
    	else if (doubleSolenoid1.get() == DoubleSolenoid.Value.kForward && doubleSolenoid2.get()==DoubleSolenoid.Value.kReverse){
			doubleSolenoid1.set(DoubleSolenoid.Value.kReverse);
			System.out.println("vulcanDeploy down "+ doubleSolenoid1.get());
    	}
    	else if(doubleSolenoid1.get()==DoubleSolenoid.Value.kOff && doubleSolenoid2.get()==DoubleSolenoid.Value.kReverse){
			doubleSolenoid1.set(DoubleSolenoid.Value.kReverse);	
    	}
    	else {
    		//doubleSolenoid1.set(DoubleSolenoid.Value.kReverse);
    		System.out.println("Unknown state, do not move!");
    	}    	
    }
    public void toggleClaw() {
    	if (doubleSolenoid2.get() == DoubleSolenoid.Value.kReverse){
    			doubleSolenoid2.set(DoubleSolenoid.Value.kForward);
    			System.out.println("vulcanClaw open "+ doubleSolenoid2.get());
    	}
    	else if (doubleSolenoid2.get() == DoubleSolenoid.Value.kForward){
			doubleSolenoid2.set(DoubleSolenoid.Value.kReverse);
			System.out.println("vulcanClaw close "+ doubleSolenoid2.get());
    	}
    	else {
    		doubleSolenoid2.set(DoubleSolenoid.Value.kReverse);
    		System.out.println("vulcanClaw close "+ doubleSolenoid2.get());
    	}    	
    }
    
    public void closeClaw() {
    	doubleSolenoid2.set(DoubleSolenoid.Value.kReverse);
		System.out.println("vulcanClaw close "+ doubleSolenoid2.get());
    }
    
    public void openClaw() {
		doubleSolenoid2.set(DoubleSolenoid.Value.kForward);
		System.out.println("vulcanClaw open "+ doubleSolenoid2.get());
    }
    
    public void downClaw() {
    	doubleSolenoid1.set(DoubleSolenoid.Value.kForward);
		System.out.println("vulcanDeploy down "+ doubleSolenoid1.get());
    }
    
    public void upClaw() {
    	doubleSolenoid1.set(DoubleSolenoid.Value.kReverse);
		System.out.println("vulcanDeploy up "+ doubleSolenoid1.get());
    }
    
    public void scoreClaw() {
    	if (RobotMap.clawUpSensor.get() && RobotMap.clawCloseSensor.get()){
    		doubleSolenoid2.set(DoubleSolenoid.Value.kForward);
    		System.out.println("vulcanClaw open "+ doubleSolenoid2.get());
    	}
    }
    
    /*if (RobotMap.gearSensor.get() == false){
    	if (RobotMap.clawUpSensor.get() == true){
        	if (RobotMap.clawDownSensor.get() == false){
        	}
        }
        else if (RobotMap.clawUpSensor.get() == false){
        	if (RobotMap.clawDownSensor.get() == true){
        	}
        }
    }*/
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

