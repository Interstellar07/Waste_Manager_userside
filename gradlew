/*
 * Copyright 2020 The Android Open Source Project
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

package androidx.activity.result.contract;

import static androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions.EXTRA_PERMISSION_GRANT_RESULTS;

import static java.util.Collections.emptyMap;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.IntentSenderRequest;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * A collection of some standard activity call contracts, as provided by android.
 */
public final class ActivityResultContracts {
    private ActivityResultContracts() {}

    /**
     * An {@link ActivityResultContract} that doesn't do any type conversion, taking raw
     * {@link Intent} as an input and {@link ActivityResult} as an output.
     *
     * Can be used with {@link ActivityResultCaller#registerForActivityResult} to avoid
     * having to manage request codes when calling an activity API for which a type-safe contract is
     * not available.
     */
    public static final class StartActivityForResult
            extends ActivityResultContract<Intent, ActivityResult> {

        /**
         * Key for the extra containing a {@link android.os.Bundle} generated from
         * {@link androidx.core.app.ActivityOptionsCompat#toBundle()} or
         * {@link android.app.ActivityOptions#toBundle()}.
         *
         * This will override any {@link ActivityOptionsCompat} passed to
         * {@link androidx.activity.result.ActivityResultLauncher#launch(Object,
         ActivityOptionsCompat)}
         */
        public static final String EXTRA_ACTIVITY_OPTIONS_BUNDLE = "androidx.activity.result"
                + ".contract.extra.ACTIVITY_OPTIONS_BUNDLE";

        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, @NonNull Intent input) {
            return input;
        }

        @NonNull
        @Override
        public ActivityResult parseResult(
                int resultCode, @Nullable Intent intent) {
            return new ActivityResult(resultCode, intent);
        }
    }

    /**
     * An {@link ActivityResultContract} that calls
     * {@link Activity#startIntentSender(IntentSender, Intent, int, int, int)}.
     *
     * This {@link ActivityResultContract} takes an {@link IntentSenderRequest}, which must be
     * constructed using an {@link IntentSenderRequest.Builder}.
     *
     * If the call to
     * {@link Activity#startIntentSenderForResult(IntentSender, int, Intent, int, int, int)}
     * throws an {@link android.content.IntentSender.SendIntentException} the
     * {@link androidx.activity.result.ActivityResultCallback} will receive an
     * {@link ActivityResult} with an {@link Activity#RESULT_CANCELED} {@code resultCode} and
     * whose intent has the {@link Intent#getAction() action} of
     * {@link #ACTION_INTENT_SENDER_REQUEST} and an extra {@link #EXTRA_SEND_INTENT_EXCEPTION}
     * that contains the thrown exception.
     */
    public static final class StartIntentSenderForResult
            extends ActivityResultContract<IntentSenderRequest, ActivityResult> {

        /**
         * An {@link Intent} action for making a request via the
         * {@link Activity#startIntentSenderForResult} API.
         */
        public static final String ACTION_INTENT_SENDER_REQUEST = "androidx.activity.result"
                + ".contract.action.INTENT_SENDER_REQUEST";

        /**
         * Key for the extra containing the {@link IntentSenderRequest}.
         *
         * @see #ACTION_INTENT_SENDER_REQUEST
         */
        public static final String EXTRA_INTENT_SENDER_REQUEST = "androidx.activity.result"
                + ".contract.extra.INTENT_SENDER_REQUEST";

        /**
         * Key for the extra containing the {@link android.content.IntentSender.SendIntentException}
         * if the call to
         * {@link Activity#startIntentSenderForResult(IntentSender, int, Intent, int, int, int)}
         * fails.
         */
        public static final String EXTRA_SEND_INTENT_EXCEPTION = "androidx.activity.result"
                + ".contract.extra.SEND_INTENT_EXCEPTION";

        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, @NonNull IntentSenderRequest input) {
            return new Intent(ACTION_INTENT_SENDER_REQUEST)
         