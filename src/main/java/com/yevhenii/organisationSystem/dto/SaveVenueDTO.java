package com.yevhenii.organisationSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveVenueDTO {
    private String title;
    private int maximumSeats;
    private String description;
    private String adresIndex;
    private int rentPrice;
    private String streetName;
    private String cityName;


}
