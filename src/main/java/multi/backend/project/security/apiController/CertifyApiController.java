package multi.backend.project.security.apiController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<Map<String, Object>> requestEmailCertification(@RequestParam String email,
                                                                         HttpServletRequest request){

        HttpSession session = request.getSession();

        String uuid = String.valueOf(UUID.randomUUID());
        String key = emailService.sendMessageWithKey(email);

        session.setAttribute(uuid, key);
        session.setMaxInactiveInterval(3 * 60);   // 3분

        HashMap<String, Object> response = new HashMap<>();
        response.put("uuid", uuid);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/email")
    public ResponseEntity<Map<String, Object>> certifyEmail(@RequestParam String uuid,
                                                            @RequestParam String key,
                                                            HttpServletRequest request){
        HttpSession session = request.getSession();

        Object getKey = session.getAttribute(uuid);
        if (getKey == null){

            HashMap<String, Object> response = new HashMap<>();
            response.put("response", false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        String keyInSession = (String) getKey;

        if (!key.equals(keyInSession)){
            HashMap<String, Object> response = new HashMap<>();
            response.put("response", false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        session.removeAttribute(uuid);
        HashMap<String, Object> response = new HashMap<>();
        response.put("response", false);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
