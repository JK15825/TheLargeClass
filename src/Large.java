import java.util.ArrayList;


public class Large 
{
	ArrayList<Integer> num;
	public Large(String s)
	{
		String badChars = "abcdefghijklmnopqrstuvwxyz-+/'\"";
		boolean bad = false;
		for(int k = 0; k < badChars.length(); k++)
		{
			if(s.toLowerCase().contains(Character.toString(badChars.charAt(k))))
				bad = true;
		}
		if(bad)
		{
			try
			{
				throw new Exception("You input a bad character\n");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
	    }
	    else
	    {
	    	this.num = new ArrayList<Integer>();
	    	int spot = s.length() - 1;
		   	while (this.num.size() < s.length())
		   	{
		   		this.num.add(convertInt(s, spot));
		   		spot--;
		    }
	    }
	}
	public Large(ArrayList<Integer> num2) 
	{
		for(int k = 0; k < num2.size(); k++)
			num.add(num2.get(k));
	}
// OPPERATORS

// ADDITION
	public void add(Large l)
	{
		if (l.getSize() > this.num.size()) 
		{
			while (this.num.size() < l.getSize()) 
			{
				this.num.add(new Integer(0));
			}
		}
	    if ((l.getSize() == this.num.size()) || (l.getSize() < this.num.size())) 
	    {
	    	for (int k = 0; k < l.getSize(); k++)
	    	{
	    		int temp = this.num.get(k) + l.getDigit(k);
	    		if (Integer.toString(temp).length() == 2)
	    		{
	    			String str = Integer.toString(temp);
	    			if (k + 2 > this.num.size()) 
	    			{
	    				this.num.add(new Integer(0));
	    			}
	    			int temp2 = convertInt(str, 0) + this.num.get(k + 1);
	    			this.num.set(k + 1, temp2);
	    			this.num.set(k,  convertInt(str, 1));
	    	}
	    		else
	    		{
	    			this.num.set(k, temp);
	    		}
	    	}
	    }
	    
	}
// SUBTRACTION
	public void sub(Large l)
	{
		if (this.num.size() > l.getSize()) 
		{
			while (l.getSize() < this.num.size()) 
			{
				l.addZero();
			}
	    } 
		else if (this.num.size() < l.getSize()) 
		{
			while (this.num.size() < l.getSize()) 
			{
				this.num.add(new Integer(0));
			}
	    }
	    if (!isLarger(this.num,l)) 
	    {
	    	for (int k = 0; k < l.getSize(); k++)
	    	{
	    		int a = this.num.get(k);
	    		int b = l.getDigit(k);
	    		this.num.set(k, b);
	    		l.setDigit(k, a);
	    	}
	    }
	    if (this.num.size() == l.getSize())
	    {
	    	for (int k = 0; k < l.getSize(); k++) 
	    	{
	    		l.setDigit(k, 9 - l.getDigit(k));
	    	}
	    	for (int k = 0; k < l.getSize(); k++)
	    	{
	    		int temp;
	    		if (k == 0) 
	    		{
	    			temp = this.num.get(k) + l.getDigit(k) + 1;
	    		} 
	    		else 
	    		{
	    			temp = this.num.get(k) + l.getDigit(k);
	    		}
	    		if (Integer.toString(temp).length() == 2)
	    		{
	    			String str = Integer.toString(temp);
	    			if (k + 2 > this.num.size()) 
	    			{
	    				this.num.add(new Integer(0));
	    			}
	    			int temp2 = convertInt(str, 0) + this.num.get(k + 1);  
	    			this.num.set(k + 1,  (temp2));
	    			this.num.set(k,  (convertInt(str, 1)));
	    		}
	    		else
	    		{
	    			this.num.set(k,  (temp));
	    		}
	    	}
	    	this.num.remove(this.num.size() - 1);
	    	removeZeros(this.num);
	    }
	}
// MULTIPLYING
	public void mult(Large l)
	{
		if (l.getDigit(0) == 0 && l.getSize() == 1)
		{
			this.num.clear();
			this.num.add( (0));
	    }
	    else if (l.getDigit(0) == 1 && l.getSize() == 1)
	    {}
	    else
	    {
	    	Large total = new Large("0");
	    	Large toAdd = new Large("0");
	    	for (int a = 0; a < l.getSize(); a++)
	    	{
	    		for (int k = 0; k < this.num.size(); k++)
	    		{
	    			int temp = this.num.get(k) * l.getDigit(a);
	    			String str = Integer.toString(temp);
	    			Large t = new Large(str);
	    			if (k != 0) 
	    			{
	    				t.moveDown(k);
	    			}
	    			toAdd.add(t);
	    		}
	    		if (a != 0)
	    		{
	    			toAdd.moveDown(a);
	    		}
	    		total.add(toAdd);
	    		toAdd.empty();
	    	}
	    	this.num.clear();
	    	for (int k = 0; k < total.getSize(); k++) 
	    	{
	    		this.num.add(total.getDigit(k));
	    	}
	    }
	}
// DIVISON
// TODO: FIX THIS	
	public void div(Large l)
	{
		Large count = new Large("0");
		Large temp =  l;
		do
		{
			temp =  l;
			count.add(new Large("1"));
			temp.mult(count);
		}
		while(isLarger(num,temp));
//		temp =  l;
//		temp.mult(count);
		num = new ArrayList<Integer>();
		for(int k = 0; k < count.getSize();  k++)
		{
			num.add(count.getDigit(k));
		}
	}
	// HELPER METHODS
	public static int convertInt(String s, int i)
	{
		int k = Integer.parseInt(Character.toString(s.charAt(i)));
	    return k;
	}
	public String getAmount()
	{
		String number = "";
		for (int k = this.num.size() - 1; k >= 0; k--) 
		{
	      number = number + Integer.toString(this.num.get(k));
		}
	    return number;
	}
	public String getAmount(String prefix)
	{
		String number = prefix + " ";
		for (int k = this.num.size() - 1; k >= 0; k--) 
		{
	      number = number + Integer.toString(this.num.get(k));
		}
	    return number;
	}
	public int getSize()
	{
		return this.num.size();
	}
	private int getDigit(int k)
	{
		return this.num.get(k);
	}
	private void addZero()
	{
		this.num.add(new Integer(0));
	}
	private void setDigit(int spot, int k)
	{
		this.num.set(spot,k);
	}
	private void removeZeros(ArrayList<Integer> n)
	{
		int count = this.num.size() - 1;
	    while (this.num.get(count) == 0)
	    {
	    	this.num.remove(count);
	    	count--;
	    }
	}
	private void moveDown(int l)
	{
		ArrayList<Integer> temp = new ArrayList<Integer>();
	    for (int k = 0; k < l; k++) 
	    {
	    	temp.add( (0));
	    }
	    for (int k = 0; k < this.num.size(); k++)
	    {
	    	temp.add(this.num.get(k));
	    }
	    this.num.clear();
	    for (int k = 0; k < temp.size(); k++) 
	    {
	    	this.num.add(temp.get(k));
	    }
	}
  	private void empty() 
  	{
  		num = new ArrayList<Integer>();
  		num.add(0);
  	}
 // COMPARITORS???
 	public static Boolean isLarger(ArrayList<Integer> one, Large two)
 	{
 		if (one.size() < two.getSize()) 
 		{
 			return false;
 	    }
 	    if (one.size() > two.getSize()) 
 	    {
 	    	return true;
 	    }
 	    for (int a = one.size() - 1; a >= 0; a--)
 	    {
 	    	if (((Integer)one.get(a)).intValue() < two.getDigit(a)) 
 	    	{
 	    		return false;
 	    	}
 	    	if (((Integer)one.get(a)).intValue() > two.getDigit(a)) 
 	    	{
 	    		return true;
 	    	}
 	    }
 	    return false;
 	}
}
