package RuralHealthAPIClient;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;

import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * Created by John on 23/04/2016.
 */
public class OrmLiteActivity extends Activity {

    private DatabaseHelper databaseHelper = null;

    public OrmLiteActivity()
    {
        super();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    protected DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper =
                    OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }
}
