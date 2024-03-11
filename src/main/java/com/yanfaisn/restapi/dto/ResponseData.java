package com.yanfaisn.restapi.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseData<T> {
    private Boolean status;
    private List<String> message = new ArrayList<>();
    private T payload;
}