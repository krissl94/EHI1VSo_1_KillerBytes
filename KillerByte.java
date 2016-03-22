package EHI1VSo_1_KillerBytes;

import robocode.MessageEvent;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;
import robocode.TurnCompleteCondition;
import robocode.util.Utils;
import sun.plugin2.message.Message;

import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

import static robocode.util.Utils.normalAbsoluteAngle;
import static robocode.util.Utils.normalRelativeAngleDegrees;

/**
 * Created by kris on 10-3-16.
 */
public class KillerByte extends TeamRobot implements Serializable {
    public String name;
    public AllyStatistics allyStats = null;
    public EnemyStatistics enemyStats;
    Boolean isLeader = false;

    public void smartShooting(){

    }

    /**
     * Move randomly across the field when there is no target or specific instruction
     */
    public void goCrazy(){
        Random rand = new Random();
        setTurnRadarRight(360);

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
        setTurnRadarRight(360);

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
        //TODO: MoveTo target, calculate which coordinates
        //TODO:                      If health is low, keep distance
        //TODO:                      If health is high, go in for a ram
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
                    setFire(targetTank, 1.5);
                } else if (targetTank.getDistance() > 100) {
                    setFire(targetTank, 2);
                }
                else {
                    setFire(targetTank, 3);
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

        // Follow enemy robot & shoot
        //turnRightRadians(e.getBearingRadians());
        //setAhead(enemyDistance);
        //fire(2);
    }

    public void setFire(ScannedRobotEvent target, double power){
        //TODO: Check if an ally is in the way.
        //TODO: If an ally is in the way and his energy is low, tell him to move
        if(!target.getName().startsWith("EHI1VSo_1_KillerBytes")) {
            setFire(power);
        }
    }

    public void moveTo(double x, double y){
        //TODO: X and Y are coordinates the robot should drive to
        //TODO: Calculate which way to drive
        double myX = getX();
        System.out.println("My X = " + myX);
        double myY = getY();
        System.out.println("My Y = " + myY);

        Double target = getAngle(x, y);
        System.out.println("Angle = " + target);
        System.out.println("My heading = " + getHeading());

        //moveTo(distanceToEnemy)
        double distanceToEnemyX = Math.abs(myX -x);
        double distanceToEnemyY = Math.abs(myY -y);
        double distanceToEnemy = Math.sqrt((Math.pow(distanceToEnemyX, 2) + Math.pow(distanceToEnemyY, 2)));

        if ((Math.abs(getHeading()) + Math.abs(target) < 180) && target < 0) {
            setTurnLeft(Math.abs(getHeading()) + Math.abs(target));
            System.out.println("1. Turns Left");
            setAhead(distanceToEnemy);
        } else if ((Math.abs(getHeading()) + Math.abs(target) < 180) && target > 0) {
            setTurnRight(Math.abs(getHeading()) + Math.abs(target));
            System.out.println("2. Turns Right");
            setAhead(distanceToEnemy);
        } else if ((Math.abs(getHeading()) + Math.abs(target) > 180 && target > 0)) {
            setTurnLeft((180 - Math.abs(getHeading()) + (180 - Math.abs(target))));
            System.out.println("3. Turns Left");
            setAhead(distanceToEnemy);
        } else if (Math.abs(getHeading()) + Math.abs(target) > 180 && target < 0) {
            setTurnRight((180 - Math.abs(getHeading()) + (180 - Math.abs(target))));
            System.out.println("4. Turns Left");
            setAhead(distanceToEnemy);
        } else {
            System.out.println("Something went wrong");
        }


        /* Message:
            1. De droid krijgt informatie over de X en Y waar deze naartoe moet.
            2. De droid verwerkt deze informatie en rijdt op dat doel af.
            3. De droid krijgt nieuwe informatie waar precies hetzelfde in staat als in de oude informatie
            4. De droid verwerkt deze informatie en rijdt zo zijn doel mis.

            Het updaten werkt nog niet goed? Of zie ik 't verkeerd...
        */



    }

    /**
     *
     * @param lat2
     * @param lon2
     * @return the angle between 2 points. Result is -180 to +180. -160 is a left turn, +160 is a right turn
     */
    private double getAngle(double lat2, double lon2){
        double lon1 = getY();
        double lat1 = getX();

        return normalRelativeAngleDegrees(Math.toDegrees(Math.atan2(lon2-lon1, lat2-lat1)));
    }

    /**
     * Not implemented yet, but might be a nice one
     * @param distance
     * @param bearing
     */
    public void moveWithBackAsFront(double distance, double bearing) {
        double angle = Utils.normalRelativeAngle(bearing - getHeadingRadians());
        double turnAngle = Math.atan(Math.tan(angle));
        setTurnRightRadians(turnAngle);
        int direction = angle == turnAngle ? 1 : -1;
        setAhead(distance * direction);
//        turnRightRadians(e.getBearingRadians());
//        setAhead(enemyDistance);
//        fire(2);

    }

    public void broadcastStats(Serializable stats){
        //TODO: Broadcast data
        try{
            broadcastMessage(stats);
        } catch(IOException IOE) {
            //TODO: Find out which robot isn't responding
            System.out.println(IOE.getCause());

        }
    }
    public void reportTo(String leader){
        try{
            sendMessage(leader, this);
        } catch(IOException IOE){

        }
    }
    public void messageReceived(MessageEvent e){
        if(!isLeader){
            robotProcessData(e);
        }
        else {
            leaderProcessData(e);
        }

    }

    /**
     * Custom sendMessage that handles try catch itself, keeping code clean
     * @param name
     * @param msg
     */
    public void sendMsg(String name, Serializable msg){
        try{
            sendMessage(name, msg);
        }
        catch (IOException IOE){
            //Todo: leader isn't receiving me D:
        }
    }

    /**
     * Robots process data
     * @param e
     * e can be allyStats, enemyStats (optionally: [or a request to register to the Leader]).
     */
    private void robotProcessData(MessageEvent e){
        //Robots and droids that aren't leaders dont do much with received messages, just register it to their own memory
        if(e.getMessage() instanceof AllyStatistics){
            this.allyStats = (AllyStatistics)e.getMessage();
            System.out.println("Is an allystats thing");
            if (!((AllyStatistics) e.getMessage()).getAllies().containsKey(this.getName())) {
                //This is only going to happen once. It's the leader requesting me to register.
                System.out.println("I'm not registered yet");
                sendMsg(((AllyStatistics) e.getMessage()).getLeader(), this);
            }
        } else if(e.getMessage() instanceof EnemyStatistics){
            System.out.println("Leader sent me new info! D:");
            //Lots of shit is gonna go down.
            //Recalculate route
            //Check if there's a new target in town
            //Perform some general checks.
            this.enemyStats = (EnemyStatistics) e.getMessage();
        }
    }

    /**
     * Leader processes the data of a message
     *
     * @param e
     * e can either be a friendly bot, or an enemy
     */
    private void leaderProcessData(MessageEvent e){
        //Leader receives the following messages:
        //  - Ally stats
        //  - Enemy stats
        System.out.println("I received a message from "+ e.getSender());
        if(e.getMessage() instanceof KillerByte){
            System.out.println("Is ally data!");
            if (!(allyStats.getAllies().containsKey(e.getSender()))) {
                System.out.println("He's not registered yet!");
                allyStats.addAlly((KillerByte) e.getMessage());
            } else {
                System.out.println("He's registered already! Updating..");
                allyStats.updateAlly((KillerByte) e.getMessage());
            }
            //Allies were updated, so i should broadcast the new object
            broadcastStats(allyStats);
            } else if (e.getMessage() instanceof ScannedRobotEvent) {
            System.out.println("Is enemy data!");
            if (!(enemyStats).getEnemies().containsKey(((EnemyBot) e.getMessage()).getName())) {
                System.out.println("This enemy's not registered yet!");
                enemyStats.addEnemy((EnemyBot) e.getMessage());
            } else {
                System.out.println("This enemy is registered, but i need to update him");
                enemyStats.updateEnemy((EnemyBot) e.getMessage());
            }
            //Enemies were updated, so i should broadcast the new object
            broadcastStats(enemyStats);
        }
    }
}
