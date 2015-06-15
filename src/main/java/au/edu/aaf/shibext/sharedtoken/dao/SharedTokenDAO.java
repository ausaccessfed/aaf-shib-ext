package au.edu.aaf.shibext.sharedtoken.dao;

/**
 * Defines contract to retrieve and store SharedToken values.
 *
 * @author rianniello
 */
public interface SharedTokenDAO {
    /**
     * Retrieves the SharedToken corresponding to the uid.
     *
     * @param uid The user identifier (primary key)
     * @return The SharedToken value
     */
    String getSharedToken(String uid);
}
