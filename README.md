# Simple Config Server Client demo

This is a simple demonstration of using a Spring Cloud config server,
and particularly a Pivotal Application Service (e.g. PWS) config server
service.

The config server service instance was created on PWS using the
following command:

```bash
cf create-service p-config-server trial config-service \
 -c '{"git": {"uri": "https://github.com/ndwinton/tracker-config.git", "label": "master"}}'
```

There are two endpoints exposed explicitly by the application code.
The `/log` endpoint shows the value of the `logging.level.root` property
which is set in the configuration loaded from the config server, and
also the current effective logging level.
The controller for this endpoint is annotated with `@RefreshScope` so
can dynamincally reload its configuration.
The `/unrefreshed` endpoint contains identical code apart from the
annotation.

After starting application, both the controllers will show the same results,
for example:
```text
logging.level.root = INFO, effective = UNKNOWN
```

However, if a change is made to the properties file stored in the 
Git repository, and a POST request is made to `/actuator/refresh` then
the results will be different.
Note, however, that even the "unrefreshed" results will change because
logging levels are set globally for the entire application.

If the `/actuator/loggers` endpoint is exposed the the built-in ability
to change log levels can be used, for example by posting the following
data to `/actuators/loggers/ROOT`:
```json
{"configuredLevel": "DEBUG"}
```
Changes made by this route have no audit trail and will not be persisted
across application restarts, unlike those using a config server.