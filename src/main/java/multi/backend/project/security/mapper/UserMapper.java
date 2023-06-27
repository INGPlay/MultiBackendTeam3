package multi.backend.project.security.mapper;

import multi.backend.project.security.domain.UserDto;
import multi.backend.project.security.domain.UserInfoPageDto;
import multi.backend.project.security.domain.UserInfoResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("Select user_name, user_pwd, user_email, user_phone, user_role \n" +
            "from MemberUser \n" +
            "where user_name = #{username}")
    UserDto selectUser(String username);

    @Insert("Insert into MemberUser (user_id, user_name, user_pwd, user_email, user_phone, user_role) \n" +
            "values ( \n" +
            "   MemberUser_Sequence.nextval, #{username}, #{password}, #{email}, #{phone}, #{role} \n" +
            ")")
    void insertUser(UserDto userDto);

    @Select("select user_name, user_pwd, user_email, user_phone, user_role \n" +
            "from MemberUser \n" +
            "where user_email = #{useremail}")
    UserDto selectUserByUserEmail(String useremail);

    @Select("select user_name, user_pwd, user_email, user_phone, user_role \n" +
            "from MemberUser \n" +
            "where user_phone = #{userphone}")
    UserDto selectUserByUserPhone(String userphone);

    @Update("UPDATE MemberUser \n" +
            "SET \n" +
            "user_pwd = #{password}\n" +
            "where user_name = #{username}")
    void updateUserPassword(@Param("username") String username, @Param("password") String password);


    @Delete("Delete from MemberUser m where m.user_id = ${userId}")
    void deleteUser(Long userId);

    // xml 매핑
    List<UserInfoResponse> selectUserList(UserInfoPageDto userInfoPageDto);
}
