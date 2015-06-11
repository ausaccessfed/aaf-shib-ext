package au.edu.aaf.shibext.sharedtoken;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class AuEduPersonSharedTokenGeneratorTest {

    private static final String IDP_IDENTIFIER = "https://idp.aaf.dev.edu.au/idp/shibboleth";
    private static final String RESOLVED_SOURCE_ATTRIBUTE_ID = "Russell Ianniello";
    private static final String SALT = "Ez8m1HDSLBxu0JNcPEywmOpy+apq4Niw9kEMmAyWbhJqcfAb";

    private AuEduPersonSharedTokenGenerator auEduPersonSharedTokenGenerator;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        auEduPersonSharedTokenGenerator = new AuEduPersonSharedTokenGenerator();
    }

    @Test
    public void throwsExceptionIfResolvedSourceIdAttributeNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("resolvedSourceAttribute cannot be blank or null");
        auEduPersonSharedTokenGenerator.generate(null, IDP_IDENTIFIER, SALT);
    }

    @Test
    public void throwsExceptionIfResolvedSourceIdAttributeBlank() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("resolvedSourceAttribute cannot be blank or null");
        auEduPersonSharedTokenGenerator.generate(StringUtils.EMPTY, IDP_IDENTIFIER, SALT);
    }

    @Test
    public void throwsExceptionIfIdPIdentifierNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("idPIdentifier cannot be blank or null");
        auEduPersonSharedTokenGenerator.generate(RESOLVED_SOURCE_ATTRIBUTE_ID, null, SALT);
    }

    @Test
    public void throwsExceptionIfIdPIdentifierBlank() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("idPIdentifier cannot be blank or null");
        auEduPersonSharedTokenGenerator.generate(RESOLVED_SOURCE_ATTRIBUTE_ID, StringUtils.EMPTY, SALT);
    }

    @Test
    public void throwsExceptionIfSaltNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("salt cannot be blank or null");
        auEduPersonSharedTokenGenerator.generate(RESOLVED_SOURCE_ATTRIBUTE_ID, IDP_IDENTIFIER, null);
    }

    @Test
    public void throwsExceptionIfSaltBlank() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("salt cannot be blank or null");
        auEduPersonSharedTokenGenerator.generate(RESOLVED_SOURCE_ATTRIBUTE_ID, IDP_IDENTIFIER, StringUtils.EMPTY);
    }

    @Test
    public void throwsExceptionIfSaltIsSmallerThan16Chars() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("salt must be at least 16 characters");
        auEduPersonSharedTokenGenerator.generate(RESOLVED_SOURCE_ATTRIBUTE_ID, IDP_IDENTIFIER, "ABCD");
    }

    @Test
    public void generatesAuEduPersonSharedTokenAsExpected() {
        String auEduPersonSharedToken = auEduPersonSharedTokenGenerator.generate(RESOLVED_SOURCE_ATTRIBUTE_ID,
                IDP_IDENTIFIER, SALT);

        assertThat(auEduPersonSharedToken, is(equalTo("IpdpeFs-WlMqjaC8l-lO1_tJme8")));

    }
}
