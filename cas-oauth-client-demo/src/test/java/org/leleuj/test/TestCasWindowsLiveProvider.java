package org.leleuj.test;

import org.scribe.up.profile.UserProfile;
import org.scribe.up.profile.windowslive.WindowsLiveProfile;
import org.scribe.up.provider.OAuthProvider;
import org.scribe.up.session.UserSession;
import org.scribe.up.test.provider.impl.TestWindowsLiveProvider;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public final class TestCasWindowsLiveProvider extends TestWindowsLiveProvider {
    
    @Override
    protected HtmlPage getAuhtorizationPage(WebClient webClient, OAuthProvider provider, UserSession session)
        throws Exception {
        return CasHelper.getAuhtorizationPage(webClient, "windowsLiveAuthorizationUrl",
                                              CasHelper.LOGIN_URL_DEV_WITH_SERVICE);
    }
    
    @Override
    protected UserProfile getProfile(OAuthProvider provider, UserSession session, String callbackUrl) {
        return CasHelper.getProfile(callbackUrl, WindowsLiveProfile.class);
    }
}
