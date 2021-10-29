package com.example.androidfinal;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class pagerAdapter extends FragmentStatePagerAdapter {
    private  int numberOfTabs;
    private String country="";
    public pagerAdapter(FragmentManager fm){
        super(fm);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                fragOne fragOne = new fragOne();
                return fragOne;
            case 1:
                fragTwo fragTwo = new fragTwo();
                return fragTwo;
          //  case 2:
            //    fragThree fragThree= new fragThree();
              //  return fragThree;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position)
        {
            case 0:
                return "Map";
            case 1:
                return "Covid Data";
            case 2:
                return "News";
        }
        return "";
    }

    @Override
    public int getCount() {
        return 3;
    }
}
