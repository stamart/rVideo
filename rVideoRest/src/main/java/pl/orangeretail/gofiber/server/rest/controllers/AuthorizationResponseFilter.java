/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.orangeretail.gofiber.server.rest.controllers;


import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import org.slf4j.LoggerFactory;
import pl.gofiber.ftth.Dictionary;
import pl.orangeretail.gofiber.server.logi.ApplicationLogs;
import pl.orangeretail.gofiber.server.logi.ConnectionsList;
import pl.orangeretail.gofiber.server.logi.Logi;
import pl.orangeretail.gofiber.server.methods.ToolBox;
import pl.orangeretail.gofiber.server.objects.LoginResponse;
import pl.orangeretail.gofiber.server.objects.SessionResponse;
import pl.orangeretail.gofiber.server.objects.StandardResponse;
import pl.orangeretail.gofiber.session.SessionData;
import pl.orangeretail.gofiber.session.SessionMap;
import pl.orangeretail.gofiber.session.SessionStatus;

/**
 *
 * @author staszek
 */

public class AuthorizationResponseFilter extends ToolBox implements ContainerResponseFilter {

    final transient static org.slf4j.Logger logger = LoggerFactory.getLogger(AuthorizationResponseFilter.class);

    @Context    
    private HttpServletRequest servletRequest;
    private String ipAddress;
    
    public StandardResponse response = new StandardResponse();
    private SessionResponse sessionResponse = new SessionResponse();
   
    private String sessionId;
    private String sessionError;
            
    private final String URL_LOGIN = "goFiber/login";
    private final String URL_LOGOUT = "goFiber/logout";
    private final String URL_REGISTER = "goFiber/registration";
    private final String URL_PASSWORD_RECOVERY = "goFiber/passworrecovery";
    
    private String pathurl;
    
    private int sessionStatus = SessionStatus.STATUS_OK;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        pathurl = requestContext.getUriInfo().getPath();
        ipAddress = servletRequest.getRemoteAddr();
        
        if (!requestContext.getMethod().contains("POST")) {
            return;
        }
        if (pathurl.equals(URL_LOGOUT)) {
            return;
        }
        
        try{
//            String key = requestContext.getRequest().toString();
            String key = "sessionKey";
            sessionResponse = (SessionResponse) requestContext.getProperty(key);
            sessionStatus = sessionResponse.getSessionStatus();
            System.out.println("############### sessionStatus: " + sessionStatus);
            
            sessionError = getResponseError(responseContext);
            setResposneContextStaus(responseContext, sessionStatus);
            
            if (pathurl.equals(URL_LOGIN) || pathurl.equals(URL_PASSWORD_RECOVERY) || pathurl.equals(URL_REGISTER)) {
                String entity = responseContext.getEntity().toString();
                if (entity.isEmpty() || entity == null) {
                    System.out.println("Brak session ID");
                } else {
                    sessionId = getResponseSessionId(responseContext);
                    logStatus(sessionId);
                }
            } else {
                if(sessionStatus == SessionStatus.STATUS_UNAUTHORIZED){  
                    return;
                } else {
                    sessionId = sessionResponse.getSessionId();
                    logStatus(sessionId);
                }
            }
        } catch (Exception ex) {
//            sessionStatus = SessionStatus.STATUS_UNAUTHORIZED;
//            setResposneContextStaus(responseContext, sessionStatus);
            logger.error(ex.getMessage(), ex);
            System.out.println("############### AuthorizationResponseFilter Exeption        : " + ex.getMessage());
            System.out.println("############### AuthorizationResponseFilter pathurl         : " + pathurl);
            System.out.println("############### AuthorizationResponseFilter sessionStatus   : " + sessionStatus);
            System.out.println("############### AuthorizationResponseFilter sessionId       : " + sessionId);
            System.out.println("############### AuthorizationResponseFilter sessionError    : " + sessionError);
            System.out.println("############### AuthorizationResponseFilter ipAddress       : " + ipAddress );
            logStatus(sessionId);
        }
       
    }

    private void logStatus(String sessionId) {

        if(sessionId == null){
            sessionData = new SessionData();
            applicationLogs = new ApplicationLogs();
        }else{
            sessionData = SessionMap.getInstance().getValue(sessionId);
            int userId = sessionData.getUserID();
            applicationLogs = ConnectionsList.getInstance().getValue(userId);
            applicationLogs.setIdSession(getIdSession(sessionId));
        }
        applicationLogs.setResponseError(sessionError);
        applicationLogs.setIpAddress(ipAddress);
        if(pathurl != null)applicationLogs.setDescription("Akcja: " + pathurl);
        switch(pathurl){
            case "goFiber/login"                    :     {applicationLogs.setTypID(Dictionary.STATUS_LOGIN);break;}
            case "goFiber/logout"                   :     {applicationLogs.setTypID(Dictionary.STATUS_LOGIN);break;}
            case "goFiber/registration"             :     {applicationLogs.setTypID(Dictionary.STATUS_REGISTRATION);break;}
            case "goFiber/passworrecovery"          :     {applicationLogs.setTypID(Dictionary.STATUS_PASSWORD_RECOVERY);break;}
            case "goFiber/authorizationpassword"    :     {applicationLogs.setTypID(Dictionary.STATUS_AUTHORIZATIONPASSWORD);break;}
            case "goFiber/resetpassword"            :     {applicationLogs.setTypID(Dictionary.STATUS_RESETPASSWORD);break;}
            case "goFiber/deviceregistration"       :     {applicationLogs.setTypID(Dictionary.STATUS_DEVICEREGISTRATION);break;}
            case "goFiber/deviceverification"       :     {applicationLogs.setTypID(Dictionary.STATUS_DEVICEVERIFICATION);break;}
            case "goFiber/addlead"                  :     {applicationLogs.setTypID(Dictionary.STATUS_ADDLEAD);break;}
            case "goFiber/findaddress"              :     {applicationLogs.setTypID(Dictionary.STATUS_FINDADDRESS);break;}
            case "goFiber/findoffers"               :     {applicationLogs.setTypID(Dictionary.STATUS_FINDOFFERS);break;}
            case "goFiber/addorder"                 :     {applicationLogs.setTypID(Dictionary.STATUS_ADDORDER);break;}
            case "goFiber/getorders"                :     {applicationLogs.setTypID(Dictionary.STATUS_GETORDERS);break;}
            case "goFiber/profile/getprofilestatus" :     {applicationLogs.setTypID(Dictionary.STATUS_PROFILE_GETPROFILESTATUS);break;}
            case "goFiber/profile/addbankaccount"   :     {applicationLogs.setTypID(Dictionary.STATUS_PROFILE_ADDBANKACCOUNT);break;}
            case "goFiber/profile/updatetaxoffice"  :     {applicationLogs.setTypID(Dictionary.STATUS_PROFILE_UPDATETAXOFFICE);break;}
            case "goFiber/profile/updateaddressdata":     {applicationLogs.setTypID(Dictionary.STATUS_PROFILE_UPDATEADDRESSDATA);break;}
            case "goFiber/profile/updaterights"     :     {applicationLogs.setTypID(Dictionary.STATUS_PROFILE_UPDATERIGHTS);break;}
            case "goFiber/profile/getrights"        :     {applicationLogs.setTypID(Dictionary.STATUS_PROFILE_GETRIGHTS);break;}
            case "goFiber/profile/getaddressdata"   :     {applicationLogs.setTypID(Dictionary.STATUS_PROFILE_GETADDRESSDATA);break;}
            case "goFiber/profile/getbankaccount"   :     {applicationLogs.setTypID(Dictionary.STATUS_PROFILE_GETBANKACCOUNT);break;}
            case "goFiber/profile/gettaxoffice"     :     {applicationLogs.setTypID(Dictionary.STATUS_PROFILE_GETTAXOFFICE);break;}
            case "goFiber/getdictionary"            :     {applicationLogs.setTypID(Dictionary.STATUS_GETDICTIONARY);break;}
            case "goFiber/profile/verifyemail"      :     {applicationLogs.setTypID(Dictionary.STATUS_PROFILE_VERIFYEMAIL);break;}
            case "goFiber/servicedesk"              :     {applicationLogs.setTypID(Dictionary.STATUS_SERVICEDESK);break;}
            case "goFiber/servicedesklogin"         :     {applicationLogs.setTypID(Dictionary.STATUS_SERVICEDESK_LOGIN);break;}
            case "goFiber/sendformtoemail"          :     {applicationLogs.setTypID(Dictionary.STATUS_SEND_FORM_TO_EMAIL);break;}
            default: {applicationLogs.setDescription(pathurl + " dodac w AuthorizationResponseFilter kolejny case!!! BRAK STAŁYCH SŁOWNIKOWYCH");}
        }
        if(sessionId != null){
            ConnectionsList.getInstance().addValue(sessionData.getUserID(), applicationLogs);
        }
        new Logi().logEvent(applicationLogs);
    }
    
    private String getResponseError(ContainerResponseContext responseContext) {
        Object obj = responseContext.getEntity();
        if(obj instanceof StandardResponse){
            StandardResponse st = (StandardResponse) obj;
            System.out.println("############### sessionError: " + st.getError());
            return st.getError();
        }
        return null;
    }

    private String getResponseSessionId(ContainerResponseContext responseContext) {
        Object obj = responseContext.getEntity();
        if(obj instanceof LoginResponse){
            LoginResponse ls = (LoginResponse) obj;
            System.out.println("############### getSessionId: " + ls.getSessionId());
            return ls.getSessionId();
        }
       return null;
    }

    private void setResposneContextStaus(ContainerResponseContext responseContext, int sessionStatus) {
        responseContext.setStatus(sessionStatus);
        System.out.println("############### AuthorizationResponseFilter /" + pathurl+" sessionStatus: " + responseContext.getStatus());
    }
    
}
