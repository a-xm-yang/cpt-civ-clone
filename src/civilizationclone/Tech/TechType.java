package civilizationclone.Tech;

//An enum of all the tech types

public enum TechType {
    NONE(0), AGRICULTURE(20), POTTERY(25), CALENDER(30), WRITING(35), ANIMAL(25), HORSERIDING(35);
    
    private int techCost;

    private TechType(int techCost) {
        this.techCost = techCost;
    }    

    public int getTechCost() {
        return techCost;
    }
     
}