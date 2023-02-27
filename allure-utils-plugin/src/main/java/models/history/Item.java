package models.history;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Item {
    public String uid;

    public String reportUrl;

    public String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String statusDetails;

    public Time time;

}
