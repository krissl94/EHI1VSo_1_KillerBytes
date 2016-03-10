package EHI1VSo_1_KillerBytes;

import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

/**
 * Created by kris on 9-3-16.
 */
public class Leader extends killerByte{
    public void run(){
        while(true){
            super.goCrazy();
        }
    }
    //Leader has no specific tasks, because another robot should be able to take over the position
    public void onScannedRobot(ScannedRobotEvent TargetTank){
        super.chase(TargetTank);
    }
}
