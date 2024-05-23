package org.example.finalproject.Project.demo.email.DTO;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmailDTO {


    private Long id ;
    private String email ;
    private String code ;
    private String name ;
    private String verification ;
    private String expire_at;


}
