package multi.backend.project.security.apiController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class TestRoleController {

    @GetMapping("/user")
    public String user(){

        return "user_OK";
    }

    @GetMapping("/manager")
    public String manager(){

        return "manager_OK";
    }

    @GetMapping("/admin")
    public String admin(){

        return "admin_OK";
    }

}
