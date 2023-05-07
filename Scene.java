import java.util.*;

public class Scene{
    public String name;
    public Position[] positions;
    public String image;
    public String sceneNum;
    public String description;
    public String budget;
    private String room;

    public Scene(String name, Position[] positions, String image, String sceneNum, String description, String budget){
        this.name = name;
        this.positions = positions;
        this.image = image;
        this.sceneNum = sceneNum;
        this.description = description;
        this.budget = budget;
    }

    public void checkPlayerCounters(int player, int budget){

    }
    public void endScene(){

    }
    public void payBonus(List<Integer> players){

    }
    public void payForWork(int player){

    }
    public void act(int player, String position){

    }
    public void rehearse(int player){

    }
    public void checkRoll(List<Integer> rolls){

    }
    public void addPlayer(int player, String position){

    }
}
