package pl.orangeretail.gofiber.server.objects;

public class SendFormResponse extends StandardResponse{
    public boolean sent;

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }
}
