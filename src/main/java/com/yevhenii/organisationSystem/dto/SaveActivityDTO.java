package com.yevhenii.organisationSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveActivityDTO {
    private String title;
    private String description;
    private int amountSeats;
    private String organisation;
    private String genre;
    private String activityType;


}
