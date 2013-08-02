package net.thuang.tip_calculator;


public class TipCalculator {
	
	private float price;
	private float taxRate;
	private float tipRate;
	
	public TipCalculator(float price, float taxRate, float tipRate)
	{
		this.price = price;
		this.taxRate = taxRate;
		this.tipRate = tipRate;
	}
	
	public float getTip()
	{
		float tip = 0.0f;
		
		tip = price * tipRate;
		
		return tip;
	}
	
	public float getTax()
	{
		float tax = 0.0f;
		
		tax = price * taxRate;
		
		return tax;
	}
	
	public float getTotal()
	{
		float total = 0.0f;
		
		total = price + getTax() + getTip();
		
		return total;
	}


}
