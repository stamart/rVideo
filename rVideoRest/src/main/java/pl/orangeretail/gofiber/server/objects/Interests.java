package pl.orangeretail.gofiber.server.objects;

public class Interests {
    public boolean mobileInterest;
    public boolean fiberInterest;
    public boolean tvInterest;

    public Interests() {
    }   
    
    public boolean isMobileInterest() {
        return mobileInterest;
    }

    public void setMobileInterest(boolean mobileInterest) {
        this.mobileInterest = mobileInterest;
    }

    public boolean isFiberInterest() {
        return fiberInterest;
    }

    public void setFiberInterest(boolean fiberInterest) {
        this.fiberInterest = fiberInterest;
    }

    public boolean isTvInterest() {
        return tvInterest;
    }

    public void setTvInterest(boolean tvInterest) {
        this.tvInterest = tvInterest;
    }
}
