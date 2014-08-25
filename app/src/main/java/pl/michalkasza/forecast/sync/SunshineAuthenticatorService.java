package pl.michalkasza.forecast.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Umożliwia SyncAdpaterowi dostęp do autoryzacji.
 */
public class SunshineAuthenticatorService extends Service {
    // Instancja przechowująca obiekt authenticator.
    private SunshineAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        // tworzenie nowego obiektu authenticator.
        mAuthenticator = new SunshineAuthenticator(this);
    }

    /**
     * Połączenie RPC
     * @param intent Intent.
     * @return authenticator jako IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
