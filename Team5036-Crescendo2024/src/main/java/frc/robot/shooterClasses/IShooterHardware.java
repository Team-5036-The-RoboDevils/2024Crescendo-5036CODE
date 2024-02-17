/*Lets have 2 different classes
Start with ShooterHardware
Similar to drivetrain hardware
1 method that sets power of one motor
1 method that sets power of second motor
1 method that gets encoder velocity from one motor
1 method that gets encoder velocity from the other motor
Then we can have a Shooter class that takes a ShooterHardware object and perform 
any necessary logic like going a certain speed */

//U have 4 methods
/*1) set power to shooter motor closest to front
2) set power to shooter motor farther from front
3) get velocity of encoder from shooter motor closest to front
4) get velocity of encoser from shooter motor closest to back
And for now, dw about wpilib
First 2 methods just print something
2nd 2 methods return 0*/

//package frc.robot.hardware;

public interface IShooterHardware {
    public void setFrontMotorPower(double val);

    public void setBackMotorPower(double val);

    // heyyyyyyy :) the smiley face looks so derpy lmaoo
    // alright well, did he say which to do first?
    // hello?
    // check shooterhardware i tried smth :sob:
    // he said to do this one first and create a concrete class to run it see above
    // alright sounds good
    // i wrote this for the interface; and classes are below; try running it
    // I can see you runnnig it

    // hmmm ok so can i try smth?
    // ofc lmao go ahead
    // I put it in shooterHardware
    // i see i see
    // ill control z after dw
    // dw
    // wait sherry, i think there might be a problem with the project itself, are
    // these classes seperate from the entire code?
    // as in do they have a diff folder + file locations
    // Yes I made a separate folder for this; its called shooter lemme show ya

    // alright, my only worry is, when you look at this code from your side, is the
    // folder you made inside the "Robot/Java/frc" or is it outside that entire code
    public double getVelocityFrontEncoder();

    public double getVelocityBackEncoder();
}
// you can undo my work heh?
// i wurote tat to give context for what you wrote below lol ohhhh
// do we start working on the rest? or wait for tahmid
// start working on rest; i'll send to tahmid tho in meantime
// ok hehe
// woah hehe alright lemme OH WAIT I CAN??? HAHAH NO ok ok ill stop annoying ya
// :sob:
// public class shooterHardware implements IShooterHardware {

// @Override
// public void setFrontMotorPower(double val) {
// System.out.println(val);
// }

// @Override
// public void setBackMotorPower(double val) {
// System.out.println(val);
// }

// @Override
// public double getVelocityFrontEncoder() {
// return(0.0);
// }

// @Override
// public double getVelocityBackEncoder() {
// return(0.0);
// }
// }
