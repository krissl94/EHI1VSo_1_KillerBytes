package EHI1VSo_1_KillerBytes;

import robocode.MessageEvent;
import robocode.ScannedRobotEvent;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by kris on 9-3-16.
 */
public class RobotTwo extends KillerByte implements Serializable{
    Boolean running = false;
    public void run(){
        init();
        name = getName();
        role = "robot";

        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        setAdjustRadarForRobotTurn(true);
        running = true;

        while(true){
            randomColor();
            if(allyStats != null)
                reportTo(allyStats.getLeader());
            attack();
            setTurnRadarRight(360);
            smartShooting();
            System.out.println(enemyStats.getTargetName());
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

    public void sendMsg(String name, Serializable msg){
        try{
            sendMessage(name, msg);
        }
        catch (IOException IOE){
            //Todo: leader isn't receiving me D:
        }
    }
}
