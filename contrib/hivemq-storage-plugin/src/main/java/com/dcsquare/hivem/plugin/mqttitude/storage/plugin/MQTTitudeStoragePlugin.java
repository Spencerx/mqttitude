package com.dcsquare.hivem.plugin.mqttitude.storage.plugin;

import com.dcsquare.hivem.plugin.mqttitude.storage.callbacks.PublishReceived;
import com.dcsquare.hivem.plugin.mqttitude.storage.callbacks.SQLStartupCheck;
import com.dcsquare.hivemq.spi.PluginEntryPoint;
import com.dcsquare.hivemq.spi.callback.registry.CallbackRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

public class MQTTitudeStoragePlugin extends PluginEntryPoint {

    private static final Logger log = LoggerFactory.getLogger(MQTTitudeStoragePlugin.class);
    private final PublishReceived publishReceived;
    private final SQLStartupCheck sqlStartupCheck;


    @Inject
    public MQTTitudeStoragePlugin(final PublishReceived publishReceived,
                                  final SQLStartupCheck sqlStartupCheck) {
        this.publishReceived = publishReceived;
        this.sqlStartupCheck = sqlStartupCheck;
    }

    @PostConstruct
    public void postConstruct() {

        final CallbackRegistry callbackRegistry = getCallbackRegistry();

        callbackRegistry.addCallback(sqlStartupCheck);
        callbackRegistry.addCallback(publishReceived);
    }
}
