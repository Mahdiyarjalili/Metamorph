package com.metamorph.file.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileResponse {

  private long id;
  private String name;
  private String type;
  private String extention;
  private LocalDateTime created;
  private LocalDateTime modified;
  private LocalDateTime deleted;
  private boolean isActive;

}
