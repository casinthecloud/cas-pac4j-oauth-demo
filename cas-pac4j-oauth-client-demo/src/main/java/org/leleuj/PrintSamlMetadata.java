package org.leleuj;

import org.pac4j.saml.client.SAML2Client;
import org.pac4j.saml.client.SAML2ClientConfiguration;

import java.io.File;

public class PrintSamlMetadata {

    public static void main(final String[] args) {
        final SAML2Client client = getClient();
        client.init();

        // generate pac4j SAML2 Service Provider metadata to import on Identity Provider side
        final String spMetadata = client.getServiceProviderMetadataResolver().getMetadata();
        System.out.println(spMetadata);
    }

    private static SAML2Client getClient() {

        final SAML2ClientConfiguration cfg =
                new SAML2ClientConfiguration("resource:samlKeystore.jks",
                        "pac4j-demo-passwd",
                        "pac4j-demo-passwd",
                        "resource:testshib-providers.xml");
        cfg.setMaximumAuthenticationLifetime(3600);
        cfg.setServiceProviderEntityId("urn:mace:saml:pac4j.org");
        cfg.setServiceProviderMetadataPath(new File("target", "sp-metadata.xml").getAbsolutePath());

        final SAML2Client saml2Client = new SAML2Client(cfg);
        saml2Client.setCallbackUrl("http://localhost:8080/cas/login?client_name=Saml2Client");
        return saml2Client;
    }
}
