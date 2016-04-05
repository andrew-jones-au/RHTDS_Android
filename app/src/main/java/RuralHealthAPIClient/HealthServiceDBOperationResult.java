package RuralHealthAPIClient;

import android.database.Cursor;

/**
 * Created by John on 3/04/2016.
 */
public class HealthServiceDBOperationResult {
    private Cursor cursor;
    private long rowId;

    public Cursor getCursor() {
        return cursor;
    }
    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public long getRowId() {
        return rowId;
    }

    public void setRowId(long rowId) {
        this.rowId = rowId;
    }
}
