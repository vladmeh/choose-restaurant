package com.vladmeh.choosing;

import com.vladmeh.choosing.model.Role;
import com.vladmeh.choosing.model.User;

/**
 * @autor Vladimir Mikhaylov <vladmeh@gmail.com> on 06.06.2018.
 * @link https://github.com/vladmeh/graduation-topjava
 */


public class UserTestData {
    public static final Long START_SEQ = 0L;
    public static final Long USER_ID = START_SEQ;
    public static final Long ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "user", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN, Role.ROLE_USER);

}
