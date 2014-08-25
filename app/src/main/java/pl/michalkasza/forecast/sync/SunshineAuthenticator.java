package pl.michalkasza.forecast.sync;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.Bundle;

/**
 * SyncAdapter wymaga obiektu autoryzacji. Przykład wzięty jest w całość ze strony:
 * http://developer.android.com/training/sync-adapters/creating-authenticator.html
 * BTW. Przyda się w przyszłości przy tworzeniu własnych syncadapterów ;-)
 */

public class SunshineAuthenticator extends AbstractAccountAuthenticator {

    public SunshineAuthenticator(Context context) {
        super(context);
    }

    @Override
    public Bundle editProperties(
            AccountAuthenticatorResponse r, String s) {
        throw new UnsupportedOperationException();
    }

    // Zwracamy null, nie dodajemy konta do urządzenia.
    @Override
    public Bundle addAccount(
            AccountAuthenticatorResponse r,
            String s,
            String s2,
            String[] strings,
            Bundle bundle) throws NetworkErrorException {
        return null;
    }

    // Ignorowanie próby potwierdzenia.
    @Override
    public Bundle confirmCredentials(
            AccountAuthenticatorResponse r,
            Account account,
            Bundle bundle) throws NetworkErrorException {
        return null;
    }

    // Nie obsługujemy również tego. (token uwierzytelnienia)
    @Override
    public Bundle getAuthToken(
            AccountAuthenticatorResponse r,
            Account account,
            String s,
            Bundle bundle) throws NetworkErrorException {
        throw new UnsupportedOperationException();
    }
    @Override
    public String getAuthTokenLabel(String s) {
        throw new UnsupportedOperationException();
    }

    // Aktualizacji też nie obsługujemy.
    @Override
    public Bundle updateCredentials(
            AccountAuthenticatorResponse r,
            Account account,
            String s, Bundle bundle) throws NetworkErrorException {
        throw new UnsupportedOperationException();
    }

    // Sprawdzenie funkcji konta: unsupported!
    @Override
    public Bundle hasFeatures(
            AccountAuthenticatorResponse r,
            Account account, String[] strings) throws NetworkErrorException {
        throw new UnsupportedOperationException();
    }
}
