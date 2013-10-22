package com.dcsquare.hivem.plugin.mqttitude.storage.plugin;

import com.dcsquare.hivemq.spi.HiveMQPluginModule;
import com.dcsquare.hivemq.spi.PluginEntryPoint;
import com.dcsquare.hivemq.spi.plugin.meta.Information;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.BoneCPDataSource;
import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import static com.dcsquare.hivemq.spi.config.Configurations.newConfigurationProvider;
import static com.dcsquare.hivemq.spi.config.Configurations.newReloadablePropertiesConfiguration;


@Information(name = "HiveMQ MQTTitude Storage plugin", author = "Dominik Obermaier", version = "1.0-SNAPSHOT")
public class MQTTItudeStorageModule extends HiveMQPluginModule {

    private static Logger log = LoggerFactory.getLogger(MQTTItudeStorageModule.class);

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.error("MySQL Driver class not found", e);
        }
    }


    @Override
    public Provider<Iterable<? extends AbstractConfiguration>> getConfigurations() {
        //We should use a config file here.
        return newConfigurationProvider(newReloadablePropertiesConfiguration("configuration.properties", 5, TimeUnit.MINUTES));

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
    public BoneCP provideConnectionPool(final Configuration configuration) {

        final BoneCPConfig boneCPConfig = createConnectionPoolConfig(configuration);

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

    @Provides
    public BoneCPDataSource provideBoneCPDatasource(Configuration configuration) {
        return new BoneCPDataSource(createConnectionPoolConfig(configuration));
    }

    private BoneCPConfig createConnectionPoolConfig(final Configuration configuration) {
        final BoneCPConfig boneCPConfig = new BoneCPConfig();
        boneCPConfig.setUsername(configuration.getString("db.username"));
        boneCPConfig.setPassword(configuration.getString("db.password"));

        final String host = configuration.getString("db.host");
        final String port = configuration.getString("db.port");
        final String dbName = configuration.getString("db.name");
        final String jdbcUrl = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
        boneCPConfig.setJdbcUrl(jdbcUrl);
        return boneCPConfig;
    }

}
