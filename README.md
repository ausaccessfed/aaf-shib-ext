# AAF Shibboleth Extensions

Shibboleth extension for shared token and targeted id.

# Deployment
## Logging

Use the pattern: `"au.edu.aaf.shibext"` in your logging configuration to enable logging.

For example, Shibboleth's `$IDP_HOME/conf/logback.xml` can use the configuration:
```
    <logger name="au.edu.aaf.shibext" level="DEBUG"/>
```
Unless specified, the log information will appear in `$IDP_HOME/logs/idp-process.log`.

# Configuration

in `$IDP_HOME/conf/attribute-resolver.xml`:
```
<resolver:DataConnector xsi:type="SharedToken" xmlns="urn:mace:aaf.edu.au:shibboleth:2.0:resolver:dc"
                    id="sharedToken"
                    sourceAttributeId="commonName"
                    idpIdentifier="https://shib3.aaf.dev.edu.au/idp/shibboleth"
                    salt="Ez8m1HDSLBxu0JNcPEywmOpy+apq4Niw9kEMmAyWbhJqcfAb">
                    <resolver:Dependency ref="myLDAP" />
</resolver:DataConnector>


```
N.B. The fields `sourceAttributeID`, `idpIdentifier` and `salt` are mandatory.

# Developer notes
## Building
To build:
```$ gradle clean build```


