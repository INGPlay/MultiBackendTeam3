package com.plan.tour.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.plan.tour.pathMap.domain.pathmap.paging.PagingResponse;
import com.plan.tour.security.domain.UserInfoPageDto;
import com.plan.tour.security.domain.UserInfoResponse;
import com.plan.tour.security.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserMapper userMapper;

    @Transactional
    public PagingResponse<UserInfoResponse> getUserInfoList(UserInfoPageDto userInfoPageDto){
        List<UserInfoResponse> userInfoResponses = userMapper.selectUserList(userInfoPageDto);

        return new PagingResponse<UserInfoResponse>(userInfoResponses, userInfoPageDto.getPage(), userInfoPageDto.getSize());
    }

    @Transactional
    public void deleteUser(Long userId){
        userMapper.deleteUser(userId);
    }
}
