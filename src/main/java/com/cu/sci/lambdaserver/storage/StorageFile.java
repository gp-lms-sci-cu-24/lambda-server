package com.cu.sci.lambdaserver.storage;

import com.cu.sci.lambdaserver.utils.entities.DateAudit;
import com.cu.sci.lambdaserver.utils.enums.FileType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "storage_files")
public class StorageFile extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_seq")
    @SequenceGenerator(name = "department_seq", sequenceName = "department_seq", allocationSize = 10)
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FileType fileType;
}
