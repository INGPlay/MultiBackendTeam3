package multi.backend.project.security.mapper;

import multi.backend.project.security.domain.UserDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
}
