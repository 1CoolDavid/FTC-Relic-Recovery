package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;


@Autonomous(name="Concept: Red Left Lower", group ="Concept")
public class RLLAuton extends OpMode {
    public static final String TAG = "Vuforia VuMark Sample";
    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer vuforia;
    int cameraMonitorViewId;
    VuforiaTrackables relicTrackables;
    VuforiaTrackable relicTemplate;
    DcMotor leftMotor;
    DcMotor rightMotor;
    DcMotor rackMotor;
    Servo bleftClamp;
    Servo brightClamp;
    Servo leftClamp;
    Servo rightClamp;


    public void init() {
        leftMotor = hardwareMap.dcMotor.get("leftMotor"); //inits left motor
        rightMotor = hardwareMap.dcMotor.get("rightMotor"); //inits right motor
        leftClamp = hardwareMap.servo.get("leftClamp"); //inits left clamp
        rightClamp = hardwareMap.servo.get("rightClamp"); //inits right clamp
        bleftClamp = hardwareMap.servo.get("bleftClamp"); //inits bottom left clamp
        brightClamp = hardwareMap.servo.get("brightClamp");//inits bottom right clamp
        leftClamp.setPosition(.23); //starting position
        rightClamp.setPosition(.6); //starting position
        bleftClamp.setPosition(.07); //starting position
        brightClamp.setPosition(.6); //starting position
        step = 0;
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AYTTAAb/////AAAAGYoQhJqtI090mf6GugkQvCZwnRgOb5UDjspCx7LbXJc3M6nfl3ZKajq/trYh5oIgnE725ZfBieYLW8OvEbkn0ujwxYsZQKGotKA4HFf/69j3bTxFtvVbnonIHBdjwdRK2EFMucuBsqJPO0RIxGJRmpkd8Hv9UNJFye5CrFkQBc4Wbt3l73SGkxxCOKwyBh8wrJWaUB8PLRjiaBEArmaZJKV8IfGLSgPSx7BqoGDy3D0SlVeVhMSFe71PlONsDlnUV42Nx4Z/usM0PqIyKVKqMJxnX8DNuGU1oTRhLA9/vcxLhkYw2QkT5e1PBZbshaiyJdB2c/QpEJS7HTdXvJ/OLZNCJA+oYYY1hcwAyO4tGrtz";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); //for debug
        telemetry.addData(">", "Press Play to start");
        telemetry.update();
    }
    public void init_loop(){
        super.init_loop();
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
    int step = 0;
    final double RMC = 1048;
    final double LMC = -1027;
    final double TR = 18;
    final double TL = -17;
    final double TR90 = -1828;
    final double TL90 = -1365;
    @Override
    public void loop() {
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        relicTrackables.activate();
        if (vuMark == RelicRecoveryVuMark.UNKNOWN) {
            telemetry.addData("VuMark", "not visible");
            telemetry.update();
        } else if (vuMark == RelicRecoveryVuMark.CENTER) {
            vuMark = RelicRecoveryVuMark.CENTER;
            telemetry.addData("vuMark", "vuMark");
            telemetry.update();
            if (step == 0){
                moveForward(0);
                step++;
            }
            if (step ==1){
                pause(1000);
                step++;
            }
            if (step ==2){
                turnR90();
                step++;
            }
            if (step ==3){
                pause(1000);
                step++;
            }
            if (step ==4){
                moveForward(0);
                step++;
            }
            if (step ==5){
                pause(1000);
                step++;
            }
            if (step ==6){
                turnR90();
                step++;
            }
            if (step ==7){
                pause(1000);
                step++;
            }
            if (step ==8){
                moveForward(0);
                step++;
            }
            if (step ==9){
                stopMotors();
            }

        } else if (vuMark == RelicRecoveryVuMark.LEFT) {
            vuMark = RelicRecoveryVuMark.LEFT;
            telemetry.addData("vuMark", "vuMark");
            telemetry.update();
            if (step == 0){
                moveForward(0);
                step++;
            }
            if (step ==1){
                pause(1000);
                step++;
            }
            if (step ==2){
                turnR90();
                step++;
            }
            if (step ==3){
                pause(1000);
                step++;
            }
            if (step ==4){
                moveForward(0);
                step++;
            }
            if (step ==5){
                pause(1000);
                step++;
            }
            if (step ==6){
                turnR90();
                step++;
            }
            if (step ==7){
                pause(1000);
                step++;
            }
            if (step ==8){
                moveForward(0);
                step++;
            }
            if (step ==9){
                stopMotors();
            }
        } else {
            vuMark = RelicRecoveryVuMark.RIGHT;
            telemetry.addData("vuMark", "vuMark");
            telemetry.update();
            if (step == 0){
                moveForward(0);
                step++;
            }
            if (step ==1){
                pause(1000);
                step++;
            }
            if (step ==2){
                turnR90();
                step++;
            }
            if (step ==3){
                pause(1000);
                step++;
            }
            if (step ==4){
                moveForward(0);
                step++;
            }
            if (step ==5){
                pause(1000);
                step++;
            }
            if (step ==6){
                turnR90();
                step++;
            }
            if (step ==7){
                pause(1000);
                step++;
            }
            if (step ==8){
                moveForward(0);
                step++;
            }
            if (step ==9){
                stopMotors();
            }
        }
    }
    public void moveForward(double meters){
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);//runs without measuring distance
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION); //runs without measuring distance
        double lp = leftMotor.getCurrentPosition();
        double rp = rightMotor.getCurrentPosition();
        lp+= LMC * meters;
        rp+= RMC * meters;
        leftMotor.setTargetPosition((int)lp);
        rightMotor.setTargetPosition((int)rp);
        leftMotor.setPower(-.3);
        rightMotor.setPower(.3);
        telemetry.addData("moving forward", "lp"+"rp");
    }
    public void moveBack(double meters){
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        double lp = leftMotor.getCurrentPosition();
        double rp = rightMotor.getCurrentPosition();
        lp += LMC * meters;
        rp += RMC * meters;
        leftMotor.setTargetPosition((int)lp);
        rightMotor.setTargetPosition((int)rp);
        leftMotor.setPower(.3);
        rightMotor.setPower(-.3);
        telemetry.addData("moving backward", "lp" + "rp");
    }
    public void turnLeft(double degrees){
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        double lp = leftMotor.getCurrentPosition();
        double rp = rightMotor.getCurrentPosition();
        lp+= TL*degrees;
        rp+=TR*degrees;
        leftMotor.setTargetPosition((int)lp);
        rightMotor.setTargetPosition((int)rp);
        leftMotor.setPower(.3);
        rightMotor.setPower(.3);
        telemetry.addData("turn left","rp"+"lp");
    }
    public void turnRight(double degrees){
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        double lp = leftMotor.getCurrentPosition();
        double rp = rightMotor.getCurrentPosition();
        lp+= TL*degrees;
        rp+=TR*degrees;
        leftMotor.setTargetPosition((int)lp);
        rightMotor.setTargetPosition((int)rp);
        leftMotor.setPower(-.3);
        rightMotor.setPower(-.3);
        telemetry.addData("turn right","rp"+"lp");
    }
    public void openClamp(){
        int a = 1;
        leftClamp.setPosition(.01);
        rightClamp.setPosition(.8);
        bleftClamp.setPosition(.27);
        brightClamp.setPosition(.39);
        telemetry.addData("open clamp","a");
    }
    public void closeClamp(){
        int b = 0;
        leftClamp.setPosition(.23);
        rightClamp.setPosition(.6);
        bleftClamp.setPosition(.07);
        brightClamp.setPosition(.6);
        telemetry.addData("close clamp", "b");
    }
    public void pause(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
    }
    public void turnR90(){
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);//runs without measuring distance
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION); //runs without measuring distance
        double lp = leftMotor.getCurrentPosition();
        double rp = rightMotor.getCurrentPosition();
        lp+= TL90;
        rp+= TR90;
        leftMotor.setTargetPosition((int)lp);
        rightMotor.setTargetPosition((int)rp);
        leftMotor.setPower(-.3);
        rightMotor.setPower(-.3);
        telemetry.addData("moving forward", "lp"+"rp");
    }
    public void turnl90(){
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);//runs without measuring distance
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION); //runs without measuring distance
        double lp = leftMotor.getCurrentPosition();
        double rp = rightMotor.getCurrentPosition();
        lp+= TL90;
        rp+= TR90;
        leftMotor.setTargetPosition((int)lp);
        rightMotor.setTargetPosition((int)rp);
        leftMotor.setPower(.3);
        rightMotor.setPower(.3);
        telemetry.addData("moving forward", "lp"+"rp");
    }
    public void stopMotors(){
        leftMotor.setPower(0);
        rightMotor.setPower(0);
    }

    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }
}

