package monkeys.engine;

import javax.ejb.Remote;

@Remote
public interface RemoteMonkeyIsland {
	public void createMap();
	public void moveResquest(String id, String move);
	public void addPlayer(String id);
	public void removePlayer(String id);
}
