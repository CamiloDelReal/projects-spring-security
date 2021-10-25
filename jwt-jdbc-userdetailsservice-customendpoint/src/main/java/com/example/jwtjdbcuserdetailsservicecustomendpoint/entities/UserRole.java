package com.example.jwtjdbcuserdetailsservicecustomendpoint.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRole {

    @EmbeddedId
    private UserRoleId id;

    @Getter
    @Setter
    @Embeddable
    public static class UserRoleId implements Serializable {

        @Column(name = "user_id")
        private Long userId;

        @Column(name = "role_id")
        private Long roleId;

    }
}
