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

package hamed_gh.ir.divaaremehrabani.bottombar;

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

/**
 * @deprecated Use {@link com.roughike.bottombar.OnMenuTabClickListener} instead
 */
@Deprecated
public interface OnMenuTabSelectedListener {
    /**
     * The method being called when currently visible {@link BottomBarTab} changes.
     * This listener won't be fired until the user changes the selected item the
     * first time. So you won't get this event when you're just initialized the
     * BottomBar.
     *
     * @param menuItemId the new visible tab's id that
     *                   was assigned in the menu xml resource file.
     */
    void onMenuItemSelected(@IdRes int menuItemId);
}
