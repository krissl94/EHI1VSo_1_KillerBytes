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
        if(!targetTank.getName().startsWith("EHI1VSo_1_KillerBytes")) {
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
        }
        else{
            if(targetTank.getDistance() < 60) {
                setBack(20);
                setTurnRight(20);
            }
            System.out.println("Ally " + targetTank.getName() + " is in the way!");
        }
        execute();
    }

    public void calculateCoordinates(ScannedRobotEvent e){
        double myX = getX();
        System.out.println("My X = " + myX);
        double myY = getY();
        System.out.println("My Y = " + myY);

        double enemyDistance = e.getDistance();
        System.out.println("Enemy distance = " + enemyDistance);
        double enemyBearing = e.getBearingRadians();
        System.out.println("Enemy bearing = " + enemyBearing);

        double absoluteBearing = getHeadingRadians() + e.getBearingRadians();
        double enemyX = myX + (enemyDistance * Math.sin(absoluteBearing));
        double enemyY = myY + (enemyDistance * Math.cos(absoluteBearing));

        System.out.println("Enemy " + e.getName() +" X = " + enemyX);
        System.out.println("Enemy " + e.getName() +"Y = " + enemyY);

    }

    public void fire(ScannedRobotEvent target, double power){
        //TODO: Check if an ally is in the way.
        //TODO: If an ally is in the way and his energy is low, tell him to move
        if(!target.getName().startsWith("EHI1VSo_1_KillerBytes")) {
            fire(power);
        }
    }
}
