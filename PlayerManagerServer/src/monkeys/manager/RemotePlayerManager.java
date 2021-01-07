package monkeys.manager;

import javax.ejb.Remote;

@Remote
public interface RemotePlayerManager {
	
	public void join(String id);
	
	public void quit(String id);
	
	public void move(String move);	
}
