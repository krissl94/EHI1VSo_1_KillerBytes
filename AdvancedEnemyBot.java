package EHI1VSo_1_KillerBytes;

import robocode.Robot;
import robocode.ScannedRobotEvent;

/**
 * Created by Nicky on 30/03/2016.
 * TODO: Nicky, please summarize this class.
 */
public class AdvancedEnemyBot extends EnemyBot {
    private double x;
    private double y;

    public AdvancedEnemyBot(ScannedRobotEvent TargetTank, double[] coordinates) {
        super(TargetTank, coordinates);
        this.x = coordinates[0];
        this.y = coordinates[1];

        reset();
    }

    public AdvancedEnemyBot(){
        reset();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    /**
     * Author: Nicky
     * TODO: Nicky, please explain
     */
    public void reset(){
        super.reset();
        setX(0);
        setY(0);
    }

    /**
     * Author: Nicky
     * @param e
     * @param robot
     * TODO: Nicky, please explain
     */
    public void update(ScannedRobotEvent e, Robot robot){

        super.update(e);

        double absBearingDeg = (robot.getHeading() + e.getBearing());
        if (absBearingDeg <0) {
            absBearingDeg += 360;
        }

        // Math.sin because for X because 0 deg is the North
        x = robot.getX() + Math.sin(Math.toRadians(absBearingDeg)) * e.getDistance();
        // Math.cos because for Y because 0 deg is the North
        y = robot.getY() + Math.cos(Math.toRadians(absBearingDeg)) * e.getDistance();

    }

    /**
     * Author: Nicky
     * @param when
     * @return
     * TODO: Nicky, please explains
     */
    public double getFutureX(long when){
        return x + Math.sin(Math.toRadians(getHeading())) * getVelocity() * when;
    }

    /**
     * Author: Nicky
     * @param when
     * @return
     * TODO: Nicky, please explain
     */
    public double getFutureY(long when) {
        return y + Math.cos(Math.toRadians(getHeading())) * getVelocity() * when;
    }




}
