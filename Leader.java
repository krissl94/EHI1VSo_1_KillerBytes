package EHI1VSo_1_KillerBytes;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import robocode.MessageEvent;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;
//TODO Delete in final
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * Created by kris on 9-3-16.
 */
public class Leader extends KillerByte {
    String targetName;
    Boolean isLeader = true;
    AllyStatistics allyStats;
    EnemyStatistics enemyStats = new EnemyStatistics();

    public void run(){
        allyStats = new AllyStatistics("EHI1VSo_1_KillerBytes.Leader");
        allyStats.addAlly(this);
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        setAdjustRadarForRobotTurn(true);

        //TODO: Broadcast an initialized object
        broadcastStats(allyStats);
        while(true){
            // SET RANDOM COLORS
            Random random = new Random();
            this.setColors(Color.getHSBColor(random.nextFloat(),random.nextFloat(),random.nextFloat()),Color.getHSBColor(random.nextFloat(),random.nextFloat(),random.nextFloat()),Color.getHSBColor(random.nextFloat(),random.nextFloat(),random.nextFloat()));

            super.goCrazy();
        }
    }

    //Leader has no specific tasks, because another robot should be able to take over the position
    public void onScannedRobot(ScannedRobotEvent TargetTank){
        super.chase(TargetTank);
        TargetTank.getBearing();
    }

    public void onMessageReceived(MessageEvent e){
        //TODO: Check if it's an enemy object or an ally object
        // functie : isFriendly(name), ez



    }


    //TODO Delete in final
    public void onPaint(Graphics2D g){
        drawDebug(g);
    }

    //TODO Delete in final
    public void drawDebug(Graphics2D g){
        // Draw scan circles
        String[] teamMates = getTeammates();


        //Draw scan circle on self
        g.setColor(Color.green);
        g.drawOval((int) this.getX() - 600, (int) this.getY() - 600, 1200, 1200);

        //draw labels on each sides of the circle.
        g.drawString("ScanRange",(float)this.getX()-600,(float)this.getY());
        g.drawString("ScanRange",(int)this.getX(),(int)this.getY()-600);
        g.drawString("ScanRange",(int)this.getX(),(int)this.getY()+600);
        g.drawString("ScanRange",(int)this.getX()+600,(int)this.getY());

        // Draw square at last known enemy position, also put the co-ordinates in text above.
        for(Map.Entry<String,EnemyBot> entry: enemyStats.getEnemies().entrySet()) {
            EnemyBot enemy = entry.getValue();
            ArrayList<double[]> positions = enemy.getRecordedPositions();

            for (double[] position : positions) {

                // change color of square to red if it's the last entry
                if (position == positions.get(positions.size())) {
                    g.setColor(Color.RED);
                }// else set color to CYAN (light blue)
                else {
                    g.setColor(Color.CYAN);
                }
                g.drawRect((int)position[0], (int)position[1], 10, 10);
                // display text; X,Y
                g.drawString(String.valueOf(position[0]) + "," + String.valueOf(position[1]), (int)position[0], (int)position[1] - 10);
                // display text; bot name;
                g.drawString(entry.getKey(), (int)(position[0] - (entry.getKey().length())), (int)(position[1] + 10));
            }
        }
    }
}
