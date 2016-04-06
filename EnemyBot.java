package EHI1VSo_1_KillerBytes;

import robocode.ScannedRobotEvent;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kris on 9-3-16.
 * Updated by Nicky on 30-3-16.
 * EnemyBot contains some information on the enemy.
 * Implements Serializable, because it can be sent as a message
 * TODO: Nicky, please explain your edits
 */
public class EnemyBot implements Serializable{
    //region Kris' variables
    private double firstRecordedHealth;
    private double lastRecordedHealth;
    private String role;
    private ArrayList<double[]> recordedPositions;
    //endregion

    //region Nicky's variables
    private double bearing;
    private double distance;
    private double energy;
    private double heading;
    private String name;
    private double velocity;
    //endregion

    //region Constructors
    public EnemyBot(ScannedRobotEvent TargetTank, double[] coordinates){//String name, int firstRecordedHealth, int lastRecordedHealth, String role, int x, int y) {
        this.name = TargetTank.getName();
        this.firstRecordedHealth = TargetTank.getEnergy();
        this.lastRecordedHealth = TargetTank.getEnergy();
        this.role = determineRole(TargetTank.getEnergy());
        this.recordedPositions = new ArrayList<>();
        this.recordedPositions.add(coordinates);
    }

    public EnemyBot(){
        reset();
    }
    //endregion

    //region Getters and Setters
    public String getName() {
        return name;
    }
    public double getFirstRecordedHealth() {
        return firstRecordedHealth;
    }
    public double getLastRecordedHealth() {
        return lastRecordedHealth;
    }
    public String getRole() {
        return role;
    }
    public ArrayList<double[]> getRecordedPositions() {
        return recordedPositions;
    }
    public double getBearing() {
        return bearing;
    }

    public double getDistance() {
        return distance;
    }

    public double getEnergy() {
        return energy;
    }

    public double getHeading() {
        return heading;
    }

    public double getVelocity() {
        return velocity;
    }


    public void setName(String name) {
        this.name = name;
    }
    public void setLastRecordedHealth(double lastRecordedHealth) {
        this.lastRecordedHealth = lastRecordedHealth;
    }
    public void setRole() {
        this.role = role;
    }
    public void setBearing(double bearing) {
        this.bearing = bearing;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }
    public void setEnergy(double energy) {
        this.energy = energy;
    }
    public void setHeading(double heading) {
        this.heading = heading;
    }
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }
    //endregion

    /**
     * Author: Kris
     * @param xy
     * A location is added to the recorded positions of this enemyBot
     */
    public void addPosition(double[] xy){
        recordedPositions.add(xy);
    }

    /**
     * Author: Kris
     * @param energy
     * @return a String of the role of this scanned robot
     */
    private String determineRole(double energy){
        if(energy > 120){
            return "leader";
        }
        else if(energy > 100){
            return "droid";
        }
        else{
            return "robot";
        }
    }

    /**
     * Author: Kris
     * @param targetTank
     * @param x
     * @param y
     * @param headingRadians
     * @return
     * TODO: Deprecated?
     */
    private double[] determinePosition(ScannedRobotEvent targetTank, double x, double y, double headingRadians){

        double myX = x;
        System.out.println("My X = " + myX);
        double myY = y;
        System.out.println("My Y = " + myY);

        double enemyDistance = targetTank.getDistance();
        System.out.println("Enemy distance = " + enemyDistance);
        double enemyBearing = targetTank.getBearingRadians();
        System.out.println("Enemy bearing = " + enemyBearing);

        double absoluteBearing = headingRadians + targetTank.getBearingRadians();
        double enemyX = myX + (enemyDistance * Math.sin(absoluteBearing));
        double enemyY = myY + (enemyDistance * Math.cos(absoluteBearing));

        System.out.println("Enemy " + targetTank.getName() +" X = " + enemyX + ", Y = " + enemyY);


        return new double[]{enemyX,enemyY};
    }

    /**
     * Author: Kris
     * @return the last X and Y coordinations of this bot
     */
    public double[] getLastRecordedPosition(){
        return this.recordedPositions.get(this.recordedPositions.size() - 1);
    }

    /**
     * Author: Nicky
     * @param e
     * TODO: Nicky, Please explain.
     */
    public void update(ScannedRobotEvent e){
        bearing = e.getBearing();
        distance = e.getDistance();
        energy = e.getEnergy();
        heading = e.getHeading();
        name = e.getName();
        velocity = e.getVelocity();
    }

    /**
     * Author: Nicky
     * TODO: Nicky, please explain
     */
    public void reset(){
        setName("");
        setBearing(0.0);
        setDistance(0.0);
        setEnergy(0.0);
        setHeading(0.0);
        setVelocity(0.0);
    }

    /**
     * Author: Nicky
     * @return
     * TODO: Nicky, please explain
     */
    public boolean none(){
        if (name.equals("")){
            return true;
        } else{
            return false;
        }
    }

    /**
     * Author: Gerton / Kris
     * @return
     * Creates a String which can be output to the console, containing various information about itself.
     */
    public String toString(){
//        return "Robot " + name + " was first recorded with" + firstRecordedHealth + "energy and is probably a " + role + ". He was last seen at " +  " his energy is " + lastRecordedHealth;
        return "Robot " + name + " was first recorded with" + firstRecordedHealth + "energy and is probably a " + role + ". He was last seen at " + recordedPositions.get(recordedPositions.size() -1 )[0] + "," + recordedPositions.get(recordedPositions.size() -1 )[0] + " his energy is " + lastRecordedHealth;
    }

}
