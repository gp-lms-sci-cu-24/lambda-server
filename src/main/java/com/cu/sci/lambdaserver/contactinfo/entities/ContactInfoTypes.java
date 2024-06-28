package com.cu.sci.lambdaserver.contactinfo.entities;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "contact_info_types")
public class ContactInfoTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_info_types_seq")
    @SequenceGenerator(name = "contact_info_types_seq", sequenceName = "contact_info_types_seq", allocationSize = 10)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column()
    private String details;


}
