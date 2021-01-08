package monkeys.engine;

import javax.ejb.Remote;

@Remote
public interface RemoteMonkeyIsland {
	public void createMap();
	public void move(String id, String move);
}
