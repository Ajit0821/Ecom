package com.ecom.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user",
uniqueConstraints = {
@UniqueConstraint(columnNames = "user_name"),
@UniqueConstraint(columnNames = "email")})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    @NotBlank
    @Column(name = "user_name")
    private String userName;
    @NotBlank
    @Column(name = "email")
    private String email;
    @NotBlank
    @Column(name = "password")
    private String password;
    @Getter
    @Setter
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> roles=new HashSet<>();

    @OneToMany(mappedBy = "user",
    cascade = {CascadeType.PERSIST,CascadeType.MERGE},
    orphanRemoval = true)
    private Set<Product> products;


}
