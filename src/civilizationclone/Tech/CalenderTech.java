
package civilizationclone.Tech;

import java.util.EnumSet;
import java.util.Set;

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
