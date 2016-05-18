
/*
 *Import List
 */
import java.awt.Color;
import java.awt.Insets;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;

/*
 *Class Steganography_View
 */
public class Steganography_View extends JFrame
{
	//sie variables for window
	private static int WIDTH  = 500;
	private static int HEIGHT = 400;
	
	//elements for JPanel
	private JTextArea 	input;
	private JScrollBar 	scroll,scroll2;
	private JButton		encodeButton,decodeButton,attackRecriptButton,attackDestroyButton;
	private JLabel		image_input;
	
	//elements for Menu
	private JMenu 		file;
	private JMenuItem 	encode;
	private JMenuItem 	decode;
	private JMenuItem   attackRecript;
	private JMenuItem   attackDestroy;
	private JMenuItem 	exit;
	
	
	public Steganography_View(String name)
	{
		//set the title of the JFrame
		super(name);
		
		//Menubar
		JMenuBar menu = new JMenuBar();
		
		JMenu file = new JMenu("File");	file.setMnemonic('F');
		encode = new JMenuItem("Encode"); encode.setMnemonic('E'); file.add(encode);
		decode = new JMenuItem("Decode"); decode.setMnemonic('D'); file.add(decode);
		attackRecript = new JMenuItem("Attack to Overwrite"); attackRecript.setMnemonic('K'); file.add(attackRecript);
		attackDestroy = new JMenuItem("Attack to Destroy"); attackDestroy.setMnemonic('L'); file.add(attackDestroy);
		file.addSeparator();
		exit = new JMenuItem("Exit"); exit.setMnemonic('x'); file.add(exit);
		
		menu.add(file);
		setJMenuBar(menu);
		
		// display rules
		setResizable(true);						//allow window to be resized: true?false
		setBackground(Color.lightGray);			//background color of window: Color(int,int,int) or Color.name
		setLocation(100,100);					//location on the screen to display window
        setDefaultCloseOperation(EXIT_ON_CLOSE);//what to do on close operation: exit, do_nothing, etc
        setSize(WIDTH,HEIGHT);					//set the size of the window
        setVisible(true);						//show the window: true?false
	}
	
	/*
	 *@return The menu item 'Encode'
	 */
	public JMenuItem	getEncode()		{ return encode;			}
	/*
	 *@return The menu item 'Decode'
	 */
	public JMenuItem	getDecode()		{ return decode;			}
	/*
	 *@return The menu item 'attackDecript'
	 */
	public JMenuItem	getAttackRecript()		{ return attackRecript;			}
	/*
	 *@return The menu item 'Decode'
	 */
	public JMenuItem	getAttackDestroy()		{ return attackDestroy;			}
	/*
	 *@return The menu item 'Exit'
	 */
	public JMenuItem	getExit()		{ return exit;				}
	/*
	 *@return The TextArea containing the text to encode
	 */
	public JTextArea	getText()		{ return input;				}
	/*
	 *@return The JLabel containing the image to decode text from
	 */
	public JLabel		getImageInput()	{ return image_input;		}
	/*
	 *@return The JPanel displaying the Encode View
	 */
	public JPanel		getTextPanel()	{ return new Text_Panel();	}
	/*
	 *@return The JPanel displaying the Decode View
	 */
	public JPanel		getImagePanel()	{ return new Image_Panel();	}
	/*
	 *@return The JPanel displaying the Decode View
	 */
	public JPanel		getAttackRecriptPanel()	{ return new Attack_Recription_Panel();	}
	/*
	 *@return The JPanel displaying the Decode View
	 */
	public JPanel		getAttackDestroyPanel()	{ return new Attack_Destroy_Panel();	}

	/*
	 *@return The Encode button
	 */
	public JButton		getEButton()	{ return encodeButton;		}
	/*
	 *@return The Decode button
	 */
	public JButton		getDButton()	{ return decodeButton;		}
	/*
	 *@return The Decode button
	 */
	public JButton		getAttackRecriptButton()	{ return attackRecriptButton;		}
	/*
	 *@return The Decode button
	 */
	public JButton		getAttackDestroyButton()	{ return attackDestroyButton;		}
	

	/*
	 *Class Text_Panel
	 */
	private class Text_Panel extends JPanel
	{
		/*
		 *Constructor to enter text to be encoded
		 */
		public Text_Panel()
		{
			//setup GridBagLayout
			GridBagLayout layout = new GridBagLayout(); 
			GridBagConstraints layoutConstraints = new GridBagConstraints(); 
			setLayout(layout);
			
			input = new JTextArea();
			layoutConstraints.gridx 	= 0; layoutConstraints.gridy = 0; 
			layoutConstraints.gridwidth = 1; layoutConstraints.gridheight = 1; 
			layoutConstraints.fill 		= GridBagConstraints.BOTH; 
			layoutConstraints.insets 	= new Insets(0,0,0,0); 
			layoutConstraints.anchor 	= GridBagConstraints.CENTER; 
			layoutConstraints.weightx 	= 1.0; layoutConstraints.weighty = 50.0;
			JScrollPane scroll = new JScrollPane(input,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
			layout.setConstraints(scroll,layoutConstraints);
			scroll.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
	    	add(scroll);
	    	
	    	encodeButton = new JButton("Encode Now");
	    	layoutConstraints.gridx 	= 0; layoutConstraints.gridy = 1; 
			layoutConstraints.gridwidth = 1; layoutConstraints.gridheight = 1; 
			layoutConstraints.fill 		= GridBagConstraints.BOTH; 
			layoutConstraints.insets 	= new Insets(0,-5,-5,-5); 
			layoutConstraints.anchor 	= GridBagConstraints.CENTER; 
			layoutConstraints.weightx 	= 1.0; layoutConstraints.weighty = 1.0;
			layout.setConstraints(encodeButton,layoutConstraints);
	    	add(encodeButton);
	    	
	    	//set basic display
			setBackground(Color.lightGray);
			setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
		}
	}
	
	/*
	 *Class Image_Panel
	 */
	private class Image_Panel extends JPanel
	{
		/*
		 *Constructor for displaying an image to be decoded
		 */
		public Image_Panel()
		{
			//setup GridBagLayout
			GridBagLayout layout = new GridBagLayout(); 
			GridBagConstraints layoutConstraints = new GridBagConstraints(); 
			setLayout(layout);
			
			image_input = new JLabel();
			layoutConstraints.gridx 	= 0; layoutConstraints.gridy = 0; 
			layoutConstraints.gridwidth = 1; layoutConstraints.gridheight = 1; 
			layoutConstraints.fill 		= GridBagConstraints.BOTH; 
			layoutConstraints.insets 	= new Insets(0,0,0,0); 
			layoutConstraints.anchor 	= GridBagConstraints.CENTER; 
			layoutConstraints.weightx 	= 1.0; layoutConstraints.weighty = 50.0;
			JScrollPane scroll2 = new JScrollPane(image_input,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
			layout.setConstraints(scroll2,layoutConstraints);
			scroll2.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			image_input.setHorizontalAlignment(JLabel.CENTER);
	    	add(scroll2);
	    	
	    	decodeButton = new JButton("Decode Now");
	    	layoutConstraints.gridx 	= 0; layoutConstraints.gridy = 1; 
			layoutConstraints.gridwidth = 1; layoutConstraints.gridheight = 1; 
			layoutConstraints.fill 		= GridBagConstraints.BOTH; 
			layoutConstraints.insets 	= new Insets(0,-5,-5,-5); 
			layoutConstraints.anchor 	= GridBagConstraints.CENTER; 
			layoutConstraints.weightx 	= 1.0; layoutConstraints.weighty = 1.0;
			layout.setConstraints(decodeButton,layoutConstraints);
	    	add(decodeButton);
	    	
	    	//set basic display
			setBackground(Color.lightGray);
			setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
	    }
	 }
	
	/*
	* Class Attack_Pannel
	*/
	private class Attack_Recription_Panel extends JPanel
	{
		/*
		 *Constructor for displaying an image to be decoded
		 */
		public Attack_Recription_Panel()
		{
			//setup GridBagLayout
			GridBagLayout layout = new GridBagLayout(); 
			GridBagConstraints layoutConstraints = new GridBagConstraints(); 
			setLayout(layout);
			
			image_input = new JLabel();
			layoutConstraints.gridx 	= 0; layoutConstraints.gridy = 0; 
			layoutConstraints.gridwidth = 1; layoutConstraints.gridheight = 1; 
			layoutConstraints.fill 		= GridBagConstraints.BOTH; 
			layoutConstraints.insets 	= new Insets(0,0,0,0); 
			layoutConstraints.anchor 	= GridBagConstraints.CENTER; 
			layoutConstraints.weightx 	= 1.0; layoutConstraints.weighty = 50.0;
			JScrollPane scroll2 = new JScrollPane(image_input,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
			layout.setConstraints(scroll2,layoutConstraints);
			scroll2.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			image_input.setHorizontalAlignment(JLabel.CENTER);
	    	add(scroll2);
	    	
	    	attackRecriptButton = new JButton("Attack to Overwrite Now");
	    	layoutConstraints.gridx 	= 0; layoutConstraints.gridy = 1; 
			layoutConstraints.gridwidth = 1; layoutConstraints.gridheight = 1; 
			layoutConstraints.fill 		= GridBagConstraints.BOTH; 
			layoutConstraints.insets 	= new Insets(0,-5,-5,-5); 
			layoutConstraints.anchor 	= GridBagConstraints.CENTER; 
			layoutConstraints.weightx 	= 1.0; layoutConstraints.weighty = 1.0;
			layout.setConstraints(attackRecriptButton,layoutConstraints);
	    	add(attackRecriptButton);
	    	
	    	//set basic display
			setBackground(Color.lightGray);
			setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
	    }
	 }

	 private class Attack_Destroy_Panel extends JPanel
	{
		/*
		 *Constructor for displaying an image to be decoded
		 */
		public Attack_Destroy_Panel()
		{
			//setup GridBagLayout
			GridBagLayout layout = new GridBagLayout(); 
			GridBagConstraints layoutConstraints = new GridBagConstraints(); 
			setLayout(layout);
			
			image_input = new JLabel();
			layoutConstraints.gridx 	= 0; layoutConstraints.gridy = 0; 
			layoutConstraints.gridwidth = 1; layoutConstraints.gridheight = 1; 
			layoutConstraints.fill 		= GridBagConstraints.BOTH; 
			layoutConstraints.insets 	= new Insets(0,0,0,0); 
			layoutConstraints.anchor 	= GridBagConstraints.CENTER; 
			layoutConstraints.weightx 	= 1.0; layoutConstraints.weighty = 50.0;
			JScrollPane scroll2 = new JScrollPane(image_input,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
			layout.setConstraints(scroll2,layoutConstraints);
			scroll2.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			image_input.setHorizontalAlignment(JLabel.CENTER);
	    	add(scroll2);
	    	
	    	attackDestroyButton = new JButton("Attack to Destroy Now");
	    	layoutConstraints.gridx 	= 0; layoutConstraints.gridy = 1; 
			layoutConstraints.gridwidth = 1; layoutConstraints.gridheight = 1; 
			layoutConstraints.fill 		= GridBagConstraints.BOTH; 
			layoutConstraints.insets 	= new Insets(0,-5,-5,-5); 
			layoutConstraints.anchor 	= GridBagConstraints.CENTER; 
			layoutConstraints.weightx 	= 1.0; layoutConstraints.weighty = 1.0;
			layout.setConstraints(attackDestroyButton,layoutConstraints);
	    	add(attackDestroyButton);
	    	
	    	//set basic display
			setBackground(Color.lightGray);
			setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
	    }
	 }


	/*
	 *Main Method for testing
	 */
	public static void main(String args[])
	{
		new Steganography_View("Steganography");
	}
}