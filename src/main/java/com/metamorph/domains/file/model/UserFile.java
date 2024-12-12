package com.metamorph.domains.file.model;

import com.metamorph.domains.file.enums.UserFileType;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Entity
@Table(name = "user_file")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "file_name", nullable = false)
  private String name;

  @Column(name = "file_category", nullable = false)
  private String category;

  @Column(name = "file_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private UserFileType type;

  @Column(name = "file_extension", nullable = false)
  private String extension;

  @Column(name = "created_date", nullable = false)
  private LocalDate created;

    @Column(name = "modified_date")
  private LocalDate modified;

  @Column(name = "deleted_date")
  private LocalDate deleted;

  @Column(name = "is_active", nullable = false)
  private boolean isActive;

  @Lob
  @Column(name = "data", nullable = false)
  private byte[] data;

  @Column(name = "user_id", nullable = false)
  private String userId;
}
