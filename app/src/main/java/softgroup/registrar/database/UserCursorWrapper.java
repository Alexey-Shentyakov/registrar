package softgroup.registrar.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import softgroup.registrar.database.pojo.User;

class UserCursorWrapper extends CursorWrapper {
    UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    User getUser()
    {
        String email = getString(getColumnIndex(DatabaseSchema.Users.Columns.EMAIL));
        String passwordHash = getString(getColumnIndex(DatabaseSchema.Users.Columns.PASSWORD_HASH));

        return new User(email, passwordHash);
    }
}
