package civilizationclone;

//An enum of all the tech types
import civilizationclone.Tile.Improvement;
import civilizationclone.Unit.UnitType;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public enum TechType {

    //COST,PREREQUISITE,UNIT,IMPROVEMENT,PROJECT, SPECIAL
    NONE(0, null, null, null, null, null),
    AGRICULTURE(20, setOf(NONE), EnumSet.of(UnitType.SETTLER), EnumSet.of(Improvement.FARM), null, null),
    POTTERY(25, setOf(AGRICULTURE), null, null, EnumSet.of(CityProject.GRANARY), null),
    IRRIGATION(45, setOf(POTTERY), null, EnumSet.of(Improvement.PLANTATION), null, null),
    WRITING(45, setOf(POTTERY), null, null, EnumSet.of(CityProject.LIBRARY), null),
    CURRENCY(120, setOf(WRITING), null, null, EnumSet.of(CityProject.MARKET), null),
    APPRENTICESHIP(250, setOf(CURRENCY), null, null, EnumSet.of(CityProject.WORKSHOP), "Increase production of all mine sites."),
    EDUCATION(350, setOf(APPRENTICESHIP), null, EnumSet.of(Improvement.ACADEMY), EnumSet.of(CityProject.UNIVERSITY), null),
    ASTRONOMY(550, setOf(EDUCATION), null, null, null, "Increase science output of all academy sites"),
    SCIENTIFIC_THEORY(680, setOf(ASTRONOMY), null, null, EnumSet.of(CityProject.HOSPITAL), "Increase food output of all plantations"),
    BANKING(445, setOf(EDUCATION, APPRENTICESHIP), null, null, EnumSet.of(CityProject.BANK), "Increaase gold output of all plantations"),
    ECONOMICS(750, setOf(SCIENTIFIC_THEORY, BANKING), null, null, EnumSet.of(CityProject.STOCKEXCHANGE), null),
    REPLACEABLE_PARTS(1200, setOf(ECONOMICS), EnumSet.of(UnitType.INFANTRY), null, null, "Double food output of all farms"),
    ANIMAL(25, setOf(AGRICULTURE), null, EnumSet.of(Improvement.RANCH), null, null),
    ARCHERY(45, setOf(ANIMAL), EnumSet.of(UnitType.ARCHER), null, null, null),
    HORSERIDING(120, setOf(ARCHERY), EnumSet.of(UnitType.HORSEMAN), null, EnumSet.of(CityProject.STABLE), null),
    STIRRUPS(350, setOf(HORSERIDING), EnumSet.of(UnitType.KNIGHT), null, null, "Increase food output from all ranch sites"),
    SAILING(25, setOf(AGRICULTURE), EnumSet.of(UnitType.GALLEY), null, null, "Allows land units to embark into water area."),
    NAVIGATION(120, setOf(SAILING), null, EnumSet.of(Improvement.FISHING), EnumSet.of(CityProject.LIGHTHOUSE), null),
    SHIPBUILDING(180, setOf(NAVIGATION), EnumSet.of(UnitType.QUADRIREME), null, null, null),
    CARTOGRAPHY(445, setOf(SHIPBUILDING), EnumSet.of(UnitType.CARAVEL), null, null, "Increase gold output of all fishing sites"),
    MINING(25, setOf(AGRICULTURE), null, EnumSet.of(Improvement.MINE), null, null),
    MASONRY(80, setOf(MINING), null, null, EnumSet.of(CityProject.ANCIENTWALL), null),
    IRONWORKING(120, setOf(MINING), EnumSet.of(UnitType.SWORDSMAN), null, EnumSet.of(CityProject.BARRACKS), null),
    CONSTRUCTION(120, setOf(MASONRY, HORSERIDING), null, null, EnumSet.of(CityProject.MEDIEVALWALL), null),
    ENGINEERING(200, setOf(MASONRY, IRONWORKING), EnumSet.of(UnitType.CATAPULT, UnitType.CROSSBOWMAN), null, null, null),
    MASS_PRODUCTION(445, setOf(EDUCATION, SHIPBUILDING), null, null, EnumSet.of(CityProject.SHIPYARD, CityProject.RENAISSANCEWALL), null),
    GUNPOWDER(445, setOf(STIRRUPS, APPRENTICESHIP), EnumSet.of(UnitType.MUSKETMAN), null, null, null),
    METALCASTING(550, setOf(GUNPOWDER), EnumSet.of(UnitType.BOMBARD), null, null, null),
    BALLISTICS(680, setOf(METALCASTING), EnumSet.of(UnitType.FIELDCANNON), null, null, null),
    INDUSTRIALIZATION(680, setOf(MASS_PRODUCTION), null, EnumSet.of(Improvement.OILWELL), EnumSet.of(CityProject.FACTORY), "Increase production of all mine sites"),
    STEAM_ENGINE(750, setOf(INDUSTRIALIZATION), EnumSet.of(UnitType.IRONCLAD), null, null, null),
    MILITARY_SCIENCE(680, setOf(METALCASTING), EnumSet.of(UnitType.MOUNTEDFORCE), null, null, null);

    private int techCost;
    private Set<TechType> prerequisites;
    private Set<UnitType> unlockUnit;
    private Set<Improvement> unlockImprovement;
    private Set<CityProject> unlockProject;
    private String message;

    private TechType(int techCost, Set<TechType> prerequisites, Set<UnitType> unlockUnit, Set<Improvement> unlockImprovement, Set<CityProject> unlockProject, String message) {
        this.techCost = techCost;
        this.prerequisites = prerequisites;
        this.unlockUnit = unlockUnit;
        this.unlockImprovement = unlockImprovement;
        this.unlockProject = unlockProject;
        this.message = message;
    }

    //GETTER & HELPER
    //<editor-fold>
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

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        String s = name();

        if (s.contains("_")) {
            s = s.replaceAll("_", " ");
            s = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
            s = s.substring(0, s.indexOf(" ") + 1) + s.substring(s.indexOf(" ") + 1, s.indexOf(" ") + 2).toUpperCase() + s.substring(s.indexOf(" ") + 2);
        } else {
            s = s.toLowerCase();
            s = s.substring(0, 1).toUpperCase() + s.substring(1);
        }

        return s;
    }

    private static Set<TechType> setOf(TechType... values) {
        return new HashSet<>(Arrays.asList(values));
    }
    //</editor-fold>

    static {
        //convert the set to a enum set so that it is consistent
        for (TechType v : values()) {
            if (v.prerequisites == null) {
                v.prerequisites = EnumSet.noneOf(TechType.class);
            } else {
                v.prerequisites = EnumSet.copyOf(v.prerequisites);
            }
        }
    }
}
