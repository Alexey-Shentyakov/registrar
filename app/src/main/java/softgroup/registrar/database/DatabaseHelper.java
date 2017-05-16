package softgroup.registrar.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import softgroup.registrar.database.pojo.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context)
    {
        super(context.getApplicationContext(), DatabaseSchema.NAME, null, DatabaseSchema.VERSION);
    }

    /**
     * insert new user into database
     * @param newUser new user
     * @return true if success
     */
    public boolean UsersTable_InsertNewUser(User newUser)
    {
        long result = 0;

        if (newUser != null)
        {
            ContentValues cv = new ContentValues();
            cv.put(DatabaseSchema.Users.Columns.EMAIL, newUser.getEmail());
            cv.put(DatabaseSchema.Users.Columns.PASSWORD_HASH, newUser.getPasswordHash());

            SQLiteDatabase db = this.getWritableDatabase();
            result = db.insert(DatabaseSchema.Users.NAME, DatabaseSchema.Users.Columns.EMAIL, cv);
            db.close();
        }

        return result > 0;
    }

    /**
     * get user from database by email
     * @param email user email
     * @return User object or null if not in DB
     */
    public User UsersTable_getUserByEmail(String email)
    {
        User user = null;

        if (email != null)
        {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseSchema.Users.NAME + " WHERE " + DatabaseSchema.Users.Columns.EMAIL + "=?", new String[]{email});

            if (cursor.getCount() > 0)
            {
                cursor.moveToFirst();
                user = new UserCursorWrapper(cursor).getUser();
            }

            cursor.close();
            db.close();
        }

        return user;
    }

    /**
     * create table for users, create index on email, add some dummy data
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // create users table
        sqLiteDatabase.execSQL(
            "CREATE TABLE " + DatabaseSchema.Users.NAME +
            " (" +
            DatabaseSchema.Users.Columns.EMAIL + " TEXT NOT NULL, " +
            DatabaseSchema.Users.Columns.PASSWORD_HASH + " TEXT NOT NULL" +
            ");"
        );

        // create index on email column
        sqLiteDatabase.execSQL("CREATE INDEX email_index ON " + DatabaseSchema.Users.NAME + " (" + DatabaseSchema.Users.Columns.EMAIL + ");");

        // insert test users
        ContentValues cv = new ContentValues();

        cv.put(DatabaseSchema.Users.Columns.EMAIL, "a@b.c");
        cv.put(DatabaseSchema.Users.Columns.PASSWORD_HASH, "ABC");
        sqLiteDatabase.insert(DatabaseSchema.Users.NAME, DatabaseSchema.Users.Columns.EMAIL, cv);

        cv.put(DatabaseSchema.Users.Columns.EMAIL, "d@e.f");
        cv.put(DatabaseSchema.Users.Columns.PASSWORD_HASH, "DEF");
        sqLiteDatabase.insert(DatabaseSchema.Users.NAME, DatabaseSchema.Users.Columns.EMAIL, cv);

        cv.put(DatabaseSchema.Users.Columns.EMAIL, "g@h.i");
        cv.put(DatabaseSchema.Users.Columns.PASSWORD_HASH, "GHI");
        sqLiteDatabase.insert(DatabaseSchema.Users.NAME, DatabaseSchema.Users.Columns.EMAIL, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
