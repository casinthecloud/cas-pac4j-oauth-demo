## What is this project?

This *cas-pac4j-oauth-demo* project has been created to test the authentication delegation in the CAS server.

## Build & test

Build the project:

```shell
cd cas-pac4j-oauth-demo
mvn clean package
```

And run the built WAR (`cas.war`) as a JAR (embedded Tomcat): it will be available on `http://localhost:8080/cas`.

Use `jleleu`/`jleleu` or `leleuj`/`leleuj` to log in.

Authorized applications match the following pattern: `^http://localhost:.*`.
