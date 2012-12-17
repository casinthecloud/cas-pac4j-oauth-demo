package org.leleuj.test;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.scribe.up.profile.OAuthProfile;
import org.scribe.up.profile.ProfileHelper;
import org.scribe.up.profile.UserProfile;
import org.scribe.up.profile.casoauthwrapper.CasOAuthWrapperProfile;
import org.scribe.up.provider.OAuthProvider;
import org.scribe.up.session.UserSession;
import org.scribe.up.test.provider.impl.TestProvider;
import org.scribe.up.test.util.CommonHelper;
import org.scribe.up.test.util.SingleUserSession;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class TestCasOAuthWrapperProvider extends TestProvider {
    
    @Override
    protected OAuthProvider getProvider() {
        return null;
    }
    
    @Override
    protected HtmlPage getAuhtorizationPage(final WebClient webClient, final OAuthProvider provider,
                                            final UserSession session) throws Exception {
        return CasHelper.getAuhtorizationPage(webClient, "caswrapperAuthorizationUrl");
    }
    
    public void testProvider() throws Exception {
        final OAuthProvider provider = getProvider();
        
        final SingleUserSession session = new SingleUserSession();
        final WebClient webClient = CommonHelper.newWebClient(isJavascriptEnabled());
        
        final HtmlPage authorizationPage = getAuhtorizationPage(webClient, provider, session);
        
        final String callbackUrl = getCallbackUrl(authorizationPage);
        
        final UserProfile profile = getProfile(provider, session, callbackUrl);
        
        OAuthProfile oauthProfile = (OAuthProfile) profile;
        assertTrue(StringUtils.isNotBlank(oauthProfile.getAccessToken()));
        verifyProfile(profile);
        
        final byte[] bytes = CommonHelper.serialize(profile);
        final UserProfile profile2 = (UserProfile) CommonHelper.unserialize(bytes);
        
        verifyProfile(profile2);
    }
    
    @Override
    protected String getCallbackUrl(final HtmlPage authorizationPage) throws Exception {
        final HtmlForm form = authorizationPage.getForms().get(0);
        final HtmlTextInput login = form.getInputByName("username");
        login.setValueAttribute("leleuj");
        final HtmlPasswordInput passwd = form.getInputByName("password");
        passwd.setValueAttribute("leleuj");
        final HtmlSubmitInput submit = form.getInputByName("submit");
        final HtmlPage confirmPage = submit.click();
        final HtmlAnchor allowLink = confirmPage.getAnchorByName("allow");
        final HtmlPage callbackPage = allowLink.click();
        final String callbackUrl = callbackPage.getUrl().toString();
        logger.debug("callbackUrl : {}", callbackUrl);
        return callbackUrl;
    }
    
    @Override
    protected UserProfile getProfile(final OAuthProvider provider, final UserSession session, final String callbackUrl) {
        final Assertion assertion = CasHelper.getProfile(callbackUrl);
        final AttributePrincipal principal = assertion.getPrincipal();
        final String id = principal.getName();
        final Map<String, Object> attributes = principal.getAttributes();
        final CasOAuthWrapperProfile profile = new CasOAuthWrapperProfile();
        profile.setId(id);
        profile.addAttributes(attributes);
        return profile;
    }
    
    @Override
    protected void verifyProfile(final UserProfile userProfile) {
        final Map<String, Object> attributes = userProfile.getAttributes();
        assertTrue(ProfileHelper.isTypedIdOf(userProfile.getTypedId(), CasOAuthWrapperProfile.class));
        assertEquals("uid", attributes.get("uid"));
        assertEquals("eduPersonAffiliation", attributes.get("eduPersonAffiliation"));
        assertEquals("groupMembership", attributes.get("groupMembership"));
        assertEquals(4, attributes.size());
    }
}
