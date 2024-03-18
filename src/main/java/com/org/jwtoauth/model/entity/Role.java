package com.org.jwtoauth.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "role_id")
    private Integer roleId;
    private String authority;

    public Role(Integer roleId, String authority) {
        this.roleId = roleId;
        this.authority = authority;
    }
    public Role(String authority) {
        this.authority = authority;
    }
}
