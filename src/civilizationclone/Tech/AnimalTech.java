
package civilizationclone.Tech;

import java.util.EnumSet;
import java.util.Set;

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
