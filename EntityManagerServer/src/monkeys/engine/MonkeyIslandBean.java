package monkeys.engine;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class MonkeyIslandBean
 */
@Stateful
public class MonkeyIslandBean implements RemoteMonkeyIsland {
	@PersistenceContext(unitName="MonkeysDS")
	EntityManager manager;
    /**
     * Default constructor. 
     */
    public MonkeyIslandBean() {
    }

}
