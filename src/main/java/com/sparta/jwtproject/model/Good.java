package com.sparta.jwtproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Good {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long goodid;

    @Column(nullable = false)
    private Long postid;

    @Column(nullable = false)
    private String username;

}
