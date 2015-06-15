package au.edu.aaf.shibext.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import javax.naming.NamingException;

/**
 * Creates an in-memory database and registers the datasource in JNDI.
 */
@Configuration
public class EmbeddedDataSourceConfig {

    /**
     * Used for lookups in JNDI context.
     */
    public static final String DATASOURCE_ID = "jdbc/DS_idp_admin";

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
