package org.usfirst.frc.team871.tools;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;

/*
 * VarMotor:  A variable motor type class
 * This class can be used to dynamically assign motor controller types to PWM and CAN bus channels.
 * The same VarMotor can be reassigned to any motor type at any address after being set.
 */

public class VarMotor {
	boolean invert;
	int address;
	
	MotorType curType;
	SpeedController motor;
	
	public enum MotorType {
		NULL(-1),
		CANJAGUAR(0),
		JAGUAR(1),
		CANTALON(2),
		TALON(3),
		TALONSRX(4),
		VICTOR(5),
		VICTORSP(6);
		
		int value;
		
		MotorType(int num) {
			value = num;
		}
		
		public int getValue() {
			return value;
		}
		
		public static MotorType getMotorType(int num) {
			switch (num) {
			case 0:
				return CANJAGUAR;

			case 1:
				return JAGUAR;

			case 2:
				return CANTALON;

			case 3:
				return TALON;

			case 4:
				return TALONSRX;

			case 5:
				return VICTOR;

			case 6:
				return VICTORSP;

			default:
				return NULL;
			}
		}
	}
	
	public VarMotor() {
		//Constructor; initializes everything to unusable values
		address = -1;
		motor = null;
		curType = MotorType.NULL;
	}
	
	public VarMotor(MotorType type, int channel, boolean inv) {
		//Constructor; initializes the motor to the specified motor
		switch (type) {
		case CANJAGUAR:
			motor = new CANJaguar(channel);
			break;

		case JAGUAR:
			motor = new Jaguar(channel);
			break;

		case CANTALON:
			motor = new CANTalon(channel);
			break;

		case TALON:
			motor = new Talon(channel);
			break;

		case TALONSRX:
			motor = new TalonSRX(channel);
			break;

		case VICTOR:
			motor = new Victor(channel);
			break;

		case VICTORSP:
			motor = new VictorSP(channel);
			break;

		default:
			motor = null;
			curType = MotorType.NULL;
			break;
		}
		
		if (motor != null) 
			motor.setInverted(inv);
		invert = inv;
	}
	
	@Override
	public String toString() {
		//Overridden toString; returns a string containing a description of the current motor
		return (motor == null) ? ("No or invalid motor set.") : (motor.getClass().getSimpleName() + " @ addr. " + address);
		
	}
	
	public void setSpeed(double speed) {
		//Sets the speed
		if (motor != null) 
			motor.set(speed);
	}
	
	public void changeMotor(MotorType type, int channel, boolean inv) {
		//Changes the motor to a new motor of the specified type with the specified properties
		switch (curType) {
		//Since SpeedController does not force the existence of a destructor, motor must be destructed based on its type
		case CANJAGUAR:
			((CANJaguar)motor).free();
			break;
			
		case JAGUAR:
			((Jaguar)motor).free();
			break;
			
		case CANTALON:
			((CANTalon)motor).delete();
			break;
			
		case TALON:
			((Talon)motor).free();
			break;
			
		case TALONSRX:
			((TalonSRX)motor).free();
			break;
			
		case VICTOR:
			((Victor)motor).free();
			break;
			
		case VICTORSP:
			((VictorSP)motor).free();
			break;
			
		default:
			motor = null;
			break;
		}
		
		address = channel;
		curType = type;
		motor = null;
		invert = inv;
		
		
		switch (type) {
		case CANJAGUAR:
			motor = new CANJaguar(channel);
			break;

		case JAGUAR:
			motor = new Jaguar(channel);
			break;

		case CANTALON:
			motor = new CANTalon(channel);
			break;

		case TALON:
			motor = new Talon(channel);
			break;

		case TALONSRX:
			motor = new TalonSRX(channel);
			break;

		case VICTOR:
			motor = new Victor(channel);
			break;

		case VICTORSP:
			motor = new VictorSP(channel);
			break;

		default:
			motor = null;
			break;
		}
		
		if (motor != null)
			motor.setInverted(inv);
		
}
	
	public MotorType getType() {
		//Returns the current type of the motor
		return curType;
	}
	
	public boolean getInvert() {
		return invert;
	}
}
