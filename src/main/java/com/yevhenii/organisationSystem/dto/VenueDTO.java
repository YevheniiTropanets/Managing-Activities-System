package com.yevhenii.organisationSystem.dto;



import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Getter
@Setter
@Value
public class VenueDTO {
     Long venueId;
     String title;
     int maximumSeats;
     String description;
     String adresIndex;
     int rentPrice;
     String streetName;
     String cityName;
}
