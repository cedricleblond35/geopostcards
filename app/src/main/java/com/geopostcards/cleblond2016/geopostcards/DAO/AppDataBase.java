package com.geopostcards.cleblond2016.geopostcards.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.geopostcards.cleblond2016.geopostcards.BO.Media;
import com.geopostcards.cleblond2016.geopostcards.BO.PostCard;
import com.geopostcards.cleblond2016.geopostcards.BO.User;

import java.util.List;

public abstract class AppDataBase extends RoomDatabase {

    private static AppDataBase sInstance;

    @VisibleForTesting
    public static final String DATABASE_NAME = "geopostcardsDB";

    public abstract UserDAO userDAO();
    public abstract PostCardDAO postCardDAO();
    public abstract MediaDAO mediaDAO();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDataBase getInstance(final Context context, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AppDataBase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    private static AppDataBase buildDatabase(final Context appContext, final AppExecutors executors) {
        return Room.databaseBuilder(appContext, AppDataBase.class, DATABASE_NAME).addCallback(new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                executors.diskIO().execute(() -> {
                    // Add a delay to simulate a long-running operation
                    addDelay();
                    // Generate the data for pre-population
                    AppDataBase database = AppDataBase.getInstance(appContext, executors);
                    List<User> users = DataGenerator.generateUsers();
                    List<PostCard> postCards = DataGenerator.generatePostCards();
                    List<Media> medias = DataGenerator.generateMediaForPostCards();

                    insertData(database, users, postCards, medias);
                    // notify that the database was created and it's ready to be used
                    database.setDatabaseCreated();
                });
            }
        }).build();
    }

    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    private static void insertData(final AppDataBase database, final List<User> users,
                                   final List<PostCard> postCards, final List<Media> medias) {
        database.runInTransaction(() -> {
            database.userDAO().insertAll(users);
            database.postCardDAO().insertAll(postCards);
            database.mediaDAO().insertAll(medias);
        });
    }

    private static void addDelay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignored) {
        }
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}
