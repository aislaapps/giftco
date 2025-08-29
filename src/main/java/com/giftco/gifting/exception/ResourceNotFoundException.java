package com.giftco.gifting.exception;

public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
    super(String.format("Resource %s not found in %s:  %s", resourceName, fieldName, fieldValue));
  }
}
