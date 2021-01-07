package monkeys.manager;

import javax.ejb.Stateful;

/**
 * Session Bean implementation class PlayerManagerBean
 */
@Stateful
public class PlayerManagerBean implements RemotePlayerManager {
	
    /**
     * Default constructor. 
     */
    public PlayerManagerBean() {
    }

	@Override
	public void join(String id) {
		System.out.println("You've successfully joined the game " + id);
	}

	@Override
	public void quit() {
		System.out.println("User disconnected from the game !");
	}

	@Override
	public void move(String move) {
		// TODO Auto-generated method stub
		// TODO Appel à l'application
	}

}
