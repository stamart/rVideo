package pl.orangeretail.gofiber.server;

import BazaDanych.Parametry_Pracy;
import Tools.LogowanieInicjacja;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.orangeretail.gofiber.session.SessionMap;

@SpringBootApplication
public class Start {

    private final static String NAZWA_INSTANCJI = "goFiber";

    public static void main(String[] args) throws IOException {
//        loadSession();
//        getVersion();
        SpringApplication.run(Start.class, args);
    }

    private static void loadSession() {
        SessionMap.getInstance().loadSession();
    }

    private static void getVersion() {
        SessionMap.getInstance().getVersion();
    }

}
