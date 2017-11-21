
package civilizationclone.Tech;

import java.util.EnumSet;
import java.util.Set;

public class AgricultureTech implements Tech{

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
