package pl.orangeretail.gofiber.server.rest.controllers;


import com.google.gson.Gson;
import java.io.IOException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.apache.commons.lang3.time.DateUtils;
import pl.orangeretail.gofiber.server.CustomLoggingFilter;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import pl.orangeretail.gofiber.session.SessionCheck;
import pl.orangeretail.gofiber.session.SessionData;
import pl.orangeretail.gofiber.error.SessionError;
import pl.orangeretail.gofiber.session.SessionMap;
import pl.orangeretail.gofiber.server.logi.ApplicationLogs;
import pl.orangeretail.gofiber.server.logi.Logi;
import pl.orangeretail.gofiber.server.objects.SessionRequest;
import pl.orangeretail.gofiber.server.objects.SessionResponse;
import pl.orangeretail.gofiber.server.objects.StandardResponse;
import pl.orangeretail.gofiber.session.SessionStatus;

/**
 *
 * @author staszek
 */
public class AuthorizationRequestFilter implements ContainerRequestFilter {

    final transient static org.slf4j.Logger logger = LoggerFactory.getLogger(AuthorizationRequestFilter.class);

    @Context    
    private HttpServletRequest servletRequest;
    
    StandardResponse response = new StandardResponse();
    SessionResponse sessionResponse = new SessionResponse();
    SessionRequest sessionRequest;
    
    SessionData sessionData;
    ApplicationLogs applicationLogs;
    
    private String sessionId;
    private String ipAddress;

    private final String URL_TEST = "goFiber/test";
    private final String URL_LOGIN = "goFiber/login";
    private final String URL_LOGOUT = "goFiber/logout";
    private final String URL_REGISTER = "goFiber/registration";
    private final String URL_PASSWORD_RECOVERY = "goFiber/passworrecovery";
    private final String URL_SERVICE_DESK = "goFiber/servicedesklogin";
    private final String URL_AUTHORIAZTION_PASSWORD = "goFiber/authorizationpassword";
    private final String URL_DEVICE_VERIFICATION = "goFiber/deviceverification";
    private final String URL_DEVICE_REGISTRATION = "goFiber/deviceregistration";
    private final String URL_RESET_PASSWORD = "goFiber/resetpassword";
    
    public static final String URL_ADD_ORDER = "goFiber/addorder";
    public static final String URL_FIND_ADDRESS = "goFiber/findaddress";
    
    
    private boolean isAuthorizationProcess;
    private boolean isDeviceRegistrationProcess;
    
    private String pathurl;
    private String messageSession = "błąd sesji";
    private String key;

    @Override
    public void filter(ContainerRequestContext request) throws IOException {
        if(!request.getMethod().contains("POST")){return;}                                                                  
        pathurl = request.getUriInfo().getPath();
//        key = request.getRequest().toString();
        key = "sessionKey";
        isAuthorizationProcess = authorizationProcess(pathurl);
        isDeviceRegistrationProcess = deviceRegistrationProcess(pathurl);
        
        String requestData = new CustomLoggingFilter().getEntityBody(request);
        if (pathurl.equals(URL_LOGIN)) {
            sessionRequest = new Gson().fromJson(requestData, SessionRequest.class);
            String appVersion = sessionRequest.getAppVersion();
            verifyVersion(appVersion, request, true);
        } else if (pathurl.equals(URL_PASSWORD_RECOVERY) || pathurl.equals(URL_REGISTER)) {
            sessionRequest = new Gson().fromJson(requestData, SessionRequest.class);
            String appVersion = sessionRequest.getAppVersion();
            verifyVersion(appVersion, request, false);
        } else if (pathurl.equals(URL_TEST) || pathurl.equals(URL_SERVICE_DESK)) {
            System.out.println("Session: " + pathurl);
            sessionResponse.setSessionStatus(SessionStatus.STATUS_OK);
            request.setProperty(key, sessionResponse);
//            MDC.put("sessionStatus", String.valueOf(SessionStatus.STATUS_OK));
        } else {
            try {
                sessionRequest = new Gson().fromJson(requestData, SessionRequest.class);
                sessionId = sessionRequest.getSessionId();
                if (sessionId == null || sessionId.isEmpty()) {
                    response.setError("Session ID is empty");
                    throw new SessionError("Null", "sessionId is null");
                }
                ipAddress = servletRequest.getRemoteAddr();
                sessionData = SessionMap.getInstance().getValue(sessionId);
            } catch (SessionError ex) {
                handleError(ex, request, SessionStatus.STATUS_FORBIDDEN, SessionStatus.STATUS_FORBIDDEN);
                logger.error(ex.getMessage(), ex);
            }
            verifySessionTime(request);
            verifySessionSecurity(request);
            saveSessionResponse(request, SessionStatus.STATUS_OK);
        }
    }
    
    private void verifyVersion(String appVersion, ContainerRequestContext request, boolean loginVersionCheck) {
        if(appVersion == null){
            sessionResponse.setSessionStatus(SessionStatus.STATUS_VERSION_IS_NULL);
            request.setProperty(key, sessionResponse);
//            MD C.put("sessionStatus", String.valueOf(SessionStatus.STATUS_VERSION_IS_NULL));
            ResponseBuilder builder = Response.status(SessionStatus.STATUS_VERSION_IS_NULL).entity(response);
            throw new WebApplicationException(builder.build());
        } else{
            try {
                SessionCheck.verifyMinimalVersion(appVersion);
                setSessionResponse(appVersion, loginVersionCheck);
            } catch (SessionError ex) {
                logger.error(ex.getMessage(), ex);
                handleError(ex, request, SessionStatus.STATUS_UPGRADE_REQUIRED, SessionStatus.STATUS_UPGRADE_REQUIRED);
            }
        }
        request.setProperty(key, sessionResponse);
    }
    
    private void verifySessionTime(ContainerRequestContext request) {

        try {
            SessionMap.getInstance().sessionIdVerification(sessionId);
            SessionCheck.sessionIsActive(sessionData);
        } catch (SessionError ex) {
            logger.error(ex.getMessage(), ex);
            handleError(ex, request, SessionStatus.STATUS_UNAUTHORIZED, SessionStatus.STATUS_UNAUTHORIZED);
        }
   }
    
    private void verifySessionSecurity(ContainerRequestContext request) {
        try {
            SessionCheck.sessionCheckIP(sessionData, ipAddress);
            if(isAuthorizationProcess){
                if(pathurl.equals(URL_RESET_PASSWORD)){
                    SessionCheck.verifyTokenValidated(sessionData);
                }
            }else{
                if(isDeviceRegistrationProcess){
                    SessionCheck.verifyUserLoggedIn(sessionData);
                }else{
                    SessionCheck.verifyDeviceRegistered(sessionData);
                }
            }
            if (pathurl.equals(URL_ADD_ORDER) || pathurl.equals(URL_FIND_ADDRESS)) {
                SessionCheck.allowSearch(sessionData);
            }
            saveSession();
        } catch (SessionError ex) {
            logger.error(ex.getMessage(), ex);
            handleError(ex, request, SessionStatus.STATUS_FORBIDDEN, SessionStatus.STATUS_FORBIDDEN);
        }
    }
    
    private void saveSession() {
        if (pathurl.equals(URL_LOGOUT)) {
            saveSessionEnd();
        } else {
            saveSessionData();

        }
    }
    
    private void saveSessionEnd() {
        Date data = new Date();
        sessionData.setDateLastActivity(data);
        sessionData.setExpirationDate(data);
        SessionMap.getInstance().addValue(sessionId, sessionData);
    }
    
    private void saveSessionData() {
        Date data = new Date();

        sessionData.setDateLastActivity(data);
        sessionData.setExpirationDate(DateUtils.addMinutes(data, SessionStatus.SESSION_LENGHT)); 
        SessionMap.getInstance().addValue(sessionId, sessionData);
    }
    
    private void logStatus(String sessionId, SessionError ex) {
        applicationLogs = new ApplicationLogs();
        applicationLogs.setResponseError(pathurl + ", walidacja Sesji " + ex.getErrorMessage() + ", " + ex.getMessage() + ", " + ex.getLogInfo() + ",sessionId: " + sessionId);
        applicationLogs.setIpAddress(servletRequest.getRemoteAddr());
        if(sessionId != null)applicationLogs.setSessionId(sessionId);
        applicationLogs.setDescription(ex.getErrorMessage()); 
        new Logi().logEvent(applicationLogs);
    }

    private void handleError(SessionError ex, ContainerRequestContext request, int error, int status) {
        response.setError(messageSession);
        logStatus(sessionId, ex);
        saveSessionResponse(request, error);
        ResponseBuilder builder = Response.status(status).entity(response);
        throw new WebApplicationException(builder.build());
    }

    private void saveSessionResponse(ContainerRequestContext request, int status) {
        System.out.println("############### AuthorizationRequestFilter /" + pathurl+" sessionStatus: " + status);
        sessionResponse.setSessionStatus(status);
        sessionResponse.setSessionId(sessionId);
        request.setProperty(key, sessionResponse);
    }

    private boolean authorizationProcess(String pathurl) {
        if(pathurl.equals(URL_AUTHORIAZTION_PASSWORD) || pathurl.equals(URL_DEVICE_VERIFICATION) || pathurl.equals(URL_RESET_PASSWORD)){
            return true;
        } else {
           return false;
        }
    }

    private boolean deviceRegistrationProcess(String pathurl) {
        if(pathurl.equals(URL_DEVICE_REGISTRATION)){
            return true;
        } else {
           return false;
        }
    }

    private void setSessionResponse(String appVersion, boolean loginVersionCheck) {
        if (loginVersionCheck) {
            sessionResponse.setSessionStatus(SessionCheck.verifyActualVersion(appVersion));
        }else{
            sessionResponse.setSessionStatus(SessionStatus.STATUS_OK);
        }
    }
}
