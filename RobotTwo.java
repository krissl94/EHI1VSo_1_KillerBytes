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
            //TODO: Every tick, a robot reports itself to the leader
            if(allyStats != null)
                reportTo(allyStats.getLeader());
            attack();

            goCrazy();
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

    public void sendMsg(String name, Serializable msg){
        try{
            sendMessage(name, msg);
        }
        catch (IOException IOE){
            //Todo: leader isn't receiving me D:
        }
    }
}
