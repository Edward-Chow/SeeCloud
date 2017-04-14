package com.example.seecloud;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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

    private Attribute attribute;

    private int weight;

    private SharedPreferences preferences;

    private SharedPreferences.Editor editor;

    private boolean commit;

    public static StepFragment newInstance(@LayoutRes int layoutResId) {
        //attribute = a;
        Bundle args = new Bundle();
        args.putInt("messageResourceId", layoutResId);
        StepFragment stepFragment = new StepFragment();
        stepFragment.setArguments(args);
        return stepFragment;
    }
    public StepFragment setAttribute(Attribute attribute) {
        this.attribute = attribute;
        return this;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weight_setting_area, container, false);

        preferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        //UI初始化
        attribute_name = (TextView) view.findViewById(R.id.attr_name);
        attribute_intro = (TextView) view.findViewById(R.id.attr_intro);
        seekBar1 = (SeekBar) view.findViewById(R.id.seek_bar_1);
        value = (TextView) view.findViewById(R.id.text_value);

        //position = getArguments().getInt("weight_position");
        InitAttribute initAttribute = new InitAttribute();
        attributes.addAll(initAttribute.getAttributes());
        attribute_name.setText(attribute.getName());
        attribute_intro.setText(attribute.getInstruction());

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
                weight = Integer.parseInt(String.valueOf(seekBar1.getProgress()));
                editor = preferences.edit();
                editor.putInt(Integer.toString(position), weight);
                Log.e(Integer.toString(position), Integer.toString(weight));
                commit = editor.commit();
                if (!commit) {
                     dialog();
                }
            }
        });
        return view;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setMessage("权重未成功提交");
        builder.setTitle("提示");
        builder.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }


    /*@Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        attribute_name = (TextView) view.findViewById(R.id.attr_name);
        attribute_intro = (TextView) view.findViewById(R.id.attr_intro);
        seekBar1 = (SeekBar) view.findViewById(R.id.seek_bar_1);
        value = (TextView) view.findViewById(R.id.text_value);

        position = getArguments().getInt("weight_position");
        InitAttribute initAttribute = new InitAttribute();
        attributes.addAll(initAttribute.getAttributes());
        attribute_name.setText(attributes.get(position).getName());
        attribute_intro.setText(attributes.get(position).getInstruction());

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
                weight = Integer.parseInt(String.valueOf(seekBar1.getProgress()));
            }
        });
    }*/

   /* @Override
    public void onStart() {
        super.onStart();
        if (isAdded()) {
            position = getArguments().getInt("weight_position");
            InitAttribute initAttribute = new InitAttribute();
            attributes.addAll(initAttribute.getAttributes());
            attribute_name.setText(attributes.get(position).getName());
            attribute_intro.setText(attributes.get(position).getInstruction());
        }
    }*/

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

    public int getWeight() {
        return weight;
    }
}
