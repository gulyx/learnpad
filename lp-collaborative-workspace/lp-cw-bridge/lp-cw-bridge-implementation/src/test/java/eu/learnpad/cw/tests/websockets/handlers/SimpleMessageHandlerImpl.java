package eu.learnpad.cw.tests.websockets.handlers;

/**
 * Interface for a simple message handler.
 * 
 * @author Gulyx
 */
public class SimpleMessageHandlerImpl implements SimpleMessageHandler{
    Runnable ackThread;
	
    public SimpleMessageHandlerImpl(Runnable ackThread){
    	this.ackThread = ackThread;
    }
    
	public void handleMessage(String message) {
        System.out.println(message);
//        this.ackThread.notify();
    }

}
