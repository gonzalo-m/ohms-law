package com.ilogic.ohmslaw.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ilogic.ohmslaw.R;
import com.ilogic.ohmslaw.presenter.CalculatorPresenter;
import com.ilogic.ohmslaw.presenter.CalculatorPresenterImpl;

/**
 * Created by G on 7/6/16.
 */
public class CalculatorFragment extends Fragment implements CalculatorView, View.OnClickListener {

    private CalculatorPresenter mPresenter;

    private EditText mVoltageEditText;
    private EditText mCurrentEditText;
    private EditText mResistanceEditText;
    private EditText mPowerEditText;

    private TextView mVoltageUnitText;
    private TextView mCurrentUnitText;
    private TextView mResistanceUnitText;
    private TextView mPowerUnitText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.calculator_fragment, container, false);

        mVoltageEditText = (EditText) view.findViewById(R.id.volts_edittext);
        mCurrentEditText = (EditText) view.findViewById(R.id.amps_edittext);
        mResistanceEditText = (EditText) view.findViewById(R.id.ohms_edittext);
        mPowerEditText = (EditText) view.findViewById(R.id.watts_edittext);

        mVoltageEditText.setKeyListener(null);
        mCurrentEditText.setKeyListener(null);
        mResistanceEditText.setKeyListener(null);
        mPowerEditText.setKeyListener(null);

        mVoltageUnitText = (TextView) view.findViewById(R.id.volts_textview);
        mCurrentUnitText = (TextView) view.findViewById(R.id.amps_textview);
        mResistanceUnitText = (TextView) view.findViewById(R.id.ohms_textview);
        mPowerUnitText = (TextView) view.findViewById(R.id.watts_textview);

        int[] nonPrefixButtonIds = {
                R.id.one_button, R.id.two_button, R.id.three_button, R.id.four_button, R.id.five_button,
                R.id.six_button, R.id.seven_button, R.id.eight_button, R.id.nine_button, R.id.zero_button,
                R.id.decimal_point_button, R.id.reset_button,R.id.delete_button
        };

        int[] prefixButtonIds = {
                R.id.nano_prefix_button, R.id.micro_prefix_button, R.id.milli_prefix_button,
                R.id.kilo_prefix_button, R.id.mega_prefix_button, R.id.giga_prefix_button
        };

        for (int id : nonPrefixButtonIds) {
            view.findViewById(id).setOnClickListener(this);
        }

        for (int id : prefixButtonIds) {
            view.findViewById(id).setOnClickListener(this);
        }

        final Button deleteButton = (Button) view.findViewById(R.id.delete_button);
        deleteButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        deleteButton.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                        mHandler.removeCallbacks(mUpdateTask);
                        mHandler.postAtTime(mUpdateTask,SystemClock.uptimeMillis() + 500);
                        break;
                    case MotionEvent.ACTION_UP:
                        mHandler.removeCallbacks(mUpdateTask);
                        break;
                }
                return false;
            }

            private Handler mHandler = new Handler();
            private Runnable mUpdateTask = new Runnable() {
                @Override
                public void run() {
                    deleteButton.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                    mHandler.postAtTime(this, SystemClock.uptimeMillis() + 50);
                }
            };
        });
        
        deleteButton.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() != KeyEvent.ACTION_DOWN) {
                    return false;
                }
                    
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DEL:
                        getPresenter().deleteButtonClicked();
                        return true;
                }
                return false;
            }
        });

        setPrefixButtonHeights(view, prefixButtonIds);

        return view;
    }

    /**
     * Adjusts the heights of the prefix buttons to match the numeric buttons
     * @param prefixBtnIds the ids of the prefix buttons that need height adjustment
     */
    private void setPrefixButtonHeights(View containerView, int[] prefixBtnIds) {
        int heightRatio;
        float displayHeight = getResources().getDisplayMetrics().heightPixels; //height is device's width in landscape mode

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            final float DEVICE_HEIGHT_TO_BTN_HEIGHT_RATIO = 12.3f;
            heightRatio = (int)((displayHeight / DEVICE_HEIGHT_TO_BTN_HEIGHT_RATIO) + 0.5f);

        } else {
            final float DEVICE_WIDTH_TO_BTN_HEIGHT_RATIO = 5.7f;
            heightRatio = (int)((displayHeight / DEVICE_WIDTH_TO_BTN_HEIGHT_RATIO) + 0.5f);
        }

        for (int id : prefixBtnIds) {
            ((Button)containerView.findViewById(id)).setHeight(heightRatio);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new CalculatorPresenterImpl(this);
        mVoltageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                getPresenter().voltageTextChanged(s.toString());
            }
        });

        mCurrentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                getPresenter().currentTextChanged(s.toString());
            }
        });

        mResistanceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                getPresenter().resistanceTextChanged(s.toString());
            }
        });

        mPowerEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                getPresenter().powerTextChanged(s.toString());
            }
        });

        mVoltageUnitText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                getPresenter().voltageLabelChanged(s.toString());
            }
        });

        mCurrentUnitText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                getPresenter().currentLabelChanged(s.toString());
            }
        });

        mResistanceUnitText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                getPresenter().resistanceLabelChanged(s.toString());
            }
        });

        mPowerUnitText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                getPresenter().powerLabelChanged(s.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zero_button:
                getPresenter().zeroButtonClicked();
                break;
            case R.id.one_button:
                getPresenter().oneButtonClicked();
                break;
            case R.id.two_button:
                getPresenter().twoButtonClicked();
                break;
            case R.id.three_button:
                getPresenter().threeButtonClicked();
                break;
            case R.id.four_button:
                getPresenter().fourButtonClicked();
                break;
            case R.id.five_button:
                getPresenter().fiveButtonClicked();
                break;
            case R.id.six_button:
                getPresenter().sixButtonClicked();
                break;
            case R.id.seven_button:
                getPresenter().sevenButtonClicked();
                break;
            case R.id.eight_button:
                getPresenter().eightButtonClicked();
                break;
            case R.id.nine_button:
                getPresenter().nineButtonClicked();
                break;
            case R.id.decimal_point_button:
                getPresenter().decimalButtonClicked();
                break;
            case R.id.nano_prefix_button:
                getPresenter().nanoPrefixButtonClicked();
                break;
            case R.id.micro_prefix_button:
                getPresenter().microPrefixButtonClicked();
                break;
            case R.id.milli_prefix_button:
                getPresenter().milliPrefixButtonClicked();
                break;
            case R.id.kilo_prefix_button:
                getPresenter().kiloPrefixButtonClicked();
                break;
            case R.id.mega_prefix_button:
                getPresenter().megaPrefixButtonClicked();
                break;
            case R.id.giga_prefix_button:
                getPresenter().gigaPrefixButtonClicked();
                break;
            case R.id.reset_button:
                getPresenter().resetButtonClicked();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        mPresenter = null;
        super.onDestroyView();
    }

    @Override
    public CalculatorPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public String getVoltageInput() {
        return mVoltageEditText.getText().toString();
    }

    @Override
    public String getCurrentInput() {
        return mCurrentEditText.getText().toString();
    }

    @Override
    public String getResistanceInput() {
        return mResistanceEditText.getText().toString();
    }

    @Override
    public String getPowerInput() {
        return mPowerEditText.getText().toString();
    }

    @Override
    public void setVoltageEditText(String qty) {
        mVoltageEditText.setText(qty);
    }

    @Override
    public void setCurrentEditText(String qty) {
        mCurrentEditText.setText(qty);
    }

    @Override
    public void setResistanceEditText(String qty) {
        mResistanceEditText.setText(qty);
    }

    @Override
    public void setPowerEditText(String qty) {
        mPowerEditText.setText(qty);
    }

    @Override
    public void setVoltagePrefixText(String pfx, String unitName) {
        mVoltageUnitText.setText(boldifyPrefix(pfx, unitName));
    }

    @Override
    public void setCurrentPrefixText(String pfx, String unitName) {
        mCurrentUnitText.setText(boldifyPrefix(pfx, unitName));
    }

    @Override
    public void setResistancePrefixText(String pfx, String unitName) {
        mResistanceUnitText.setText(boldifyPrefix(pfx, unitName));
    }

    @Override
    public void setPowerPrefixText(String pfx, String unitName) {
        mPowerUnitText.setText(boldifyPrefix(pfx, unitName));
    }

    @Override
    public void setVoltageViews(String qty, String pfx, String unitName) {
        setVoltageEditText(qty);
        setVoltagePrefixText(pfx, unitName);
    }

    @Override
    public void setCurrentViews(String qty, String pfx, String unitName) {
        setCurrentEditText(qty);
        setCurrentPrefixText(pfx, unitName);
    }

    @Override
    public void setResistanceViews(String qty, String pfx, String unitName) {
        setResistanceEditText(qty);
        setResistancePrefixText(pfx, unitName);
    }

    @Override
    public void setPowerViews(String qty, String pfx, String unitName) {
        setPowerEditText(qty);
        setPowerPrefixText(pfx, unitName);
    }

    @Override
    public void setVoltageEditTextEnabled(boolean enabled) {
        mVoltageEditText.setEnabled(enabled);
    }

    @Override
    public void setCurrentEditTextEnabled(boolean enabled) {
        mCurrentEditText.setEnabled(enabled);
    }

    @Override
    public void setResistanceEditTextEnabled(boolean enabled) {
        mResistanceEditText.setEnabled(enabled);
    }

    @Override
    public void setPowerEditTextEnabled(boolean enabled) {
        mPowerEditText.setEnabled(enabled);
    }

    @Override
    public void setVoltageEditViewColor(int color) {
        mVoltageEditText.setTextColor(color);
    }

    @Override
    public void setCurrentEditViewColor(int color) {
        mCurrentEditText.setTextColor(color);
    }

    @Override
    public void setResistanceEditViewColor(int color) {
        mResistanceEditText.setTextColor(color);
    }

    @Override
    public void setPowerEditViewColor(int color) {
        mPowerEditText.setTextColor(color);
    }

    @Override
    public boolean isVoltageFocused() {
        return mVoltageEditText.isFocused();
    }

    @Override
    public boolean isCurrentFocused() {
        return mCurrentEditText.isFocused();
    }

    @Override
    public boolean isResistanceFocused() {
        return mResistanceEditText.isFocused();
    }

    @Override
    public boolean isPowerFocused() {
        return mPowerEditText.isFocused();
    }


    @Override
    public void appendToVoltageEditText(String c) {
        mVoltageEditText.append(c);
    }

    @Override
    public void appendToCurrentEditText(String c) {
        mCurrentEditText.append(c);
    }

    @Override
    public void appendToResistanceEditText(String c) {
        mResistanceEditText.append(c);
    }

    @Override
    public void appendToPowerEditText(String c) {
        mPowerEditText.append(c);
    }

    @Override
    public String get0() {
        return getString(R.string.zero_button_tag);
    }

    @Override
    public String get1() {
        return getString(R.string.one_button_tag);
    }

    @Override
    public String get2() {
        return getString(R.string.two_button_tag);
    }

    @Override
    public String get3() {
        return getString(R.string.three_button_tag);
    }

    @Override
    public String get4() {
        return getString(R.string.four_button_tag);
    }

    @Override
    public String get5() {
        return getString(R.string.five_button_tag);
    }

    @Override
    public String get6() {
        return getString(R.string.six_button_tag);
    }

    @Override
    public String get7() {
        return getString(R.string.seven_button_tag);
    }

    @Override
    public String get8() {
        return getString(R.string.eight_button_tag);
    }

    @Override
    public String get9() {
        return getString(R.string.nine_button_tag);
    }

    @Override
    public String getDecimalPoint() {
        return getString(R.string.point);
    }

    @Override
    public String getNanoPrefix() {
        return getString(R.string.nano);
    }

    @Override
    public String getMicroPrefix() {
        return getString(R.string.micro);
    }

    @Override
    public String getMilliPrefix() {
        return getString(R.string.milli);
    }

    @Override
    public String getKiloPrefix() {
        return getString(R.string.kilo);
    }

    @Override
    public String getMegaPrefix() {
        return getString(R.string.mega);
    }

    @Override
    public String getGigaPrefix() {
        return getString(R.string.giga);
    }

    @Override
    public String getVoltsString() {
        return getString(R.string.label_volts);
    }

    @Override
    public String getAmpsString() {
        return getString(R.string.label_amps);
    }

    @Override
    public String getOhmsString() {
        return getString(R.string.label_ohms);
    }

    @Override
    public String getWattsString() {
        return getString(R.string.label_watts);
    }

    @Override
    public int getComputedColor() {
        return getResources().getColor(R.color.blue);
    }

    @Override
    public int getDefaultColor() {
        return getResources().getColor(R.color.black);
    }

    private static Spanned boldifyPrefix(String prefix, String unitName) {
        String source = prefix.equals("") ? unitName : "<b>" + prefix + "</b>" + unitName;
        return Html.fromHtml(source);
    }
}