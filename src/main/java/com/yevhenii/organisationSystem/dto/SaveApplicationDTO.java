package com.yevhenii.organisationSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
@AllArgsConstructor
public class SaveApplicationDTO {
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private String activityTitle;
    public Timestamp getStartTime() {
        return Timestamp.valueOf(startDate + " " + startTime + ":00");
    }
    public Timestamp getFinishTime() {
        return Timestamp.valueOf(endDate + " " + endTime + ":00");
    }

}
