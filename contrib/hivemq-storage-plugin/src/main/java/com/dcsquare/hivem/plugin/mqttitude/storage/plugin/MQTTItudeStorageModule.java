package com.dcsquare.hivem.plugin.mqttitude.storage.plugin;

import com.dcsquare.hivemq.spi.HiveMQPluginModule;
import com.dcsquare.hivemq.spi.PluginEntryPoint;
import com.dcsquare.hivemq.spi.plugin.meta.Information;
import com.google.inject.Provider;
import org.apache.commons.configuration.AbstractConfiguration;

import static com.dcsquare.hivemq.spi.config.Configurations.noConfigurationNeeded;


@Information(name = "HiveMQ MQTTitude Storage plugin", author = "Dominik Obermaier", version = "1.0-SNAPSHOT")
public class MQTTItudeStorageModule extends HiveMQPluginModule {


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
}
