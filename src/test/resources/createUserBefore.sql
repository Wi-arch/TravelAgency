delete from bank_user;
delete from user_role;

insert into user_role (user_role_value) values ('Admin');
insert into user_role (user_role_value) values ('User');
insert into bank_user (`bank_user_login`, `bank_user_activation_link`, `bank_user_email`, `bank_user_is_account_activeted`, `bank_user_is_blocked`, `user_role`, `bank_user_password`)
 VALUES ('Login123', 'ActivationLink', 'Login123@gmail.com', true, false, 'User', 'Password12345');
insert into bank_user (`bank_user_login`, `bank_user_activation_link`, `bank_user_email`, `bank_user_is_account_activeted`, `bank_user_is_blocked`, `user_role`, `bank_user_password`)
VALUES ('LoginAdmin123', 'ActivationLinkAdmin', 'LoginAdmin123@gmail.com', true, false, 'Admin', 'PasswordAdmin12345');