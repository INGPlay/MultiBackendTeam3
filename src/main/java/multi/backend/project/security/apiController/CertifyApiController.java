package multi.backend.project.security.apiController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import multi.backend.project.security.domain.certify.CertifyEmailDto;
import multi.backend.project.security.domain.certify.CertifySessionDto;
import multi.backend.project.security.domain.certify.RequestEmailDto;
import multi.backend.project.security.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/api/cert")
@RequiredArgsConstructor
public class CertifyApiController {
    private final EmailService emailService;

    @PostMapping("/email")
    public ResponseEntity<Map<String, Object>> requestEmailCertification(@RequestBody RequestEmailDto requestEmailDto,
                                                                         HttpServletRequest request){

        HttpSession session = request.getSession();

        String uuid = String.valueOf(UUID.randomUUID());
        String key = emailService.sendMessageWithKey(requestEmailDto.getEmail());

        CertifySessionDto certifySessionDto = new CertifySessionDto(key);

        session.setAttribute(uuid, certifySessionDto);
        session.setMaxInactiveInterval(3 * 60);   // 3분

        HashMap<String, Object> response = new HashMap<>();
        response.put("uuid", uuid);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/email")
    public ResponseEntity<Map<String, Object>> certifyEmail(@RequestBody CertifyEmailDto certifyEmailDto,
                                                            HttpServletRequest request){
        HttpSession session = request.getSession();

        Object certifyInform = session.getAttribute(certifyEmailDto.getUuid());
        
        // UUID에 해당하는 세션이 없는 경우
        if (certifyInform == null){

            HashMap<String, Object> response = new HashMap<>();
            response.put("response", false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        CertifySessionDto certifySessionDto = (CertifySessionDto) certifyInform;

        // 이미 인증된 경우
        if (certifySessionDto.isCertified()){
            HashMap<String, Object> response = new HashMap<>();
            response.put("response", "이전에 인증이 완료된 요청입니다");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        // 세션의 인증정보와 일치하지 않는 경우
        if (!certifyEmailDto.getKey().equals(certifySessionDto.getKey())){
            HashMap<String, Object> response = new HashMap<>();
            response.put("response", false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        certifySessionDto.setKey("");
        certifySessionDto.setCertified(true);
        session.setAttribute(certifyEmailDto.getUuid(), certifySessionDto);
        session.setMaxInactiveInterval(5 * 60);

        HashMap<String, Object> response = new HashMap<>();
        response.put("response", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
