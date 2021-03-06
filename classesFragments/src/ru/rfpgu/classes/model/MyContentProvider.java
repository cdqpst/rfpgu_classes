package ru.rfpgu.classes.model;

/**
 * Created with IntelliJ IDEA.
 * User: AlexChe
 * Date: 23.02.14
 * Time: 13:45
 * To change this template use File | Settings | File Templates.
 */
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;


public class MyContentProvider extends ContentProvider {

    private DatabaseCreator dbHelper;

    private static final int ALL_CLASSES = 1;
    private static final int SINGLE_CLASSES = 2;
    private static final int ALL_TEACHERS = 3;
    private static final int SINGLE_TEACHERS = 4;

    // authority is the symbolic name of your provider
    // To avoid conflicts with other providers, you should use
    // Internet domain ownership (in reverse) as the basis of your provider authority.
    private static final String AUTHORITY = "ru.rfpgu.classes";

    // create content URIs from the authority by appending path to database table
    public static final Uri CONTENT_URI_CLASSES =
            Uri.parse("content://" + AUTHORITY + "/" + TableModel.Classes.SQLITE_TABLE);

    public static final Uri CONTENT_URI_TEACHERS =
            Uri.parse("content://" + AUTHORITY + "/" + TableModel.Teachers.SQLITE_TABLE);

    // a content URI pattern matches content URIs using wildcard characters:
    // *: Matches a string of any valid characters of any length.
    // #: Matches a string of numeric characters of any length.
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, TableModel.Classes.SQLITE_TABLE, ALL_CLASSES);
        uriMatcher.addURI(AUTHORITY, TableModel.Classes.SQLITE_TABLE + "/#", SINGLE_CLASSES);
        uriMatcher.addURI(AUTHORITY, TableModel.Teachers.SQLITE_TABLE, ALL_TEACHERS);
        uriMatcher.addURI(AUTHORITY, TableModel.Teachers.SQLITE_TABLE + "/#", SINGLE_TEACHERS);
    }

    // system calls onCreate() when it starts up the provider.
    @Override
    public boolean onCreate() {
        // get access to the database helper
        dbHelper = new DatabaseCreator(getContext());
        return false;
    }

    //Return the MIME type corresponding to a content URI
    @Override
    public String getType(Uri uri) {

        switch (uriMatcher.match(uri)) {
            case ALL_CLASSES:
                return "vnd.android.cursor.dir/vnd.ru.rfpgu.classes" + TableModel.Classes.SQLITE_TABLE;
            case SINGLE_CLASSES:
                return "vnd.android.cursor.item/vnd.ru.rfpgu.classes" + TableModel.Classes.SQLITE_TABLE;
            case ALL_TEACHERS:
                return "vnd.android.cursor.dir/vnd.ru.rfpgu.classes" + TableModel.Teachers.SQLITE_TABLE;
            case SINGLE_TEACHERS:
                return "vnd.android.cursor.item/vnd.ru.rfpgu.classes" + TableModel.Teachers.SQLITE_TABLE;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    // The insert() method adds a new row to the appropriate table, using the values
    // in the ContentValues argument. If a column name is not in the ContentValues argument,
    // you may want to provide a default value for it either in your provider code or in
    // your database schema.
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = 0;
        Uri myUri = null;

        switch (uriMatcher.match(uri)) {
            case ALL_CLASSES:
                id = db.insert(TableModel.Classes.SQLITE_TABLE, null, values);
                myUri = Uri.parse(CONTENT_URI_CLASSES + "/" + id);
                break;
            case SINGLE_CLASSES:
                //do nothing
                break;
            case ALL_TEACHERS:
                id = db.insert(TableModel.Teachers.SQLITE_TABLE, null, values);
                myUri = Uri.parse(CONTENT_URI_TEACHERS + "/" + id);
                break;
            case SINGLE_TEACHERS:
                //do nothing
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return myUri;
    }

    // The query() method must return a Cursor object, or if it fails,
    // throw an Exception. If you are using an SQLite database as your data storage,
    // you can simply return the Cursor returned by one of the query() methods of the
    // SQLiteDatabase class. If the query does not match any rows, you should return a
    // Cursor instance whose getCount() method returns 0. You should return null only
    // if an internal error occurred during the query process.
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        String id;

        switch (uriMatcher.match(uri)) {
            case ALL_CLASSES:
                queryBuilder.setTables(TableModel.Classes.SQLITE_TABLE);
                break;
            case SINGLE_CLASSES:
                queryBuilder.setTables(TableModel.Classes.SQLITE_TABLE);
                id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(TableModel.Classes.KEY_ROW_ID + "=" + id);
                break;
            case ALL_TEACHERS:
                queryBuilder.setTables(TableModel.Teachers.SQLITE_TABLE);
                break;
            case SINGLE_TEACHERS:
                queryBuilder.setTables(TableModel.Teachers.SQLITE_TABLE);
                id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(TableModel.Teachers.KEY_ROW_ID + "=" + id);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        return cursor;

    }

    // The delete() method deletes rows based on the seletion or if an id is
    // provided then it deleted a single row. The methods returns the numbers
    // of records delete from the database. If you choose not to delete the data
    // physically then just update a flag here.
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String id;
        int deleteCount = 0;

        switch (uriMatcher.match(uri)) {
            case ALL_CLASSES:
                //do nothing
                break;
            case SINGLE_CLASSES:
                id = uri.getPathSegments().get(1);
                selection = TableModel.Classes.KEY_ROW_ID + "=" + id
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
                deleteCount = db.delete(TableModel.Classes.SQLITE_TABLE, selection, selectionArgs);
                break;
            case ALL_TEACHERS:
                //do nothing
                break;
            case SINGLE_TEACHERS:
                id = uri.getPathSegments().get(1);
                selection = TableModel.Teachers.KEY_ROW_ID + "=" + id
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
                deleteCount = db.delete(TableModel.Teachers.SQLITE_TABLE, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return deleteCount;
    }

    // The update method() is same as delete() which updates multiple rows
    // based on the selection or a single row if the row id is provided. The
    // update method returns the number of updated rows.
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String id;
        int updateCount = 0;

        switch (uriMatcher.match(uri)) {
            case ALL_CLASSES:
                updateCount = db.update(TableModel.Classes.SQLITE_TABLE, values, selection, selectionArgs);
                break;
            case SINGLE_CLASSES:
                id = uri.getPathSegments().get(1);
                selection = TableModel.Classes.KEY_ROW_ID + "=" + id
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
                updateCount = db.update(TableModel.Classes.SQLITE_TABLE, values, selection, selectionArgs);
                break;
            case ALL_TEACHERS:
                updateCount = db.update(TableModel.Teachers.SQLITE_TABLE, values, selection, selectionArgs);
                break;
            case SINGLE_TEACHERS:
                id = uri.getPathSegments().get(1);
                selection = TableModel.Teachers.KEY_ROW_ID + "=" + id
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
                updateCount = db.update(TableModel.Teachers.SQLITE_TABLE, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return updateCount;
    }

}