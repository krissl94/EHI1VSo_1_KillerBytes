package EHI1VSo_1_KillerBytes;

import robocode.ScannedRobotEvent;

import java.io.Serializable;

/**
 * Created by kris on 17-3-16.
 */
public class AllyBot implements Serializable{
    private String name;
    private double energy;
    private String role;
    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public AllyBot(KillerByte bot) {
        this.name = bot.getName();
        this.energy = bot.getEnergy();
        this.role = bot.role    ;
    }
    public AllyBot(String name, double energy, String role) {
        this.name = name;
        this.energy = energy;
        this.role = role;
    }

    public String getName() {
        return name;
    }
    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }
    public String getRole() {
        return role;
    }
}
