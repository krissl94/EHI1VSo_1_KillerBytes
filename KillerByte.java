package EHI1VSo_1_KillerBytes;

import robocode.*;
import robocode.util.Utils;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

import static robocode.util.Utils.normalRelativeAngleDegrees;

/**
 * Created by kris on 10-3-16.
 * Extends TeamRobot. It's a superclass of all our robots
 * Implements Serializable. It can be sent as a message
 * This class contains all functionality and behavior for every bot.
 *
 */
public class KillerByte extends TeamRobot implements Serializable {
    public String name;
    public String role;
    public AllyStatistics allyStats = null;
    public EnemyStatistics enemyStats = null;
    public AdvancedEnemyBot enemeyAdv = new AdvancedEnemyBot();
    public float movedir = 1;
    public boolean isLeader = false;

    //region General
    /**
     * Author: Kris
     * Initializes standard variables. Called by every bot
     */
    public void init(){
        allyStats = new AllyStatistics("EHI1VSo_1_KillerBytes.Leader");
        allyStats.addAlly(new AllyBot(this));
        enemyStats = new EnemyStatistics();
    }
    /**
     * Author: Kris
     * @param target
     * @param power
     * Override with an extra parameter to check if we're firing at an ally
     */
    public void setFire(ScannedRobotEvent target, double power){
        if(!target.getName().startsWith("EHI1VSo_1_KillerBytes")) {
            setFire(power);
        }
    }

    /**
     * Author: Gerton? / Kris?
     * @param coords
     * Instructs a bot to shoo
     */
    public void shootAt(double[] coords){
        //Don't fire if anyone is in my line of fire
        //Calculate line of fire..
        //Gun position is known
        //
        if(!role .equals("droid")){
            //Set
            //my radar needs to lock on to the target.
            double radarPosition = normalRelativeAngleDegrees(getAngle(coords));
            setTurnRadarRight(radarPosition);

            double directionToTarget = getHeading() - getGunHeading();
            setTurnGunRight(directionToTarget);

            //Aim gun at target
            //Aim radar with gun
            //Check line of fire
            //
        }else {
        }
        setFire(3);
    }

    /**
     * Author: Remy?
     * Generates 3 randam numbers between 0.0 and 1, and uses this to create a new HSB (HUE, SATURATION, BRIGHTNESS) value to assign to a part of the bot.
     * Each part of the bots receives its own random value.
     */
    public void randomColor(){
        Random random = new Random();
        this.setColors(Color.getHSBColor(random.nextFloat(), random.nextFloat(), random.nextFloat()), Color.getHSBColor(random.nextFloat(), random.nextFloat(), random.nextFloat()), Color.getHSBColor(random.nextFloat(), random.nextFloat(), random.nextFloat()));
    }
    //endregion

    //region Communication
    /**
     * Author: Kris
     * @param stats
     * Override of the Broadcast function with a Try/Catch built in.
     */
    public void broadcastStats(Serializable stats){
        //TODO: Broadcast data
        try{
            broadcastMessage(stats);
        } catch(IOException IOE) {
            //TODO: Find out which robot isn't responding
            System.out.println(IOE.getCause());

        }
    }

    /**
     * Author: Kris
     * @param leader
     * Report to the leader. Sends an object of AllyBot that is created from this Killerbyte instance
     */
    public void reportTo(String leader){
        try{
            sendMessage(leader, new AllyBot(this));
        } catch(IOException IOE){
            //TODO: Some error handling may be nice
        }
    }

    /**
     * Author: Kris
     * @param e
     * Checks if the bot that received a message is a leader and coordinates what to do accordingly
     */
    public void messageReceived(MessageEvent e){
        if(!isLeader){
            robotProcessData(e);
        }
        else {
            leaderProcessData(e);
        }
    }

    /**
     * Author: Kris
     * Custom sendMessage that handles try catch itself, keeping code clean
     * @param name
     * @param msg
     */
    public void sendMsg(String name, Serializable msg){
        try{
            sendMessage(name, msg);
        }
        catch (IOException IOE){
            //TODO: Some error handling may be nice
        }
    }

    /**
     * Author: Kris
     * Robots process data
     * @param e
     * e can be allyStats, enemyStats (optionally: [or a request to register to the Leader]).
     */
    private void robotProcessData(MessageEvent e){
        if(e.getMessage() instanceof AllyStatistics){
            this.allyStats = (AllyStatistics)e.getMessage();
        } else if(e.getMessage() instanceof EnemyStatistics){
            this.enemyStats = (EnemyStatistics) e.getMessage();
        }
    }

    /**
     * Author: Kris
     * Leader processes the data of a message
     * @param e
     * e can either contain a friendly bot, or an enemy
     */
    private void leaderProcessData(MessageEvent e){
        if(e.getMessage() instanceof AllyBot){
            if (!(allyStats.getAllies().containsKey(e.getSender()))) {
                allyStats.addAlly((AllyBot) e.getMessage());
            } else {
                allyStats.updateAlly((AllyBot) e.getMessage());
            }
            broadcastStats(allyStats);
        } else if (e.getMessage() instanceof EnemyBot && !(((EnemyBot) e.getMessage()).getName().startsWith("EHI1VSo_1_"))) {
            EnemyBot enemyBot = (EnemyBot) e.getMessage();
            if (!(enemyStats.getEnemies().containsKey(enemyBot.getName()))) {
                enemyStats.addEnemy(enemyBot);
            } else {
                enemyStats.updateEnemy(enemyBot);
            }
            broadcastStats(enemyStats);
        }
    }

    //endregion

    //region Movement
    /**
     * Author: Nicky
     * @param coords
     * TODO: Nicky, please explain
     * Deprecated? Isn't MoveTo used instead?
     */
    public void goTo(double[] coords) {
        double x = coords[0];
        double y = coords[1];

	/* Calculate the turn required to get there */
        double angleToTarget = getAngle(coords);
        double targetAngle = Utils.normalRelativeAngle(angleToTarget - getHeadingRadians());

        //Stelling v pythagoras
        double distanceToEnemyX = Math.abs(getX() - x);
        double distanceToEnemyY = Math.abs(getY() - y);
        double distance = Math.sqrt((Math.pow(distanceToEnemyX, 2) + Math.pow(distanceToEnemyY, 2)));


        System.out.println(String.valueOf(distance));
        if(true){
            System.out.println("yes");
            System.out.println(String.valueOf(enemeyAdv.getBearing()));
            circleTarget();
            return;
        }

        System.out.println(targetAngle);
        double turnAngle = Math.atan(Math.tan(targetAngle));
        setTurnRightRadians(turnAngle);

        setAhead(distance * movedir);

	/* This is a simple method of performing set front as back */
    }

    /**
     * Author: Gerton
     * Creates a circling movement pattern around the current target.
     * It uses the relativeAngleDegrees to get the required turn degrees to make sure the circling target is correct
     * if the bot is reversing then change the circle movement into an oval to move towards the target
     * Because it's based on direction (And there is never a prefered direction) there is a 50/50 chance of moving towards, or away from the target
     * thus keeping a nice distance.
     * */
    private void circleTarget() {
        Double tankTurn;

        System.out.println("Too Close");
        // Circle around it.
        if(movedir == 1){
            System.out.println("Too Close");
            // Circle around it.
            tankTurn = Utils.normalRelativeAngleDegrees(enemeyAdv.getBearing() +80);
            System.out.println(tankTurn);
        }
        else{
            tankTurn = Utils.normalRelativeAngleDegrees(enemeyAdv.getBearing() +100);

        }

        setTurnRight(tankTurn);
        setAhead(50*movedir);
    }

    /**
     * Author: Gerton
     * The movedirection toggles between 1 and -1
     */
    private void reverseDirection(){
        movedir = movedir *-1;
    }

    /**
     * Author: Nicky
     * TODO: Nicky, please explain
     */
    public void smartShooting(){

        if (enemeyAdv.none()){
            return;
        }

        // calculate for bullet
        double firePower = Math.min(500 / enemeyAdv.getDistance(), 3);
        double bulletSpeed = 20 - firePower * 3;
        long time = (long)(enemeyAdv.getDistance() / bulletSpeed);

        //Calculate future X and Y of target
        double futureX = enemeyAdv.getFutureX(time);
        double futureY = enemeyAdv.getFutureY(time);

        double absDeg = 0;
        if(enemeyAdv.getVelocity() == 0){
            absDeg = absoluteBearing(getX(),getY(),enemeyAdv.getX(),enemeyAdv.getY());
        }
        else{
            absDeg = absoluteBearing(getX(), getY(), futureX, futureY);
        }

        setTurnGunRight(normalizeBearing(absDeg - getGunHeading()));

        if (getGunHeat() == 0 && Math.abs(getGunTurnRemaining()) < 10){
            setFire(firePower);
        }

    }

    /**
     * Author: Kris
     * If i have a target, attack him.
     * First, we're gonna go to his coordinates and then we're gonna shoot him
     */
    public void attack(){
        if(enemyStats != null && enemyStats.getTargetName() != null) {
            if(enemyStats.getEnemies().get(enemyStats.getTargetName()) != null){
                goTo(enemyStats.getEnemies().get(enemyStats.getTargetName()).getLastRecordedPosition());//This could be null, that means that the target has died but not updated yet, if that's the case, force update the
                shootAt(enemyStats.getEnemies().get(enemyStats.getTargetName()).getLastRecordedPosition());
            }
        }else{
            //Wait for new target?
        }


    }
    //endregion

    //region Calculations
    /**
     * Author: Nicky / Kris / Internet (SHould we source?)
     * @param coords
     * @return the angle between 2 points. Result is -180 to +180. -160 is a left turn, +160 is a right turn
     */
    private double getAngle(double[] coords){// x, double y){
        double x = coords[0] - getX();
        double y = coords[1] - getY();

        return Math.atan2(x, y);
    }

    /**
     * Author: Nicky
     * @param e
     * @return
     * TODO: Nicky, please explain
     */
    public double[] calculateCoordinates(ScannedRobotEvent e){
        double absoluteBearing = getHeadingRadians() + e.getBearingRadians();

        double enemyX = getX() + (e.getDistance() * Math.sin(absoluteBearing));
        double enemyY = getY() + (e.getDistance() * Math.cos(absoluteBearing));

        return new double[]{enemyX, enemyY};
    }

    /**
     * Author: Nicky
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     * TODO: Nicky, please explain
     */
    public double absoluteBearing(double x1, double y1, double x2, double y2){
        double xo = x2 - x1;
        double yo = y2 - y1;
        double hyp = Point2D.distance(x1, y1, x2, y2);
        double arcSin = Math.toDegrees(Math.asin(xo / hyp));
        double bearing = 0;

        if (xo > yo && yo > 0) {
            // lower left
            bearing = arcSin;
        } else if (xo < 0 && yo > 0) {
            // lower right
            bearing = 360 + arcSin;
        } else if (xo > 0 && yo < 0) {
            // upper left
            bearing = 180 - arcSin;
        } else if (xo < 0 && yo < 0) {
            // upper right
            bearing = 180 - arcSin;
        }

        return bearing;
    }

    /**
     * Author: Nicky
     * @param angle
     * @return
     * TODO: Nicky, please explain
     */
    public double normalizeBearing(double angle){

        while (angle > 180){
            angle -= 360;
        }
        while (angle < -180){
            angle += 360;
        }

        return angle;
    }

    //endregion

    //region Events
    /**
     * Author: Remy
     * TODO: Remy, please explain
     */
    @Override
    public void onWin(WinEvent event) {
        turnRight(200);
        turnLeft(200);
        ahead(0);
    }

    /**
     * Author: Kris
     * @param e
     * First, checks if this bot is the leader
     *  If not, checks if an ally died
     *      If an ally died, it might be the leader
     *          If the leader died, assign a new leader
     *  If isLeader, checks if ally died
     *      If so, updates allystats and broadcasts
     *      If not, updates enemystats and broadcasts
     */
    @Override
    public void onRobotDeath(RobotDeathEvent e){
        if(!isLeader){
            if(e.getName().startsWith("EHI1VSo_1_KillerBytes")){//Ally died
                if(e.getName().equals(allyStats.getLeader())){//Leader died
                    if(role.equals("robot")){//I'm a robot
                        if( allyStats.getOtherRobot(getName()) == null || this.getEnergy() > allyStats.getOtherRobot(getName()).getEnergy() ){//My energy is higher than the other Robot's energy. I will take over the leader role
                            isLeader = true;
                            allyStats.setLeader(name);
                            allyStats.allyDied(allyStats.getAlly(e.getName()));
                            broadcastStats(allyStats);
                        }
                    }
                }
            }
        }
        else{//I'm the leader and i obviously didn't die.
            if(e.getName().startsWith("EHI1VSo_1_KillerBytes")){//Ally died
                allyStats.allyDied(allyStats.getAllies().get(e.getName()));
                broadcastStats(allyStats);
            }
            else{  //Enemy died
                enemyStats.enemyDied(enemyStats.getEnemies().get(e.getName()));
                broadcastStats(enemyStats);
            }

        }
    }

    /**
     * Author: Gerton
     * @param event
     * When a robot hits a wall, reverse.s
     */
    @Override
    public void onHitWall(HitWallEvent event) {
        reverseDirection();
    }

    /**
     * Author: Gerton
     * @param event
     * When a robot hits another robot, reverse direction
     */
    @Override
    public void onHitRobot(HitRobotEvent event) {
        reverseDirection();
    }
    //endregion

    /**
     * Author: Kris
     * Function that lets the robot with a scanner chase the enemy
     * @param targetTank The tank that we've spotted
     * Deprecated. Implementation should be removed
     */
    @Deprecated
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


}
