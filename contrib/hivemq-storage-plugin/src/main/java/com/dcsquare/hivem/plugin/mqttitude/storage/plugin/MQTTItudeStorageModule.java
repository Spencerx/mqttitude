package com.dcsquare.hivem.plugin.mqttitude.storage.plugin;

import com.dcsquare.hivemq.spi.HiveMQPluginModule;
import com.dcsquare.hivemq.spi.PluginEntryPoint;
import com.dcsquare.hivemq.spi.plugin.meta.Information;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import org.apache.commons.configuration.AbstractConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.sql.SQLException;

import static com.dcsquare.hivemq.spi.config.Configurations.noConfigurationNeeded;


@Information(name = "HiveMQ MQTTitude Storage plugin", author = "Dominik Obermaier", version = "1.0-SNAPSHOT")
public class MQTTItudeStorageModule extends HiveMQPluginModule {

    private static Logger log = LoggerFactory.getLogger(MQTTItudeStorageModule.class);


    @Override
    public Provider<Iterable<? extends AbstractConfiguration>> getConfigurations() {
        //We should use a config file here.
        return noConfigurationNeeded();
    }

    @Override
    protected void configurePlugin() {

    }


    @Override
    protected Class<? extends PluginEntryPoint> entryPointClass() {
        return MQTTitudeStoragePlugin.class;
    }


    @Provides
    @Singleton
    @Nullable
    public BoneCP provideConnectionPool() {

        final BoneCPConfig boneCPConfig = new BoneCPConfig();

        final BoneCP boneCP;
        try {
            boneCP = new BoneCP(boneCPConfig);
            return boneCP;
        } catch (SQLException e) {
            //We are checking for the database in a Broker start callback
            log.debug("Could not connect to the database", e);
        }
        return null;
    }
}
