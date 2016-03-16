package EHI1VSo_1_KillerBytes;

import robocode.MessageEvent;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

/**
 * Created by kris on 9-3-16.
 */
public class Leader extends KillerByte {
    String targetName;
    Boolean isLeader = true;
    AllyStatistics allyStats = new AllyStatistics(this.getName());
    EnemyStatistics enemyStats = new EnemyStatistics();

    public void run(){
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        setAdjustRadarForRobotTurn(true);

        //TODO: Broadcast an initialized object
        broadcastStats(allyStats);
        while(true){
            super.goCrazy();
        }
    }
    //Leader has no specific tasks, because another robot should be able to take over the position
    public void onScannedRobot(ScannedRobotEvent TargetTank){
        super.chase(TargetTank);
    }

    public void onMessageReceived(MessageEvent e){
        //TODO: Check if it's an enemy object or an ally object
        System.out.println("i received a message from "+ e.getSender());
        if(e.getMessage() instanceof TeamRobot){
            System.out.println("Is ally data!");
            if (!(allyStats).getAllies().containsKey(e.getSender())) {
                System.out.println("He's not registered yet!");
                allyStats.addAlly((TeamRobot)e.getMessage());
            }
        } else if(e.getMessage() instanceof EnemyBot){
            System.out.println("Is enemy data!");
            if (!(enemyStats).getEnemies().containsKey(((EnemyBot) e.getMessage()).getName())) {
                System.out.println("He's not registered yet!");
                enemyStats.addEnemy((EnemyBot) e.getMessage());
            }
            else {
                System.out.println("He's registered, but i need to update him");
                enemyStats.updateEnemy((EnemyBot) e.getMessage());
            }
        }


    }
}
