package pl.orangeretail.gofiber.server.rest.controllers;

import BazaDanych.Wyjatek_Serwera;
import BazaDanych.Baza_Danych_Connection;
import BazaDanych.Parametry_Pracy;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gofiber.ftth.FindOffers;
import pl.gofiber.addres.FindAddress;
import pl.orangeretail.gofiber.server.methods.*;
import pl.orangeretail.gofiber.server.objects.*;

@Path("/goFiber")
public class Controller {
    final transient static Logger logger = LoggerFactory.getLogger(Controller.class);

    private final static String NAZWA_INSTANCJI = "goFiber";

  
    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)    
    public LoginResponse login(LoginRequest request,@Context HttpServletRequest requestContext) throws IOException {
        Login logon = new Login();
        logon.setObiketWywolanyPrzezManageraPolaczen(true);
        LoginResponse loginResponse = logon.zaloguj(request, requestContext);
        return loginResponse;
    }
    
    
}
