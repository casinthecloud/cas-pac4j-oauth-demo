<p align="center">
  <img src="https://pac4j.github.io/pac4j/img/logo-cas.png" width="300" />
</p>

This *cas-pac4j-oauth-demo* project is a web demo for the **CAS server** to test **authentication delegation** for the OAuth, OpendID, CAS, SAML and OpenID Connect protocols. It's composed of two modules (web applications):

- the *cas-pac4j-oauth-client-demo* module is a CAS server which uses the `cas-server-support-pac4j-webflow` module to delegate authentication to Facebook, Twitter, another CAS server... See [https://apereo.github.io/cas/5.0.x/integration/Delegate-Authentication.html](https://apereo.github.io/cas/5.0.x/integration/Delegate-Authentication.html)
- the *cas-pac4j-oauth-server-demo* module is a CAS server which uses the `cas-server-support-oauth` module to act as an OAuth server: [https://apereo.github.io/cas/5.0.x/installation/OAuth-OpenId-Authentication.html](https://apereo.github.io/cas/5.0.x/installation/OAuth-OpenId-Authentication.html).

## Start & test

To start quickly, build the project:

```shell
cd cas-pac4j-oauth-demo
mvn clean install
```

and launch the two web applications:

```shell
java -jar cas-pac4j-oauth-client-demo/target/cas.war
```

and

```shell
java -jar cas-pac4j-oauth-server-demo/target/cas2.war
```

To test:

- call the [http://localhost:8080/cas](http://localhost:8080/cas) url and click on the appropriate login link
- authenticate at your favorite provider (Facebook, Twitter...) or at the OAuth wrapped CAS server (`jleleu/jleleu` or `leleuj/leleuj`, url : _http://localhost:8081/cas2)
- be redirected to the first CAS server and successfully authenticated.
