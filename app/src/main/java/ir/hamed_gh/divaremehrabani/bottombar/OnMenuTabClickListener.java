package ir.hamed_gh.divaremehrabani.bottombar;

import android.support.annotation.IdRes;


/*
 * BottomBar library for Android
 * Copyright (c) 2016 Iiro Krankka (http://github.com/roughike).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public interface OnMenuTabClickListener {
    /**
     * The method being called when currently visible {@link com.roughike.bottombar.BottomBarTab} changes.
     * <p>
     * This listener is fired for the first time after the items have been set and
     * also after a configuration change, such as when screen orientation changes
     * from portrait to landscape.
     *
     * @param menuItemId the new visible tab's id that
     *                   was assigned in the menu xml resource file.
     */
    void onMenuTabSelected(@IdRes int menuItemId);

    /**
     * The method being called when currently visible {@link com.roughike.bottombar.BottomBarTab} is
     * reselected. Use this method for scrolling to the top of your content,
     * as recommended by the Material Design spec
     *
     * @param menuItemId the reselected tab's id that was assigned in the menu
     *                   xml resource file.
     */
    void onMenuTabReSelected(@IdRes int menuItemId);
}
