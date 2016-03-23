package EHI1VSo_1_KillerBytes;

import robocode.MessageEvent;
import robocode.TeamRobot;

import java.io.Serializable;

/**
 * Created by kris on 9-3-16.
 */
public class Droid extends KillerByte implements Serializable, robocode.Droid {
    public void run(){
        role = "droid";
        init();

        while(true){
            if(allyStats != null) {
                reportTo(allyStats.getLeader());
            }
            attack();
            execute();
        }
    }
    public void onMessageReceived(MessageEvent e){
        messageReceived(e);
    }
}
