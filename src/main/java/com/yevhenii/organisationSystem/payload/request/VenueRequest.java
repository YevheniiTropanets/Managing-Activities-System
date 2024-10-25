package com.yevhenii.organisationSystem.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class VenueRequest {
    String startDate;
    String startTimeHour;
    String startTimeMin;
    String endDate;
    String endTimeHour;
    String endTimeMin;
    String activityTitle;
    Long[] venueList;
}
