package monkeys.communication;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class CommunicationBean
 */
@Stateless
@LocalBean
public class CommunicationBean implements RemoteCommunication {

    /**
     * Default constructor. 
     */
    public CommunicationBean() {
        // TODO Auto-generated constructor stub
    }

}
