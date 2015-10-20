<p align="center">
  <img src="https://pac4j.github.io/pac4j/img/logo-cas.png" width="300" />
</p>

This `cas-pac4j-oauth-demo` project is a web demo for the **CAS server** to test **authentication delegation** for the OAuth, OpendID, CAS, SAML and OpenID Connect protocols as well as **OAuth server support**. It's composed of two modules (web applications):

- the *cas-pac4j-oauth-client-demo* module is a CAS server which uses the `cas-server-support-pac4j` module to delegate authentication to Facebook, Twitter, another CAS server... See [http://jasig.github.io/cas/4.1.x/integration/Delegate-Authentication.html](http://jasig.github.io/cas/4.1.x/integration/Delegate-Authentication.html)
- the *cas-pac4j-oauth-server-demo* module is a CAS server which uses the `cas-server-support-oauth` module to act as an OAuth server: [http://jasig.github.io/cas/4.1.x/protocol/OAuth-Protocol.html](http://jasig.github.io/cas/4.1.x/protocol/OAuth-Protocol.html).

## Start & test

Build the project and launch the two web applications with jetty on http://localhost:8080:

    cd cas-pac4j-oauth-demo
    mvn clean package
    cd cas-pac4j-oauth-client-demo
    mvn jetty:run

To test,

- call the [http://localhost:8080/cas](http://localhost:8080/cas) url and click on the appropriate "Authenticate with xxx" link at the bottom of the login page
- authenticate at your favorite provider (Facebook, Twitter...) or at the OAuth wrapped CAS server (same password as login, url : _http://localhost:8080/cas2_)
- be redirected to the first CAS server and successfully authenticated.
