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

# Developer notes
## Building
To build:
```$ gradle clean build```


