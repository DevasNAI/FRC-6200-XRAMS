/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  //motors
    //private static final int Portmotor0 = 0; //Elevator Low Negative
    private static final int Portmotor1 = 1; //Intake Elevator
    private static final int Portmotor2 = 2; //Intake
    private static final int Portmotor3 = 3; //Elevator High
    private static final int Portmotor4 = 4; //Drive "Back Left"
    private static final int Portmotor5 = 5; //Drive "Back Right"
    //private static final int Portmotor6 = 6; //Spare
    private static final int Portmotor7 = 7; //Drive "Front Right"
    //private static final int Portmotor8 = 8; //Spare
    private static final int Portmotor9 = 9; //Drive "Front left"

    //Spares
      //private static final int Portmotor2 = 1;
      //private static final int Portmotor6 = 5;
      //private static final int Portmotor8 = 7;

    //Pneumatics
      //Solenoids
        private static final int SolenoidPort0 = 0;
        private static final int SolenoidPort1 = 1;
        private static final int SolenoidPort2 = 2;
        private static final int SolenoidPort3 = 3;
      //Compressor
        private static final int CompressorPort10 = 0;

      
  //Control ports
    private static final int ContPort0 = 0;
    private static final int ContPort1 = 2;


  //Xbox Controls  
    private XboxController Cont1 = new XboxController(ContPort0);
    private XboxController Cont2 = new XboxController(ContPort1);


  //Limit switch
    private static final int limitswitchport = 9;
 
  //Objects
        //private WPI_TalonSRX motor0;
      private WPI_TalonSRX motor1;
      private WPI_TalonSRX motor2;
      private WPI_TalonSRX motor3;
      private WPI_TalonSRX motor4;
      private WPI_TalonSRX motor5;
      //private WPI_TalonSRX motor6;
      private WPI_TalonSRX motor7;
        //private WPI_TalonSRX motor8;
      private WPI_TalonSRX motor9;

  //drive
      private MecanumDrive robotDrive;

    //compressor
      private Compressor compressor;
      private Solenoid sol1;
      private Solenoid sol2;
      private Solenoid sol3;
      private Solenoid sol4;
      
     
      private DigitalInput LowLimitSwitch;


  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  
  @Override
  public void robotInit() {
    compressor = new Compressor(CompressorPort10);

    LowLimitSwitch = new DigitalInput(limitswitchport);

    sol1 = new Solenoid(SolenoidPort0);
    sol2 = new Solenoid(SolenoidPort1);

    sol3 = new Solenoid(SolenoidPort2);
    sol4 = new Solenoid(SolenoidPort3);

    motor1 = new WPI_TalonSRX(Portmotor1);
    motor2 = new WPI_TalonSRX(Portmotor2);
    motor3 = new WPI_TalonSRX(Portmotor3);
    motor4 = new WPI_TalonSRX(Portmotor4);
    motor5 = new WPI_TalonSRX(Portmotor5);
    //motor6 = new WPI_TalonSRX(Portmotor6);
    motor7 = new WPI_TalonSRX(Portmotor7);
    motor9 = new WPI_TalonSRX(Portmotor9);

    robotDrive = new MecanumDrive(motor5, motor9, motor4, motor7);

    
      UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
      //Set the resolution
      camera.setResolution(240, 180);
      camera.setFPS(30);
      
      
    
  
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    compressor.setClosedLoopControl(true);
    
          
    
    
    //System.out.println(LowLimitSwitch.get());
    //Cont2
      if(Cont2.getTriggerAxis(Hand.kLeft) >= 0.4){
        motor2.set(ControlMode.PercentOutput, 0.65);
        motor1.set(ControlMode.PercentOutput, 0.55);
      } else if(Cont2.getTriggerAxis(Hand.kRight) >= 0.4) {
        motor2.set(ControlMode.PercentOutput, -0.8);
        motor1.set(ControlMode.PercentOutput, -0.9);
      } else {
        motor2.set(ControlMode.PercentOutput, 0.0);
        motor1.set(ControlMode.PercentOutput, 0.0);
      }

    if(Cont2.getBButton()){
      sol3.set(true);
      sol4.set(true);
    } else if(Cont2.getYButton()){
      sol4.set(false);
      sol3.set(true);
    }

    if(Cont2.getStartButton()) sol3.set(false);

    if(Cont1.getBumper(Hand.kLeft)){
      sol2.set(true);
      //sol1.set(true);
    } else if(Cont1.getBumper(Hand.kRight)){
      //sol1.set(false);
      sol2.set(false);
    }


   /* if(LowLimitSwitch.get()){
      motor3.set(ControlMode.PercentOutput, 0.0);
    }else if(Cont2.getXButton()){
      motor3.set(ControlMode.PercentOutput, 0.2);
    } else {
      motor3.set(ControlMode.PercentOutput, 0.0);
    }

    }*/
    if(Cont2.getAButton()){
      motor3.set(ControlMode.PercentOutput, -1.0);

    } else if(LowLimitSwitch.get()){
      motor3.set(ControlMode.PercentOutput, 0.0);
    }else if(Cont2.getXButton()){
      motor3.set(ControlMode.PercentOutput, 0.6);
    } else {
      motor3.set(ControlMode.PercentOutput, 0.0);
    }

    
    
  

    
    
    //Cont1
    Double x;
    x = Cont1.getTriggerAxis(Hand.kRight);
      if(x <= 0.6){
      x = 0.75;
      
    } else if(x >=0.6) {
      x = 1.0;
    }

    if(Cont2.getBumper(Hand.kLeft)){
      //sol2.set(true);
      sol1.set(true);
    } else if(Cont2.getBumper(Hand.kRight)){
      sol1.set(false);
      sol2.set(false);
    }

    robotDrive.driveCartesian(-x*Cont1.getX(Hand.kLeft), x*Cont1.getY(Hand.kLeft),
    x*Cont1.getX(Hand.kRight));
    
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    
  }
  
  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
