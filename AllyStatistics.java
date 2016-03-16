package EHI1VSo_1_KillerBytes;

import robocode.TeamRobot;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kris on 16-3-16.
 */
public class AllyStatistics implements Serializable{
    private String leader;
    private Map<String, KillerByte> allies;

    public AllyStatistics(String leader ) {
        this.leader = leader;
        this.allies = new HashMap<>();
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public Map<String, KillerByte> getAllies() {
        return allies;
    }

    public TeamRobot getAlly(String name) {
        return allies.get(name);
    }

    public void addAlly(KillerByte ally) {
        this.allies.put(ally.name, ally);
    }
    public void updateAlly(KillerByte ally){
        this.allies.replace(ally.name, ally);
    }
}
