package com.yevhenii.organisationSystem.payload.response;

import lombok.Data;

@Data
public class AlgorithmResponse {
    private String venueTitle;
    private String activityTitle;
    private String description;
    private int amountSeats;
    private String organisation;
    private String genre;
    private String activityType;
}
