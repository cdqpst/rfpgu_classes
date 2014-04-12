package ru.rfpgu.classes.ui.dialogs;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import ru.rfpgu.classes.MyActivity;
import ru.rfpgu.classes.R;
import ru.rfpgu.classes.Settings;
import ru.rfpgu.classes.controller.DatabaseController;
import ru.rfpgu.classes.model.MyContentProvider;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: AlexChe
 * Date: 10.03.14
 * Time: 13:20
 * To change this template use File | Settings | File Templates.
 */
public class ChoiceTeacherDialog extends DialogFragment implements AdapterView.OnItemClickListener{

    ListView listView;
    ArrayList<String> teacherList = null;
    SharedPreferences pref = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Выберите себя из списка");
        getDialog().setCanceledOnTouchOutside(false);

        View rootView = inflater.inflate(R.layout.dialog_choise, null);
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        listView = (ListView) rootView.findViewById(R.id.dialog_choice_lv);

        teacherList = DatabaseController.getTeachersNames(getActivity(), MyContentProvider.CONTENT_URI_TEACHERS);

        if(teacherList != null) {
            listView.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.dialog_lv_item, teacherList));
        }

        listView.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String teacherName = teacherList.get(position);
        int teacherId = DatabaseController.getTeacherId(getActivity(), teacherName);
        if(teacherId != -1) {
            pref.edit().putString(Settings.DEF_TEACHER, String.valueOf(teacherId)).commit();
        }
        getDialog().dismiss();
        reloadFragment();
    }

    private void reloadFragment() {
        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentByTag(MyActivity.FRAGMENT_TAG);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.detach(currentFragment);
        ft.attach(currentFragment);
        ft.commit();
    }
}