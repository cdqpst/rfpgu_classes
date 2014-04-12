package ru.rfpgu.classes.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class ClassesFragmentAdapter extends FragmentStatePagerAdapter {

    int group;
    int week;
    String days[] = new String[] {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};

    public ClassesFragmentAdapter(FragmentManager fm, int group, int week) {
        super(fm);
        this.group = group;
        this.week = week;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return  days[position];
    }

    @Override
    public Fragment getItem(int i) {
        return ClassesFragment.create(i, group, week);
    }

    @Override
    public int getCount() {
        return ClassesFragment.PAGE_COUNT;
    }
}
