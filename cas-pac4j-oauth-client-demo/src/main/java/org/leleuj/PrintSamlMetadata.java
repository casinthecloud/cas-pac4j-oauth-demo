package org.leleuj;

import org.pac4j.saml.client.Saml2Client;

public class PrintSamlMetadata {

    public static void main(String[] args) {
        Saml2Client client = new Saml2Client();
        client.setCallbackUrl("http://localhost:8080/cas/login?client_name=Saml2Client");
        // configure keystore
        client.setKeystorePath("resource:samlKeystore.jks");
        client.setKeystorePassword("pac4j-demo-passwd");
        client.setPrivateKeyPassword("pac4j-demo-passwd");
        // configure a file containing the Identity Provider metadata 
        client.setIdpMetadataPath("resource:testshib-providers.xml");

        // generate pac4j SAML2 Service Provider metadata to import on Identity Provider side
        String spMetadata = client.printClientMetadata();
        System.out.println(spMetadata);
    }
}
