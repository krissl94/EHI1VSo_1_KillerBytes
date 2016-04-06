package EHI1VSo_1_KillerBytes;

import robocode.TeamRobot;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kris on 16-3-16.
 * Class that contains a HashMap of AllyBots and a String with the name of the leader
 * Implements Serializable, because it is sent as a message
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

    /**
     * Author: Kris
     * @param ally
     * This function adds the AllyBot if it's not in the HashMap yet
     */
    public void addAlly(AllyBot ally) {
        this.allies.put(ally.getName(), ally);
    }

    /**
     * Author: Gerton
     * @param ally
     * This function updates the AllyBot if it's already in the HashMap
     */
    public void updateAlly(AllyBot ally){
        this.allies.replace(ally.getName(), ally);
    }

    /**
     * Author: Kris
     * @param myName
     * @return the other robot in the team
     * Our team contains 2 robots, this function is called by one robot and it returns the other robot
     */
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
