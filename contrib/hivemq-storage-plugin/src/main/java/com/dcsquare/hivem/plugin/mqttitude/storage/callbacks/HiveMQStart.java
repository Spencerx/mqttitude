package com.dcsquare.hivem.plugin.mqttitude.storage.callbacks;

import com.dcsquare.hivemq.spi.callback.CallbackPriority;
import com.dcsquare.hivemq.spi.callback.events.broker.OnBrokerStart;
import com.dcsquare.hivemq.spi.callback.exception.BrokerUnableToStartException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HiveMQStart implements OnBrokerStart {

    private static final Logger log = LoggerFactory.getLogger(HiveMQStart.class);


    @Override
    public void onBrokerStart() throws BrokerUnableToStartException {
        log.info("HiveMQ is starting");
    }

    @Override
    public int priority() {
        return CallbackPriority.MEDIUM;
    }
}
