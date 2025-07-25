package com.inflearn.security.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ROLE_HIERARCHY")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoleHierarchy {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "role_name")
    private String roleName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RoleHierarchy parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private Set<RoleHierarchy> children = new HashSet<>();
}
