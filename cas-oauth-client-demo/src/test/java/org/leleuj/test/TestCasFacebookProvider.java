package org.leleuj.test;

import org.scribe.up.profile.UserProfile;
import org.scribe.up.profile.facebook.FacebookProfile;
import org.scribe.up.provider.OAuthProvider;
import org.scribe.up.session.UserSession;
import org.scribe.up.test.provider.impl.TestFacebookProvider;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public final class TestCasFacebookProvider extends TestFacebookProvider {
    
    @Override
    protected HtmlPage getAuhtorizationPage(WebClient webClient, OAuthProvider provider, UserSession session)
        throws Exception {
        return CasHelper.getAuhtorizationPage(webClient, "facebookAuthorizationUrl");
    }
    
    @Override
    protected UserProfile getProfile(OAuthProvider provider, UserSession session, String callbackUrl) {
        return CasHelper.getProfile(callbackUrl, FacebookProfile.class);
    }
}
