package test.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import test.domain.Response;
import test.domain.dto.UsersDTO;
import test.service.AllControllerService;


@RestController
@RequiredArgsConstructor
@Slf4j
public class AllController {

    private final AllControllerService allControllerService;
    private HttpSession session;



    /**
     * 로그인 로직 , session 저장
     * @param name
     * @return
     */
    @PostMapping("/login")
    public Response<?> user(@RequestParam String name , HttpServletRequest request) {
        if(name.isEmpty()){
            return Response.error("null", "널 값");
        }
        UsersDTO users = allControllerService.users(name.trim());
        session = request.getSession();
        session.setAttribute("userPk",users.getId());
        session.setAttribute("userName",users.getName());
        return Response.success(users);
    }

    /**
     * 필수 확장자 체크박스 클릭시
     * 없으면 생성 있으면 db 저장 반대로 useyn 변환
     * 컬럼 삭제 안한 이유(사용자 히스토리 간직 하기 위해)
     * @param extendType
     */
    @PostMapping("/checkbox")
    public void checkbox(@RequestParam String extendType) {
        long userPk = Long.parseLong(session.getAttribute("userPk").toString());
        allControllerService.checkbox(extendType, userPk);
    }

    /**
     * 커스텀 확장자 추가
     * @param extendType
     */
    @PostMapping("/custom")
    public Response<String> custom(@RequestParam String extendType) {
        if(extendType.isEmpty()){
            return Response.error("null", "널 값");
        }
        long userPk = Long.parseLong(session.getAttribute("userPk").toString());
        String message = allControllerService.customIns(extendType.trim(), userPk);
        if(message.equals("등록 완료")) {
            return Response.success(message);
        } else {
            return Response.error("null",message);
        }
    }

    /**
     * 커스텀 확장자 삭제
     * @param extendId
     */
    @PutMapping("/custom")
    public Response<String> customDel(@RequestParam Long extendId) {
        long userPk = Long.parseLong(session.getAttribute("userPk").toString());
         allControllerService.customDel(extendId, userPk);
        return Response.success("성공");
    }

    /**
     * 커스텀 확장자 추가200개 맞추기
     * @param extendType
     */
    @PostMapping("/custom-full")
    public Response<String> customFull(@RequestParam String extendType) {
        if(extendType.isEmpty()){
            return Response.error("null", "널 값");
        }
        long userPk = Long.parseLong(session.getAttribute("userPk").toString());
        String message = allControllerService.customFull(extendType.trim(), userPk);
        if(message.equals("등록 완료")) {
            return Response.success(message);
        } else {
            return Response.error("null",message);
        }
    }

}
