package EHI1VSo_1_KillerBytes;

import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
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
        enemies = new HashMap<>();
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

    public boolean hasEnemiesRegistered(){
        return this.enemies.size() > 0;
    }

    public void addEnemyFromEvent(ScannedRobotEvent e){
//    addEnemy(new EnemyBot(e));
    }
    public void addEnemy(EnemyBot enemy ){
        enemies.put(enemy.getName(), enemy);
        switch(enemy.getRole()){
            case "leader":
                break;
            case "droid":
                this.droidsAlive++;
                break;
            case "robot":
                this.robotsAlive++;
                break;
        }
    }
    public void updateEnemy(EnemyBot enemy){
        //TODO: Update instead of Replace
        EnemyBot bot = this.enemies.get(enemy.getName());
        bot.setLastRecordedHealth(enemy.getLastRecordedHealth());
//        bot.addPosition(enemy.getRecordedPositions().get(0));

//        this.enemies.replace(enemy.getName(), enemy);
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

    public String toString(){
        String toReturn = "Enemy leader is alive " + leaderAlive + " and they have " + robotsAlive +" living robots" + " and " + droidsAlive + "living droids";
        Iterator it = enemies.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            toReturn += " Robot " + pair.getKey() + ((EnemyBot)pair.getValue()).toString();
//            System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
        for (int i = 0; i < enemies.size(); i++) {
            toReturn += " Robot " + enemies.get(i).toString();

        }
        return toReturn;
    }
}