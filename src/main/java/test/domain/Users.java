package test.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true)
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;       //순번

    @Setter
    @Column(nullable = false , length = 30)
    private String name;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "users",fetch = FetchType.LAZY)
    private final Set<ExtendType> extendTypes = new LinkedHashSet<>();


    protected Users() {

    }

    private Users(String name) {
        this.name = name;
    }

    public static Users of(String name) {
        return new Users(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Users users)) return false;
        return id != null && id.equals(users.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
