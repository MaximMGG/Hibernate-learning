package com.maxim.hibernate.entity;
import org.hibernate.annotations.Type;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "username")
@ToString(exclude = "company")
@Builder
@Entity
@Table(name = "users", schema = "public")
@Access(value = AccessType.FIELD)
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private PersonalInfo personalInfo;

    // @ColumnTransformer(write="encrypt(?)", read="decrypt(username)")
    @Column(unique = true, name = "username")
    private String username;


    @Enumerated(EnumType.STRING)
    private Role role;
    
    @Type(JsonBinaryType.class)
    private String info;

    @ManyToOne(cascade = {CascadeType.ALL}
                ,fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id") // company_id
    private Company company;
}
