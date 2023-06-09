// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// 2023 WORM-E Robot Code

  // Notes for robot movement
  // x = left and right (not used)
  // y = forward and backward
  // z = twist (used for turning)

  // Notes for Logitech Gamepad F310 Buttons
  // A = 1, B = 2, X = 3, Y = 4, Left Bumper = 5, Right Bumper = 6, Back = 7, Start = 8, 
  // Left Joystick Button = 9, Right Joystick Button= 10
  // Joysticks have X and Y axes, Triggers are axes

// External Imports
package frc.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoSink;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
//import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.motorcontrol.PWMTalonSRX;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
//import edu.wpi.first.wpilibj.util.Color;
//import com.revrobotics.ColorSensorV3;
//import edu.wpi.first.wpilibj.I2C;


/* 

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
*/


public class Robot extends TimedRobot {

  // set drive motor variables    
  
  public final PWMTalonSRX leftMotor = new PWMTalonSRX(5);
  public final PWMTalonSRX rightMotor = new PWMTalonSRX(0);
  
  
  public final PWMTalonSRX loaderMotor = new PWMTalonSRX(3);
  public final PWMTalonSRX insertMotor = new PWMTalonSRX(2);
  public final PWMTalonSRX spinnerMotor = new PWMTalonSRX(11);
  public final PWMTalonSRX turn_table = new PWMTalonSRX(6);
  public final PWMTalonSRX spinner2 = new PWMTalonSRX(1); // Shooter motor
  public final PWMTalonSRX spinner3 = new PWMTalonSRX(4); // Shooter motor
  public final DifferentialDrive drive = new DifferentialDrive(leftMotor, rightMotor);

  public final Joystick controller = new Joystick(1);
  public final Joystick joystick = new Joystick(0);

// Test motor
//private static final int testDeviceID = 9;
//private CANSparkMax test_motor;
//private RelativeEncoder test_encoder;

  // establishes pid contants
  
  // set cameras
  VideoSink server;
  UsbCamera cam0 = CameraServer.startAutomaticCapture(0);
  UsbCamera cam1 = CameraServer.startAutomaticCapture(1);

 

  

  // Set telescoping limits
  
  /* 
  // Sets autos
  private static final String defaultAuto = "Default";  // Just drives out
  private static final String Auto1 = "Auto1";  // Drives onto charger station and stays on
  private static final String Auto2 = "Auto2";  // Places cube on middle and drives out
  private static final String Auto3 = "Auto3";  // Places cube on high and drives out
  private String autoSelected;
  private final SendableChooser<String> autochooser = new SendableChooser<>();
    
  */

  // set timer
  double startTime;
  

  @Override
  public void robotInit() { 
    rightMotor.setInverted(true);
    spinner3.setInverted(true);
    //MotorControllerGroup shooter = new MotorControllerGroup(spinner2,spinner3);
   
    /* 
    autochooser.setDefaultOption("Just Drive Out", defaultAuto);
    autochooser.addOption("Over Charge Station", Auto1);
    autochooser.addOption("Place Cube on Middle and Drive Out", Auto2);
    autochooser.addOption("Testing grabber", Auto3);
    SmartDashboard.putData("Auto choices", autochooser);
    /* 
    colorchooser.setDefaultOption("Red Alliance",red);
    colorchooser.addOption("Blue Alliance",blue);
    SmartDashboard.putData("Color Choices", colorchooser);
    */
    
  }



  @Override
  public void teleopPeriodic() {

    
    

    // establish variables
    boolean A = controller.getRawButton(1);
    boolean B = controller.getRawButton(2);
    boolean X = controller.getRawButton(3);
    boolean Y = controller.getRawButton(4);
    //boolean LB = controller.getRawButton(5);
    //boolean RB = controller.getRawButton(6); // change to getRawButtonPressed
    
    /* 
    SmartDashboard.putNumber("Top Pivot Position", top_pivPosition);
    SmartDashboard.putNumber("Bottom Pivot Position", bot_pivPosition);
    SmartDashboard.putBoolean("Retracted", retractLimit.get());
    SmartDashboard.putBoolean("Extended", extendLimit.get());
    SmartDashboard.putString("Game Piece Mode",cMethods.detectGamePiece(m_joystick));
    SmartDashboard.putNumber("Pitch", ahrs.getPitch());
    */
      
   // Robot drive 
      // Use joystick for driving
    if(joystick.getRawButton(2)){
      drive.arcadeDrive(-joystick.getY()*0.4, -joystick.getZ()*0.4);
    }
    else{
      drive.arcadeDrive(-joystick.getY(), -joystick.getZ()*.65);
    }
    

    // Cameras
      // When the trigger on the joystick is held, display will change from drive camera to arm camera
    if (joystick.getRawButtonPressed(1)) {
      System.out.println("Setting camera 0");
      server.setSource(cam0);
    }
    else if (joystick.getRawButtonReleased(1)) {
      System.out.println("Setting camera 1");
      server.setSource(cam1);
    }

    

    if(A){
      spinner2.set(0.35);
      spinner3.set(0.35);
      loaderMotor.set(0.85);
      turn_table.set(1);
    }
    else if(B){
      spinner2.set(0.55);
      spinner3.set(0.55);
      loaderMotor.set(0.85);
      turn_table.set(1);
    }
    else if(Y){
      spinner2.set(0.55);
      spinner3.set(0.55);
      loaderMotor.set(0.85);
      turn_table.set(1);
    }
    else if(X){
      spinner2.set(1);
      spinner3.set(1);
      loaderMotor.set(1);
      turn_table.set(1);
    }
    else if(controller.getRawAxis(2)>0.5&&A==false&&B==false){
      loaderMotor.set(0.65);
    }
    else if(controller.getRawAxis(3)>0.5&&A==false&&B==false){
      loaderMotor.set(-0.65);
    }
    else if(controller.getRawButton(8)){
      spinner2.set(-0.45);
      spinner3.set(-0.45);
      loaderMotor.set(-0.65);
      turn_table.set(-1);
    }
    else if(controller.getRawButton(7)){
      spinner2.set(0.45);
      spinner3.set(0.45);
    }
    else{
      spinner2.set(0);
      spinner3.set(0);
      loaderMotor.set(0);
      turn_table.set(0);
    }
    
    
  
    
  } 
  // Autonomous

  @Override
  public void autonomousInit() {
    //autoSelected = autochooser.getSelected();
    //System.out.println("Auto Selected: "+ autoSelected);
    startTime = Timer.getFPGATimestamp();


  }


  @Override
  public void autonomousPeriodic() {
    
    

  } 
  
}
