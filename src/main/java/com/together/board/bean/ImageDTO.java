package com.together.board.bean;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table( name = "board_image" )
@Getter
@Setter
@ToString
public class ImageDTO {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int seq;

    @Column(
            name = "originalfilename",
            nullable = false
    )
    private String originalfilename;

    @Column(
            name = "uuid",
            nullable = false
    )
    private String uuid;

    @Column(
            name = "directory",
            nullable = false
    )
    private String directory;

    @Column(
            name="board_seq"
            ,nullable=false
    )
    private int boardSeq;
}
