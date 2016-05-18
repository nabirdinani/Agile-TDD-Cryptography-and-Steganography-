import java.math.BigInteger;


public class BigInt{

	BigInteger possiblePrime;

	public BigInt()
	{
		possiblePrime = BigInteger.valueOf(0);
	}
	public void randNum()
	{
		int length = (int)(Math.random() * 64 + 2);
		byte[] intBit = new byte[length];
		byte temp = (byte)128;
		System.out.println(temp);
		int val;
		for(int i = 0; i < length; i++)
		{
			intBit[i] = (byte)(Math.random()*255);
		}
		possiblePrime = new BigInteger(intBit);
	}
// ------------------------------------------------------------------------------------mod
	public BigInteger mod(int divisor)
	{
		BigInteger modTool;
		modTool = possiblePrime.divide(BigInteger.valueOf(divisor));
		modTool = modTool.multiply(BigInteger.valueOf(divisor));
		modTool = possiblePrime.subtract(modTool);
		return modTool;
	}

	public BigInteger mod(BigInteger divisor)
	{
		BigInteger modTool;
		modTool = possiblePrime.divide(divisor);
		modTool = modTool.multiply(divisor);
		modTool = possiblePrime.subtract(modTool);
		return modTool;
	}
// ------------------------------------------------------------------------------------root
	// public void root(int num)
	// {
		
	// }
// ------------------------------------------------------------------------------------power
	public void pow(int power)
	{
		for(int i = 0; i < power; i++)
			possiblePrime = possiblePrime.multiply(possiblePrime);
	}
// ------------------------------------------------------------------------------------add
	public void add(int num)
	{
		possiblePrime = possiblePrime.add(BigInteger.valueOf(num));
	}
	public void add(BigInteger num)
	{
		possiblePrime = possiblePrime.add(num);
	}
// ------------------------------------------------------------------------------------subtract
	public void subtract(BigInteger num)
	{
		possiblePrime = possiblePrime.subtract(num);
	}
	public void subtract(int num)
	{
		possiblePrime = possiblePrime.subtract(BigInteger.valueOf(num));
	}
// ------------------------------------------------------------------------------------multiply
	public void mult(BigInteger num)
	{
		possiblePrime = possiblePrime.multiply(num);
	}
	public void mult(int num)
	{
		possiblePrime = possiblePrime.multiply(BigInteger.valueOf(num));
	}
// ------------------------------------------------------------------------------------divide
	public void div(int num)
	{
		possiblePrime = possiblePrime.divide(BigInteger.valueOf(num));
	}
	public void div(BigInteger num)
	{
		possiblePrime = possiblePrime.divide(num);
	}
// ------------------------------------------------------------------------------------getnum
	public BigInteger getNum()
	{
		return possiblePrime;
	}

// ------------------------------------------------------------------------------------

	// public static void main(String [] args)
	// {
	// 	BigInt test = new BigInt();
	// 	test.randNum();
	// 	// System.out.println(test.mod(BigInteger.valueOf(27),BigInteger.valueOf(5)));
	// 	// System.out.println(test.getNum().toString());
	// }
}

