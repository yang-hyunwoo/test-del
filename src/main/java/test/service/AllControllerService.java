package test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.domain.ExtendType;
import test.domain.ExtendTypeEnum;
import test.domain.Users;
import test.domain.dto.ExtendTypeDTO;
import test.domain.dto.UsersDTO;
import test.repository.ExtendTypeRepository;
import test.repository.UsersRepository;


@Service
@RequiredArgsConstructor
public class AllControllerService {

    private final ExtendTypeRepository extendTypeRepository;
    private final UsersRepository usersRepository;

    private final String[] assignment = {"bat", "cmd", "com", "cpl", "exe", "scr", "js"};

    /**
     * 로그인 및 DB 없을 시 DB 저장 로직
     * @param name
     * @return
     */
    @Transactional
    public UsersDTO users(String name) {
        Users users;
        if(usersRepository.findByName(name).isPresent()) {
            users = usersRepository.findByName(name).orElseThrow();
        } else {
            UsersDTO user = UsersDTO.of(name);
            users = usersRepository.save(user.toEntity());
        }
        return UsersDTO.from(users);
    }

    /**
     * 필수 확장자 체크 시 DB 저장 로직
     * @param extendType
     * @param id
     */
    @Transactional
    public void checkbox(String extendType , Long id) {

        Users users = usersRepository.findById(id).orElseThrow();

        if(extendTypeRepository.findByNameAndUsers_Id(extendType, id).isPresent()) {
            ExtendType extendType1 = extendTypeRepository.findByNameAndUsers_Id(extendType, id).orElseThrow();
            extendType1.setUseYn(!extendType1.isUseYn());
        } else {
            ExtendTypeDTO extendTypeDTO = ExtendTypeDTO.ofNec(users, extendType);
            extendTypeRepository.save(extendTypeDTO.toEntity());
        }
    }

    /**
     * 커스텀 확장자
     * @param extendName
     * @param id
     * @return
     */
    @Transactional
    public String customIns(String extendName , Long id) {
        String message="";
        if(extendName.length()>20) {
            return "20자 이내";
        }

        for(String x : assignment) {
           if(x.equals(extendName)) {
               message ="필수 확장자";
               return message;
           }
        }
        Users users = usersRepository.findById(id).orElseThrow();
        if(extendTypeRepository.findByUsers_IdAndUseYnAndTypeOrderByName(id,true, ExtendTypeEnum.C).size()>=200) {
            message = "200개 초과";
            return message;
        }

        if(extendTypeRepository.findByNameAndUsers_Id(extendName, id).isPresent()) {
            ExtendType extendType1 = extendTypeRepository.findByNameAndUsers_Id(extendName, id).orElseThrow();
            if(!extendType1.isUseYn()){
                extendType1.setUseYn(true);
                message="등록 완료";
            } else {
                message = "중복";
            }
        } else {
            ExtendTypeDTO extendTypeDTO = ExtendTypeDTO.ofCho(users, extendName);
            extendTypeRepository.save(extendTypeDTO.toEntity());
            message="등록 완료";
        }

        return message;
    }


    @Transactional
    public String customFull(String extendName , Long id) {
        String message="";
        if(extendName.length()>=20) {
            return "20자 이내";
        }
        
        Users users = usersRepository.findById(id).orElseThrow();
        if(extendTypeRepository.findByUsers_IdAndUseYnAndTypeOrderByName(id,true, ExtendTypeEnum.C).size()>=200) {
            message = "200개 초과";
            return message;
        }

        int size = extendTypeRepository.findByUsers_IdAndUseYnAndTypeOrderByName(id, true, ExtendTypeEnum.C).size();
        for (int i = 0; i < 200-size; i++) {
            ExtendTypeDTO extendTypeDTO = ExtendTypeDTO.ofCho(users, extendName+i);
            extendTypeRepository.save(extendTypeDTO.toEntity());
        }
            message="등록 완료";

        return message;
    }


    /**
     * 커스텀 확장자 useYn false
     * @param extendId
     * @param userId
     */
    @Transactional
    public void customDel(Long extendId , Long userId) {
        if(extendTypeRepository.findByIdAndUsers_Id(extendId, userId).isPresent()) {
            ExtendType extendType1 = extendTypeRepository.findByIdAndUsers_Id(extendId, userId).orElseThrow();
            extendType1.setUseYn(false);
        }
    }

}
