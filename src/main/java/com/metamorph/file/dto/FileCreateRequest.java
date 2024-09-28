package com.metamorph.file.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class FileCreateRequest {

  @NotBlank(message = "Name is required and can not be blank.")
  private String name;

  @NotBlank(message = "Type is required and can not be blank.")
  private String type;

  @NotBlank(message = "Extention is required and can not be blank.")
  private String extention;

  @NotBlank(message = "Created date is required and can not be blank.")
  private LocalDateTime created;

  @NotBlank(message = "Modified date is required and can not be blank.")
  private LocalDateTime modified;

  @NotBlank(message = "Deleted date is required and can not be blank.")
  private LocalDateTime deleted;

  @NotNull(message = "Is active attribute is required and can not be blank.")
  private boolean isActive;

}
