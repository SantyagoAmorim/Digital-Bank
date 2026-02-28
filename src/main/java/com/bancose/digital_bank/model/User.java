package com.bancose.digital_bank.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
/*
@Entity → diz ao JPA que isso vira tabela
@Table(name = "users") → nome da tabela
@Id → chave primária
@GeneratedValue → auto incremento
@Column(nullable = false) → campo obrigatório
unique = true → email não pode repetir
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDateTime CreatedAt;
}
