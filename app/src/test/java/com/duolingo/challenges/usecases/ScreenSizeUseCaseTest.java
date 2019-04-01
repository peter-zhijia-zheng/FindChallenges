package com.duolingo.challenges.usecases;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public class ScreenSizeUseCaseTest {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 150;

    @Mock
    private Context context;
    @Mock
    private WindowManager windowManager;
    @Mock
    private Display display;

    private ScreenSizeUseCase useCase;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        useCase = new ScreenSizeUseCase(context);
        initializeDisplay();
    }

    @Test
    public void getScreenWidth_returnsWidthFromDisplay() {
        int screenWidth = useCase.getScreenWidth();

        assertEquals(screenWidth, WIDTH);
    }

    private void initializeDisplay() {
        prepareDisplaySizes();
        when(context.getSystemService(Context.WINDOW_SERVICE)).thenReturn(windowManager);
        when(windowManager.getDefaultDisplay()).thenReturn(display);
    }

    private void prepareDisplaySizes() {
        doAnswer(invocation -> {
            Point point = (Point) invocation.getArguments()[0];
            point.x = WIDTH;
            point.y = HEIGHT;
            return null;
        }).when(display).getSize(any());
    }
}