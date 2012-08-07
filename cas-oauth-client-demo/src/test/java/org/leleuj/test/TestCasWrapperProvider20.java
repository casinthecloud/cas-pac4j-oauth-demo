package org.leleuj.test;

import java.util.Map;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.support.oauth.profile.CasWrapperProfile;
import org.scribe.up.profile.ProfileHelper;
import org.scribe.up.profile.UserProfile;
import org.scribe.up.provider.OAuthProvider;
import org.scribe.up.session.UserSession;
import org.scribe.up.test.provider.impl.TestProvider;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class TestCasWrapperProvider20 extends TestProvider {
    
    @Override
    protected OAuthProvider getProvider() {
        return null;
    }
    
    @Override
    protected HtmlPage getAuhtorizationPage(WebClient webClient, OAuthProvider provider, UserSession session)
        throws Exception {
        return CasHelper.getAuhtorizationPage(webClient, "caswrapperAuthorizationUrl");
    }
    
    @Override
    protected String getCallbackUrl(HtmlPage authorizationPage) throws Exception {
        HtmlForm form = authorizationPage.getForms().get(0);
        HtmlTextInput login = form.getInputByName("username");
        login.setValueAttribute("leleuj");
        HtmlPasswordInput passwd = form.getInputByName("password");
        passwd.setValueAttribute("leleuj");
        HtmlSubmitInput submit = form.getInputByName("submit");
        HtmlPage confirmPage = submit.click();
        HtmlAnchor allowLink = confirmPage.getAnchorByName("allow");
        HtmlPage callbackPage = allowLink.click();
        String callbackUrl = callbackPage.getUrl().toString();
        logger.debug("callbackUrl : {}", callbackUrl);
        return callbackUrl;
    }
    
    @Override
    protected UserProfile getProfile(OAuthProvider provider, UserSession session, String callbackUrl) {
        Assertion assertion = CasHelper.getProfile(callbackUrl);
        AttributePrincipal principal = assertion.getPrincipal();
        String id = principal.getName();
        Map<String, Object> attributes = principal.getAttributes();
        return new UserProfile(id, attributes);
    }
    
    @Override
    protected void verifyProfile(UserProfile userProfile) {
        Map<String, Object> attributes = userProfile.getAttributes();
        assertTrue(ProfileHelper.isTypedIdOf(userProfile.getId(), CasWrapperProfile.class));
        assertEquals("uid", attributes.get("uid"));
        assertEquals("eduPersonAffiliation", attributes.get("eduPersonAffiliation"));
        assertEquals("groupMembership", attributes.get("groupMembership"));
        assertEquals(4, attributes.size());
    }
}
