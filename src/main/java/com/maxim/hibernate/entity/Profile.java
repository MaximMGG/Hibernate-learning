package com.maxim.hibernate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

    @Id
    @Column(name = "user_id")
    private Long id;

    @OneToOne()
    // @JoinColumn(name = "user_id")
    @PrimaryKeyJoinColumn
    private User user;

    private String street;

    private String language;

    public void setUser(User user) {
        user.setProfile(this);
        this.user = user;
        this.id = user.getId();
    }
    
}
