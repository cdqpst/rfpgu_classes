package ru.rfpgu.classes;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import ru.rfpgu.classes.controller.NetworkController;
import ru.rfpgu.classes.ui.MainFragment;
import ru.rfpgu.classes.ui.dialogs.ChoiceGroupDialog;
import ru.rfpgu.classes.ui.dialogs.ChoiceTeacherDialog;


public class MyActivity extends ActionBarActivity {
    /**
     * Called when the activity is first created.
     */

    public static final String LOG_TAG = "appLogs";
    public static final String FRAGMENT_TAG = "MainFragment";
    SyncDataBase syncDataBase;
    MainFragment mainFragment;
    SharedPreferences mSettings = null;

    ListView lvGroup;
    DrawerLayout drawerLayout;
    ProgressDialog progress;
    AlertDialog errorDialog = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_frame, mainFragment, FRAGMENT_TAG).commit();
        mSettings = PreferenceManager.getDefaultSharedPreferences(this);
//        syncDataBase = new SyncDataBase();
//        syncDataBase.execute();
//        new DownloadAllData().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mSettings.getBoolean("first_run", true)) {
            Log.d(LOG_TAG,"IT IS FIRST RUN");
            new DownloadAllData().execute();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(errorDialog != null) {
            errorDialog.dismiss();
        }

    }

    class SyncDataBase extends AsyncTask<Void, Void, Void> {

        int serverRequestClasses;
        int serverRequestTeachers;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(MyActivity.this);
            progress.setCanceledOnTouchOutside(false);
            progress.show();
            progress.setContentView(R.layout.progress_bar);
            Log.d(MyActivity.LOG_TAG, "Start \n\r");
        }

        @Override
        protected Void doInBackground(Void... params) {
            NetworkController.delRemovedClasses(getApplicationContext());
            NetworkController.delRemovedTeachers(getApplicationContext());
            NetworkController.updateClasses(getApplicationContext());
            NetworkController.updateTeachers(getApplicationContext());
            serverRequestClasses = NetworkController.addClassesFromServer(getApplicationContext());
            serverRequestTeachers = NetworkController.addTeachersFromServer(getApplicationContext());

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if(serverRequestClasses == 200 && serverRequestTeachers == 200) {
                reloadFragment();
                Toast.makeText(getApplicationContext(),"Обновлено", Toast.LENGTH_LONG).show();
            } else Toast.makeText(getApplicationContext(), "Возникла ошибка при загрузке данных", Toast.LENGTH_LONG).show();

            progress.dismiss();
            Log.d(MyActivity.LOG_TAG, "End \n\r");
        }
    }

    class DownloadAllData extends AsyncTask<Void, Void, Void> {

        int serverRequestClasses;
        int serverRequestTeachers;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(MyActivity.this);
            progress.setCanceledOnTouchOutside(false);
            progress.show();
            progress.setContentView(R.layout.progress_bar);
            Log.d(MyActivity.LOG_TAG, "Start \n\r");
        }

        @Override
        protected Void doInBackground(Void... params) {
            serverRequestClasses = NetworkController.addClassesFromServer(getApplicationContext());
            serverRequestTeachers = NetworkController.addTeachersFromServer(getApplicationContext());
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            reloadFragment();
            progress.dismiss();
            if(serverRequestClasses == 200 && serverRequestTeachers == 200) {
//                ConfigDialog configDialog = new ConfigDialog();
//                configDialog.show(getSupportFragmentManager(), "dialog_settings");
                mSettings.edit().putBoolean("first_run", false).commit();
                showSettingsDialog();
            } else {
                errorDialog = showErrorDialog(getResources().getString(R.string.dialog_download_error_desc), getResources().getString(R.string.dialog_download_error_title));
            }
            Log.d(MyActivity.LOG_TAG, "End \n\r");
        }
    }

    private void reloadFragment() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(currentFragment);
        ft.attach(currentFragment);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                lvGroup = (ListView) findViewById(R.id.lv_group);
                drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                if(drawerLayout.isDrawerOpen(lvGroup)) drawerLayout.closeDrawer(lvGroup);
                else drawerLayout.openDrawer(lvGroup);
                break;
            case R.id.action_refresh:
                syncDataBase = new SyncDataBase();
                syncDataBase.execute();
                break;
            case R.id.action_settings:
                Intent intent = new Intent(this,Settings.class);
                startActivity(intent);
                break;

            default:
                break;
        }

        return true;
    }

    public AlertDialog showErrorDialog(String content, String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(MyActivity.this);
        builder.setTitle(title)
                .setMessage(content)
                .setCancelable(false)
                .setNegativeButton(getResources().getString(R.string.dialog_download_error_replace), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        new DownloadAllData().execute();
                    }
                })
                .setPositiveButton(getResources().getString(R.string.dialog_download_error_exit),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                moveTaskToBack(true);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }

    public AlertDialog showSettingsDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MyActivity.this);
        builder.setTitle(getResources().getString(R.string.dialog_settings_title))
                .setMessage(getResources().getString(R.string.dialog_settings_content))
                .setCancelable(false)
                .setNegativeButton(getResources().getString(R.string.dialog_settings_bt_teacher), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        mSettings.edit().putString(Settings.DEF_STANDING, "1").commit();
                        ChoiceTeacherDialog choiceTeacherDialog = new ChoiceTeacherDialog();
                        choiceTeacherDialog.show(getSupportFragmentManager(), "dialog_teachers");
                    }
                })
                .setPositiveButton(getResources().getString(R.string.dialog_settings_bt_student),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                mSettings.edit().putString(Settings.DEF_STANDING, "0").commit();
                                ChoiceGroupDialog choiceGroupDialog = new ChoiceGroupDialog();
                                choiceGroupDialog.show(getSupportFragmentManager(), "dialog_groups");
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }

}
