package com.duolingo.challenges.usecases;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class ConnectionUseCaseTest {

    @Mock private Context context;
    @Mock
    private ConnectivityManager manager;
    @Mock private NetworkInfo networkInfo;

    private ConnectionUseCase useCase;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        useCase = new ConnectionUseCase(context);
        when(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(manager);
    }

    @Test
    public void deviceConnectedAndNoNetworkInfo_returnFalse() {
        boolean isConnected = useCase.isDeviceConnected();

        assertFalse(isConnected);
    }

    @Test
    public void deviceConnectedAndNetworkInfoNotConnected_returnFlase() {
        when(manager.getActiveNetworkInfo()).thenReturn(networkInfo);
        when(networkInfo.isConnectedOrConnecting()).thenReturn(false);

        boolean isConnected = useCase.isDeviceConnected();

        assertFalse(isConnected);
    }

    @Test
    public void deviceConnectedAndNetworkInfoConnected_returnTrue() {
        when(manager.getActiveNetworkInfo()).thenReturn(networkInfo);
        when(networkInfo.isConnectedOrConnecting()).thenReturn(true);

        boolean isConnected = useCase.isDeviceConnected();

        assertTrue(isConnected);
    }
}