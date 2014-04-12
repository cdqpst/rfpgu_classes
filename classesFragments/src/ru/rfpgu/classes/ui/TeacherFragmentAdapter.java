package ru.rfpgu.classes.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class TeacherFragmentAdapter extends FragmentStatePagerAdapter {

    int teacher;
    int week;
    String days[] = new String[] {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};

    public TeacherFragmentAdapter(FragmentManager fm, int teacher, int week) {
        super(fm);
        this.teacher = teacher;
        this.week = week;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return  days[position];
    }

    @Override
    public Fragment getItem(int i) {
        return TeacherFragment.create(i, teacher, week);
    }

    @Override
    public int getCount() {
        return TeacherFragment.PAGE_COUNT;
    }
}
