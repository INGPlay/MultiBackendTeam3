package multi.backend.project.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import multi.backend.project.pathMap.domain.pathmap.paging.PagingResponse;
import multi.backend.project.security.domain.UserInfoPageDto;
import multi.backend.project.security.domain.UserInfoResponse;
import multi.backend.project.security.mapper.UserMapper;
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
