## What is this project?

This project *cas-pac4j-oauth-demo* has been created to test the OAuth/OpendID/CAS support in *CAS server version >= 4.0.0*. It's composed of two modules:

- the *cas-pac4j-oauth-client-demo* module is a CAS server which uses the OAuth/OpenID/CAS client mode : it acts as a client to delegate authentication to Facebook, Twitter... : [https://wiki.jasig.org/display/CASUM/OAuth+client+support+for+CAS+server+version+%3E%3D+4.0.0](https://wiki.jasig.org/display/CASUM/OAuth+client+support+for+CAS+server+version+%3E%3D+4.0.0)
- the *cas-pac4j-oauth-server-demo* module is a CAS server which uses the OAuth server mode : it plays the role of an OAuth server : [https://wiki.jasig.org/display/CASUM/OAuth+server+support](https://wiki.jasig.org/display/CASUM/OAuth+server+support).

## Deploy

Deploy manually these two web applications in your favorite web applications server:

- cas-pac4j-oauth-client-demo on /cas
- cas-pac4j-oauth-server-demo on /cas2.

For example, with Tomcat (in your *server.xml* file):

    <Context docBase="/usr/local/myapps/cas-pac4j-oauth-demo/cas-pac4j-oauth-client-demo/target/cas-pac4j-oauth-client-demo" path="/cas" />
    <Context docBase="/usr/local/myapps/cas-pac4j-oauth-demo/cas-pac4j-oauth-server-demo/target/cas-pac4j-oauth-server-demo" path="/cas2" />

## Test

To test,

- call the [http://localhost:8080/cas](http://localhost:8080/cas) url and click on "Authenticate with ..." (on the CAS server configured in OAuth client mode)
- authenticate at your favorite provider (Facebook, Twitter...) or at the OAuth wrapped CAS server (same password as login, url : _http://localhost:8080/cas2_)
- be redirected to the first CAS server and successfully authenticated.
