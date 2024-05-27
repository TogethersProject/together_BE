package intro.global.bean;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table( name = "globalintro" )
@Getter
@Setter
@ToString
public class GlobalIntroDTO {
    @Column(
            name = "name",
            nullable = false,
            length = 30
    )
    private String name;
    @Column(
            name = "id",
            nullable = false,
            length = 30
    )
    private String id;
    @Column(
            name = "title",
            nullable = false,
            length = 50
    )
    private String title;
    @Column(
            name = "content",
            nullable = false,
            length = 9999999
    )
    private String content;
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long seq;
}
