/*
 * Copyright (C) 2015 The Android Open Source Project
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
package android.os;

import android.annotation.NonNull;
import android.annotation.Nullable;

/**
 * @hide Only for use within the system server.
 */
public abstract class UserManagerInternal {
    public interface UserRestrictionsListener {
        /**
         * Called when a user restriction changes.
         *
         * @param userId target user id
         * @param newRestrictions new user restrictions
         * @param prevRestrictions user restrictions that were previously set
         */
        void onUserRestrictionsChanged(int userId, Bundle newRestrictions, Bundle prevRestrictions);
    }

    /**
     * Called by {@link com.android.server.devicepolicy.DevicePolicyManagerService}
     * to set per-user as well as global user restrictions.
     *
     * @param userId target user id for the local restrictions.
     * @param localRestrictions per-user restrictions.
     *     Caller must not change it once passed to this method.
     * @param globalRestrictions global restrictions set by DO.  Must be null when PO changed user
     *     restrictions, in which case global restrictions won't change.
     *     Caller must not change it once passed to this method.
     */
    public abstract void setDevicePolicyUserRestrictions(int userId,
            @NonNull Bundle localRestrictions, @Nullable Bundle globalRestrictions);
    /**
     * Returns the "base" user restrictions.
     *
     * Used by {@link com.android.server.devicepolicy.DevicePolicyManagerService} for upgrading
     * from MNC.
     */
    public abstract Bundle getBaseUserRestrictions(int userId);

    /**
     * Called by {@link com.android.server.devicepolicy.DevicePolicyManagerService} for upgrading
     * from MNC.
     */
    public abstract void setBaseUserRestrictionsByDpmsForMigration(int userId,
            Bundle baseRestrictions);

    /** Return a user restriction. */
    public abstract boolean getUserRestriction(int userId, String key);

    /** Adds a listener to user restriction changes. */
    public abstract void addUserRestrictionsListener(UserRestrictionsListener listener);

    /** Remove a {@link UserRestrictionsListener}. */
    public abstract void removeUserRestrictionsListener(UserRestrictionsListener listener);
}
