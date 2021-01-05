package monkeys.engine;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class MonkeyIslandBean
 */
@Stateless
@LocalBean
public class MonkeyIslandBean implements RemoteMonkeyIsland {

    /**
     * Default constructor. 
     */
    public MonkeyIslandBean() {
        // TODO Auto-generated constructor stub
    }

}
