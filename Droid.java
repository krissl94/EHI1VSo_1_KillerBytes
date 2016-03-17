package EHI1VSo_1_KillerBytes;

import robocode.TeamRobot;

/**
 * Created by kris on 9-3-16.
 */
public class Droid extends KillerByte implements robocode.Droid {
    //Doei
    public void run(){
        while(true){
            moveTo( enemyStats.getEnemies().get(enemyStats.getTargetName()).getRecordedPositions().get(0)[0], enemyStats.getEnemies().get(enemyStats.getTargetName()).getRecordedPositions().get(0)[1]);
        }
    }
}
