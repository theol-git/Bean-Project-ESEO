package monkeys.engine;

import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import monkeys.communication.RemoteCommunication;
import monkeys.model.Island;
import monkeys.model.Treasure;

/**
 * Session Bean implementation class MonkeyIslandBean
 */
@Stateful
public class MonkeyIslandBean implements RemoteMonkeyIsland {
	@PersistenceContext(unitName="MonkeysDS")
	EntityManager manager;
	
	@Resource(name="ejb:/CommunicationServer/CommunicationBean!monkeys.communication.RemoteCommunication")
	RemoteCommunication comm;
	
	private static final int MAP_WIDTH = 7;
	private static final int MAP_HEIGHT = 7;
    /**
     * Default constructor. 
     */
    public MonkeyIslandBean() {
    }
    
	@Override
	public void createMap() {
		Island map = new Island();
		int[][] cells = {{0,0,0,0,0},
						 {0,1,1,1,0},
						 {0,1,0,1,0},
						 {0,1,1,1,0},
						 {0,0,0,0,0}};
		map.setCells(cells);
		manager.persist(map);
		comm.sendIsland(map);
		Treasure treasure = new Treasure(map, 1, 1, true);
		manager.persist(treasure);
		comm.sendTreasure(treasure);
	}
	@Override
	public void move(String id, String move) {
		
	}

}
