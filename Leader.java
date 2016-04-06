package EHI1VSo_1_KillerBytes;

import robocode.MessageEvent;
import robocode.ScannedRobotEvent;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by kris on 9-3-16.
 */
public class Leader extends KillerByte {
    private AdvancedEnemyBot enemyRobot = new AdvancedEnemyBot();

    public void run(){
        isLeader = true;
        init();
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        setAdjustRadarForRobotTurn(true);
        enemyRobot.reset(); //Testing predictive shooting
        setTurnRadarRight(360); // Testing predictive shooting

        broadcastStats(allyStats);
        while(true){
            // SET RANDOM COLORS
            System.out.println(enemyStats.toString());
            randomColor();
            /*Random random = new Random();
            this.setColors(Color.getHSBColor(random.nextFloat(), random.nextFloat(), random.nextFloat()), Color.getHSBColor(random.nextFloat(), random.nextFloat(), random.nextFloat()), Color.getHSBColor(random.nextFloat(), random.nextFloat(), random.nextFloat()));*/

            setTurnRadarRight(360);
            smartShooting();

            System.out.println("tick");
            execute();
        }
    }

    public void onScannedRobot(ScannedRobotEvent TargetTank){
        super.chase(TargetTank);
    }

    public void onMessageReceived(MessageEvent e){
        messageReceived(e);
    }

    //region Debugging functions
    /**
     * Author: Gerton
     * @param g
     * TODO: Gerton, please explain
     */
    public void onPaint(Graphics2D g){
        drawDebug(g);
    }

    /**
     * Author: Gerton
     * @param g
     * TODO: Gerton, please explain
     */
    public void drawDebug(Graphics2D g){
        // Draw scan circles
        String[] teamMates = getTeammates();

        //Draw scan circle on self
        g.setColor(Color.green);

        g.drawString("Taste the fucking rainbow",(int)this.getX()-30,(int)this.getY()+20);

        g.drawOval((int) this.getX() - 600, (int) this.getY() - 600, 1200, 1200);
        //draw labels on each sides of the circle.
        g.drawString("ScanRange",(int)this.getX()-600,(int)this.getY());
        g.drawString("ScanRange",(int)this.getX(),(int)this.getY()-600);
        g.drawString("ScanRange",(int)this.getX(),(int)this.getY()+600);
        g.drawString("ScanRange",(int)this.getX()+600,(int)this.getY());

        // Draw square at last known enemy position, also put the co-ordinates in text above.

        // ONLY DO THIS IF THE ENEMYSTATS HAS ENEMIES.
        // SAVES A BUTTLOAD OF NULLPOINTER ERRORS.
        if(enemyStats.hasEnemiesRegistered()){
            for(Map.Entry<String,EnemyBot> entry: enemyStats.getEnemies().entrySet()) {
                EnemyBot enemy = entry.getValue();
                ArrayList<double[]> positions = enemy.getRecordedPositions();
                if(positions.size() > 0){
                    for (double[] position : positions) {
                        // TODO : Check functionality after fixing position registration. (this should work)
                        if(position.length > 0){
                            // change color of square to red if it's the last entry
                            if (position[0] == positions.get(positions.size() - 1)[0] && position[1] == positions.get(positions.size() - 1)[1]) {
                                g.setColor(Color.RED);
                                // display text; X,Y
                                g.drawString(String.valueOf(position[0]) + "," + String.valueOf(position[1]), (int) position[0], (int) position[1] - 10);
                                // display text; bot name;
                                g.drawString(entry.getKey(), (int) (position[0] - (entry.getKey().length())), (int) (position[1] + 10));
                            }// else set color to CYAN (light blue)
                            else {
                                g.setColor(Color.CYAN);
                            }
                            g.drawRect((int) position[0], (int) position[1], 10, 10);

                        }
                    }
                }
            }
        }
        for(Map.Entry allyEntry:allyStats.getAllies().entrySet()){
            AllyBot ally = (AllyBot) allyEntry.getValue();
                g.drawOval((int) ally.getX() - 600, (int) ally.getY() - 600, 1200, 1200);

                //draw labels on each sides of the circle.
                g.drawString("ScanRange",(int)ally.getX()-600,(int)ally.getY());
                g.drawString("ScanRange",(int)ally.getX(),(int)ally.getY()-600);
                g.drawString("ScanRange",(int)ally.getX(),(int)ally.getY()+600);
                g.drawString("ScanRange",(int)ally.getX()+600,(int)ally.getY());
            }
        }
    //endregion
}
