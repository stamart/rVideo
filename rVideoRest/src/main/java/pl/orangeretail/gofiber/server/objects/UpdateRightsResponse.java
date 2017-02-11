package pl.orangeretail.gofiber.server.objects;

/**
 * Created by marek.rusnak on 19.09.2016.
 */
public class UpdateRightsResponse extends StandardResponse {
    public boolean marketingRightAccept;

    public boolean isMarketingRightAccept() {
        return marketingRightAccept;
    }

    public void setMarketingRightAccept(boolean marketingRightAccept) {
        this.marketingRightAccept = marketingRightAccept;
    }
}
