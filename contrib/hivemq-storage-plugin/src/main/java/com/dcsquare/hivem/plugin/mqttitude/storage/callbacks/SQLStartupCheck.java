package com.dcsquare.hivem.plugin.mqttitude.storage.callbacks;

import com.dcsquare.hivemq.spi.callback.CallbackPriority;
import com.dcsquare.hivemq.spi.callback.events.broker.OnBrokerStart;
import com.dcsquare.hivemq.spi.callback.exception.BrokerUnableToStartException;
import com.google.common.base.Optional;
import com.jolbox.bonecp.BoneCP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.inject.Inject;


public class SQLStartupCheck implements OnBrokerStart {

    private static Logger log = LoggerFactory.getLogger(SQLStartupCheck.class);
    private final Optional<BoneCP> boneCP;

    @Inject
    public SQLStartupCheck(@Nullable BoneCP boneCP) {
        this.boneCP = Optional.fromNullable(boneCP);
    }

    @Override
    public void onBrokerStart() throws BrokerUnableToStartException {

        log.debug("Checking if MySQL database is available");

        if (!boneCP.isPresent()) {
            //We are preventing the broker to startup if the boneCP connection pool ist not available.
            //This means that the pool config is most likely invalid or the DB isn't available.
            throw new BrokerUnableToStartException("The Database is not available");
        }
    }


    @Override
    public int priority() {
        return CallbackPriority.CRITICAL;
    }
}