package com.bntech.qrekru.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.bntech.qrekru.config.Const.ROLE_TABLE;

@Entity
@Table(name = ROLE_TABLE)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String name;

    public Role(String name) {
        this.name = name;
    }
}
