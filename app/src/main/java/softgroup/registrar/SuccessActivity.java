package softgroup.registrar;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        // display user email
        String userEmail = getIntent().getExtras().getString("user-email");
        TextView textViewUserEmail = (TextView) findViewById(R.id.textView_user_email);
        textViewUserEmail.setText(userEmail);

        // save user session
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user-email", userEmail);
        editor.commit();
    }

    public void onLogoutButtonClick(View view)
    {
        // destroy user session
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user-email", null);
        editor.commit();

        // close activity
        finish();
    }
}
