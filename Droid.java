package EHI1VSo_1_KillerBytes;

import robocode.TeamRobot;

/**
 * Created by kris on 9-3-16.
 */
public class Droid extends KillerByte implements robocode.Droid {
    public void run(){
        role = "droid";
        init();
        while(true){
            goTo(170,450);
            //smartShooting(100, 100);
            execute();
            //enemyStats.getEnemies().get(enemyStats.getTargetName()).getRecordedPositions().get(0)[0], enemyStats.getEnemies().get(enemyStats.getTargetName()).getRecordedPositions().get(0)[1]
        }
    }
}
