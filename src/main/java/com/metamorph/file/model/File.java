package com.metamorph.file.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "files")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class File {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "file_name", nullable = false)
  private String name;

  @Column(name = "file_type", nullable = false)
  private String type;

  @Column(name = "file_extention", nullable = false)
  private String extention;

  @Column(name = "created_date", nullable = false)
  private LocalDateTime created;

  @Column(name = "modified_date", nullable = false)
  private LocalDateTime modified;

  @Column(name = "deleted_date", nullable = false)
  private LocalDateTime deleted;

  @Column(name = "is_active", nullable = false)
  private boolean isActive;

  @Column(name = "user_id", nullable = false)
  private String userId;
}
