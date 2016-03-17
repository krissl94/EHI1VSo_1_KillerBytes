package EHI1VSo_1_KillerBytes;

import robocode.MessageEvent;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import static robocode.util.Utils.normalRelativeAngleDegrees;

/**
 * Created by kris on 9-3-16.
 */
public class RobotOne extends KillerByte implements Serializable {
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


}
