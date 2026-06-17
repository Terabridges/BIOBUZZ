package org.firstinspires.ftc.teamcode.autos;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.config.pedroPathing.Constants;

@Autonomous(name = "Simple Pedro Auto", group = "Examples")
public class SimplePedroAuto extends OpMode {
    private final double robotWidth = 14.65;
    private final double robotLength = 14.65;
    private final Pose startPose = new Pose(24 + robotWidth/2, robotLength, Math.toRadians(90));
    private final Pose shootPose = new Pose(60, 13, Math.toRadians(90));
    private final Pose parkPose = new Pose(40, 13, Math.toRadians(90));

    private Follower follower;
    private PathChain goToShoot;
    private PathChain goToPark;
    private int pathState;

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);

        buildPaths();
        pathState = 0;

        telemetry.addLine("Simple Pedro Auto initialized");
        telemetry.addData("Start", formatPose(startPose));
        telemetry.update();
    }

    @Override
    public void init_loop() {
        follower.update();
        telemetry.addData("Pose", formatPose(follower.getPose()));
        telemetry.update();
    }

    @Override
    public void start() {
        pathState = 1;
        follower.followPath(goToShoot);
    }

    @Override
    public void loop() {
        follower.update();
        runPathStateMachine();

        telemetry.addData("Path State", pathState);
        telemetry.addData("Busy", follower.isBusy());
        telemetry.addData("Pose", formatPose(follower.getPose()));
        telemetry.update();
    }

//    @Override
//    public void stop() {
//        follower.startTeleopDrive(true);
//        follower.setTeleOpDrive(0, 0, 0, true);
//    }

    private void buildPaths() {
        goToShoot = follower.pathBuilder()
                .addPath(new BezierLine(startPose, shootPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                .build();

        goToPark = follower.pathBuilder()
                .addPath(new BezierLine(shootPose, parkPose))
                .setLinearHeadingInterpolation(shootPose.getHeading(), parkPose.getHeading())
                .build();
    }

    private void runPathStateMachine() {
        if (follower.isBusy()) {
            return;
        }

        if (pathState == 1) {
            pathState = 2;
            follower.followPath(goToPark);
        } else if (pathState == 2) {
            pathState = 3;
        }
    }

    private String formatPose(Pose pose) {
        return String.format("(%.1f, %.1f, %.0f deg)",
                pose.getX(),
                pose.getY(),
                Math.toDegrees(pose.getHeading()));
    }
}
