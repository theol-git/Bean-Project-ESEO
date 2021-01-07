import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
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

public class MonkeyClient implements MessageListener, GameObserver {
	public TopicConnection connection;
	public Topic topic;
	public TopicConnectionFactory connectionFactory;
	public TopicSession session;
	
	public static MonkeyClient instance;
	public RemotePlayerManager rPM;
	public static Fenetre fenetre;

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
            // Cr�ation de la session
            session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            // Cr�ation du MessageConsummer
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyMove(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessage(Message message) {
		System.out.println("You've got a new message :)");
		try {
			switch(message.getJMSType()){
				case "map" :
					ObjectMessage sm = (ObjectMessage) message;
					Island map = (Island)sm.getObject();
					fenetre.creationCarte(map.getCells());
					fenetre.getEnergyView().setEnergieMax(10);
					fenetre.updateEnergieView(10);
					break;
				default: break;
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}