package org.leleuj.test;

import org.scribe.up.profile.UserProfile;
import org.scribe.up.profile.google.GoogleProfile;
import org.scribe.up.provider.OAuthProvider;
import org.scribe.up.session.UserSession;
import org.scribe.up.test.provider.impl.TestGoogleProvider;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public final class TestCasGoogleProvider extends TestGoogleProvider {
    
    @Override
    protected HtmlPage getAuhtorizationPage(WebClient webClient, OAuthProvider provider, UserSession session)
        throws Exception {
        return CasHelper.getAuhtorizationPage(webClient, "googleAuthorizationUrl");
    }
    
    @Override
    protected UserProfile getProfile(OAuthProvider provider, UserSession session, String callbackUrl) {
        return CasHelper.getProfile(callbackUrl, GoogleProfile.class);
    }
    
}
