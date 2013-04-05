/*
 * Copyright (C) 2013 JellyBam project
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

package com.android.settings.jellybam;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import android.util.Log;
import android.view.IWindowManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BAMModder extends SettingsPreferenceFragment {
    private static final String TAG = "BAMModder";

    private static final String BAM_MODDER_LAUNCHERS = "bam_modder_launchers";
    private static final String BAM_MODDER_SOUNDS = "bam_modder_sounds";
    private static final String BAM_MODDER_GRAPHICS = "bam_modder_graphics";
    private static final String BAM_MODDER_MISCS = "bam_modder_miscs";
    private static final String BAM_MODDER_STORE = "bammodderstore";

    private Preference mBamStore;
    private PreferenceScreen mBamLaunchers;
    private PreferenceScreen mBamSounds;
    private PreferenceScreen mBamGraphics;
    private PreferenceScreen mBamMiscs;

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference == mBamStore) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("market://details?id=com.bam.android.bammodder.donor"));
		startActivity(intent);
            return true;
        }
	return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.bammodder);
        PreferenceScreen prefScreen = getPreferenceScreen();

        mBamLaunchers = (PreferenceScreen) prefScreen.findPreference(BAM_MODDER_LAUNCHERS);
        mBamSounds = (PreferenceScreen) prefScreen.findPreference(BAM_MODDER_SOUNDS);
        mBamGraphics = (PreferenceScreen) prefScreen.findPreference(BAM_MODDER_GRAPHICS);
        mBamMiscs = (PreferenceScreen) prefScreen.findPreference(BAM_MODDER_MISCS);
	mBamStore = (Preference) prefScreen.findPreference(BAM_MODDER_STORE);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
