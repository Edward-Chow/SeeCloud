package com.example.seecloud;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.example.seecloud.database.Attribute;
import com.example.seecloud.database.InitAttribute;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.adapter.AbstractStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import java.util.List;

/**
 * Created by monkeysmac on 2017/4/2.
 */

public class MyStepperAdapter extends AbstractFragmentStepAdapter {

    private static final String CURRENT_STEP_POSITION_KEY = "position";

    private FragmentManager fragmentManager;

    List<Attribute> attribute;

    StepFragment step0;
    StepFragment step1;
    StepFragment step2;
    StepFragment step3;
    StepFragment step4;
    StepFragment step5;
    StepFragment step6;
    StepFragment step7;
    StepFragment step8;

    public MyStepperAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
        super(fm, context);
        fragmentManager = fm;
        InitAttribute initAttribute = new InitAttribute();
        attribute = initAttribute.getAttributes();
        step0 = StepFragment.newInstance(R.layout.weight_setting_area);
        step1 = StepFragment.newInstance(R.layout.weight_setting_area);
        step2 = StepFragment.newInstance(R.layout.weight_setting_area);
        step3 = StepFragment.newInstance(R.layout.weight_setting_area);
        step4 = StepFragment.newInstance(R.layout.weight_setting_area);
        step5 = StepFragment.newInstance(R.layout.weight_setting_area);
        step6 = StepFragment.newInstance(R.layout.weight_setting_area);
        step7 = StepFragment.newInstance(R.layout.weight_setting_area);
        step8 = StepFragment.newInstance(R.layout.weight_setting_area);
        step0.setAttribute(attribute.get(0));
        step1.setAttribute(attribute.get(1));
        step2.setAttribute(attribute.get(2));
        step3.setAttribute(attribute.get(3));
        step4.setAttribute(attribute.get(4));
        step5.setAttribute(attribute.get(5));
        step6.setAttribute(attribute.get(6));
        step7.setAttribute(attribute.get(7));
        step8.setAttribute(attribute.get(8));
    }


    @Override
    public Step createStep(@IntRange(from = 0L) int position) {
        /*final StepFragment step = new StepFragment();
        Bundle b = new Bundle();
        b.putInt(CURRENT_STEP_POSITION_KEY, position);
        step.setArguments(b);
        return step;*/
        switch (position) {
            case 0:
                return step0;
            case 1:
                return step1;
            case 2:
                return step2;
            case 3:
                return step3;
            case 4:
                return step4;
            case 5:
                return step5;
            case 6:
                return step6;
            case 7:
                return step7;
            case 8:
                return step8;
            default:
                throw new IllegalArgumentException("Unsupported position: " + position);
        }
    }

    @Override
    public int getCount() {
        return 9;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0L) int position) {
        return super.getViewModel(position);
    }
}
