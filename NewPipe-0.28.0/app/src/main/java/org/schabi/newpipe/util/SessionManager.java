package org.schabi.newpipe.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
import org.schabi.newpipe.R;

public class SessionManager {

    private static SessionManager instance;
    private int videosWatched;
    private long lastActivityTimestamp;

    private static final long INACTIVITY_TIMEOUT_MS = 15 * 60 * 1000; // 15 minutes

    private SessionManager() {
        videosWatched = 0;
        lastActivityTimestamp = System.currentTimeMillis();
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void onVideoStarted(Context context) {
        long now = System.currentTimeMillis();
        if (now - lastActivityTimestamp > INACTIVITY_TIMEOUT_MS) {
            videosWatched = 0; // Reset counter after inactivity
        }
        videosWatched++;
        lastActivityTimestamp = now;

        checkAndShowReminder(context);
    }

    private void checkAndShowReminder(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean reminderEnabled = prefs.getBoolean("break_reminder_enabled_key", true);
        if (!reminderEnabled) {
            return;
        }

        int videoThreshold = Integer.parseInt(prefs.getString("break_reminder_video_count_key", "3"));

        if (videosWatched > videoThreshold) {
            showReminder(context);
        }
    }

    private void showReminder(Context context) {
        Intent intent = new Intent(context, ReminderActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void resetSession() {
        videosWatched = 0;
        lastActivityTimestamp = System.currentTimeMillis();
    }
}
