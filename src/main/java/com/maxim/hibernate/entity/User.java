package com.maxim.hibernate.entity;
import org.hibernate.annotations.Type;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Access(value = AccessType.FIELD)
public class User {
    
    @EmbeddedId
    private PersonalInfo personalInfo;

    // @ColumnTransformer(write="encrypt(?)", read="decrypt(username)")
    @Column(unique = true, name = "username")
    private String username;


    @Enumerated(EnumType.STRING)
    private Role role;
    
    @Type(JsonBinaryType.class)
    private String info;
}
