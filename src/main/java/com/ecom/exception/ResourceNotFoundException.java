package com.ecom.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceNotFoundException extends RuntimeException {
    String resourceName;
    String field;
    String fieldName;
    Long fieldId;
    String message;

    public ResourceNotFoundException(String category, String categoryId, Long categoryId1, String message) {
        setResourceName(String.format("%s not found  with %s %s",category,categoryId,categoryId1));
        this.resourceName=category;
        this.field=categoryId;
        this.fieldId=categoryId1;
        this.message=message;
    }
}
