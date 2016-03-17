package EHI1VSo_1_KillerBytes;

import robocode.ScannedRobotEvent;

import java.lang.annotation.Target;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by kris on 9-3-16.
 */
public class EnemyBot {
    private String name;
    private double firstRecordedHealth;
    private double lastRecordedHealth;
    private String role;
    private ArrayList<double[]> recordedPositions;

    public EnemyBot(ScannedRobotEvent TargetTank){//String name, int firstRecordedHealth, int lastRecordedHealth, String role, int x, int y) {
        this.name = TargetTank.getName();
        this.firstRecordedHealth = TargetTank.getEnergy();
        this.lastRecordedHealth = TargetTank.getEnergy();
        this.role = determineRole(TargetTank.getEnergy());
        this.recordedPositions = new ArrayList<>();
        this.recordedPositions.add(determinePosition(TargetTank));
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

    public void setLastRecordedHealth(int lastRecordedHealth) {
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

    public void addPosition(ScannedRobotEvent targetTank){
        recordedPositions.add(determinePosition(targetTank));
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

    private double[] determinePosition(ScannedRobotEvent targetTank){
        //Coordinaten uitrekenen adhv eigen positie en afstand enzo, mooi klusje voor nicky
        return new double[]{1,1};
    }

    public double[] getLastRecordedPosition(){
        return this.recordedPositions.get(this.recordedPositions.size() - 1);
    }

    public String toString(){
        return "Robot " + name + " was first recorded with" + firstRecordedHealth + "energy and is probably a " + role + ". He was last seen at " + recordedPositions.get(recordedPositions.size() -1 )[0] + "," + recordedPositions.get(recordedPositions.size() -1 )[0] + " his energy is " + lastRecordedHealth;
    }
}
