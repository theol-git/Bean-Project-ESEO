package monkeys.manager;

import javax.annotation.Resource;
import javax.ejb.Stateful;

import monkeys.communication.RemoteCommunication;
import monkeys.engine.RemoteMonkeyIsland;

/**
 * Session Bean implementation class PlayerManagerBean
 */
@Stateful
public class PlayerManagerBean implements RemotePlayerManager {
	
	@Resource(name="ejb:/CommunicationServer/CommunicationBean!monkeys.communication.RemoteCommunication")
	RemoteCommunication comm;
	@Resource(name="ejb:/EntityManagerServer/MonkeyIslandBean!monkeys.engine.RemoteMonkeyIsland?stateful")
	RemoteMonkeyIsland rMI;
    /**
     * Default constructor. 
     */
    public PlayerManagerBean() {
    }

	@Override
	public void join(String id) {
		System.out.println("You've successfully joined the game, player " + id);
		addPlayer(id);
	}

	@Override
	public void quit(String id) {
		System.out.println("User " + id + " disconnected from the game !");
		rMI.removePlayer(id);
	}

	@Override
	public void move(String id, String move) {
		System.out.println("Player " + id + " is moving !");
		rMI.moveResquest(id, move);
	}
	
	private void addPlayer(String id) {
		rMI.createMap();
		// TODO add player rMI
	}

}
