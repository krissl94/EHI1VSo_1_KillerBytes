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
        role = "robot";
        running = true;
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        setAdjustRadarForRobotTurn(true);

        while(true){
            //TODO: Every tick, a robot reports itself to the leader
            if(allyStats != null) {
                reportTo(allyStats.getLeader());
            }
            setTurnRadarLeft(360);

            attack();

            execute();
        }
    }
    public void onScannedRobot(ScannedRobotEvent TargetTank){
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
