package ru.rfpgu.classes.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import ru.rfpgu.classes.MyActivity;
import ru.rfpgu.classes.R;
import ru.rfpgu.classes.controller.DatabaseController;
import ru.rfpgu.classes.model.ObjectModel;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: AlexChe
 * Date: 02.03.14
 * Time: 9:57
 * To change this template use File | Settings | File Templates.
 */
public class TeacherFragment extends Fragment {

    public static final String PAGE_NUMBER = "page_num";
    public static final String TEACHER_ID = "teach_id";
    public static final String WEEK = "week";
    static final int PAGE_COUNT = 6;

    ListView listViewClasses;
    ArrayList<ObjectModel.Classes> objectsList;
    TeacherObjectAdapter teacherObjectAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.classes_fragment, null);

        listViewClasses = (ListView) rootView.findViewById(R.id.list_view_classes);
        showTeacherClasses(getArguments().getInt(TEACHER_ID), getArguments().getInt(PAGE_NUMBER) + 1, getArguments().getInt(WEEK));

        return rootView;
    }

    private void showTeacherClasses(int teacher, int day, int week){

        objectsList = DatabaseController.getTeacherClasses(getActivity(), teacher, day, week);

        if(objectsList == null || objectsList.size() == 0) {
            Log.d(MyActivity.LOG_TAG, "NULL OBJECT");
            return;
        }

//        listAdapter = new ArrayAdapter<ObjectModel.Classes>(getActivity(),android.R.layout.simple_list_item_1, objectsList);
        teacherObjectAdapter = new TeacherObjectAdapter(getActivity(), R.layout.student_classes_item, objectsList);
//        listViewClasses.setAdapter(listAdapter);
        listViewClasses.setAdapter(teacherObjectAdapter);
    }

    public static TeacherFragment create(int num, int teacher, int week) {
        TeacherFragment fragment = new TeacherFragment();
        Bundle args = new Bundle();
        args.putInt(PAGE_NUMBER, num);
        args.putInt(TEACHER_ID, teacher);
        args.putInt(WEEK, week);
        fragment.setArguments(args);

        return fragment;

    }
}
