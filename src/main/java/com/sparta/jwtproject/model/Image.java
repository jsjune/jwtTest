package com.sparta.jwtproject.model;

import javax.persistence.*;

@Entity
public class Image {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long imageid;

    @Column(nullable = false)
    private Long postid;

    @Column(nullable = false)
    private String username;
}
