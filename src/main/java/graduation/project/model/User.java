package graduation.project.model;

import graduation.project.repository.UserType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String username;

    @Email
    @Column(nullable = false)
    private String email;

    private String password;

    private int age;

    private String imageUrl;

    @Column(nullable = false)
    private Boolean emailVerified=false;

    private String gender;

    @Column(name = "userType")
    @Enumerated(EnumType.STRING)
    private UserType userType;


    private String busNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

}
