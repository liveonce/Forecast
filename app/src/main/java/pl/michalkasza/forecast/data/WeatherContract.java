package pl.michalkasza.forecast.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Definicje tabel i kolumn dla bazy danych prognoz.
 */
public class WeatherContract {

    // CONTENT_AUTHORITY to nazwa dla "dostawcy" zawartości (danych), która gwarantuje unikalność w
    // urządzeniu. Jest jak domena dla strony internetowej.
    public static final String CONTENT_AUTHORITY = "pl.michalkasza.forecast";

    // CONTENT_AUTHORITY zostanie użyty przy tworzeniu bazy wszystkich URI (URL + URN),
    // wykorzystanych podczas pobierania danych z openweathermap.com
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_WEATHER = "weather";
    public static final String PATH_LOCATION = "location";

    // Format przechowywania dat w bazie, oraz możliwość konwersji z powrotem do obiektu typu data.
    public static final String DATE_FORMAT = "yyyyMMdd";

    /**
     * Konwersja data -> string (łatwiejsze porównanie)
     * @param date obiekt typu data
     * @return bardziej przyjazna forma daty (forma zdefiniowana w DATE_FORMAT)
     */
    public static String getDbDateString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date);
    }

    /**
     * Konwersja dateText -> reprezentacja "czasu" w Unix
     * @param dateText wejściowy string z datą
     * @return obiekt Date
     */
    public static Date getDateFromDb(String dateText) {
        SimpleDateFormat dbDateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            return dbDateFormat.parse(dateText);
        } catch ( ParseException e ) {
            e.printStackTrace();
            return null;
        }
    }

    /* Klasa wewnętrzna, która określa zawartość tabeli lokalizacji */
    public static final class LocationEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOCATION).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;
        public static final String TABLE_NAME = "location";

        // To będziemy wysyłać do openweathermap w zapytaniu.
        public static final String COLUMN_LOCATION_SETTING = "location_setting";

        // Nazwa miejsc do łatwiejszego rozpoznawania (dla usera ofc...)
        public static final String COLUMN_CITY_NAME = "city_name";

        // Szerokość i długość geograficzna dla intentu z mapą.
        public static final String COLUMN_COORD_LAT = "coord_lat";
        public static final String COLUMN_COORD_LONG = "coord_long";

        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    /* Klasa wewnętrzna, która określa zawartość tabeli pogody */
    public static final class WeatherEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_WEATHER).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_WEATHER;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_WEATHER;

        public static final String TABLE_NAME = "weather";

        // FOREIGN_KEY do tabeli z lokalizacją.
        public static final String COLUMN_LOC_KEY = "location_id";
        // Data jako String (yyyy-MM-dd).
        public static final String COLUMN_DATETEXT = "date";
        // ID pogody, by wiedzieć której ikony użyć.
        public static final String COLUMN_WEATHER_ID = "weather_id";
        // Krótki opis pogody (clear, rain, ...)
        public static final String COLUMN_SHORT_DESC = "short_desc";
        // Minimalna/maksymalna temperatura dnia.
        public static final String COLUMN_MIN_TEMP = "min";
        public static final String COLUMN_MAX_TEMP = "max";
        // Wilgotność.
        public static final String COLUMN_HUMIDITY = "humidity";
        // Ciśnienie.
        public static final String COLUMN_PRESSURE = "pressure";
        // Prędkość wiatru
        public static final String COLUMN_WIND_SPEED = "wind";
        // Stopnie meteorologiczne (0 - północ, 180 - południe)
        public static final String COLUMN_DEGREES = "degrees";


        public static Uri buildWeatherUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildWeatherLocation(String locationSetting) {
            return CONTENT_URI.buildUpon().appendPath(locationSetting).build();
        }

        public static Uri buildWeatherLocationWithStartDate(
                String locationSetting, String startDate) {
            return CONTENT_URI.buildUpon().appendPath(locationSetting)
                    .appendQueryParameter(COLUMN_DATETEXT, startDate).build();
        }

        public static Uri buildWeatherLocationWithDate(String locationSetting, String date) {
            return CONTENT_URI.buildUpon().appendPath(locationSetting).appendPath(date).build();
        }

        public static String getLocationSettingFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static String getDateFromUri(Uri uri) {
            return uri.getPathSegments().get(2);
        }

        public static String getStartDateFromUri(Uri uri) {
            return uri.getQueryParameter(COLUMN_DATETEXT);
        }
    }
}
