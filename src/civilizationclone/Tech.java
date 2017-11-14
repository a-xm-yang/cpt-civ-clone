
package civilizationclone;


import java.util.EnumSet;
import java.util.Set;

enum TechType {
    NONE, AGRICULTURE, POTTERY, CALENDER, WRITING, ANIMAL, HORSERIDING;
}

public interface Tech {

    public Set getPrerequisites();

    public TechType getType();

}

class AgricultureTech implements Tech{

    private static TechType type = TechType.AGRICULTURE;
    private static Set<TechType> prerequisites = EnumSet.of(TechType.NONE);
        
    @Override
    public Set getPrerequisites() {
        return prerequisites;
    }

    @Override
    public TechType getType() {
        return type;
    }
    
}

class PotteryTech implements Tech{

    private static TechType type = TechType.POTTERY;
    private static Set<TechType> prerequisites = EnumSet.of(TechType.AGRICULTURE);
    
    @Override
    public Set getPrerequisites() {
        return prerequisites;
    }

    @Override
    public TechType getType() {
        return type;
    }
    
}

class CalenderTech implements Tech{

    private static TechType type = TechType.CALENDER;
    private static Set<TechType> prerequisites = EnumSet.of(TechType.POTTERY);
    
    @Override
    public Set getPrerequisites() {
        return prerequisites;
    }

    @Override
    public TechType getType() {
        return type;
    }
    
}

class AnimalTech implements Tech{

    private static TechType type = TechType.ANIMAL;
    private static Set<TechType> prerequisites = EnumSet.of(TechType.AGRICULTURE);
    
    @Override
    public Set getPrerequisites() {
        return prerequisites;
    }

    @Override
    public TechType getType() {
        return type;
    }
    
}