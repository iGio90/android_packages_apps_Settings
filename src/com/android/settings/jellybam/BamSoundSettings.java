package com.android.settings.jellybam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings;

import com.android.settings.jellybam.FlipService;
import com.android.settings.jellybam.HeadphoneService;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

public class BamSoundSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final String PREF_HEADPHONES_PLUGGED_ACTION = "headphone_audio_mode";
    private static final String PREF_BT_CONNECTED_ACTION = "bt_audio_mode";
    private static final String PREF_PHONE_RING_SILENCE = "phone_ring_silence";
    private static final String PREF_LESS_NOTIFICATION_SOUNDS = "less_notification_sounds";

    SharedPreferences prefs;
    ListPreference mHeadphonesPluggedAction;
    ListPreference mBTPluggedAction;
    ListPreference mPhoneSilent;
    ListPreference mAnnoyingNotifications;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_sound);

        addPreferencesFromResource(R.xml.jellybam_sound_settings);
        PreferenceManager.setDefaultValues(mContext, R.xml.jellybam_sound_settings, true);
        prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
	ContentResolver resolver = getContentResolver();

        mAnnoyingNotifications = (ListPreference) findPreference(PREF_LESS_NOTIFICATION_SOUNDS);
        mAnnoyingNotifications.setOnPreferenceChangeListener(this);
        mAnnoyingNotifications.setValue(Integer.toString(Settings.System.getInt(getActivity()
                .getContentResolver(), Settings.System.MUTE_ANNOYING_NOTIFICATIONS_THRESHOLD,
                0)));

        mPhoneSilent = (ListPreference) findPreference(PREF_PHONE_RING_SILENCE);
        mPhoneSilent.setValue((prefs.getString(PREF_PHONE_RING_SILENCE, "0")));
        mPhoneSilent.setOnPreferenceChangeListener(this);

        if (HeadphoneService.DEBUG)
            mContext.startService(new Intent(mContext, HeadphoneService.class));

        if (FlipService.DEBUG)
            mContext.startService(new Intent(mContext, FlipService.class));
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
            Preference preference) {
              return super.onPreferenceTreeClick(preferenceScreen, preference);
     }

    private void toggleFlipService() {
        if (FlipService.isStarted()) {
            mContext.stopService(new Intent(mContext, FlipService.class));
        }
        mContext.startService(new Intent(mContext, FlipService.class));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mAnnoyingNotifications) {
            int val = Integer.parseInt((String) newValue);
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.MUTE_ANNOYING_NOTIFICATIONS_THRESHOLD, val);
            return true;

        } else if (preference == mPhoneSilent) {
            int val = Integer.parseInt((String) newValue);
            if (val != 0) {
                toggleFlipService();
            }
            return true;
        }
        return false;
    }
}
