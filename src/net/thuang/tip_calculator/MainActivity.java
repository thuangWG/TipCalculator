package net.thuang.tip_calculator;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemSelectedListener{

	private MySpinner localitySpinner;
	private MySpinner tipRateSpinner;
	private MySpinner splitSpinner;
	private ArrayAdapter<CharSequence> localityAdapter;
	private ArrayAdapter<CharSequence> tipRateAdapter;
	private ArrayAdapter<CharSequence> splitAdapter;
	private int[] taxRateInt;
	private String[] tipRateInt;
	private String[] splitInt;
	private ArrayList<Float> splitIntFloat = new ArrayList<Float>();
	private float taxDivider = 100000f;
	private ArrayList<Float> taxRateDecimal = new ArrayList<Float>();
	private TextView selectedTax;
	private TextView selectedTip;
	private TextView selectedSplit;
	private EditText setPrice;
	private TextView total;
	private TextView tip;
	private TextView tax;
	private TextView splitNum;
	private int lastTaxIdx=0;
	private int lastTipIdx=0;
	private int lastSplitIdx=0;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Drawable background = getResources().getDrawable(R.drawable.keroppi_background);
		background.setAlpha(30);
		
		/* setup spinner */
		localitySpinner = (MySpinner) findViewById(R.id.locality_spinner);
		tipRateSpinner = (MySpinner) findViewById(R.id.tipRate_spinner);
		splitSpinner = (MySpinner) findViewById(R.id.split_spinner);
		localitySpinner.setOnItemSelectedSameSelectionListener(this);
		tipRateSpinner.setOnItemSelectedSameSelectionListener(this);
		splitSpinner.setOnItemSelectedSameSelectionListener(this);
		localityAdapter = ArrayAdapter.createFromResource(this, R.array.locality, android.R.layout.simple_spinner_dropdown_item);
		tipRateAdapter = ArrayAdapter.createFromResource(this, R.array.tipRate, android.R.layout.simple_spinner_dropdown_item);
		splitAdapter = ArrayAdapter.createFromResource(this, R.array.splitBetween, android.R.layout.simple_spinner_dropdown_item);
		localitySpinner.setAdapter(localityAdapter);
		tipRateSpinner.setAdapter(tipRateAdapter);
		splitSpinner.setAdapter(splitAdapter);
		localitySpinner.setOnItemSelectedListener(this);
		tipRateSpinner.setOnItemSelectedListener(this);
		splitSpinner.setOnItemSelectedListener(this);
		/* get more input from ui */
		taxRateInt = getResources().getIntArray(R.array.salesTaxRate);
		tipRateInt = getResources().getStringArray(R.array.tipRate);
		splitInt = getResources().getStringArray(R.array.splitBetween);
		selectedTax = (TextView) findViewById(R.id.taxRateNum);
		selectedTax.setText("8.875");
		selectedTax.setInputType(InputType.TYPE_CLASS_NUMBER);
		selectedTip = (TextView) findViewById(R.id.tipRateNum);
		selectedTip.setText("15");
		selectedTip.setInputType(InputType.TYPE_CLASS_NUMBER);
		selectedSplit = (TextView) findViewById(R.id.splitNum);
		selectedSplit.setText("1");
		selectedSplit.setInputType(InputType.TYPE_CLASS_NUMBER);
		setPrice = (EditText) findViewById(R.id.priceInput);
		total = (TextView) findViewById(R.id.totalAmount);
		tip = (TextView) findViewById(R.id.tipNum);
		tax = (TextView) findViewById(R.id.taxNum);
		splitNum = (TextView) findViewById(R.id.SplitAmount);
		
		for (int i=0; i<taxRateInt.length; i++)
		{
			taxRateDecimal.add(taxRateInt[i]/taxDivider);
		}
		
		
		splitIntFloat.add(1.0f);
		for (int i=1; i<(splitInt.length-1); i++)
		{
			splitIntFloat.add(Float.parseFloat(splitInt[i])*1.0f);
		}
	}


	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		
		parent.getItemAtPosition(pos);
		final int pos_external=pos;
		
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
		
		switch(parent.getId())
		{
			case R.id.locality_spinner:
			{
				if (pos < taxRateDecimal.size()) //last position is for custom tax rate
				{
					selectedTax.setText(String.valueOf(taxRateDecimal.get(pos)*100));	
					lastTaxIdx=pos;
				}
				else
				{
					new AlertDialog.Builder(this)
				    .setTitle("Custom Tax Rate")
				    .setMessage("Set your own tax rate:")
				    .setView(input)
				    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int whichButton) {
				        	if (!input.getText().toString().equals(""))
				        	{
				        		selectedTax.setText(input.getText());
				        		lastTaxIdx=pos_external;
				        	}
				        	else
				        	{
				        		if (lastTaxIdx != (taxRateDecimal.size()))
				        			localitySpinner.setSelection(lastTaxIdx);
				        	}

				        }
				    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int whichButton) {
				        	if (lastTaxIdx != (taxRateDecimal.size()))
				        	{
				        		localitySpinner.setSelection(lastTaxIdx);
				        	}
				            
				        }
				    }).show();
				}
				
				break;
			}
			case R.id.tipRate_spinner:
			{
				
				if (pos < (tipRateInt.length-1))
				{
					selectedTip.setText(tipRateAdapter.getItem(pos));
					lastTipIdx = pos;
				}
				else
				{
					new AlertDialog.Builder(this)
				    .setTitle("Custom Tip Rate")
				    .setMessage("Set your own tip rate:")
				    .setView(input)
				    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int whichButton) {
				        	if (!input.getText().toString().equals(""))
				        	{
				        		selectedTip.setText(input.getText());
				        		lastTipIdx=pos_external;
				        	}
				        	else
				        	{
				        		if (lastTipIdx != (tipRateInt.length-1))
				        			tipRateSpinner.setSelection(lastTipIdx);
				        	}
				        	

				        }
				    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int whichButton) {
				        	if (lastTipIdx != (tipRateInt.length-1))
				        	{
				        		tipRateSpinner.setSelection(lastTipIdx);
				        	}
				            
				        }
				    }).show();
				}
				break;
			}
			
			case R.id.split_spinner:
			{
				if (pos < (splitInt.length-1))
				{
					if (pos != 0)
					{
						selectedSplit.setText(splitAdapter.getItem(pos));
					}
					else
					{
						selectedSplit.setText("1");
					}
					
					lastSplitIdx=pos;
				}
				else
				{
					new AlertDialog.Builder(this)
				    .setTitle("Custom Split")
				    .setMessage("Split how many way?:")
				    .setView(input)
				    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int whichButton) {
				        	if (!input.getText().toString().equals(""))
				        	{
				        		selectedSplit.setText(input.getText());
				        		lastSplitIdx=pos_external;
				        	}
				        	else
				        	{
				        		if (lastSplitIdx != (splitInt.length-1))
				        			splitSpinner.setSelection(lastSplitIdx);
				        	}

				        }
				    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int whichButton) {
				        	if (lastSplitIdx != (splitInt.length-1))
				        	{
				        		splitSpinner.setSelection(lastSplitIdx);
				        	}
				        }
				    }).show();
				}
				break;
			}
				
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		return;
	}
	
	public static String roundToTwoDigit(float paramFloat) {
	    return String.format("%.2f%n", paramFloat);
	}
	
	public boolean isFloat( String input ) {
	    try {
	        Float.parseFloat( input );
	        return true;
	    }
	    catch( NumberFormatException e ) {
	        return false;
	    }
	    catch( NullPointerException e) {
	    	return false;
	    }
	}
	
	public void onCalculateBtnClick(View view)
	{
		if (setPrice.getText().toString().equals(""))
		{
			Toast.makeText(getApplicationContext(), "Invalid Price!", Toast.LENGTH_LONG).show();
			return;
		}
		float taxRate = Float.parseFloat((String) selectedTax.getText().toString());
		float tipRate = Float.parseFloat((String) selectedTip.getText().toString());
		float price = Float.parseFloat((String) setPrice.getText().toString());
		
		TipCalculator helper = new TipCalculator(price, (taxRate/100), (tipRate/100));
		
		float splitPrice = 0.0f;
		
		if (lastSplitIdx< (splitInt.length)-1)
		{
			splitPrice = helper.getTotal() / splitIntFloat.get(lastSplitIdx);
		}
		else
		{
			splitPrice = helper.getTotal() / (Float.parseFloat((String) selectedSplit.getText().toString()) * 1.0f);
		}
		
		tip.setText(roundToTwoDigit(helper.getTip()));
		tax.setText(roundToTwoDigit(helper.getTax()));
		total.setText(roundToTwoDigit(helper.getTotal()));
		splitNum.setText(roundToTwoDigit(splitPrice));
	}
	

}
