package com.cu.sci.lambdaserver.contactinfo;


import com.cu.sci.lambdaserver.user.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "contact_info")
public class ContactInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_info_seq")
    @SequenceGenerator(name = "contact_info_seq", sequenceName = "contact_info_seq", allocationSize = 10)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String phone;

    private String telephone;

    private String email;

}
