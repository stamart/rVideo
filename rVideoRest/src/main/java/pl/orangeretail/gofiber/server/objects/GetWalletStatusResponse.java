package pl.orangeretail.gofiber.server.objects;

import java.util.Date;

/**
 * Created by marek.rusnak on 2016-10-04.
 */
public class GetWalletStatusResponse extends StandardResponse {
    private double avaiableFunds;
    private int status;
    private Date statusTime;

    public double getAvaiableFunds() {
        return avaiableFunds;
    }

    public void setAvaiableFunds(double avaiableFunds) {
        this.avaiableFunds = avaiableFunds;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int state) {
        this.status = state;
    }

    public Date getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(Date stateTime) {
        this.statusTime = stateTime;
    }
}