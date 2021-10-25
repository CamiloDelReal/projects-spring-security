package com.example.jwtjdbcuserdetailsservicecustomendpoint.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "roles_authorities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoleAuthority {

    @EmbeddedId
    private RoleAuthorityId id;

    @Getter
    @Setter
    @Embeddable
    public static class RoleAuthorityId implements Serializable {

        @Column(name = "role_id")
        private Long roleId;

        @Column(name = "authority_id")
        private Long authorityId;

    }

}
