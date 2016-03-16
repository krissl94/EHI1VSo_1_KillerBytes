package EHI1VSo_1_KillerBytes;

import robocode.MessageEvent;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;
//TODO Delete in final
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by kris on 9-3-16.
 */
public class Leader extends KillerByte {
    String targetName;
    Boolean isLeader = true;
    AllyStatistics allyStats;
    EnemyStatistics enemyStats = new EnemyStatistics();

    public void run(){
        allyStats = new AllyStatistics(this.getName());
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        setAdjustRadarForRobotTurn(true);

        //TODO: Broadcast an initialized object
        broadcastStats(allyStats);
        while(true){
            super.goCrazy();
        }
    }
    //Leader has no specific tasks, because another robot should be able to take over the position
    public void onScannedRobot(ScannedRobotEvent TargetTank){
        super.chase(TargetTank);
    }

    public void onMessageReceived(MessageEvent e){
        //TODO: Check if it's an enemy object or an ally object
        System.out.println("i received a message from "+ e.getSender());
        if(e.getMessage() instanceof TeamRobot){
            System.out.println("Is ally data!");
            if (!(allyStats).getAllies().containsKey(e.getSender())) {
                System.out.println("He's not registered yet!");
                allyStats.addAlly((TeamRobot)e.getMessage());
            }
            else{
                System.out.println("He's registered already! Updating..");
                allyStats.updateAlly((TeamRobot) e.getMessage());
            }
        } else if(e.getMessage() instanceof EnemyBot){
            System.out.println("Is enemy data!");
            if (!(enemyStats).getEnemies().containsKey(((EnemyBot) e.getMessage()).getName())) {
                System.out.println("He's not registered yet!");
                enemyStats.addEnemy((EnemyBot) e.getMessage());
            }
            else {
                System.out.println("He's registered, but i need to update him");
                enemyStats.updateEnemy((EnemyBot) e.getMessage());
            }
        }
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


        for(String mate : teamMates){

        }

    }
}
