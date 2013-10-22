package com.dcsquare.hivem.plugin.mqttitude.storage.callbacks;

import com.dcsquare.hivemq.spi.callback.CallbackPriority;
import com.dcsquare.hivemq.spi.callback.events.broker.OnBrokerStart;
import com.dcsquare.hivemq.spi.callback.exception.BrokerUnableToStartException;
import com.googlecode.flyway.core.Flyway;
import com.googlecode.flyway.core.api.FlywayException;
import com.jolbox.bonecp.BoneCPDataSource;

import javax.inject.Inject;

/**
 * @author Dominik Obermaier
 */
public class DBMigration implements OnBrokerStart {

    private final BoneCPDataSource boneCPDataSource;

    @Inject
    public DBMigration(BoneCPDataSource boneCPDataSource) {
        this.boneCPDataSource = boneCPDataSource;
    }

    @Override
    public void onBrokerStart() throws BrokerUnableToStartException {

        //We have to set the context classloader correctly, otherwise
        //Flyway won't find the migrations
        Thread.currentThread().setContextClassLoader(DBMigration.class.getClassLoader());
        try {
            final Flyway flyway = new Flyway();
            flyway.setDataSource(boneCPDataSource);
            flyway.setInitOnMigrate(true);
            flyway.migrate();
        } catch (FlywayException e) {
            throw new BrokerUnableToStartException(e);
        }
    }

    @Override
    public int priority() {
        return CallbackPriority.VERY_HIGH;
    }
}
