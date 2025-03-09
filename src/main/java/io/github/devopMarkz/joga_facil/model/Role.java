package io.github.devopMarkz.joga_facil.model;

import io.github.devopMarkz.joga_facil.model.enums.RoleEnum;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "authority", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private RoleEnum authority;

    @ManyToMany(mappedBy = "roles")
    private Set<Usuario> usuarios = new HashSet<>();

    public Role() {
    }

    public Role(Integer id, RoleEnum authority) {
        this.id = id;
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority.name();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAuthority(RoleEnum authority) {
        this.authority = authority;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Role role = (Role) object;
        return Objects.equals(id, role.id) && authority == role.authority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authority);
    }
}
