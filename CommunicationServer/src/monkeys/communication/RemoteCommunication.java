package monkeys.communication;

import javax.ejb.Remote;

import monkeys.model.Island;
import monkeys.model.Monkey;
import monkeys.model.Pirate;
import monkeys.model.RumBottle;
import monkeys.model.Treasure;

@Remote
public interface RemoteCommunication {
	public void sendIsland(Island map);
	public void sendMonkeys(Monkey[] monkeys);
	public void sendTreasure(Treasure treasure);
	public void sendBottles(RumBottle[] bottles, String[] bottleIds);
	public void sendPirates(Pirate[] pirates, String[] piratesIds);
	public void updateBottle(RumBottle bottle, String id);
	public void updatePirate(Pirate pirate, String id);
}
