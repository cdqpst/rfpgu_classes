package ru.rfpgu.classes.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ru.rfpgu.classes.R;
import ru.rfpgu.classes.model.ObjectModel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: AlexChe
 * Date: 02.03.14
 * Time: 15:51
 * To change this template use File | Settings | File Templates.
 */
public class TeacherObjectAdapter extends ArrayAdapter<ObjectModel.Classes> {

    public TeacherObjectAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public TeacherObjectAdapter(Context context, int resource, List<ObjectModel.Classes> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {

            LayoutInflater layoutInflater;
            layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.teacher_classes_item, null);

        }

        ObjectModel.Classes classes = getItem(position);

        if (classes != null) {

            TextView subject = (TextView) view.findViewById(R.id.tv_subject);
            TextView group = (TextView) view.findViewById(R.id.tv_group);
            TextView room = (TextView) view.findViewById(R.id.tv_auditory);
            TextView lessonNum = (TextView) view.findViewById(R.id.tv_lesson_num);


            if(subject != null){
                subject.setText(classes.getSubject());
            }

            if(group != null){
                group.setText(String.valueOf(classes.getGroupId()));
            }

            if(room != null){
                room.setText(classes.getRoomNumber());
            }

            if(lessonNum != null){
                lessonNum.setText(String.valueOf(classes.getLessonNumber()));
            }
        }

        return view;
    }
}
