import java.util.Hashtable;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import guybrush.view.GameObserver;
import monkeys.manager.RemotePlayerManager;

public class MonkeyClient implements MessageListener, GameObserver {
	
	public static MonkeyClient instance;
	public RemotePlayerManager rPM;

	/* (non-Java-doc)
	 * @see java.lang.Object#Object()
	 */
	public MonkeyClient() {
		super();
	}
	
	public RemotePlayerManager getrPM() {
		return rPM;
	}
	
	public static void main(String[] args) throws NamingException {
		instance = new MonkeyClient();
		instance.lookup();
		instance.getrPM().join("123");
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
	public void onMessage(Message arg0) {
		// TODO Auto-generated method stub
		
	}

}