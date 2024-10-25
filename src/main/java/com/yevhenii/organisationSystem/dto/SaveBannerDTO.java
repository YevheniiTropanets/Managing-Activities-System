package com.yevhenii.organisationSystem.dto;

import lombok.Value;

@Value
public class SaveBannerDTO {
    String description;
    byte[] image;
    Long activityId;
}
