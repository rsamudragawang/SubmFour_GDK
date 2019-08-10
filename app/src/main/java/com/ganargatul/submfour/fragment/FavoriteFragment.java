package com.ganargatul.submfour.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.tabs.TabLayout;

import com.ganargatul.submfour.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    TabLayout mTabLayout;
    ViewPager mViewPager;
    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_favorite, container, false);
        mTabLayout = v.findViewById(R.id.tabs);
        mViewPager = v.findViewById(R.id.viewpager);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mViewPager.setAdapter(new viewpageradapter(getFragmentManager(),mTabLayout.getTabCount()));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
       // mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return v;
    }

    public class viewpageradapter extends FragmentStatePagerAdapter {
        int mNumofTabs;
        public viewpageradapter(FragmentManager fm,int mNumOfTabs) {
            super(fm);
            this.mNumofTabs = mNumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    
                    return new MovieFav();
                case 1:
                    return new TvFav();
                    default:
                        return null;
            }
        }

        @Override
        public int getCount() {
            return mNumofTabs;
        }
    }

}
