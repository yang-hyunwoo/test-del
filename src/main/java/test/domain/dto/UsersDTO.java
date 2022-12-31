package test.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import test.domain.ExtendType;
import test.domain.Users;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class UsersDTO {

    Long id;
    String name;
    Set<ExtendType> extendTypes;

    private UsersDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    private UsersDTO(Long id, String name, Set<ExtendType> extendTypes) {
        this.id = id;
        this.name = name;
        this.extendTypes = extendTypes;
    }

    public static UsersDTO of(
            String name)
    {
        return new UsersDTO(null,name);
    }

    public Users toEntity() {
        return Users.of(name);
    }

    public static UsersDTO from(Users entity) {
        return new UsersDTO(
                entity.getId(),
                entity.getName(),
                entity.getExtendTypes().stream().filter(extendType -> extendType.isUseYn()==true).collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }


}
