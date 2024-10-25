package com.yevhenii.organisationSystem.payload.request;

import lombok.Data;

import java.util.List;
@Data
public class SendApplicationRequest {
    List<String> venueTitle;
}
