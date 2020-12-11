package monkeys;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;

/**
 * Session Bean implementation class PlayerManagerBean
 */
@Stateful
@LocalBean
public class PlayerManagerBean implements PlayerManagerBeanRemote {

    /**
     * Default constructor. 
     */
    public PlayerManagerBean() {
        // TODO Auto-generated constructor stub
    }

}
