/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.orangeretail.gofiber.error;

/**
 *
 * @author staszek.martowicz
 */
public class VersionError extends Exception {

    private final String errorMessage;
    private final String logInfo;

    public VersionError(String errorMessage, String logInfo) {
        this.errorMessage = errorMessage;
        this.logInfo = logInfo;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getLogInfo() {
        return logInfo;
    }
    
    
    
}
