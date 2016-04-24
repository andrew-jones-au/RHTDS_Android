package RuralHealthAPIClient;

import android.support.v4.app.FragmentActivity;

import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * Created by John on 24/04/2016.
 */
public class OrmLiteFragmentActivity extends FragmentActivity {

    private DatabaseHelper databaseHelper = null;

    public OrmLiteFragmentActivity()
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
