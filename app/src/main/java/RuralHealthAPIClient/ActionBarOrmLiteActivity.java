package RuralHealthAPIClient;

import android.support.v7.app.ActionBarActivity;

import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * Created by John on 23/04/2016.
 */
public class ActionBarOrmLiteActivity extends ActionBarActivity {

    private DatabaseHelper databaseHelper = null;

    public ActionBarOrmLiteActivity()
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
