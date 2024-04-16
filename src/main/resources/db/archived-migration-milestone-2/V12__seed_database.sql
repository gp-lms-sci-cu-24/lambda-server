INSERT INTO public.users(id, username, password, profile_picture, is_account_non_expired, is_account_non_locked,
                         is_credentials_non_expired, is_enabled)
VALUES (1, 'admin', '$2y$10$29mW8AIZP73kHXC/u2FKw.p7oQcErTJMMWPlusKlnGUfdjWWrGexG', null, true, true, true, true);

INSERT INTO public.user_roles(user_id, roles)
VALUES (1, 'SUPER_ADMIN');

INSERT INTO public.user_roles(user_id, roles)
VALUES (1, 'ADMIN');

INSERT INTO public.departments(id, name, info, image, code)
VALUES (1, 'General', '', null, 'GEN');