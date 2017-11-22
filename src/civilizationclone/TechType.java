package civilizationclone;

//An enum of all the tech types
import civilizationclone.Tile.Improvement;
import civilizationclone.Unit.UnitType;
import java.util.EnumSet;
import java.util.Set;

public enum TechType {

    //COST,PREREQUISITE,UNIT,IMPROVEMENT,PROJECT
    NONE(0, null, null, null, null),
    AGRICULTURE(20, EnumSet.of(NONE), null, EnumSet.of(Improvement.FARM), EnumSet.of(CityProject.GRANARY)),
    POTTERY(25, EnumSet.of(AGRICULTURE), null, null, null),
    CALENDER(30, EnumSet.of(POTTERY), null, null, null),
    WRITING(35, EnumSet.of(CALENDER), null, null, EnumSet.of(CityProject.LIBRARY)),
    ANIMAL(25, EnumSet.of(AGRICULTURE), null, EnumSet.of(Improvement.RANCH), null),
    HORSERIDING(35, EnumSet.of(ANIMAL), EnumSet.of(UnitType.HORSEMAN), null, null);
    
    private int techCost;
    private Set<TechType> prerequisites;
    private Set<UnitType> unlockUnit;
    private Set<Improvement> unlockImprovement;
    private Set<CityProject> unlockProject;
    
    private TechType(int techCost, Set<TechType> prerequisites, Set<UnitType> unlockUnit, Set<Improvement> unlockImprovement, Set<CityProject> unlockProject) {
        this.techCost = techCost;
        this.prerequisites = prerequisites;
        this.unlockUnit = unlockUnit;
        this.unlockImprovement = unlockImprovement;
        this.unlockProject = unlockProject;
    }
    
    public Set<TechType> getPrerequisites() {
        return prerequisites;
    }

    public Set<UnitType> getUnlockUnit() {
        return unlockUnit;
    }

    public Set<Improvement> getUnlockImprovement() {
        return unlockImprovement;
    }

    public Set<CityProject> getUnlockProject() {
        return unlockProject;
    }
    
    public int getTechCost() {
        return techCost;
    }
    
}
