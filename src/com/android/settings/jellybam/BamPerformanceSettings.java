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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.android.settings.jellybam.FlipService;
import com.android.settings.jellybam.HeadphoneService;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

public class BamPerformanceSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final String MEDIA_SCANNER_ON_BOOT = "media_scanner_on_boot";

    private ListPreference mMSOB;

    private final ArrayList<Preference> mAllPrefs = new ArrayList<Preference>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.bamcontrol_performance);

        addPreferencesFromResource(R.xml.jellybam_performance_settings);
	ContentResolver resolver = getContentResolver();

        mMSOB = (ListPreference) findPreference(MEDIA_SCANNER_ON_BOOT);
        mAllPrefs.add(mMSOB);
        mMSOB.setOnPreferenceChangeListener(this);

    }

    private void updateAllOptions() {
        updateMSOBOptions();
     }

    private void resetMSOBOptions() {
        Settings.System.putInt(getActivity().getContentResolver(),
                Settings.System.MEDIA_SCANNER_ON_BOOT, 0);
    }

    private void writeMSOBOptions(Object newValue) {
        Settings.System.putInt(getActivity().getContentResolver(),
                Settings.System.MEDIA_SCANNER_ON_BOOT,
                Integer.valueOf((String) newValue));
        updateMSOBOptions();
    }

    private void updateMSOBOptions() {
        int value = Settings.System.getInt(getActivity().getContentResolver(),
                Settings.System.MEDIA_SCANNER_ON_BOOT, 0);
        mMSOB.setValue(String.valueOf(value));
        mMSOB.setSummary(mMSOB.getEntry());
    }

    private void resetDangerousOptions() {
        resetMSOBOptions();
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
            Preference preference) {
              return super.onPreferenceTreeClick(preferenceScreen, preference);
     }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mMSOB) {
            writeMSOBOptions(newValue);
            return true;
        }
        return false;
    }
}
