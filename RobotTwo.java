package EHI1VSo_1_KillerBytes;

import robocode.TeamRobot;

/**
 * Created by kris on 9-3-16.
 */
public class RobotTwo extends TeamRobot {
    Boolean isLeader = false;

    public void run() {
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        setAdjustRadarForRobotTurn(true);

        while(true) {
            scan();
        }
    }
}
