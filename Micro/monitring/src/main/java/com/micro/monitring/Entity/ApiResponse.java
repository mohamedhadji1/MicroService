package com.micro.monitring.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private boolean success;
    private String message;
    private Object data;

    public static ApiResponse success(Object data) {
        return new ApiResponse(true, "Operation successful", data);
    }

    public static ApiResponse error(String message) {
        return new ApiResponse(false, message, null);
    }
}