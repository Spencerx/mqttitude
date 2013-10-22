package com.dcsquare.hivem.plugin.mqttitude.storage.plugin;

import com.dcsquare.hivem.plugin.mqttitude.storage.callbacks.DBMigration;
import com.dcsquare.hivem.plugin.mqttitude.storage.callbacks.PublishReceived;
import com.dcsquare.hivem.plugin.mqttitude.storage.callbacks.SQLStartupCheck;
import com.dcsquare.hivemq.spi.PluginEntryPoint;
import com.dcsquare.hivemq.spi.callback.registry.CallbackRegistry;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

public class MQTTitudeStoragePlugin extends PluginEntryPoint {

    private final PublishReceived publishReceived;
    private final SQLStartupCheck sqlStartupCheck;
    private final DBMigration dbMigration;


    @Inject
    public MQTTitudeStoragePlugin(final PublishReceived publishReceived,
                                  final SQLStartupCheck sqlStartupCheck,
                                  final DBMigration dbMigration) {
        this.publishReceived = publishReceived;
        this.sqlStartupCheck = sqlStartupCheck;
        this.dbMigration = dbMigration;
    }

    @PostConstruct
    public void postConstruct() {

        final CallbackRegistry callbackRegistry = getCallbackRegistry();

        callbackRegistry.addCallback(sqlStartupCheck);
        callbackRegistry.addCallback(publishReceived);
        callbackRegistry.addCallback(dbMigration);
    }
}
