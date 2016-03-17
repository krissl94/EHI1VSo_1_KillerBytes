package EHI1VSo_1_KillerBytes;

import robocode.TeamRobot;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class EnemyStatistics implements Serializable{
    private boolean leaderAlive;
    private int droidsAlive;
    private int robotsAlive;
    private String targetName;

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    private Map<String, EnemyBot> enemies;

    public EnemyStatistics() {
        this.leaderAlive = true;
        this.droidsAlive = 0;
        this.robotsAlive = 0;
        this.enemies = new HashMap<>();
    }

    public boolean isLeaderAlive() {
        return leaderAlive;
    }
    public void leaderDied() {
        this.leaderAlive = false;
    }
    public int getDroidsAlive() {
        return droidsAlive;
    }
    public void droidDied() {
        this.droidsAlive--;
    }
    public int getRobotsAlive() {
        return robotsAlive;
    }
    public void robotDied() {
        this.robotsAlive--;
    }
    public Map<String, EnemyBot> getEnemies() {
        return enemies;
    }
    public void setEnemies(Map<String, EnemyBot> enemies) {
        this.enemies = enemies;
    }
    public void addEnemy(EnemyBot enemy){
        this.enemies.put(enemy.getName(), enemy);
    }
    public void updateEnemy(EnemyBot enemy){
        this.enemies.replace(enemy.getName(), enemy);
    }
    public void enemyDied(EnemyBot enemy){
        if(enemy.getRole().equals("droid")){
            this.droidDied();
        }else if(enemy.getRole().equals("robot")){
            this.robotDied();
        }else if(enemy.getRole().equals("leader")){
            this.leaderDied();
        }
    }

    public EnemyStatistics deSerialize(Serializable serializedStats){
        return null;
    }
}