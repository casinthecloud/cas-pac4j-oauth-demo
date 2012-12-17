package org.leleuj.test;

import java.util.Map;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Saml11TicketValidator;
import org.jasig.cas.client.validation.TicketValidationException;
import org.scribe.up.profile.ProfileHelper;
import org.scribe.up.profile.UserProfile;
import org.scribe.up.test.util.CommonHelper;
import org.scribe.utils.OAuthEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public final class CasHelper {
    
    private static final Logger logger = LoggerFactory.getLogger(CasHelper.class);
    
    private final static String SERVICE = "http://www.google.fr/";
    
    private final static String PREFIX_URL = "http://localhost:8080/cas";
    
    private final static String LOGIN_URL = PREFIX_URL + "/login";
    
    private final static String LOGIN_URL_DEV = "http://casdemo.localhost.com:8080/cas/login";
    
    private final static String LOGIN_URL_WITH_SERVICE = LOGIN_URL + "?service=" + OAuthEncoder.encode(SERVICE);
    
    public final static String LOGIN_URL_DEV_WITH_SERVICE = LOGIN_URL_DEV + "?service=" + OAuthEncoder.encode(SERVICE);
    
    private final static String TICKET = "ticket";
    
    public static HtmlPage getAuhtorizationPage(final WebClient webClient, final String linkName) throws Exception {
        return getAuhtorizationPage(webClient, linkName, LOGIN_URL_WITH_SERVICE);
    }
    
    public static HtmlPage getAuhtorizationPage(final WebClient webClient, final String linkName, final String loginUrl)
        throws Exception {
        final HtmlPage loginPage = webClient.getPage(loginUrl);
        final HtmlAnchor link = (HtmlAnchor) loginPage.getElementById(linkName);
        final HtmlPage authorizationPage = link.click();
        return authorizationPage;
    }
    
    public static Assertion getProfile(final String callbackUrl) {
        final Map<String, String[]> parameters = CommonHelper.getParametersFromUrl(callbackUrl);
        final String[] tickets = parameters.get(TICKET);
        if (tickets != null && tickets.length == 1) {
            final String ticket = tickets[0];
            final Saml11TicketValidator saml11TicketValidator = new Saml11TicketValidator(CasHelper.PREFIX_URL);
            try {
                return saml11TicketValidator.validate(ticket, CasHelper.SERVICE);
            } catch (final TicketValidationException e) {
                logger.error("Ticket validation exception : ", e);
            }
        }
        return null;
    }
    
    public static UserProfile getProfile(final String callbackUrl, final Class<? extends UserProfile> clazz) {
        final Assertion assertion = getProfile(callbackUrl);
        if (assertion != null) {
            final AttributePrincipal principal = assertion.getPrincipal();
            final String id = principal.getName();
            final Map<String, Object> attributes = principal.getAttributes();
            return ProfileHelper.buildProfile(id, attributes);
        }
        return new UserProfile();
    }
}
