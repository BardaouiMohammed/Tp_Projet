package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Bdd extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bdd.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_STATE = "Etat"; // Renamed to COLUMN_STATE
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_VILLE = "ville";

    public static final String TABLE_ANNONCE = "annonce";
    public static final String COLUMN_ANNONCE_ID = "_id";
    public static final String COLUMN_TITRE = "titre";
    public static final String COLUMN_CATEGORIE = "categorie";
    public static final String COLUMN_SECTEUR = "secteur";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_ANNONCE_VILLE = "ville";

    private static final String SQL_CREATE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_EMAIL + " TEXT," +
                    COLUMN_STATE + " TEXT," + // Changed from COLUMN_etat
                    COLUMN_PASSWORD + " TEXT," +
                    COLUMN_VILLE + " TEXT)";

    private static final String SQL_CREATE_ANNONCE =
            "CREATE TABLE " + TABLE_ANNONCE + " (" +
                    COLUMN_ANNONCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_TITRE + " TEXT," +
                    COLUMN_CATEGORIE + " TEXT," +
                    COLUMN_SECTEUR + " TEXT," +
                    COLUMN_DESCRIPTION + " TEXT," +
                    COLUMN_ANNONCE_VILLE + " TEXT)";

    public Bdd(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USERS);
        db.execSQL(SQL_CREATE_ANNONCE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANNONCE);
        onCreate(db);
    }

    public long addUser(String email, String password, String ville) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_VILLE, ville);
        long newRowId = -1;
        try {
            newRowId = db.insert(TABLE_USERS, null, values);
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error inserting user: " + e.getMessage());
        } finally {
            db.close();
        }
        return newRowId;
    }

    public long addAnnonce(String titre, String categorie, String secteur, String description, String ville) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITRE, titre);
        values.put(COLUMN_CATEGORIE, categorie);
        values.put(COLUMN_SECTEUR, secteur);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_ANNONCE_VILLE, ville);
        long newRowId = -1;
        try {
            newRowId = db.insert(TABLE_ANNONCE, null, values);
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error inserting annonce: " + e.getMessage());
        } finally {
            db.close();
        }
        return newRowId;
    }

    public boolean verifierUtilisateur(String email, String motDePasse) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] colonnes = {COLUMN_ID, COLUMN_EMAIL, COLUMN_PASSWORD};
        String selection = COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {email, motDePasse};
        Cursor cursor = null;
        boolean utilisateurExiste = false;

        try {
            cursor = db.query(TABLE_USERS, colonnes, selection, selectionArgs, null, null, null);
            utilisateurExiste = cursor.moveToFirst();
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error querying user: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return utilisateurExiste;
    }
    public int getCountAnnoncesByVille(String ville) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"COUNT(*)"};
        String selection = COLUMN_ANNONCE_VILLE + " = ?";
        String[] selectionArgs = {ville};
        Cursor cursor = null;
        int count = 0;

        try {
            cursor = db.query(TABLE_ANNONCE, columns, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error querying count: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return count;
    }

}
