package com.cu.sci.lambdaserver.location;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long locationId;

    @Column(nullable = false)
    private String locationPath;

    private String locationInfo;

    @Column(nullable = false)
    private Integer maxCapacity;
}
