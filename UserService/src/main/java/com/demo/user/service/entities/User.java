package com.demo.user.service.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="micro_users")
public class User {

    @Id
    @Column(name = "Id")
    private String userId;
    @Column(name="Name")
    private String name;
    @Column(name ="Email")
    private String email;
    @Column(name ="About")
    private String about;

    @Transient
    private List<Rating> ratings=new ArrayList<>();

}