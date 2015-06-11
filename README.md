# AAF Shibboleth Extensions

Shibboleth extension for generating auEduPersonSharedToken.

# Deployment

## 1. Configure resolvers

in `$IDP_HOME/conf/attribute-resolver.xml`:

Import the definition
```
xsi:schemaLocation="...
                    urn:mace:aaf.edu.au:shibboleth:2.0:resolver:dc classpath:/schema/aaf-shib-ext-dc.xsd
```

Define the `DataConnector`
```
<resolver:DataConnector xsi:type="SharedToken" xmlns="urn:mace:aaf.edu.au:shibboleth:2.0:resolver:dc"
                    id="sharedToken"
                    sourceAttributeId="uniqueIdentifier"
                    salt="Ez8m1HDSLBxu0JNcPEywmOpy+apq4Niw9kEMmAyWbhJqcfAb">
                    <resolver:Dependency ref="..." />
</resolver:DataConnector>
``` 

Attributes (all mandatory):

- id: the unique identifier for the data connector.
- sourceAttributeID: used for computing the sharedToken — ideally a unique identifier that never changes.
- salt: a string of random data, used when computing sharedToken. Must be at least 16 characters. N.B. Once set, 
this value **must never change**. Please keep a copy of this value. This value can be generated with openssl:
                                                      
```
openssl rand -base64 36 2>/dev/null
```

## 2. Configure logging

Use the pattern: `"au.edu.aaf.shibext"` in your logging configuration to enable logging.

For example, Shibboleth's `$IDP_HOME/conf/logback.xml` can use the configuration:
```
    <logger name="au.edu.aaf.shibext" level="DEBUG"/>
```
Unless specified, the log information will appear in `$IDP_HOME/logs/idp-process.log`.

## 3. Installing the library

1. Copy the jar file to `$IDP_HOME/edit-webapp/WEB-INF/lib/`
2. Re-run the installer `sh $IDP_HOME/bin/build.sh`
3. Restart the app server

