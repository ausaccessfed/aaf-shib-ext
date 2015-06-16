package au.edu.aaf.shibext.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import javax.naming.NamingException;

/**
 * Configuration for tests requiring an in-memory database.
 */
@Configuration
public class EmbeddedDataSourceConfig {

    /**
     * Used for lookups in JNDI context.
     */
    public static final String DATASOURCE_ID = "jdbc/DS_idp_admin";

    /**
     * Creates an in-memory database and registers the datasource in JNDI.
     *
     * @return EmbeddedDatabase instance
     * @throws NamingException
     */
    @Bean(destroyMethod = "shutdown")
    public EmbeddedDatabase dataSource() throws NamingException {
        EmbeddedDatabase dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("schema.sql")
                .addScript("seed.sql")
                .build();
        SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
        builder.bind(DATASOURCE_ID, dataSource);
        builder.activate();
        return dataSource;
    }
}
