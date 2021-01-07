package monkeys.manager;

import javax.annotation.Resource;
import javax.ejb.Stateful;

import monkeys.communication.RemoteCommunication;

/**
 * Session Bean implementation class PlayerManagerBean
 */
@Stateful
public class PlayerManagerBean implements RemotePlayerManager {
	
	@Resource(name="ejb:/CommunicationServer/CommunicationBean!monkeys.communication.RemoteCommunication")
	RemoteCommunication comm;
//	@Resource(name="ejb:/CommunicationServer/CommunicationBean!monkeys.communication.RemoteCommunication")
//	RemoteMonkeyIsland rMI;
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
		// TODO Remove player
	}

	@Override
	public void move(String move) {
		// TODO Auto-generated method stub
		// TODO Appel à l'application
	}
	
	private void addPlayer(String id) {
		//rMI.createMap();
		//comm.sendIsland(null,"myMI");
		// TODO add player rMI
	}

}
