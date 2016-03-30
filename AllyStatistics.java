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
    private Map<String, AllyBot> allies;

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

    public Map<String, AllyBot> getAllies() {
        return allies;
    }

    public AllyBot getAlly(String name) {
        return allies.get(name);
    }

    public void addAlly(AllyBot ally) {
        this.allies.put(ally.getName(), ally);
    }
    public void updateAlly(AllyBot ally){
        this.allies.replace(ally.getName(), ally);
    }

    public AllyBot getOtherRobot(String myName){
        for(Map.Entry<String,AllyBot> entry: allies.entrySet()) {
            AllyBot ally = entry.getValue();
            if(!ally.getRole().equals(myName)){
                return ally;
            }
        }
        return null;

    }

    public void allyDied(AllyBot allyBot) {
        allies.remove(allyBot.getName());
    }
}
