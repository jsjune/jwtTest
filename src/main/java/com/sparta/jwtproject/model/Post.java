package com.sparta.jwtproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Post {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long postid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(nullable = false)
    private String title;


    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String imageSrc;

    @Column(nullable = false)
    private int star;

}
