package EHI1VSo_1_KillerBytes;

import robocode.MessageEvent;
import robocode.ScannedRobotEvent;
import java.io.Serializable;

/**
 * Created by kris on 9-3-16.
 */
public class RobotOne extends KillerByte implements Serializable {
    Boolean running = false;

    public void run(){
        init();
        name = getName();
        role = "robot";
        running = true;
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        setAdjustRadarForRobotTurn(true);

        while(true){
            //random colors
            randomColor();
            System.out.println("Target = " + enemyStats.getTargetName());
            System.out.println("Current Leader = " + allyStats.getLeader());
            //TODO: Every tick, a robot reports itself to the leader
            if(allyStats != null)
                reportTo(allyStats.getLeader());
            attack();
            setTurnRadarRight(360);
            smartShooting();
            execute();
        }
    }

    /**
     * Author: Gerton / Kris / Nicky
     * @param TargetTank
     * Checks if the current scanned tank is the current target, if so update the AdvancedEnemyBot instance of this object
     * if i'm not the leader send this scannedRobotEvent information in a new EnemyBot instance to the leader.
     */
    public void onScannedRobot(ScannedRobotEvent TargetTank){
        if(TargetTank.getName().equals(enemyStats.getTargetName())){
            System.out.println("YES I NEED UPDATE PLS");
            enemeyAdv.update(TargetTank,this);
        }
        System.out.println("I spotted " + TargetTank.getName());

        if(!isLeader){
            if(allyStats != null){
                EnemyBot enemy = new EnemyBot(TargetTank , calculateCoordinates(TargetTank));
                sendMsg(allyStats.getLeader(), enemy);
            }
            else {
                //Don't do anything cus idk who to send to
            }
        } else {
            //TODO: Update shit myself..
        }
    }

    public void onMessageReceived(MessageEvent e){
        messageReceived(e);
    }


}
