package frc.robot.shooterClasses;

/*public class Shooter {

    public static void main(String[] args) {

        shooterHardware shooter = new shooterHardware();
        System.out.println("CURRENT ROBOT STATE:");
        shooter.setFrontMotorPower(20);
        shooter.setBackMotorPower(30);
        System.out.println(shooter.getVelocityFrontEncoder());
        System.out.println(shooter.getVelocityBackEncoder());
    }

}*/
public class Shooter {
    private IShooterHardware hardware;

    public Shooter(IShooterHardware hardware) {
        this.hardware = hardware;
    }

    public void runOpenLoopFront(double val) {
        hardware.setFrontMotorPower(val);
    }

    public void runOpenLoopBack(double val) {
        hardware.setBackMotorPower(val);
    }

}