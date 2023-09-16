package com.maxim.hibernate.entity;
import org.hibernate.annotations.Type;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private String username;

    @Embedded
    private PersonalInfo personalInfo;

    @Enumerated(EnumType.STRING)
    private Role role;
    // @Convert(converter = com.vladmihalcea.hibernate.type.json.JsonBinaryType.class)
    // @Type(JsonBinaryType.class)
    // @JdbcTypeCode(SqlTypes.JSON)
    // private Map<String, String> info;
    @Type(JsonBinaryType.class)
    private String info;
}
