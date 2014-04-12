package ru.rfpgu.classes.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.*;
import android.widget.*;
import ru.rfpgu.classes.MyActivity;
import ru.rfpgu.classes.R;
import ru.rfpgu.classes.Settings;
import ru.rfpgu.classes.controller.DatabaseController;
import ru.rfpgu.classes.model.MyContentProvider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: AlexChe
 * Date: 02.03.14
 * Time: 9:57
 * To change this template use File | Settings | File Templates.
 */
public class MainFragment extends Fragment implements AdapterView.OnItemClickListener, ActionBar.OnNavigationListener {

    private ViewPager pagerClasses;
    private ClassesFragmentAdapter classesFragmentAdapter;
    private TeacherFragmentAdapter teacherFragmentAdapter;
    private ListView lvGroup;
    ArrayList<Integer> groupList;
    ArrayList<String> teacherNameList;
    SharedPreferences mSettings;
    int selectedGroup;
    int selectedTeacher;
    int displayMode = 0;
    int defGroup;
    int defTeacher;

    View rootView;
    ActionBar actionBar;
    DrawerLayout drawerLayout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.main_fragment, null);
        actionBar =((ActionBarActivity) getActivity()).getSupportActionBar();
        groupList = DatabaseController.getGroups(getActivity(), MyContentProvider.CONTENT_URI_CLASSES);
        teacherNameList = DatabaseController.getTeachersNames(getActivity(), MyContentProvider.CONTENT_URI_TEACHERS);
        mSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        findViews();

        defGroup = Integer.valueOf(mSettings.getString(Settings.DEF_GROUP, "0"));
        defTeacher = Integer.valueOf(mSettings.getString(Settings.DEF_TEACHER, "1"));
        displayMode = Integer.valueOf(mSettings.getString(Settings.DEF_STANDING, "0"));
        classesFragmentAdapter = new ClassesFragmentAdapter(getActivity().getSupportFragmentManager(), defGroup, getWeek());
        teacherFragmentAdapter = new TeacherFragmentAdapter(getActivity().getSupportFragmentManager(), defTeacher, getWeek());
        pagerClasses.setOffscreenPageLimit(1);

        if(displayMode == 0) {
            pagerClasses.setAdapter(classesFragmentAdapter);
        } else {
            pagerClasses.setAdapter(teacherFragmentAdapter);
        }


        showGroupsInSlideMenu();
        showListInActionBar();
        homeActions();
        actionBar();

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void findViews() {
        lvGroup = (ListView) rootView.findViewById(R.id.lv_group);
        pagerClasses = (ViewPager) rootView.findViewById(R.id.classes_pager);
        drawerLayout = (DrawerLayout) rootView.findViewById(R.id.drawer_layout);
    }

    private void homeActions() {
        actionBar.setCustomView(R.layout.ab_buttons);
        Button btnHome = (Button) actionBar.getCustomView().findViewById(R.id.btn_home);
        Button btnShare = (Button) actionBar.getCustomView().findViewById(R.id.btn_share);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_home:
                        if(displayMode == 0) {
                            defGroup = Integer.valueOf(mSettings.getString(Settings.DEF_GROUP, "0"));
                            classesFragmentAdapter.group = defGroup;
                            classesFragmentAdapter.week = getWeek();
                            pagerClasses.setAdapter(classesFragmentAdapter);
                            selectDefNavItem(defGroup);
                        } else {
                            defTeacher = Integer.valueOf(mSettings.getString(Settings.DEF_TEACHER, "1"));
                            teacherFragmentAdapter.teacher = defTeacher;
                            teacherFragmentAdapter.week = getWeek();
                            pagerClasses.setAdapter(teacherFragmentAdapter);
                            selectDefNavItem(defTeacher);
                        }
                        Log.d(MyActivity.LOG_TAG, "DAY ==  " + getDayOfWeek());
                        pagerClasses.setCurrentItem(getDayOfWeek());
                        break;
                    case R.id.btn_share:
                        Intent share = new Intent(android.content.Intent.ACTION_SEND);
                        share.setType("text/plain");
                        share.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_body));
                        startActivity(Intent.createChooser(share, getResources().getString(R.string.share)));
                        break;
                }
            }
        };

        btnHome.setOnClickListener(onClickListener);
        btnShare.setOnClickListener(onClickListener);
    }

    public void actionBar(){

//
//        actionBar.setCustomView(R.layout.ab_et);
//        EditText search = (EditText) actionBar.getCustomView().findViewById(R.id.searchfield);
//        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//
//            @Override
//            public boolean onEditorAction(TextView v, int actionId,
//                                          KeyEvent event) {
//                Toast.makeText(getActivity(), "Search triggered",
//                        Toast.LENGTH_LONG).homeActions();
//                return false;
//            }
//        });


        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
//        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

//        Drawable bg = getResources().getDrawable(R.drawable.bg_action_bar_top);
//        actionBar.setBackgroundDrawable(bg);

//        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout,R.drawable.actionbar_menu, R.string.drawer_open, R.string.drawer_close) {
//            public void onDrawerClosed(View view) {
//                super.onDrawerClosed(view);
//                actionBar.setTitle("Расписание");
//            }
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//                actionBar.setTitle(" Выберите группу");
//            }
//        };
//        drawerLayout.setDrawerListener(mDrawerToggle);

    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        mDrawerToggle.onConfigurationChanged(newConfig);
//    }

    private void showGroupsInSlideMenu() {
        lvGroup.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.sidebar_item, getResources().getStringArray(R.array.left_menu)));
        lvGroup.setOnItemClickListener(this);
    }
    private void showListInActionBar() {
//        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        if(displayMode == 0) {
            if(groupList != null) {
                ArrayAdapter<Integer> abAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_list_item_1, groupList);
                actionBar.setListNavigationCallbacks(abAdapter, this);
                selectDefNavItem(defGroup);
            }
        } else {
            if(teacherNameList != null) {
                ArrayAdapter<String> abAdapter = new ArrayAdapter<String>(getActivity(), R.layout.ab_list_item, teacherNameList);
                actionBar.setListNavigationCallbacks(abAdapter, this);
                selectDefNavItem(defTeacher);
            }
        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case 0:
                if(displayMode == 0) break;
                displayMode = 0;
                pagerClasses.setAdapter(classesFragmentAdapter);
                showListInActionBar();
                break;
            case 1:
                if(displayMode == 1) break;
                displayMode = 1;
                pagerClasses.setAdapter(teacherFragmentAdapter);
                showListInActionBar();
                break;
            case 2:

                if(displayMode == 0) {
                    classesFragmentAdapter.week = getNextWeek();
                    pagerClasses.setAdapter(classesFragmentAdapter);
                } else {
                    teacherFragmentAdapter.week = getNextWeek();
                    pagerClasses.setAdapter(teacherFragmentAdapter);
                }

                pagerClasses.setCurrentItem(getDayOfWeek());
                break;
        }
        drawerLayout.closeDrawer(lvGroup);
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {

        if(displayMode == 0) {
            selectedGroup = groupList.get(position);
            classesFragmentAdapter.group = selectedGroup;
            classesFragmentAdapter.week = getWeek();
            pagerClasses.setAdapter(classesFragmentAdapter);
        } else {
            selectedTeacher = DatabaseController.getTeacherId(getActivity(), teacherNameList.get(position));
            if(selectedTeacher == -1) return false;
            teacherFragmentAdapter.teacher = selectedTeacher;
            teacherFragmentAdapter.week = getWeek();
            pagerClasses.setAdapter(teacherFragmentAdapter);
        }

        pagerClasses.setCurrentItem(getDayOfWeek());

        return false;
    }

    private void selectDefNavItem(int defValue) {

        int index = -1;
        if(displayMode == 0) {
            if (groupList != null) index = groupList.indexOf(defValue);
        }
        else {
            if(teacherNameList != null)
            index = teacherNameList.indexOf(DatabaseController.getTeacherById(getActivity(), defValue));
        }
        if( index != -1) {
            actionBar.setSelectedNavigationItem(index);
        }
    }

    private int getWeek() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        if(week % 2 == 0) {
            return 1;
        } else  return 0;
    }

    private int getNextWeek() {
        if(getWeek() == 0) return 1;
        else return 0;
    }

    private int getDayOfWeek() {
        Date date = new Date();
        if(date.getDay() == 0) {
            return 5;
        }
        return date.getDay()-1;
    }
}
