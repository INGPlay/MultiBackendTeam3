package com.plan.tour.security.apiController;

import com.plan.tour.security.domain.certify.CertifyEmailDto;
import com.plan.tour.security.domain.certify.CertifySessionDto;
import com.plan.tour.security.domain.certify.CertifyStatus;
import com.plan.tour.security.domain.certify.RequestEmailDto;
import com.plan.tour.security.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
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

        // 인증을 3번 실패한 경우
        if (certifySessionDto.getCertifyStatus().equals(CertifyStatus.BANNED)){
            HashMap<String, Object> response = new HashMap<>();
            response.put("response", "금지된 요청입니다");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        // 이미 인증된 경우
        if (certifySessionDto.getCertifyStatus().equals(CertifyStatus.CERTIFIED)){
            HashMap<String, Object> response = new HashMap<>();
            response.put("response", "이전에 인증이 완료된 요청입니다");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        // 세션의 인증정보와 일치하지 않는 경우
        if (!certifyEmailDto.getKey().equals(certifySessionDto.getKey())){
            HashMap<String, Object> response = new HashMap<>();
            
            certifySessionDto.plusRepeat();

            response.put("response", false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        certifySessionDto.setKey("");
        certifySessionDto.setCertifyStatus(CertifyStatus.CERTIFIED);
        session.setAttribute(certifyEmailDto.getUuid(), certifySessionDto);
        session.setMaxInactiveInterval(5 * 60);

        HashMap<String, Object> response = new HashMap<>();
        response.put("response", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
