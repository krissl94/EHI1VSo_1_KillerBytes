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
    private EnemyStatistics enemyStats;
    private AllyStatistics allyStats;
    Boolean isLeader = false;

    public void run() {
        enemystats = new EnemyStatistics();
        while(true){
            //TODO: Every tick, a robot reports itself to the leader
            //TODO:
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
        System.out.println("i received a message from "+ e.getSender());
        if(e.getMessage() instanceof AllyStatistics){
            System.out.println("Is an allystats thingy");
            if (!((AllyStatistics) e.getMessage()).getAllies().containsKey(this.getName())) {
                //This is only going to happen once. It's the leader requesting me to register.
                System.out.println("I'm not registered yet");
                sendMsg(((AllyStatistics) e.getMessage()).getLeader(), this);
            }
        }
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
