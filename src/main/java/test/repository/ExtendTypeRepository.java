package test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.domain.ExtendType;
import test.domain.ExtendTypeEnum;

import java.util.List;
import java.util.Optional;

public interface ExtendTypeRepository extends JpaRepository<ExtendType, Long> {

    Optional<ExtendType> findByNameAndUsers_Id(String name, Long usersId);

    List<ExtendType> findByUsers_IdAndUseYnAndTypeOrderByName(Long userId, boolean useYn , ExtendTypeEnum type);

    Optional<ExtendType> findByIdAndUsers_Id(Long extendId, Long usersId);

}
