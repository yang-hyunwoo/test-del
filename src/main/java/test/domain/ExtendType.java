package test.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Entity
@Table(indexes = {
        @Index(columnList = "name"),
        @Index(columnList = "users_id")
})
public class ExtendType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;       //순번

    @Setter
    @ManyToOne(optional = false)
    private Users users; // 유저 정보 (ID)

    @Setter
    @Column(nullable = false , length = 20)
    private String name;        //확장자 이름

    @Setter
    @Enumerated(EnumType.STRING)
    private ExtendTypeEnum type;        //타입

    @Setter
    private boolean useYn;


    protected ExtendType() {

    }

    public ExtendType(Users users,
                      String name,
                      ExtendTypeEnum type,
                      boolean useYn) {
        this.users = users;
        this.name = name;
        this.type = type;
        this.useYn = useYn;
    }

    public static ExtendType of(Users users,
                                String name,
                                ExtendTypeEnum type,
                                boolean useYn) {
        return new ExtendType(users,
                name,
                type,
                useYn);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExtendType extendType)) return false;
        return id != null && id.equals(extendType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }





}
