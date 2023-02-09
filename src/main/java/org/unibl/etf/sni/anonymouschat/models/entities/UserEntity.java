package org.unibl.etf.sni.anonymouschat.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.unibl.etf.sni.anonymouschat.models.enums.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user", schema = "sniproject")
public class UserEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Basic
    @Column(name = "password", nullable = false)
    private String password;
    @Basic
    @Column(name = "name", nullable = false)
    private String name;
    @Basic
    @Column(name = "surname", nullable = false)
    private String surname;
    @Basic
    @Column(name = "pubkey", nullable = false)
    private String publicKeyString;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('USER')")
    private Role role;
}
