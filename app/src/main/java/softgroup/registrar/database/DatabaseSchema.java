package softgroup.registrar.database;

class DatabaseSchema {

    static final String NAME = "registrar.db";  // database name
    static final int VERSION = 1;               // database version

    // users table
    static final class Users
    {
        static final String NAME = "users";     // table name

        static final class Columns
        {
            static final String EMAIL = "email";
            static final String PASSWORD_HASH = "password_hash";
        }
    }
}
