import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import guybrush.view.Fenetre;
import guybrush.view.GameObserver;
import monkeys.manager.RemotePlayerManager;
import monkeys.model.Island;
import monkeys.model.Monkey;
import monkeys.model.Pirate;
import monkeys.model.RumBottle;
import monkeys.model.Treasure;

public class MonkeyClient implements MessageListener, GameObserver {
	public TopicConnection connection;
	public Topic topic;
	public TopicConnectionFactory connectionFactory;
	public TopicSession session;
	
	public static MonkeyClient instance;
	public RemotePlayerManager rPM;
	public static Fenetre fenetre;
	
	private Pirate p;

	/* (non-Java-doc)
	 * @see java.lang.Object#Object()
	 */
	public MonkeyClient() {
		super();
	}
	
	public RemotePlayerManager getrPM() {
		return rPM;
	}
	
	public static void main(String[] args) throws NamingException, JMSException {
		instance = new MonkeyClient();
		// Creates the GUI
		fenetre = new Fenetre("Monkeys");
		fenetre.addObserver(instance);
		// Gets the components
		instance.lookup();
		instance.subscribeTopic();
		// Display GUI
		fenetre.setVisible(true);
		instance.getrPM().join(String.valueOf(instance.hashCode()));
	}
	
	public void subscribeTopic() throws JMSException, NamingException{
		InputStream is = Thread.currentThread().getContextClassLoader().
				getResourceAsStream("META-INF/jndi-topic-client.properties");
		Properties jndiProperties = new Properties();
		try {
			// Loading property file
			jndiProperties.load(is);
			jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
			jndiProperties.put(Context.PROVIDER_URL, "remote+http://localhost:8080");
			Context context = new InitialContext(jndiProperties);
			// Get instance of connectionFactoryURI
			connectionFactory = (TopicConnectionFactory) context.lookup(jndiProperties.get("connectionFactoryURI").toString());
            topic = (Topic) context.lookup(jndiProperties.get("topicURI").toString());
            
            connection = connectionFactory.createTopicConnection(jndiProperties.get("user").toString(), jndiProperties.get("password").toString());
            String id = String.valueOf(instance.hashCode());
            connection.setClientID(id);
            // Création de la session
            session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            // Création du MessageConsummer
            MessageConsumer mc = session.createDurableConsumer(topic, id);
            // Enregistrement du client en tant qu'observateur
            mc.setMessageListener(instance);
            // Start connection
            connection.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void lookup() throws NamingException {
		Hashtable<String, String> t = new Hashtable<>();
		t.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		t.put(Context.PROVIDER_URL, "remote+http://localhost:8080");
		
		Context context = new InitialContext(t);
		String url = "ejb:/PlayerManagerServer/PlayerManagerBean!monkeys.manager.RemotePlayerManager?stateful";
		rPM = (RemotePlayerManager) context.lookup(url);
	}

	@Override
	public void notifyDisconnect() {
		try {
			instance.getrPM().quit(String.valueOf(p.getId()));
			this.connection.close();
			System.exit(0);
		
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void notifyMove(int arg0, int arg1) {
		System.out.println("Moving " + arg0 + " " + arg1);
		instance.getrPM().move(String.valueOf(this.p.getId()), arg0 + " " + arg1);
	}

	@Override
	public void onMessage(Message message) {
		System.out.println("You've got a new message :)");
		try {
			ObjectMessage om = (ObjectMessage) message;
			switch(message.getJMSType()){
				case "map" :
					System.out.println("map");
					Island map = (Island)om.getObject();
					fenetre.creationCarte(map.getCells());
					fenetre.getEnergyView().setEnergieMax(10);
					fenetre.updateEnergieView(10);
					break;
				case "monkeys":
					System.out.println("monkeys");
					Monkey[] monkeys = (Monkey[])om.getObject();
					for (Monkey m : monkeys) {
						fenetre.creationEMonkey(m.getId(), m.getX(), m.getY());
					}
					break;
				case "treasure":
					System.out.println("treasure");
					Treasure treasure = (Treasure)om.getObject();
					fenetre.creationTresor(treasure.getX(), treasure.getY(), treasure.isVisible());
					break;
				case "bottles":
					System.out.println("bottles");
					HashMap<String, RumBottle> mappedBottles = (HashMap<String, RumBottle>)om.getObject();
					fenetre.removeRhums();
					for (RumBottle b : mappedBottles.values()) {
						fenetre.creationRhum(b.getX(), b.getY(), b.isVisible());
					}
					break;
				case "pirates":
					System.out.println("pirates");
					HashMap<String, Pirate> mappedPirates = (HashMap<String, Pirate>)om.getObject();
					for (Pirate p : mappedPirates.values()) {
						this.p = p;
						fenetre.ajoutPirate(p.getId(), p.getX(), p.getY(), null, 100);
					}
					break;
				case "updatePirate":
					System.out.println("update pirate");
					Pirate p = (Pirate)om.getObject();
					fenetre.suppressionPirate(p.getId());
					fenetre.ajoutPirate(p.getId(), p.getX(), p.getY(), null, 100);
					this.p = p;
					break;
				case "updateBotlle":
					System.out.println("update bottle");
					break;
				default: break;
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}