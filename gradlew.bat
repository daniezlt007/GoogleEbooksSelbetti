/*
 * Copyright (C) 2010 The Android Open Source Project
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

package android.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * @hide
 */
public class PreferenceFrameLayout extends FrameLayout {
    private static final int DEFAULT_BORDER_TOP = 0;
    private static final int DEFAULT_BORDER_BOTTOM = 0;
    private static final int DEFAULT_BORDER_LEFT = 0;
    private static final int DEFAULT_BORDER_RIGHT = 0;
    private final int mBorderTop;
    private final int mBorderBottom;
    private final int mBorderLeft;
    private final int mBorderRight;
    private boolean mPaddingApplied;

    public PreferenceFrameLayout(Context context) {
        this(context, null);
    }

    public PreferenceFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, com.android.internal.R.attr.preferenceFrameLayoutStyle);
    }

    public PreferenceFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PreferenceFrameLayout(
            Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        final TypedArray a = context.obtainStyledAttributes(attrs,
                com.android.internal.R.styleable.PreferenceFrameLayout, defStyleAttr, defStyleRes);

        float density = context.getResources().getDisplayMetrics().density;
        int defaultBorderTop = (int) (density * DEFAULT_BORDER_TOP + 0.5f);
        int defaultBottomPadding = (int) (density * DEFAULT_BORDER_BOTTOM + 