package com.duolingo.challenges.usecases;


import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import javax.inject.Inject;

public class ScreenSizeUseCase {
    private Context context;

    @Inject
    public ScreenSizeUseCase(Context context) {
        this.context = context;
    }

    public int getScreenWidth() {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            Display display = windowManager.getDefaultDisplay();

            Point size = new Point();
            display.getSize(size);
            return size.x;
        }
        return 0;
    }

}