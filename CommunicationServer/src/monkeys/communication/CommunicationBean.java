package monkeys.communication;

import java.io.Serializable;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Topic;

import monkeys.engine.RemoteMonkeyIsland;
import monkeys.model.Island;
import monkeys.model.Monkey;
import monkeys.model.Pirate;
import monkeys.model.RumBottle;
import monkeys.model.Treasure;

/**
 * Session Bean implementation class CommunicationBean
 */
@Stateless
public class CommunicationBean implements RemoteCommunication {
	
	@Inject
	private JMSContext context;
	
	@Resource(mappedName = "java:jboss/exported/jms/topic/monkeys")
    private Topic topic;
	
	@Resource(name="ejb:/EntityManagerServer/MonkeyIslandBean!monkeys.engine.RemoteMonkeyIsland?stateful")
	RemoteMonkeyIsland rMI;
	
    /**
     * Default constructor. 
     */
    public CommunicationBean() {
    }

	@Override
	public void sendIsland(Island map) {
		System.out.println("Sending map....");
		sendObjectMessage(map, "map");
	}

	@Override
	public void sendMonkeys(Monkey[] monkeys) {
		System.out.println("Sending monkeys....");
		sendObjectMessage(monkeys, "monkeys");
	}

	@Override
	public void sendTreasure(Treasure treasure) {
		System.out.println("Sending treasure....");
		sendObjectMessage(treasure, "treasure");
	}

	@Override
	public void sendBottles(RumBottle[] bottles, String[] bottleIds) {
		System.out.println("Sending rum bottles....");
		HashMap<String, RumBottle> mappedBottles = new HashMap<>();
		for (int i = 0; i < bottles.length; i++) {
			mappedBottles.put(bottleIds[i], bottles[i]);
		}
		sendObjectMessage(mappedBottles, "bottles");
	}

	@Override
	public void sendPirates(Pirate[] pirates, String[] pirateIds) {
		System.out.println("Sending pirates....");
		HashMap<String, Pirate> mappedPirates = new HashMap<>();
		for (int i = 0; i < pirates.length; i++) {
			mappedPirates.put(pirateIds[i], pirates[i]);
		}
		sendObjectMessage(mappedPirates, "pirates");
	}

	@Override
	public void updatePirate(Pirate pirate, String id) {
		System.out.println("Updating pirate...");
		sendObjectMessageWithId(pirate, id, "updatePirate");
	}
	
	@Override
	public void updateBottle(RumBottle bottle, String id) {
		System.out.println("Updating bottle...");
		sendObjectMessageWithId(bottle, id, "updateBottle");
	}
	
	private void sendObjectMessageWithId(Serializable object, String id, String type) {
    	ObjectMessage message = context.createObjectMessage();
    	try {
    		message.setStringProperty("id", id);
    		message.setJMSType(type);
    		message.setObject(object);
		} catch (JMSException e) {
			e.printStackTrace();
		}
    	context.createProducer().send(topic, message);
    }
	
	private void sendObjectMessage(Serializable object, String type) {
    	ObjectMessage message = context.createObjectMessage();
    	try {
    		message.setJMSType(type);
    		message.setObject(object);
		} catch (JMSException e) {
			e.printStackTrace();
		}
    	context.createProducer().send(topic, message);
    }

}
