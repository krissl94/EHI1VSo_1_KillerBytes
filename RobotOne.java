package EHI1VSo_1_KillerBytes;

import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

import java.util.Map;

import static robocode.util.Utils.normalRelativeAngleDegrees;

/**
 * Created by kris on 9-3-16.
 */
public class RobotOne extends TeamRobot {
    private EnemyStatistics enemystats;
    public void run() {
        enemystats = new EnemyStatistics();
        while(true){
            setAhead(1);
            execute();
        }
    }
    public void onScannedRobot(ScannedRobotEvent TargetTank){
        if(enemystats.getEnemies().containsKey(TargetTank.getName())){
//            enemystats.updateEnemy(new EnemyBot(TargetTank));
        }
        else{
            enemystats.addEnemy(new EnemyBot(TargetTank));
        }
    }
}
