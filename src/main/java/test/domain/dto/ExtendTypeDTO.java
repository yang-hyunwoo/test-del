package test.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import test.domain.ExtendType;
import test.domain.ExtendTypeEnum;
import test.domain.Users;

@Getter
@Setter
@NoArgsConstructor
public class ExtendTypeDTO {

    Long id;
    Users users;
    String name;
    boolean useYn;
    ExtendTypeEnum type;

    private ExtendTypeDTO(Long id, Users users, String name, boolean useYn, ExtendTypeEnum type) {
        this.id = id;
        this.users = users;
        this.name = name;
        this.useYn = useYn;
        this.type = type;
    }

    public static  ExtendTypeDTO ofNec(Users users,
                                    String name) {
        return new ExtendTypeDTO(
                null,
                users,
                name,
                true,
                ExtendTypeEnum.N
        );
    }

    public static  ExtendTypeDTO ofCho(Users users,
                                       String name) {
        return new ExtendTypeDTO(
                null,
                users,
                name,
                true,
                ExtendTypeEnum.C
        );
    }

    public ExtendType toEntity() {
        return ExtendType.of(users,
                name,
                type,
                useYn);
    }

    public static ExtendTypeDTO from(ExtendType entity) {
        return new ExtendTypeDTO(
                entity.getId(),
                entity.getUsers(),
                entity.getName(),
                entity.isUseYn(),
                entity.getType()
        );
    }


}
