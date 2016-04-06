package EHI1VSo_1_KillerBytes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class EnemyStatistics implements Serializable{
    //region Variables
    private boolean leaderAlive;
    private int droidsAlive;
    private int robotsAlive;
    private String targetName;
    private Map<String, EnemyBot> enemies;
    //endregion

    public EnemyStatistics() {
        this.leaderAlive = true;
        this.droidsAlive = 0;
        this.robotsAlive = 0;
        enemies = new HashMap<>();
    }

    /**
     * Author: Kris
     * @param role
     * @return an enemybot's name that has a certain role
     */
    public String getTargetByRole(String role){
        for(Map.Entry<String,EnemyBot> entry: enemies.entrySet()) {
            EnemyBot enemy = entry.getValue();
            if(enemy.getRole().equals(role)){
                return enemy.getName();
            }
        }
        return "";
    }

    //region Getters and Setters
    public String getTargetName() {
        return targetName;
    }

    public void leaderDied() {
        this.leaderAlive = false;
    }
    public void droidDied() {
        this.droidsAlive--;
    }
    public void robotDied() {
        this.robotsAlive--;
    }
    public Map<String, EnemyBot> getEnemies() {
        return enemies;
    }
    //endregion

    /**
     * Author: Gerton
     * @return a boolean stating if the enemies map is empty
     */
    public boolean hasEnemiesRegistered(){
        return this.enemies.size() > 0;
    }

    /**
     * Author: Kris
     * @param enemy
     * Adds an enemy to the enemies HashMap
     * If no target is set, this one becomes the target.
     *  If a target is set, but this enemy is the leader, the target is reassigned to this bot.
     */
    public void addEnemy(EnemyBot enemy ){
        if(targetName == null){
            targetName = enemy.getName();
        }
        enemies.put(enemy.getName(), enemy);
        switch(enemy.getRole()){
            case "leader":
                targetName = enemy.getName();
                break;
            case "droid":
                this.droidsAlive++;
                break;
            case "robot":
                this.robotsAlive++;
                break;
        }
    }

    /**
     * Author: Gerton
     * @param enemy
     * TODO: Gerton, please explain
     */
    public void updateEnemy(EnemyBot enemy){
        //TODO: Update instead of Replace
        EnemyBot bot = this.enemies.get(enemy.getName());
        bot.setLastRecordedHealth(enemy.getLastRecordedHealth());

        double[] lastCoordinates = enemy.getLastRecordedPosition();
        bot.addPosition(lastCoordinates);
    }

    /**
     * Author: Kris
     * @param enemy
     * Check role of deceased enemy and execute according function
     */
    public void enemyDied(EnemyBot enemy){
        if(enemy.getRole().equals("droid")){
            this.droidDied();
        }else if(enemy.getRole().equals("robot")){
            this.robotDied();
        }else if(enemy.getRole().equals("leader")){
            this.leaderDied();
        }

        if(this.targetName.equals(enemy.getName())){
            //TODO: Update target
            if(robotsAlive > 0){
                targetName = getTargetByRole("robot");
            }
            else{
                //Set target to a droid
                targetName = getTargetByRole("droid");
            }
        }

        enemies.remove(enemy.getName());
    }

    /**
     * Author: Kris / Gerton
     * @return a String value of the enemyStatistics
     */
    public String toString(){
        String toReturn = "/-------------------------------------/\r\n";
        toReturn += "Enemy leader is alive " + leaderAlive + " and they have " + robotsAlive +" living robots" + " and " + droidsAlive + "living droids";

        toReturn+= "\r\n++++++++++++++++++++\r\n";
        for(Map.Entry<String,EnemyBot> entry: enemies.entrySet()) {
            EnemyBot enemy = entry.getValue();
            toReturn += " Robot " + enemy.toString() + "\r\n";
        }
        toReturn+="++++++++++++++++++++\r\n";
        toReturn+="/-------------------------------------/\r\n";
        return toReturn;
    }

}