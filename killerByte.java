package EHI1VSo_1_KillerBytes;

import robocode.ScannedRobotEvent;
import robocode.TeamRobot;
import robocode.TurnCompleteCondition;

import java.util.Random;

import static robocode.util.Utils.normalRelativeAngleDegrees;

/**
 * Created by kris on 10-3-16.
 */
public class killerByte extends TeamRobot {
    public void smartShooting(){

    }

    /**
     * Move randomly across the field when there is no target or specific instruction
     */
    public void goCrazy(){
        Random rand = new Random();
        setTurnRadarRight(3);

        if(rand.nextInt(2) == 0){
            //Go backward
            setBack(100);
            setTurnRadarRight(360);
            setTurnRight(40);
        }else{
            //Go forward
            setAhead(100);
            setTurnRadarRight(360);

            setTurnRight(50);
        }
        waitFor(new TurnCompleteCondition(this));
        setTurnRadarRight(3);

        execute();
    }

    /**
     * Function that instructs all bots to avoid attacks
     * We should be able to track every robot's energy, so we should also be able to check when a shot is fired.
     * Because everyone on the field, the entire team needs to dodge
     */
    public void dodgeAttack(){

    }

    /**
     * Function that keeps robots from crashing into the walls
     */
    public void wallSafe(){
        //Gerton had toch iets voor walls enzo?
    }

    /**
     * Function that lets the robot with a scanner chase the enemy
     * @param targetTank The tank that we've spotted
     */
    public void chase( ScannedRobotEvent targetTank){
        //My radar is pointed towards the enemy, i need my body to point in the same direction
        setTurnRight(targetTank.getBearing());

        //my radar needs to lock on to the target.
        double radarPosition = normalRelativeAngleDegrees(targetTank.getBearing() +  getHeading() - getRadarHeading());
        setTurnRadarRight(radarPosition);

        double directionToTarget = getHeading() - getGunHeading();
        setTurnGunRight(directionToTarget);

        setAhead(targetTank.getDistance() );
        if(targetTank.getDistance() < 140){
            if(targetTank.getDistance() > 120){
                fire(targetTank, 1.5);
            } else if (targetTank.getDistance() > 100) {
                fire(targetTank, 2);
            }
            else {
                fire(targetTank, 3);
            }
        }
        execute();
    }

    public void fire(ScannedRobotEvent target, double power){
        if(!target.getName().startsWith("EO1")){
        }
        fire(power);
    }
}
