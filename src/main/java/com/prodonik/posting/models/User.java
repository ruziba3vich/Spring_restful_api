package com.prodonik.posting.models;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @NonNull private String firstname;
    @NonNull private String lastname;
    @Column(name = "usernames", unique = true, length = 64)
    private String username;
    private LocalDateTime birthDate;
    @Transient
    private int age;
    @Column(name = "passwords", length = 64)
    private String password;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "roles", length = 10)
    private Role role;

    @OneToMany(mappedBy = "user")
    List<Token> tokens;

    @OneToMany(mappedBy = "users")
    List<Post> posts;

    public User (String firstname, String lastname, String username,
                LocalDateTime birthDateTime, String password, Role role,
                List<Token> tokens, List<Post> posts) {
            this.setFirstname(firstname);
            this.setLastname(lastname);
            this.setUsername(username);
            this.setBirthDate(birthDateTime);
            this.setPassword(password);
            this.setRole(role);
            age = birthDate.getYear() - LocalDateTime.now().getYear();
            this.setTokens(tokens);
            this.setPosts(posts);
        }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
