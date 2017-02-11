package pl.orangeretail.gofiber.server.objects;

/**
 *
 * @author stanislaw.martowicz
 */
public class RegistrationResponse extends StandardResponse {
     private String sessionId;

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        @Override
        public String toString() {
            return "LoginResponse{" + "sessionId=" + sessionId + '}';
        }
}
