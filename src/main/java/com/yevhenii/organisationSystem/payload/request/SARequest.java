package com.yevhenii.organisationSystem.payload.request;

import com.yevhenii.organisationSystem.dto.SaveApplicationDTO;
import lombok.Data;

@Data
public class SARequest {
    private SaveApplicationDTO saveApplication;
    private SendApplicationRequest sendApplicationRequest;
}
