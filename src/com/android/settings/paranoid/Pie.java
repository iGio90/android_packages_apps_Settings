/*
 * Copyright (C) 2012 ParanoidAndroid Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.paranoid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceClickListener;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

public class Pie extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener {

    private static final String PIE_CONTROLS = "pie_controls";
    private static final String PIE_GRAVITY = "pie_gravity";
    private static final String PIE_MODE = "pie_mode";
    private static final String PIE_SIZE = "pie_size";

    private ListPreference mPieMode;
    private ListPreference mPieSize;
    private ListPreference mPieGravity;
    private CheckBoxPreference mPieControls;

    private Context mContext;
    private int mAllowedLocations;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pie_settings);
        PreferenceScreen prefSet = getPreferenceScreen();
        mContext = getActivity();

        mPieControls = (CheckBoxPreference) findPreference(PIE_CONTROLS);
        mPieControls.setChecked((Settings.System.getInt(mContext.getContentResolver(),
                Settings.System.PIE_CONTROLS, 0) == 1));

        mPieGravity = (ListPreference) prefSet.findPreference(PIE_GRAVITY);
        int pieGravity = Settings.System.getInt(mContext.getContentResolver(),
                Settings.System.PIE_GRAVITY, 3);
        mPieGravity.setValue(String.valueOf(pieGravity));
        mPieGravity.setOnPreferenceChangeListener(this);

        mPieMode = (ListPreference) prefSet.findPreference(PIE_MODE);
        int pieMode = Settings.System.getInt(mContext.getContentResolver(),
                Settings.System.PIE_MODE, 2);
        mPieMode.setValue(String.valueOf(pieMode));
        mPieMode.setOnPreferenceChangeListener(this);

        mPieSize = (ListPreference) prefSet.findPreference(PIE_SIZE);
        String pieSize = Settings.System.getString(mContext.getContentResolver(),
                Settings.System.PIE_SIZE);
        mPieSize.setValue(pieSize != null && !pieSize.isEmpty() ? pieSize : "1");
        mPieSize.setOnPreferenceChangeListener(this);

        checkControls();
    }

    private void checkControls() {
        boolean pieCheck = mPieControls.isChecked();
        mPieGravity.setEnabled(pieCheck);
        mPieMode.setEnabled(pieCheck);
        mPieSize.setEnabled(pieCheck);
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference == mPieControls) {
            Settings.System.putInt(mContext.getContentResolver(),
                    Settings.System.PIE_CONTROLS,
                    mPieControls.isChecked() ? 1 : 0);
            checkControls();
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mPieMode) {
            int pieMode = Integer.valueOf((String) newValue);
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.PIE_MODE, pieMode);
            return true;
        } else if (preference == mPieSize) {
            float pieSize = Float.valueOf((String) newValue);
            Settings.System.putFloat(getActivity().getContentResolver(),
                    Settings.System.PIE_SIZE, pieSize);
            return true;
        } else if (preference == mPieGravity) {
            int pieGravity = Integer.valueOf((String) newValue);
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.PIE_GRAVITY, pieGravity);
            return true;
        }
        return false;
    }
}
