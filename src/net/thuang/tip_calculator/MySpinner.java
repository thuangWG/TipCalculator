package net.thuang.tip_calculator;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

public class MySpinner extends Spinner {
	
	OnItemSelectedListener listener;

	public MySpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setSelection(int position)
	{
		boolean sameSelected = position == getSelectedItemPosition();
		super.setSelection(position);
		if (sameSelected)
		{
			listener.onItemSelected(this, getSelectedView(), position, getSelectedItemId());
		}
	}
	
	public void setOnItemSelectedSameSelectionListener(android.widget.AdapterView.OnItemSelectedListener listener)
	{
		this.listener = listener;
	}
	

}
