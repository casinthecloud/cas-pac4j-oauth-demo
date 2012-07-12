package org.leleuj.test;

import java.lang.reflect.Constructor;
import java.util.Map;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Saml11TicketValidator;
import org.jasig.cas.client.validation.TicketValidationException;
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
    
    public static HtmlPage getAuhtorizationPage(WebClient webClient, String linkName) throws Exception {
        return getAuhtorizationPage(webClient, linkName, LOGIN_URL_WITH_SERVICE);
    }
    
    public static HtmlPage getAuhtorizationPage(WebClient webClient, String linkName, String loginUrl) throws Exception {
        HtmlPage loginPage = webClient.getPage(loginUrl);
        HtmlAnchor link = (HtmlAnchor) loginPage.getElementById(linkName);
        HtmlPage authorizationPage = link.click();
        return authorizationPage;
    }
    
    public static Assertion getProfile(String callbackUrl) {
        Map<String, String[]> parameters = CommonHelper.getParametersFromUrl(callbackUrl);
        String[] tickets = parameters.get(TICKET);
        if (tickets != null && tickets.length == 1) {
            String ticket = tickets[0];
            Saml11TicketValidator saml11TicketValidator = new Saml11TicketValidator(CasHelper.PREFIX_URL);
            try {
                return saml11TicketValidator.validate(ticket, CasHelper.SERVICE);
            } catch (TicketValidationException e) {
                logger.error("Ticket validation exception : ", e);
            }
        }
        return null;
    }
    
    public static UserProfile getProfile(String callbackUrl, Class<? extends UserProfile> clazz) {
        Assertion assertion = getProfile(callbackUrl);
        if (assertion != null) {
            try {
                AttributePrincipal principal = assertion.getPrincipal();
                String id = principal.getName();
                Map<String, Object> attributes = principal.getAttributes();
                Constructor<? extends UserProfile> constructor = clazz.getDeclaredConstructor(Object.class, Map.class);
                return constructor.newInstance(id, attributes);
            } catch (Exception e) {
                logger.error("Exception : ", e);
            }
        }
        return new UserProfile();
    }
}
