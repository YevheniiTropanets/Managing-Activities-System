package com.yevhenii.organisationSystem.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Getter
@Setter
public class ManageVenueRequest {
    @NotBlank
    private String date;

    @NotBlank
    private String time;

    public Timestamp getTimestamp() {
        return Timestamp.valueOf(date + " " + time + ":00");
    }
}
