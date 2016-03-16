package EHI1VSo_1_KillerBytes;

import robocode.MessageEvent;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by kris on 9-3-16.
 */
public class RobotTwo extends KillerByte implements Serializable{
    private EnemyStatistics enemyStats;
    Boolean isLeader = false;
    Boolean running = false;
    public void run(){
        name = getName();
        running = true;
        enemyStats = new EnemyStatistics();
        while(true){
            //TODO: Every tick, a robot reports itself to the leader
            if(allyStats != null)
                reportTo(allyStats.getLeader());
            scan();
            execute();
        }
    }


    public void onScannedRobot(ScannedRobotEvent TargetTank){
        System.out.println("I spotted " + TargetTank.getName());
        if(!isLeader){
            if(allyStats != null){
                sendMsg(allyStats.getLeader(), TargetTank);
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
