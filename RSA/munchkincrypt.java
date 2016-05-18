import java.math.*;
import java.util.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class munchkincrypt
{
	public static final Random rand = new Random();
	public static final BigInteger one = new BigInteger("1");
	// Actual main
	public static void main(String[] args) throws IOException
	{
		Scanner kb = new Scanner(System.in);
		System.out.print("Test(1), Run(2), or Attack(3): ");
		int t = kb.nextInt();
		if(t==1)
			test();
		else if(t==2)
			run();
		else
			atk();
	}
	public static void run() throws IOException
	{
		Scanner kb = new Scanner(System.in);
		boolean run = true;
		String in;
 		int numBits=0;
 		String store1 = "";
 		String store2 = "";
 		System.out.println("Main Menu Commands: \nExit\nEncrypt\nDecrypt\nGenerate\n");
 		do
 		{
 			System.out.print("Main Menu\nEnter a command: ");
 			in = kb.next();
 			switch(in)
 			{
 				case "Exit": 		run = false;
 									break;

 				case "Generate": 	gen();
 									break;

 				case "Encrypt": 	System.out.print("Enter file to be encrypted: ");
	 								store1 = kb.next();
	 								System.out.print("Enter key file: ");
	 								store2 = kb.next();
	 								encryptFl(store1, store2);
	 								break;

	 			case "Decrypt":		System.out.print("Enter file to be decrypted: ");
	 								store1 = kb.next();
	 								System.out.print("Enter key file: ");
	 								store2 = kb.next();
	 								decryptFl(store1, store2);
	 								break;
 			}

 			System.out.println("\n");	
 		}while(run);
	}
	//------------------------------------------------------------attacks
	public static void atk() throws IOException
	{
		System.out.println("Attack 1 encrypts the file with your own encryption after generating a private and public key.\n"+
							"Attack 2 deletes a file it thinks has been encrypted using our encrytion method.\n"+
							"Attack 3 finds the private key given the public key.");
		type1("message.txt"); //This attack should scramble message.txt
		type2("message.txt"); //This attack should not do anything
		type2("encrypted-message.txt"); //This attack deletes the file if it is encrypted
		FermatAttack(new BigInteger("1690106601581"), new BigInteger("179426453"));
	}
	//------------------------------------------------------------test main
	public static void test() throws IOException
	{
		//---------------------------------------------Prime Test
		System.out.print("Generate a prime number: ");
		BigInteger p = getBigInteger();
		if(p.compareTo(BigInteger.valueOf(0))>0)
			System.out.println("PASSED");
		else
			System.out.println("FAILED");

		// --------------------------------------------Factor Test
		BigInteger factorTest1 = BigInteger.valueOf(7);
		BigInteger factorTest2 = BigInteger.valueOf(22);
		System.out.print("Testing if 7 and 22 are coprime, expected TRUE: ");
		if(factors(factorTest1, factorTest2))
			System.out.println("PASSED");
		else
			System.out.println("FAILED");
		
		// --------------------------------------------d = ed test

		BigInteger d = getD(BigInteger.valueOf(7), BigInteger.valueOf(22));
		System.out.print("Finding d value using 7 and 22, expected TRUE: ");
		if(mod(d.multiply(BigInteger.valueOf(7)), BigInteger.valueOf(22)).compareTo(BigInteger.valueOf(1))==0)
			System.out.println("PASSED");
		else
			System.out.println("FAILED");
		// System.out.println(e.multiply(d).mod(on));

		// --------------------------------------------Pow Test
		BigInteger pow = BigInteger.valueOf(1505311);
		BigInteger exp = BigInteger.valueOf(2);
		System.out.print("Testing 1505311^2, expected 2265961206721: ");
		if(pow(pow, exp).compareTo(new BigInteger("2265961206721"))==0)
			System.out.println("PASSED");
		else
			System.out.println("FAILED");

		// --------------------------------------------Mod Test
		BigInteger quo = BigInteger.valueOf(1505310);
		BigInteger div = BigInteger.valueOf(10);
		System.out.print("Testing 1505310 % 10, expected 0: ");
		if(mod(quo, div).compareTo(new BigInteger("0"))==0)
			System.out.println("PASSED");
		else
			System.out.println("FAILED");

		// -------------------------------------------ModPow Test
		System.out.print("Testing 16 modPow 5 3, expected 1: ");
		if(modPow(BigInteger.valueOf(16),BigInteger.valueOf(5),BigInteger.valueOf(3)).compareTo(BigInteger.valueOf(1))==0)
			System.out.println("PASSED");
		else
			System.out.println("FAILED");

		//--------------------------------------------
		encryptFl("message.txt","pubKey.txt");
 		decryptFl("encrypted-message.txt", "priKey.txt");


	}
	public static void gen() throws IOException
	{
		Scanner kb = new Scanner(System.in);
		int numBits = 0;
		System.out.print("Random bitlength(1) or specific bitlength(2): ");
		do
		{
			if(kb.hasNextInt())
			{
				do
				{
					numBits = kb.nextInt();
					if(numBits == 2)
					{
						do
						{
							System.out.print("Enter an approximate bit length between 16 and 512: ");
							do
							{
								if(kb.hasNextInt())
								{
									numBits = kb.nextInt();
									if(numBits >= 16 && numBits <= 512)
									{	
										// kb.close();
										System.out.printf("Generating your keys of approximately %d bits.\n", numBits);
										deGenerate(numBits, "priKey.txt", "pubKey.txt");
										// System.out.println(priKey.toString());
										break;
									}
									else
									{
										System.out.println("Value given not between 16 and 512");
									}
									break;
								}
								else
								{
									System.out.println("Not a number between 16 and 512");
									String trash = kb.nextLine();
								}

							}
							while(true);
							break;
						}while(true);
						break;
					}
					else if(numBits == 1)
					{
						System.out.println("Generating your keys of random bitlength:");
						deGenerate(getRandomBit(512,16), "priKey.txt", "pubKey.txt");
						break;
					}
				}while(true);
				break;
			}
			else
			{
				System.out.println("Not a valid entry please choose 1 or 2.");
				String trash = kb.nextLine();
			}

		}while(true);
	
	}
	public static void deGenerate(int x, String priFile, String pubFile) throws IOException
	{
		BigInteger p = getPrime(x);
		BigInteger q = getPrime(x);
		while(p.compareTo(q)==0)
		{
			p = getPrime(x);
			q = getPrime(x);
		}
		BigInteger n = p.multiply(q);
		BigInteger on = p.subtract(BigInteger.valueOf(1)).multiply(q.subtract(BigInteger.valueOf(1)));
		BigInteger e = coprime(on);
		BigInteger d = getD(e, on);

		Scanner kb = new Scanner(System.in);
		
		do
		{
			System.out.print("Display Keys?(y/n): ");
			if(kb.hasNext())
			{

				switch(kb.next())
				{
					case "y": System.out.println("Private Keys: ");
							System.out.println("p:"+p);
							System.out.println("q:"+q);
							System.out.println("d:"+d);
							System.out.println("Public Keys: ");
							System.out.println("n:"+n);
							System.out.println("e:"+e);
							

					default: FileWriter privatefl = new FileWriter(priFile, false);
							FileWriter publicfl = new FileWriter(pubFile, false);

							privatefl.write(n.toString());
							privatefl.write(":");
							privatefl.write(d.toString());
							privatefl.write(":");
							privatefl.write(p.toString());
							privatefl.write(":");
							privatefl.write(q.toString());
							privatefl.close();

							publicfl.write(n.toString());
							publicfl.write(":");
							publicfl.write(e.toString());
							publicfl.close();
							break;
				}
				break;
			}
			else
			{
				System.out.println("Invalid entry.");
				kb.next();
			}
			break;
		}while(true);
	}
	public static BigInteger getPrime(int x)
	{
		BigInteger big;
		while(true)
		{
			int lower = x - 10;
			int upper = x + 10;

			if(lower < 16)
				for(; lower < 16; lower++)
					upper++;

			if(upper > 512)
				for(; upper > 512; upper--)
					lower--;

			big = new BigInteger(getRandomBit(upper, lower),rand);
			String stringBig = big.toString();
			int last = Integer.parseInt(stringBig.substring(stringBig.length()-1));
			if(last%2 != 0 && last != 5)
			{
				// System.out.println("new num");
				if(isPrime(big))
					return big;

			}
		}
	}
	public static BigInteger getBigInteger()
	{
		BigInteger big;
		while(true)
		{
			big = new BigInteger(getRandomBit(512,16),rand);
			String stringBig = big.toString();
			int last = Integer.parseInt(stringBig.substring(stringBig.length()-1));
			if(last%2 != 0 && last != 5)
			{
				// System.out.println("new num");
				if(isPrime(big))
					return big;
			}
		}
	}
	
	public static int getRandomBit(int max,int min)
	{
		return (int)((Math.random()*(max-min))+min);
	}
	
	public static BigInteger getRandomLessThan(BigInteger num)
	{
		BigInteger random;
		while(true)
		{
			random = new BigInteger(getRandomBit(num.bitLength(),0),rand);
			if(random.compareTo(num) <= 0)
				return random;
		}
	}
	
	public static boolean isPrime(BigInteger num)
	{
		//higher iterations: more probability more time
		//lower iterations: less probability less time
		long iterations = 200;
		if(fermat(num,iterations) && millerrabin(num,iterations))
			return true;
		return false;
	}
	
	public static boolean fermat(BigInteger num, long iter)
	{
		for(int i=0;i<iter;i++)
		{
			BigInteger a = getRandomLessThan(num);
			// System.out.println("fermat before modPow");
			a = a.modPow(num.subtract(one), num);
			if(!a.equals(one))
				return false;
		}
		return true;
	}
	
	public static boolean millerrabin(BigInteger num, long iter)
	{
		final BigInteger two = new BigInteger("2");
		long s = 0;
		BigInteger d = num.subtract(one);
		while(mod(d, two).equals(new BigInteger("0")))
		{
			// System.out.println("Miller 1");
			s++;
			d = d.divide(two);
		}
		for(int i=0;i<iter;i++)
		{
			// System.out.println("Miller 2");
			BigInteger a = getRandomLessThan(num);
			BigInteger x = a.modPow(d, num);
			if(x.equals(one) || x.equals(num.subtract(one)))
				continue;
			int r=1;
			for(; r<s;r++)
			{
				x = x.modPow(two, num);
				if(x.equals(one))
					return false;
				if(x.equals(num.subtract(one)))
					break;
			}
			if(r == s)
				return false;
		}
		return true;
	}

	public static BigInteger coprime(BigInteger num)
	{
		BigInteger two = new BigInteger("2");
		boolean go = false;
		BigInteger random;
		do
		{
			random = new BigInteger(num.bitLength()/2,rand);
			// System.out.println(random.toString());
			if(random.compareTo(num) == -1)
			{
				go = factors(num, random);
			}
		}while(!go);
		return random;
	}

	public static boolean factors(BigInteger t1, BigInteger t2)
	{

		if(isPrime(t1)||isPrime(t2))
			return true;

		BigInteger lower;
		BigInteger upper;
		BigInteger temp;
		if(t1.compareTo(t2)>0)
		{
			upper = new BigInteger(t1.toString());
			lower = new BigInteger(t2.toString());
		}
		else
		{
			lower = new BigInteger(t1.toString());
			upper = new BigInteger(t2.toString());
		}

		while(lower.compareTo(BigInteger.valueOf(0))>0)
		{
			temp = lower;
			lower = mod(upper,lower);
			upper = temp;
		}
		if(upper.compareTo(BigInteger.valueOf(1))==0)
			return true;

		return false;

	}

	public static BigInteger modPow(BigInteger base, BigInteger expon, BigInteger m)
	{
		BigInteger result = new BigInteger(base.toString());
		// System.out.printf("runs %s times", expon.toString());
		for(int i = 0; i< 1; i++)
		{
			result = result.multiply(base);
			result = mod(result, m);
			if(result.equals(one))
				return result;
			if(result.equals(base))
				return result;
			// System.out.println(i);
		}
		return result;
	}
	public static BigInteger mod(BigInteger possiblePrime, BigInteger divisor)
	{
		// System.out.println("PossiblePrime: " + possiblePrime + " \nDivisor: " + divisor);
		BigInteger modTool;
		modTool = possiblePrime.divide(divisor);
		// System.out.println("After Division "+modTool+" 1");
		modTool = modTool.multiply(divisor);
		// System.out.println(modTool+" 2");
		modTool = possiblePrime.subtract(modTool);
		// System.out.println(modTool+" 3");
		return modTool;
	}
	public static BigInteger pow(BigInteger base, BigInteger power)
	{
		// System.out.println("Base:" +base);

		// System.out.println("Expon:" +power);
		if(power.intValue() == 0)
			return BigInteger.valueOf(1);
		else if(power.intValue() < 0)
			return base.multiply( pow( base, power.add( BigInteger.valueOf(1) ) ) );
		else
			return base.multiply( pow( base, power.subtract( BigInteger.valueOf(1) ) ) );
	}
	public static BigInteger fakert(BigInteger num)
	{
		BigInteger hold = BigInteger.valueOf(0);
		BigInteger inc;
		BigInteger root = num.divide(BigInteger.valueOf(2));
		BigInteger comp = root.multiply(root);
		int counter = 0;
		// System.out.println("Loop 1 begin");
		while(comp.compareTo(num)>0)
		{
			hold = new BigInteger(root.toString());
			root = root.divide(BigInteger.valueOf(2));
			comp = root.multiply(root);
			counter++;
		}
		inc = hold.subtract(root).divide(BigInteger.valueOf(2));
		hold = new BigInteger(root.toString());
		root = root.add(inc);
		comp = root.multiply(root);
		// System.out.println("Loop 1 end, Loop 2 begin: with, comp at"+ comp.toString()+ "and root at "+ root.toString());

		for(;counter>0;counter--)
		{
			hold = new BigInteger(root.toString());
			if(comp.compareTo(num)>0)
			{
				inc = inc.divide(BigInteger.valueOf(2));
				root = root.subtract(inc);
				comp = root.multiply(root);
			}
			else
			{
				inc = inc.divide(BigInteger.valueOf(2));
				root = root.add(inc);
				comp = root.multiply(root);
			}
		}
		root.subtract(BigInteger.valueOf(1));
		// System.out.println("found root");
		return root;
	}
	public static BigInteger getD(BigInteger e, BigInteger o)
	{
		BigInteger holdo = o;
		BigInteger x = BigInteger.valueOf(0);
		BigInteger y = BigInteger.valueOf(1);
		BigInteger prevx = BigInteger.valueOf(1);
		BigInteger prevy =  BigInteger.valueOf(0);
		BigInteger temp;
		BigInteger q;
		BigInteger r;
		int count = 0;
		while(e.compareTo(BigInteger.valueOf(0))>0)
		{
			q = o.divide(e);
			r = mod(o, e);

			o = e;
			e = r;

			temp = x;
			x = prevx.subtract(q.multiply(x));
			prevx = temp;
			temp = y;
			y = prevy.subtract(q.multiply(y));
			prevy = temp;
		}
		if(prevy.compareTo(BigInteger.valueOf(0))>0)
		{
			return prevy;
		}
		else
			return holdo.add(prevy);
	}

	public static void encryptFl(String filename, String keyPath) throws IOException
	{
		Scanner getK;
		try
		{
			getK = new Scanner(new File(keyPath));
		}catch (IOException e)
		{
			System.out.println("The file " + keyPath
				+ " could not be opened.");
			return;
		}
		String holdKeys = "";
		while(getK.hasNext())
			holdKeys+=getK.nextLine();
		BigInteger holdN = new BigInteger(holdKeys.split(":")[0]);
		BigInteger holdE = new BigInteger(holdKeys.split(":")[1]);

		// System.out.println("encrypt with "+ holdE + " mod "+ holdN);

		Scanner fl;
		try
		{
			fl = new Scanner(new File(filename));
		}catch (IOException e)
		{
			System.out.println("The file " + filename
				+ " could not be opened.");
			return;
		}
		FileWriter flout = new FileWriter(("encrypted-"+filename), false);
		// FileWriter fout = new FileWriter(("fake.txt"), false);
		String buff = "";
		byte[] ascii;
		BigInteger hold;
		while(fl.hasNext())
		{
			buff = fl.nextLine();
			ascii = buff.getBytes("US-ASCII");
			for(int i = 0; i< ascii.length; i++)
			{
				// fout.write(ascii[i]+" ");
				hold = BigInteger.valueOf((int)ascii[i]);
				hold = hold.modPow(holdE, holdN);
				flout.write(hold.toString()+"~");
			}
		}
		System.out.println("Encrypted message saved as "+("encrypted-"+filename));
		fl.close();
		flout.close();
		getK.close();
		// fout.close();
	}

	public static void decryptFl(String filename, String keyPath) throws IOException
	{
		Scanner getK;
		try
		{
			getK = new Scanner(new File(keyPath));
		}catch (IOException e)
		{
			System.out.println("The file " + keyPath
				+ " could not be opened.");
			return;
		}
		String holdKeys = "";
		while(getK.hasNext())
			holdKeys+=getK.nextLine();
		BigInteger holdN = new BigInteger(holdKeys.split(":")[0]);
		BigInteger holdD = new BigInteger(holdKeys.split(":")[1]);

		// System.out.println("decrypt with "+ holdD + " mod "+ holdN);

		BigInteger hold;
		Scanner fl;
		try
		{
			fl = new Scanner(new File(filename));
		}catch (IOException e)
		{
			System.out.println("The file " + filename
				+ " could not be opened.");
			return;
		}
		FileWriter flout = new FileWriter(("decypted-"+filename), false);
		String buff = "";
		while(fl.hasNext())
			buff+=fl.nextLine();

		for(int i = 0; i < (buff.split("~").length); i++)
		{
			hold = new BigInteger(buff.split("~")[i]);
			// System.out.println(hold.toString());
			hold = hold.modPow(holdD, holdN);
			// System.out.println("------------------------------------------------------------------------");
			// System.out.println(hold.toString());
			flout.write((char)(hold.intValue()));
		}
		System.out.println("Decrypted message saved as "+("decrypted-"+filename));
		fl.close();
		flout.close();
		getK.close();
	}

	public static void type1(String filename) throws IOException //private encryption
	{
		String t1 = "t1.txt";
		String t2 = "t2.txt";
		deGenerate(getRandomBit(512,16), t1, t2);
		encryptFl(filename, t1);
	}
	public static void type2(String filename) throws IOException //private encryption
	{
		File x = new File(filename);
		Scanner fl = new Scanner(x);
		int count = 0;
		String hold = "";
		while(fl.hasNext())
		{
			hold = fl.nextLine();
			if(hold.indexOf("~")>0)
				count++;
		}
		fl.close();

		if(count>3)
		{
			x.delete();
		}
	}
	public static BigInteger sqrt(BigInteger n){
		BigInteger a = BigInteger.ONE;
		BigInteger b = new BigInteger(n.shiftRight(5).add(new BigInteger("8")).toString());
		while(b.compareTo(a) >= 0) {
			BigInteger mid = new BigInteger(a.add(b).shiftRight(1).toString());
			if(mid.multiply(mid).compareTo(n) > 0) b = mid.subtract(BigInteger.ONE);
			else a = mid.add(BigInteger.ONE);
		}
		return a.subtract(BigInteger.ONE);
	}
	
	public static boolean isSquare(BigInteger num){
		if(num.equals(pow(sqrt(num), BigInteger.valueOf(2)))) return true;
		else return false;
	}
	
	public static void FermatAttack(BigInteger  n, BigInteger  e){
		BigInteger a;
		BigInteger b2;
		
		a = sqrt(n);
		b2 = pow(a, BigInteger.valueOf(2)).subtract(n);
		if(b2.max(BigInteger.valueOf(0)).equals(BigInteger.valueOf(0))){
			a = a.add(BigInteger.valueOf(1));
			b2 = pow(a, BigInteger.valueOf(2)).subtract(n);
		}
		
		while(!isSquare(b2)){
			a = a.add(BigInteger.valueOf(1));
			b2 = pow(a, BigInteger.valueOf(2)).subtract(n);
		}
		
		BigInteger p;
		BigInteger q;
		p = a.subtract(sqrt(b2));
		q = a.add(sqrt(b2));
		BigInteger d = getD(e, p.subtract(BigInteger.valueOf(1)).multiply(q.subtract(BigInteger.valueOf(1))));
		
		//Prints values of private key
		System.out.println("p = " + p.toString());
		System.out.println("q = " + q.toString());
		System.out.println("d = " + d.toString());
	}
}
