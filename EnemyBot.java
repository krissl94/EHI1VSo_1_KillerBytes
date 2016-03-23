package EHI1VSo_1_KillerBytes;

import robocode.ScannedRobotEvent;

import java.io.Serializable;
import java.lang.annotation.Target;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by kris on 9-3-16.
 */
public class EnemyBot implements Serializable{
    private String name;
    private double firstRecordedHealth;
    private double lastRecordedHealth;
    private String role;
    private ArrayList<double[]> recordedPositions;

    public EnemyBot(ScannedRobotEvent TargetTank, double[] coordinates){//String name, int firstRecordedHealth, int lastRecordedHealth, String role, int x, int y) {
        this.name = TargetTank.getName();
        this.firstRecordedHealth = TargetTank.getEnergy();
        this.lastRecordedHealth = TargetTank.getEnergy();
        this.role = determineRole(TargetTank.getEnergy());
        this.recordedPositions = new ArrayList<>();
        this.recordedPositions.add(coordinates);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFirstRecordedHealth() {
        return firstRecordedHealth;
    }

    public double getLastRecordedHealth() {
        return lastRecordedHealth;
    }

    public void setLastRecordedHealth(double lastRecordedHealth) {
        this.lastRecordedHealth = lastRecordedHealth;
    }

    public String getRole() {
        return role;
    }

    public void setRole() {
        this.role = role;
    }

    public ArrayList<double[]> getRecordedPositions() {
        return recordedPositions;
    }

    public void addPosition(double[] xy){
        recordedPositions.add(xy);
    }

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

    public double[] getLastRecordedPosition(){
        return this.recordedPositions.get(this.recordedPositions.size() - 1);
    }

    public String toString(){
//        return "Robot " + name + " was first recorded with" + firstRecordedHealth + "energy and is probably a " + role + ". He was last seen at " +  " his energy is " + lastRecordedHealth;
        return "Robot " + name + " was first recorded with" + firstRecordedHealth + "energy and is probably a " + role + ". He was last seen at " + recordedPositions.get(recordedPositions.size() -1 )[0] + "," + recordedPositions.get(recordedPositions.size() -1 )[0] + " his energy is " + lastRecordedHealth;
    }

    //public EnemyBot(ScannedRobotEvent e) {

    //}
}
