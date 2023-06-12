INSERT INTO testtable(test_id, test_name, test_date)
    VALUES(100, 'hongildong', sysdate);

INSERT INTO MemberUser(user_id, user_name, user_pwd, user_email, user_phone, user_role)
VALUES(MEMBERUSER_SEQUENCE.nextval, '나', '1234', 'a@naver.com', '010-1111-1111', 'role_user');