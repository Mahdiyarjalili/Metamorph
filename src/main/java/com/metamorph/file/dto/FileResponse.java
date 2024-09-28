package com.metamorph.file.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileResponse {

  private long id;
  private String name;
  private String type;
  private String extension;
  private LocalDate created;
  private LocalDate modified;
  private LocalDate deleted;
  private boolean isActive;
  private byte[] data;


}
