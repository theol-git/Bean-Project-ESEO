package monkeys.engine;

import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import monkeys.communication.RemoteCommunication;
import monkeys.model.Island;
import monkeys.model.Monkey;
import monkeys.model.Pirate;
import monkeys.model.RumBottle;
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
		RumBottle bottle1 = new RumBottle(map, 3, 1, true);
		RumBottle bottle2 = new RumBottle(map, 1, 3, true);
		manager.persist(bottle1);
		manager.persist(bottle2);
		RumBottle[] bottles = {bottle1, bottle2};
		String[] bottleIds = {String.valueOf(bottle1.getId()), String.valueOf(bottle2.getId())};
		comm.sendBottles(bottles, bottleIds);
		Monkey monkeyE = new Monkey(map, 2, 1, Monkey.Type.ERRATIC);
		Monkey monkeyH = new Monkey(map, 2, 3, Monkey.Type.HUNTER);
		manager.persist(monkeyE);
		manager.persist(monkeyH);
		Monkey[] monkeys = {monkeyE, monkeyH};
		comm.sendMonkeys(monkeys);
		Pirate p = new Pirate(3, 3);
		Pirate[] pirates = {p};
		String[] piratesIds = {String.valueOf(p.getId())};
		comm.sendPirates(pirates, piratesIds);
	}
	
	
	@Override
	public void moveResquest(String id, String move) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPlayer(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removePlayer(String id) {
		// TODO Auto-generated method stub
		
	}

}
