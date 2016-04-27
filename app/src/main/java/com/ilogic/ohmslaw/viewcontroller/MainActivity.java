package com.ilogic.ohmslaw.viewcontroller;

import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ilogic.ohmslaw.R;
import com.ilogic.ohmslaw.model.OhmsLawModel;
import com.ilogic.ohmslaw.model.Prefix;
import com.ilogic.ohmslaw.model.Type;
import com.ilogic.ohmslaw.model.Unit;
import com.ilogic.ohmslaw.preferences.Preferences;

public class MainActivity extends AppCompatActivity implements Observer, OnClickListener,
					OnFocusChangeListener, TextWatcher, OnKeyListener {

	protected OhmsLawModel mOhmsLaw;
	/* Labels */
	private TextView[] labels;
	private static final int NUM_OF_FIELDS = 4;
	
	/* Values fields */
	private EditText[] inputFields;	// volts[0], amps[1], ohms[2], watts[3]
	
	/* Used to enter values in the current, "focused" field */
	private EditText currentField;  // maintains a reference to one of the four input fields
	private EditText[] knownValues = new EditText[2];
	
	/* Keypad */
	private Button[] numBtns;	    // 0 - 9
	private static final int NUM_OF_BUTTONS = 10;
	private Button[] siPrefixBtns;	// nano[0], micro[1], milli[2], kilo[3] Mega[4], Giga[5]
	private static final int NUM_OF_PREFIX_BUTTONS = 6;
	private Button pointBtn;
	private Button deleteBtn;
	private Button resetBtn;
	
	/* Field's text colors */
	private int blueColor;
	private ColorStateList defaultColor;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mOhmsLaw = new OhmsLawModel();
		mOhmsLaw.addObserver(this);
		initViews();
		regOnClickListeners();
		regOnFocusChangeListeners();
		regDeleteBtnListeners();
		setFieldsToNull();
		
		/* volts field is the default "focused" field */
		currentField = inputFields[0];
		currentField.addTextChangedListener(this);
		/* have text colors ready */
		blueColor = getResources().getColor(R.color.blue);
		defaultColor = inputFields[0].getTextColors();
		setPrefixBtnsHeight();
		
		// New features dialog
        settings = getSharedPreferences("prefs", MODE_PRIVATE);
		boolean dialogDidNotRunOnce = settings.getBoolean("featuresDialogRan", true);
		
		if (dialogDidNotRunOnce) {
			//Run only once
			showFeaturesDialog();
			SharedPreferences.Editor dialogPrefsEditor = settings.edit();
			dialogPrefsEditor.putBoolean("featuresDialogRan", false);
		    dialogPrefsEditor.commit();
		} 
	}
	
	@SuppressWarnings("deprecation")
	private void showFeaturesDialog() {
		AlertDialog.Builder customDialog = new AlertDialog.Builder(
				new ContextThemeWrapper(this,android.R.style.Theme_Dialog));
		customDialog.setTitle("What is new in this version?");
		customDialog.setMessage(getString(R.string.change_log));
		customDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// do nothing
			}
		});
		AlertDialog dialog = customDialog.create();
		dialog.show();

		Button b = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
		if(b != null) {
			b.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.btn_default));
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			/* Buttons */
			case R.id.reset_button: resetAll(); break;
			case R.id.decimal_point_button:
				if(!currentField.getText().toString().contains("."))
					currentField.append(".");
				break;
			case R.id.zero_button: currentField.append("0"); break;
			case R.id.one_button: currentField.append("1"); break;
			case R.id.two_button: currentField.append("2"); break;
			case R.id.three_button: currentField.append("3"); break;
			case R.id.four_button: currentField.append("4"); break;
			case R.id.five_button: currentField.append("5"); break;
			case R.id.six_button: currentField.append("6"); break;
			case R.id.seven_button: currentField.append("7"); break;
			case R.id.eight_button: currentField.append("8"); break;
			case R.id.nine_button: currentField.append("9"); break;
			
			/* SI prefix buttons */
			case R.id.nano_prefix_button: 
				setPrefix(currentField, 'n');
				mOhmsLaw.setPrefix(currentField.getId(), Prefix.NANO);
				// triggers afterTextChanged method to update calculations
				currentField.setText(currentField.getText()); 
				break;
			case R.id.micro_prefix_button: 
				setPrefix(currentField, '\u03BC');
				mOhmsLaw.setPrefix(currentField.getId(), Prefix.MICRO);
				currentField.setText(currentField.getText()); 
				break;
			case R.id.milli_prefix_button: 
				setPrefix(currentField, 'm');
				mOhmsLaw.setPrefix(currentField.getId(), Prefix.MILLI);
				currentField.setText(currentField.getText()); 
				break;
			case R.id.kilo_prefix_button: 
				setPrefix(currentField, 'k');
				mOhmsLaw.setPrefix(currentField.getId(), Prefix.KILO);
				currentField.setText(currentField.getText()); 
				break;
			case R.id.mega_prefix_button: 
				setPrefix(currentField, 'M');
				mOhmsLaw.setPrefix(currentField.getId(), Prefix.MEGA);
			    currentField.setText(currentField.getText()); 
				break;
			case R.id.giga_prefix_button: 
				setPrefix(currentField, 'G');
				mOhmsLaw.setPrefix(currentField.getId(), Prefix.GIGA);
				currentField.setText(currentField.getText()); 
				break;
		}
		// Keeps the cursor always at the end when any view is clicked
		currentField.setSelection(currentField.getText().length());
		Log.d("OHM'S LAW", mOhmsLaw.toString());
	}
	
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (hasFocus) {
			switch (v.getId()) {
			case R.id.volts_edittext: shiftFieldAndTextWatcher(inputFields[0]);
				break;
			case R.id.amps_edittext: shiftFieldAndTextWatcher(inputFields[1]);
				break;
			case R.id.ohms_edittext: shiftFieldAndTextWatcher(inputFields[2]);
				break;
			case R.id.watts_edittext: shiftFieldAndTextWatcher(inputFields[3]);
				break;
			}
		}
		// Keeps the cursor always at the end when any view is clicked
		currentField.setSelection(currentField.getText().length());
	}
	
	@Override
	public void afterTextChanged(Editable s) {
		Log.d("OHM'S LAW", "After text changed " + s.toString() );
		double value = 0;
		if (s.toString().equals("")) { 
			/* set value to zero */
			resetField(currentField);
		} else if (s.toString().equals(".")) {
			setField(currentField);
			for (int i=0; i<inputFields.length; i++) {
				if (!inputFields[i].isEnabled()) {
					inputFields[i].setText("");
					mOhmsLaw.setQuantity(inputFields[i].getId(), 0);
					setPrefix(inputFields[i], '-');
					mOhmsLaw.setPrefix(inputFields[i].getId(), Prefix.NONE);
				}
			}
		} else {
			try {
				value = Double.parseDouble(s.toString());
				if (value == 0) {
					// clear disabled fields
					for (int i=0; i<inputFields.length; i++) {
						if (!inputFields[i].isEnabled()) {
							inputFields[i].setText("");
							mOhmsLaw.setQuantity(inputFields[i].getId(), 0);
							setPrefix(inputFields[i], '-');
							mOhmsLaw.setPrefix(inputFields[i].getId(), Prefix.NONE);
						}
					}
				} else {
					setField(currentField);
				}
			} catch (NumberFormatException e) {
				System.out.println(e.getMessage());
				/* set value to zero */
				value = 0;
				resetField(currentField);
			} 
		}
			
		switch (currentField.getId()) {
			case R.id.volts_edittext: mOhmsLaw.setVoltage(value); break;
			case R.id.amps_edittext: mOhmsLaw.setCurrent(value); break;
			case R.id.ohms_edittext: mOhmsLaw.setResistance(value); break;
			case R.id.watts_edittext: mOhmsLaw.setPower(value); break;
			
		}
		if (isInputValid()) {
			for (int i=0; i<inputFields.length; i++) {
				if (!(
						inputFields[i] == knownValues[0] || 
						inputFields[i] == knownValues[1])) {
					// disable field
					disable(inputFields[i]);
				}
			}
			if (mOhmsLaw.hasTwoValidValues()) {
				int idComb = 1;
				switch(knownValues[0].getId()) {
					case R.id.volts_edittext:
						idComb *= Type.VOLT.getId();
						break;
					case R.id.amps_edittext:
						idComb *= Type.AMP.getId();
						break;
					case R.id.ohms_edittext:
						idComb *= Type.OHM.getId();
						break;
					case R.id.watts_edittext:
						idComb *= Type.WATT.getId();
						break;
				}
				switch(knownValues[1].getId()) {
					case R.id.volts_edittext:
						idComb *= Type.VOLT.getId();
						break;
					case R.id.amps_edittext:
						idComb *= Type.AMP.getId();
						break;
					case R.id.ohms_edittext:
						idComb *= Type.OHM.getId();
						break;
					case R.id.watts_edittext:
						idComb *= Type.WATT.getId();
						break;
				}
				mOhmsLaw.calculateUnknownValues(idComb);
			}	
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (!inputFields[0].isEnabled()) {
			inputFields[0].setText(mOhmsLaw.getVoltage().toCurrentDecimals());
			setPrefix(inputFields[0], mOhmsLaw.getVoltage().getPrefix().getSymbol());
		}
		if (!inputFields[1].isEnabled()) {
			inputFields[1].setText(mOhmsLaw.getCurrent().toCurrentDecimals());
			setPrefix(inputFields[1], mOhmsLaw.getCurrent().getPrefix().getSymbol());
		}
		if (!inputFields[2].isEnabled()) {
			inputFields[2].setText(mOhmsLaw.getResistance().toCurrentDecimals());
			setPrefix(inputFields[2], mOhmsLaw.getResistance().getPrefix().getSymbol());
			
		}
		if (!inputFields[3].isEnabled()) {
			inputFields[3].setText(mOhmsLaw.getPower().toCurrentDecimals());
			setPrefix(inputFields[3], mOhmsLaw.getPower().getPrefix().getSymbol());
		}
		System.out.println("updtate method called");
	}
	
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (event.getAction() != KeyEvent.ACTION_DOWN)
			return true;
		switch (keyCode) {
			case KeyEvent.KEYCODE_DEL:
				String value = ((EditText) v).getText().toString();
				if (!value.equals("")) {
                	((EditText) v).setText(value.substring(0, value.length() - 1));
                	((EditText) v).setSelection(((EditText) v).getText().length());
				}
				break;
			case KeyEvent.KEYCODE_BACK:
	            	finish(); //Terminate app
		}
		return false;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		if (keyCode == KeyEvent.KEYCODE_MENU) 
		{
	        event.startTracking();
	        if (!event.isLongPress())
	        {
	        	//Launch Preferences
	        	Intent prefIntent = new Intent(MainActivity.this, Preferences.class);
	    		startActivity(prefIntent);
	        }
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	//==================== Support methods ========================
	
	/**
	 * Initializes views
	 */
	private void initViews() {
		
		labels = new TextView[NUM_OF_FIELDS];
		inputFields = new EditText[NUM_OF_FIELDS];
		numBtns = new Button[NUM_OF_BUTTONS];
		siPrefixBtns = new Button[NUM_OF_PREFIX_BUTTONS];
		
		/* Labels */
		labels[0] = (TextView) findViewById(R.id.volts_textview);
		labels[1] = (TextView) findViewById(R.id.amps_textview);
		labels[2] = (TextView) findViewById(R.id.ohms_textview);
		labels[3] = (TextView) findViewById(R.id.watts_textview);
		
		/* Value fields */
		inputFields[0] = (EditText) findViewById(R.id.volts_edittext);
		inputFields[1] = (EditText) findViewById(R.id.amps_edittext);
		inputFields[2] = (EditText) findViewById(R.id.ohms_edittext);
		inputFields[3] = (EditText) findViewById(R.id.watts_edittext);
		
		
		/* Keypad */
		numBtns[0] = (Button) findViewById(R.id.zero_button);
		numBtns[1] = (Button) findViewById(R.id.one_button);
		numBtns[2] = (Button) findViewById(R.id.two_button);
		numBtns[3] = (Button) findViewById(R.id.three_button);
		numBtns[4] = (Button) findViewById(R.id.four_button);
		numBtns[5] = (Button) findViewById(R.id.five_button);
		numBtns[6] = (Button) findViewById(R.id.six_button);
		numBtns[7] = (Button) findViewById(R.id.seven_button);
		numBtns[8] = (Button) findViewById(R.id.eight_button);
		numBtns[9] = (Button) findViewById(R.id.nine_button);
		
		/* SI prefixes */
		siPrefixBtns[0] = (Button) findViewById(R.id.nano_prefix_button);
		siPrefixBtns[1] = (Button) findViewById(R.id.micro_prefix_button);
		siPrefixBtns[2] = (Button) findViewById(R.id.milli_prefix_button);
		siPrefixBtns[3] = (Button) findViewById(R.id.kilo_prefix_button);
		siPrefixBtns[4] = (Button) findViewById(R.id.mega_prefix_button);
		siPrefixBtns[5] = (Button) findViewById(R.id.giga_prefix_button);
		
		/* Delete & reset */
		pointBtn = (Button) findViewById(R.id.decimal_point_button);
		deleteBtn = (Button) findViewById(R.id.delete_button);
		resetBtn = (Button) findViewById(R.id.reset_button);	
	}
	
	/**
	 * Registers "OnClickListeners" with buttons
	 */
	private void regOnClickListeners() {
		for (Button btn : numBtns) {
			btn.setOnClickListener(this);
		}
		
		for (Button btn : siPrefixBtns) {
			btn.setOnClickListener(this);
		}
		
		pointBtn.setOnClickListener(this);
		deleteBtn.setOnClickListener(this);
		resetBtn.setOnClickListener(this);
	}
	
	/**
	 * Registers "OnFocusChangeListeners" with EditText fields
	 */
	private void regOnFocusChangeListeners() {
		for (EditText editText : inputFields) {
			editText.setOnFocusChangeListener(this);
		}
	}
	
	/**
	 * Sets input fields to null so user input from system's soft keyboard is not allowed
	 */
	private void setFieldsToNull() {
		for (EditText editText : inputFields) {
			editText.setKeyListener(null);
			editText.setOnKeyListener(this);//
		}
	}

	/**
	 * Adjusts the prefix buttons' height so they match other buttons' height
	 */
	private void setPrefixBtnsHeight() {
		int heightRatio = 0;
		float displayHeight = getResources().getDisplayMetrics().heightPixels; //height is device's width in landscape mode
		
		if (getResources().getConfiguration().orientation 
				== Configuration.ORIENTATION_PORTRAIT) {
			final float DEVICE_HEIGHT_TO_BTN_HEIGHT_RATIO = 12.3F;
			heightRatio = (int)((displayHeight / DEVICE_HEIGHT_TO_BTN_HEIGHT_RATIO) + 0.5F);
			
		} else { 
			final float DEVICE_WIDTH_TO_BTN_HEIGHT_RATIO = 5.7F;
			heightRatio = (int)((displayHeight / DEVICE_WIDTH_TO_BTN_HEIGHT_RATIO) + 0.5F);
		}
		
		for (int i = 0; i < siPrefixBtns.length; i++) {
			siPrefixBtns[i].setHeight(heightRatio);
		}
	}
	
	private void regDeleteBtnListeners() {
		deleteBtn.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						currentField.dispatchKeyEvent(
								new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
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
		            currentField.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, 
		            		KeyEvent.KEYCODE_DEL));
		            mHandler.postAtTime(this, SystemClock.uptimeMillis() + 50);
		        }
		    };
		});
	}
	
	/**
	 * Shifts reference variable to the new "focused" field that is passed in. 
	 * It also removes the "TextChangedListener" from the previous "focused" field
	 * and registers the "TextChangedListener" with the new "focused" one.
	 * 
	 * @param focusedField the new "focused" field that receives input 
	 * 		from keypad
	 */
	private void shiftFieldAndTextWatcher(EditText focusedField) {
		currentField.removeTextChangedListener(this);
		currentField = focusedField;
		currentField.addTextChangedListener(this);
	}
	
	private void setField(EditText field) {
		if (!setContains(field)) {
			addToKnownValuesSet(field);
		}
	}
	
	private void resetField(EditText field) {
		if (setContains(field)) {
			removeFromKnownValuesSet(field);
			setPrefix(field, '-');
			mOhmsLaw.setPrefix(field.getId(), Prefix.NONE);
			for (int i=0; i<inputFields.length; i++) {
				if (!inputFields[i].isEnabled()) {
					// reset and enable it
					enable(inputFields[i]);
					inputFields[i].setText("");
					mOhmsLaw.setQuantity(inputFields[i].getId(), 0);
					setPrefix(inputFields[i], '-');
					mOhmsLaw.setPrefix(inputFields[i].getId(), Prefix.NONE);
				}
			}
		}
	}
	
	private void disable(EditText fieldToDisable) {
		fieldToDisable.setTextColor(blueColor);
		fieldToDisable.setEnabled(false);
		fieldToDisable.setFocusable(false);
	}
	
	private void enable(EditText fieldToEnable) {
		fieldToEnable.setTextColor(defaultColor);
		fieldToEnable.setEnabled(true);
		fieldToEnable.setFocusable(true);
		fieldToEnable.setFocusableInTouchMode(true);
	}
	
	private boolean setContains(EditText field) {
		for (int i = 0; i < knownValues.length; i++) {
			if (field == knownValues[i]) {
				return true;
			}
		}
		return false;
	}
	private void addToKnownValuesSet(EditText field) {
		if (knownValues[0] == null) {
			knownValues[0] = field;
		} else if (knownValues[1] == null) {
			knownValues[1] = field;
		}
	}
	
	private void removeFromKnownValuesSet(EditText field) {
		if (knownValues[0] == field) {
			knownValues[0] = null;
		} else if (knownValues[1] == field) {
			knownValues[1] = null;
		}
	}
	
	/**
	 * Checks whether two fields have valid values or not
	 * 
	 * @return true if two fields have valid input, false otherwise
	 */
	private boolean isInputValid() {
		return (knownValues[0] != null && knownValues[1] != null);
	}
	
	private void resetAll() {
		for (int i = 0; i < inputFields.length; i++) {
			enable(inputFields[i]);
			inputFields[i].setText("");
			removeFromKnownValuesSet(inputFields[i]);
		}
		mOhmsLaw.reset();
		labels[0].setText("Volts");
		labels[1].setText("Amps");
		labels[2].setText("Ohms");
		labels[3].setText("Watts");
	}
	
	private void setPrefix(EditText focusedField, char prefixSymbol) {
		TextView currentLabel = null;
		String defaultLabel = "";
		switch (focusedField.getId()) {
			case R.id.volts_edittext:
				currentLabel = labels[0];
				defaultLabel = "Volts";
				break;
			case R.id.amps_edittext:
				currentLabel = labels[1];
				defaultLabel = "Amps";
				break;
			case R.id.ohms_edittext:
				currentLabel = labels[2];
				defaultLabel = "Ohms";
				break;
			case R.id.watts_edittext:
				currentLabel = labels[3];
				defaultLabel = "Watts";
				break;
		}
		switch (prefixSymbol) {
			case 'n':
				currentLabel.setText(Html.fromHtml("<b>" + "n" + "</b>" + defaultLabel));
					break;
			case '\u03BC':
				currentLabel.setText(Html.fromHtml("<b>" + "\u03BC" + "</b>" + defaultLabel));
					break;
			case 'm':
				currentLabel.setText(Html.fromHtml("<b>" + "m" + "</b>" + defaultLabel));
					break;
			case '-':
				currentLabel.setText(defaultLabel);
					break;
			case 'k':
				currentLabel.setText(Html.fromHtml("<b>" + "k" + "</b>" + defaultLabel));
					break;
			case 'M':
				currentLabel.setText(Html.fromHtml("<b>"+ "M"+"</b>" + defaultLabel));
					break;
			case 'G':
				currentLabel.setText(Html.fromHtml("<b>" + "G" + "</b>" + defaultLabel));
					break;
		}
	}
	
	@Override
	protected void onStart() {
		getPrefDecPlaces();
		getPrefAutorange();
		getPrefSIButtons();
		// triggers afterTextChanged method to update calculations
		currentField.setText(currentField.getText()); 
		// Keeps the cursor always at the end when any view is clicked
		currentField.setSelection(currentField.getText().length());
		super.onStart();	
	}
	
	//===================== Preferences =============================
	private SharedPreferences settings;
	
	private void getPrefDecPlaces() 
	{
        // Get the xml/preferences.xml
        settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String decimalPlaces = settings.getString("decimalPlaces", "2");
       
        if (decimalPlaces.equals("1")) {
        	Unit.setDecimalPlaces(new DecimalFormat("#0.#")); //1 decimal places
        } else if (decimalPlaces.equals("2")) {
        	Unit.setDecimalPlaces(new DecimalFormat("#0.##")); //2 decimal places
        } else if (decimalPlaces.equals("3")) {
        	Unit.setDecimalPlaces(new DecimalFormat("#0.###")); //3 decimal places
        } else if (decimalPlaces.equals("4")) {
        	Unit.setDecimalPlaces(new DecimalFormat("#0.####")); //4 decimal places
        } else if (decimalPlaces.equals("5")) {
        	Unit.setDecimalPlaces(new DecimalFormat("#0.#####")); //5 decimal places
        } else if (decimalPlaces.equals("6")) {
        	Unit.setDecimalPlaces(new DecimalFormat("#0.######")); //6 decimal places
        } else if (decimalPlaces.equals("7")) {
        	Unit.setDecimalPlaces(new DecimalFormat("#0.#######")); //7 decimal places
        } else if (decimalPlaces.equals("8")) {
        	Unit.setDecimalPlaces(new DecimalFormat("#0.########")); //8 decimal places
        } else if (decimalPlaces.equals("9")) {
        	Unit.setDecimalPlaces(new DecimalFormat("#0.#########")); //9 decimal places
        } 
	}
	
	private boolean autorangeVal;
	
	private void getPrefAutorange() {
		autorangeVal = settings.getBoolean("autorange", true);
		Unit.setAutorange(autorangeVal);
	}
	
	private String siButtonVals;
	private void getPrefSIButtons() {
		siButtonVals = settings.getString("siButtons", "milli,kilo");
		if (siButtonVals.contains("nano"))
			siPrefixBtns[0].setVisibility(View.VISIBLE);
		else 
			siPrefixBtns[0].setVisibility(View.GONE);
		if (siButtonVals.contains("micro"))
			siPrefixBtns[1].setVisibility(View.VISIBLE);
		else 
			siPrefixBtns[1].setVisibility(View.GONE);
		if (siButtonVals.contains("milli"))
			siPrefixBtns[2].setVisibility(View.VISIBLE);
		else 
			siPrefixBtns[2].setVisibility(View.GONE);
		if (siButtonVals.contains("kilo"))
			siPrefixBtns[3].setVisibility(View.VISIBLE);
		else 
			siPrefixBtns[3].setVisibility(View.GONE);
		if (siButtonVals.contains("Mega"))
			siPrefixBtns[4].setVisibility(View.VISIBLE);
		else 
			siPrefixBtns[4].setVisibility(View.GONE);
		if (siButtonVals.contains("Giga"))
			siPrefixBtns[5].setVisibility(View.VISIBLE);
		else 
			siPrefixBtns[5].setVisibility(View.GONE);
	}
	
	public static final String SAVED_UNIT = "myOhmsLawObject";
	public static final String SAVED_FIELDS_IDCOMB = "ohmsLawIdComb";
	public static final String SAVED_FOCUSED_VIEW = "currentFocusedView";
	
	@Override
	protected void onSaveInstanceState(Bundle saveInstanceState) {
		//super.onSaveInstanceState(saveInstanceState);
		int idComb = 1;
		if(inputFields[0].isEnabled() && mOhmsLaw.getVoltage().getQuantity() > 0) {
			idComb = idComb * Type.VOLT.getId();
			saveInstanceState.putSerializable(SAVED_UNIT + "V", mOhmsLaw.getVoltage());
		}
		
		if(inputFields[1].isEnabled() && mOhmsLaw.getCurrent().getQuantity() > 0) {
			idComb = idComb * Type.AMP.getId();
			saveInstanceState.putSerializable(SAVED_UNIT + "I", mOhmsLaw.getCurrent());
		}
		
		if(inputFields[2].isEnabled() && mOhmsLaw.getResistance().getQuantity() > 0) {
			idComb = idComb * Type.OHM.getId();
			saveInstanceState.putSerializable(SAVED_UNIT + "R", mOhmsLaw.getResistance());
		}
		
		if(inputFields[3].isEnabled() && mOhmsLaw.getPower().getQuantity() > 0) {
			idComb = idComb * Type.WATT.getId();
			saveInstanceState.putSerializable(SAVED_UNIT + "P", mOhmsLaw.getPower());
		}
		saveInstanceState.putInt(SAVED_FIELDS_IDCOMB, idComb);
		saveInstanceState.putInt(SAVED_FOCUSED_VIEW, getCurrentFocus().getId());
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		//super.onRestoreInstanceState(savedInstanceState);
		Unit savedVoltage = (Unit) savedInstanceState.getSerializable(SAVED_UNIT + "V");
		Unit savedCurrent = (Unit) savedInstanceState.getSerializable(SAVED_UNIT + "I");
		Unit savedResistance = (Unit) savedInstanceState.getSerializable(SAVED_UNIT + "R");
		Unit savedPower = (Unit) savedInstanceState.getSerializable(SAVED_UNIT + "P");
		int idCombination = savedInstanceState.getInt(SAVED_FIELDS_IDCOMB);
		
		switch (idCombination) {
			case 2:
				inputFields[0].requestFocus();
				setPrefix(inputFields[0], savedVoltage.getPrefix().getSymbol());
				inputFields[0].setText(savedVoltage.toCurrentDecimals());
				mOhmsLaw.setVoltage(savedVoltage);
				break;
			case 3:
				inputFields[1].requestFocus();
				setPrefix(inputFields[1], savedCurrent.getPrefix().getSymbol());
				inputFields[1].setText(savedCurrent.toCurrentDecimals());
				mOhmsLaw.setCurrent(savedCurrent);
				break;
			case 5:
				inputFields[2].requestFocus();
				setPrefix(inputFields[2], savedResistance.getPrefix().getSymbol());
				inputFields[2].setText(savedResistance.toCurrentDecimals());
				mOhmsLaw.setResistance(savedResistance);
				break;
			case 6:
				inputFields[0].requestFocus();
				setPrefix(inputFields[0], savedVoltage.getPrefix().getSymbol());
				inputFields[0].setText(savedVoltage.toCurrentDecimals());
				mOhmsLaw.setVoltage(savedVoltage);
				inputFields[1].requestFocus();
				setPrefix(inputFields[1], savedCurrent.getPrefix().getSymbol());
				inputFields[1].setText(savedCurrent.toCurrentDecimals());
				mOhmsLaw.setCurrent(savedCurrent);
				mOhmsLaw.calculateUnknownValues(idCombination);
				break;
			case 7:
				inputFields[3].requestFocus();
				setPrefix(inputFields[3], savedPower.getPrefix().getSymbol());
				inputFields[3].setText(savedPower.toCurrentDecimals());
				mOhmsLaw.setPower(savedPower);
				break;
			case 10:
				inputFields[0].requestFocus();
				setPrefix(inputFields[0], savedVoltage.getPrefix().getSymbol());
				inputFields[0].setText(savedVoltage.toCurrentDecimals());
				mOhmsLaw.setVoltage(savedVoltage);
				inputFields[2].requestFocus();
				setPrefix(inputFields[2], savedResistance.getPrefix().getSymbol());
				inputFields[2].setText(savedResistance.toCurrentDecimals());
				mOhmsLaw.setResistance(savedResistance);
				mOhmsLaw.calculateUnknownValues(idCombination);
				break;
			case 14:
				inputFields[0].requestFocus();
				setPrefix(inputFields[0], savedVoltage.getPrefix().getSymbol());
				inputFields[0].setText(savedVoltage.toCurrentDecimals());
				mOhmsLaw.setVoltage(savedVoltage);
				inputFields[3].requestFocus();
				setPrefix(inputFields[3], savedPower.getPrefix().getSymbol());
				inputFields[3].setText(savedPower.toCurrentDecimals());
				mOhmsLaw.setPower(savedPower);
				mOhmsLaw.calculateUnknownValues(idCombination);
				break;
			case 15:
				inputFields[1].requestFocus();
				setPrefix(inputFields[1], savedCurrent.getPrefix().getSymbol());
				inputFields[1].setText(savedCurrent.toCurrentDecimals());
				mOhmsLaw.setCurrent(savedCurrent);
				inputFields[2].requestFocus();
				setPrefix(inputFields[2], savedResistance.getPrefix().getSymbol());
				inputFields[2].setText(savedResistance.toCurrentDecimals());
				mOhmsLaw.setResistance(savedResistance);
				mOhmsLaw.calculateUnknownValues(idCombination);
				break;
			case 21:
				inputFields[1].requestFocus();
				setPrefix(inputFields[1], savedCurrent.getPrefix().getSymbol());
				inputFields[1].setText(savedCurrent.toCurrentDecimals());
				mOhmsLaw.setCurrent(savedCurrent);
				inputFields[3].requestFocus();
				setPrefix(inputFields[3], savedPower.getPrefix().getSymbol());
				inputFields[3].setText(savedPower.toCurrentDecimals());
				mOhmsLaw.setPower(savedPower);
				mOhmsLaw.calculateUnknownValues(idCombination);
				break;
			case 35:
				inputFields[2].requestFocus();
				setPrefix(inputFields[2], savedResistance.getPrefix().getSymbol());
				inputFields[2].setText(savedResistance.toCurrentDecimals());
				mOhmsLaw.setResistance(savedResistance);
				inputFields[3].requestFocus();
				setPrefix(inputFields[3], savedPower.getPrefix().getSymbol());
				inputFields[3].setText(savedPower.toCurrentDecimals());
				mOhmsLaw.setPower(savedPower);
				mOhmsLaw.calculateUnknownValues(idCombination);
				break;
		}
		
		switch (savedInstanceState.getInt(SAVED_FOCUSED_VIEW)) {
		case R.id.volts_edittext:
			inputFields[0].requestFocus();
			inputFields[0].setSelection(inputFields[0].getText().length());
			break;
		case R.id.amps_edittext:
			inputFields[1].requestFocus();
			inputFields[1].setSelection(inputFields[1].getText().length());
			break;
		case R.id.ohms_edittext:
			inputFields[2].requestFocus();
			inputFields[2].setSelection(inputFields[2].getText().length());
			break;
		case R.id.watts_edittext:
			inputFields[3].requestFocus();
			// Keeps the cursor always at the end when any view is clicked
			inputFields[3].setSelection(inputFields[3].getText().length());
			break;
		}
	}
	
	//==================== Unused methods ========================
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
	}
}