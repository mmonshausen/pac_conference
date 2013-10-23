package com.prodyna.pac.mmonshausen.conference.service;

import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * MessageDrivenBean which writes received asynchronous queue messages into logfile
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@MessageDriven(mappedName="queue/log", activationConfig =  {
        @ActivationConfigProperty(propertyName = "destinationType",
                                  propertyValue = "javax.jms.Queue"),        
        @ActivationConfigProperty(propertyName = "destination",
        						  propertyValue = "queue/log")
    })
public class LoggingMDB  implements MessageListener {
	@Inject
	private Logger logger;

	@Override
	public void onMessage(Message msg) {
		if(msg instanceof TextMessage) {
			TextMessage txtMsg = (TextMessage) msg;
			try {
				logger.info(txtMsg.getText());
			} catch (JMSException e) {
				logger.warning("error during JMS message reading");
			}
		}
	}
}
