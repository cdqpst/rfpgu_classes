package ru.rfpgu.classes;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import ru.rfpgu.classes.controller.DatabaseController;
import ru.rfpgu.classes.model.MyContentProvider;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: AlexChe
 * Date: 05.03.14
 * Time: 23:36
 * To change this template use File | Settings | File Templates.
 */
public class Settings extends PreferenceActivity {

    public static final String DEF_GROUP = "group";
    public static final String DEF_TEACHER = "teacher";
    public static final String DEF_STANDING = "standing";

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.settings);
        final ListPreference group = (ListPreference) findPreference(DEF_GROUP);
        final ListPreference teacher = (ListPreference) findPreference(DEF_TEACHER);
        ArrayList<String> groupList = DatabaseController.getGroupForSettings(getApplicationContext(), MyContentProvider.CONTENT_URI_CLASSES);
        ArrayList<String> teacherNameList = DatabaseController.getTeachersNames(getApplicationContext(), MyContentProvider.CONTENT_URI_TEACHERS);
        ArrayList<String> teacherIdsList = DatabaseController.getTeachersIds(getApplicationContext(), MyContentProvider.CONTENT_URI_TEACHERS);

        if(groupList != null){
            CharSequence[] groups = groupList.toArray(new CharSequence[groupList.size()]);
            group.setEntries(groups);
            group.setEntryValues(groups);
        }

        if(teacherIdsList != null && teacherNameList != null) {
            CharSequence[] teacherNames = teacherNameList.toArray(new CharSequence[teacherNameList.size()]);
            CharSequence[] teacherIds = teacherIdsList.toArray(new CharSequence[teacherIdsList.size()]);
            teacher.setEntries(teacherNames);
            teacher.setEntryValues(teacherIds);
        }
    }
}
