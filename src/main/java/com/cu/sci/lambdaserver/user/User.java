package com.cu.sci.lambdaserver.user;

import com.cu.sci.lambdaserver.utils.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

/**
 * User class implements UserDetails interface from Spring Security.
 * It represents a user in the application.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 10)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "varchar(255) default 'https://res.cloudinary.com/dyafviw2c/image/upload/users/defaultuserimage.jpg'")
    private String profilePicture = "https://res.cloudinary.com/dyafviw2c/image/upload/users/defaultuserimage.jpg";

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean isEnabled = true;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean isAccountNonExpired = true;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean isAccountNonLocked = true;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean isCredentialsNonExpired = true;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.LAZY, targetClass = Role.class)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    private Collection<Role> roles;

    /**
     * Checks if the user has a specific role.
     *
     * @param role The role to check for.
     * @return true if the user has the role, false otherwise.
     */
    public boolean hasRole(Role role) {
        return roles.contains(role);
    }

    /**
     * Returns the authorities granted to the user.
     *
     * @return a collection of GrantedAuthority objects.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    /**
     * Checks if the user is equal to another object.
     *
     * @param o the object to compare with.
     * @return a boolean indicating if the user is equal to the object.
     */
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    /**
     * Returns the hash code of the user.
     *
     * @return the hash code of the user.
     */
    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}