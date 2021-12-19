package team9.clover.Model;

public class RewardModel {

    String title;
    String expiredDate;
    String coupenBody;

    public RewardModel(String title, String expiredDate, String coupenBody) {
        this.title = title;
        this.expiredDate = expiredDate;
        this.coupenBody = coupenBody;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getCoupenBody() {
        return coupenBody;
    }

    public void setCoupenBody(String coupenBody) {
        this.coupenBody = coupenBody;
    }
}
