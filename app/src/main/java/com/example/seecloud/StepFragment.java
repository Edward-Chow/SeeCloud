package com.example.seecloud;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jaygoo.widget.RangeSeekBar;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.util.zip.Inflater;

/**
 * Created by monkeysmac on 2017/4/2.
 */

public class StepFragment extends Fragment implements Step{

    private TextView attribute_name;

    private TextView attribute_intro;

    private RangeSeekBar seekBar;
    private SeekBar seekBar1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weight_setting_area, container, false);

        //UI初始化
        attribute_name = (TextView) view.findViewById(R.id.attr_name);
        attribute_intro = (TextView) view.findViewById(R.id.attr_intro);
        seekBar = (RangeSeekBar) view.findViewById(R.id.seek_bar);
        return view;
    }

    @Override
    public VerificationError verifyStep() {
        //return null if the user can go to the next step, create a new VerificationError instance otherwise
        return null;
    }

    @Override
    public void onSelected() {
        //update UI when selected
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        //handle error inside of the fragment, e.g. show error on EditText
    }
}
