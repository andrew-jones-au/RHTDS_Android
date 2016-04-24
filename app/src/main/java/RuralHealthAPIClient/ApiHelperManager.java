package RuralHealthAPIClient;

import android.app.Application;
import android.content.Context;

/**
 * Created by John on 23/04/2016.
 */
public class ApiHelperManager {

    private static ApiHelper apiHelper;

    public static ApiHelper getApiHelper(Context context)
    {
        if(apiHelper == null)
            apiHelper = new ApiHelper(context);
        return apiHelper;
    }
}
