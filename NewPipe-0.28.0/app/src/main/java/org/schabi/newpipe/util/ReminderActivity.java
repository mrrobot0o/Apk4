package org.schabi.newpipe.util;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.schabi.newpipe.R;

public class ReminderActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        setFinishOnTouchOutside(false);

        Button okButton = findViewById(R.id.ok_button);
        Button continueWatchingButton = findViewById(R.id.continue_watching_button);

        View.OnClickListener listener = v -> {
            SessionManager.getInstance().resetSession();
            finish();
        };

        okButton.setOnClickListener(listener);
        continueWatchingButton.setOnClickListener(listener);
    }

    @Override
    public void onBackPressed() {
        // Prevent dismissing with the back button
        // The user must choose one of the options.
    }
}
