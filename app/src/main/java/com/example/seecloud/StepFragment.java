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
import android.widget.Toast;

import com.example.seecloud.database.Attribute;
import com.example.seecloud.database.InitAttribute;
import com.jaygoo.widget.RangeSeekBar;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by monkeysmac on 2017/4/2.
 */

public class StepFragment extends Fragment implements Step{

    private TextView attribute_name;

    private TextView attribute_intro;

    private SeekBar seekBar1;

    private TextView value;

    private int position;

    private List<Attribute> attributes = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weight_setting_area, container, false);

        //UI初始化
        attribute_name = (TextView) view.findViewById(R.id.attr_name);
        attribute_intro = (TextView) view.findViewById(R.id.attr_intro);
        seekBar1 = (SeekBar) view.findViewById(R.id.seek_bar_1);
        value = (TextView) view.findViewById(R.id.text_value);


        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 当拖动条的滑块位置发生改变时触发该方法,在这里直接使用参数progress，即当前滑块代表的进度值
                value.setText("Value:" + Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //取得滑块停止时的值作为权重
                int weight = Integer.parseInt(String.valueOf(seekBar1.getProgress()));
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isAdded()) {
            position = getArguments().getInt("weight_position");
            InitAttribute initAttribute = new InitAttribute();
            attributes.addAll(initAttribute.getAttributes());
            attribute_name.setText(attributes.get(position).getName());
            attribute_intro.setText(attributes.get(position).getInstruction());
        }
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
