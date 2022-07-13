// Copyright 2018 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.firebase.firestore;

import static com.google.firebase.firestore.util.Preconditions.checkNotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.util.CustomClassMapper;
import com.google.firestore.v1.Value;
import java.util.Date;
import java.util.Map;

/**
 * A {@code DocumentSnapshot} contains data read from a document in your Cloud Firestore database.
 * The data can be extracted with the {@link #getData()} or {@link #get(String)} methods.
 *
 * <p>If the {@code DocumentSnapshot} points to a non-existing document, {@link #getData()} and its
 * corresponding methods will return {@code null}. You can always explicitly check for a document's
 * existence by calling {@link #exists()}.
 *
 * <p><b>Subclassing Note</b>: Cloud Firestore classes are not meant to be subclassed except for use
 * in test mocks. Subclassing is not supported in production code and new SDK releases may break
 * code that does so.
 */
public class DocumentSnapshot {

  /**
   * Controls the return value for server timestamps that have not yet been set to their final
   * value.
   */
  public enum ServerTimestampBehavior {
    /**
     * Return {@code null} for {@link com.google.firebase.firestore.FieldValue#serverTimestamp
     * ServerTimestamps} that have not yet been set to their final value.
     */
    NONE,

    /**
     * Return local estimates for {@link com.google.firebase.firestore.FieldValue#serverTimestamp
     * ServerTimestamps} that have not yet been set to their final value. This estimate will likely
     * differ from the final value and may cause these pending values to change once the server
     * result becomes available.
     */
    ESTIMATE,

    /**
     * Return the previous value for {@link com.google.firebase.firestore.FieldValue#serverTimestamp
     * ServerTimestamps} that have not yet been set to their final value.
     */
    PREVIOUS;

    static final ServerTimestampBehavior DEFAULT = ServerTimestampBehavio