package multi.backend.project.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import multi.backend.project.security.domain.ContextDto;
import multi.backend.project.security.domain.UserContext;
import multi.backend.project.security.domain.UserDto;
import multi.backend.project.security.mapper.UserMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserMapper userMapper;

    private final GrantedAuthority ROLE_USER = new SimpleGrantedAuthority("ROLE_USER");

    private final GrantedAuthority ROLE_ADMIN = new SimpleGrantedAuthority("ROLE_ADMIN");

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDto userDto = userMapper.selectUser(username);

        if (userDto == null){
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }

        List<GrantedAuthority> roles = handleRole(userDto.getRole());

        return new UserContext(new ContextDto(userDto.getUsername(), userDto.getPassword()), roles);
    }

    private List<GrantedAuthority> handleRole(String roleString){
        List<GrantedAuthority> roles = new ArrayList<>();

        String[] tokens = roleString.split("_");

        if (tokens[1].equals("USER")){
            roles.add(ROLE_USER);

        } else if (tokens[1].equals("ADMIN")){
            roles.add(ROLE_USER);
            roles.add(ROLE_ADMIN);
        } else {
            log.info("------------------정의되지 않은 권한----------------");
        }

        return roles;
    }
}
