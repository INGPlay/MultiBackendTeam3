package com.plan.tour.security.apiController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.plan.tour.pathMap.domain.pathmap.paging.PagingResponse;
import com.plan.tour.security.domain.UserInfoPageDto;
import com.plan.tour.security.domain.UserInfoResponse;
import com.plan.tour.security.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminApiController {

    private final AdminService adminService;

    @GetMapping("/user")
    public ResponseEntity<PagingResponse<UserInfoResponse>> getUserInfoList(@RequestParam(defaultValue = "1") int page,
                                                                           @RequestParam(defaultValue = "10") int size,
                                                                           @RequestParam(defaultValue = "role") String orderBy,
                                                                           @RequestParam(defaultValue = "") String searchWord,
                                                                           @RequestParam(defaultValue = "username") String searchOption){
        UserInfoPageDto userInfoPageDto = new UserInfoPageDto(page, size, orderBy, searchWord, searchOption);


        return new ResponseEntity<PagingResponse<UserInfoResponse>>(adminService.getUserInfoList(userInfoPageDto), HttpStatus.OK);
    }

    @DeleteMapping("/user")
    public ResponseEntity<Map<String, Object>> deleteUser(@RequestBody Map<String, Long> requestJson){

        Long userId = requestJson.get("userId");

        adminService.deleteUser(userId);

        HashMap<String, Object> result = new HashMap<>();
        result.put("response", "OK");

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
