package com.maxim.hibernate.entity;
import org.hibernate.annotations.Type;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", schema = "public")
public class User {

    @Id
    @GeneratedValue(generator = "user_gen", strategy = GenerationType.IDENTITY)
    // @SequenceGenerator(name = "user_gen", sequenceName = "users_id_seq", allocationSize = 1)
    // @TableGenerator(name = "user_gen", table = "all_sequence", pkColumnName = "table_name", valueColumnName = "pk_value", allocationSize = 1)
    private Long id;

    @Column(unique = true)
    private String username;

    @Embedded
    private PersonalInfo personalInfo;

    @Enumerated(EnumType.STRING)
    private Role role;
    
    @Type(JsonBinaryType.class)
    private String info;
}
