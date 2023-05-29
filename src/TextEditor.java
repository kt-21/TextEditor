/*
 * Katherine Tsai
 * A basic text editor that will allow the user to type into a window and edit the text with a few menu options.
 */

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.*;

import java.io.*;
import java.util.*;

public class TextEditor extends JFrame implements ActionListener{

	private JTextPane textArea;
	private JFileChooser fileChooser;

	public TextEditor() {

		//set file chooser look
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}catch(Exception e) {
			System.out.println("That look and feel is not found");
			System.exit(-1);
		}
		
		setTitle("Text Editor");
		setSize(615,435);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//set background
		PicPanel mainPanel = new PicPanel("pic.jpg");
		mainPanel.setLayout(null);
		
		//create menu bar and options
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		JMenu menuFont = new JMenu("Font");
		JMenuItem open = new JMenuItem("Open");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem exit = new JMenuItem("Exit");
		JMenuItem color = new JMenuItem("Color");
		JMenu size = new JMenu("Size");
		JMenu style = new JMenu("Style");
		JMenuItem size14 = new JMenuItem("14");
		JMenuItem size16 = new JMenuItem("16");
		JMenuItem fontTimes = new JMenuItem("Times New Roman");
		JMenuItem fontComic = new JMenuItem("Comic Sans");
		
		//add all menu bar options
		menuFile.add(open);
		menuFile.add(save);
		menuFile.add(exit);
		menuFont.add(color);
		menuFont.add(size);
		menuFont.add(style);
		size.add(size14);
		size.add(size16);
		style.add(fontTimes);
		style.add(fontComic);
		menuBar.add(menuFile);
		menuBar.add(menuFont);
		setJMenuBar(menuBar);
		
		//allow all menu items to trigger events
		open.addActionListener(this);
		save.addActionListener(this);
		exit.addActionListener(this);
		color.addActionListener(this);
		size.addActionListener(this);
		style.addActionListener(this);
		size14.addActionListener(this);
		size16.addActionListener(this);
		fontTimes.addActionListener(this);
		fontComic.addActionListener(this);

		//create text pane
		textArea = new JTextPane();
		JScrollPane scrollBar = new JScrollPane(textArea);
		scrollBar.setBounds(100, 50, 400, 270);
		mainPanel.add(scrollBar);
		add(mainPanel);
		
		fileChooser = new JFileChooser();
		
		setVisible(true);
	}

	//updates text pane based on events triggered
	public void actionPerformed(ActionEvent ae) {

		//open file selected
		if(ae.getActionCommand().equals("Open")) {
			
			int result = fileChooser.showOpenDialog(null);
			
			//extract file
			File file = null;
			Scanner fileIn = null;
			if(result == JFileChooser.APPROVE_OPTION){
				file = fileChooser.getSelectedFile();
				
				//read data from file
				try{
					fileIn = new Scanner(file);
				}catch(FileNotFoundException e){
					
					System.out.println("File Not Found!");
					System.exit(-1);
				}
				
				//add all lines in file to text pane
				String text = "";
				while(fileIn.hasNextLine())
					text += fileIn.nextLine() + "\n";
				
				textArea.setText(text);
			}
		}
		
		//saves text in text pane to file destination selected
		else if(ae.getActionCommand().equals("Save")) {
			
			int result = fileChooser.showSaveDialog(null);
			
			File file = null;
			if(result == JFileChooser.APPROVE_OPTION){
				file = fileChooser.getSelectedFile();

				//write text in text pane to file
				try{
					FileWriter outFile = new FileWriter(file);
					outFile.write(textArea.getText());
					outFile.close();
				}catch(IOException e){
					System.out.println("IO issue.");
					System.exit(-1);
				}
			}
		}
		
		else if(ae.getActionCommand().equals("Exit"))
			System.exit(0);
		
		else if(ae.getActionCommand().equals("Color"))
			changeColor();
		
		else if(ae.getActionCommand().equals("14"))
			changeSize(14);
		
		else if(ae.getActionCommand().equals("16"))
			changeSize(16);
		
		//change text pane font to font selected
		else
			changeStyle(ae.getActionCommand());
	}

	private void changeSize(int size) {
		Font currentFont = textArea.getFont();
		Font newFont = new Font(currentFont.getFontName(),currentFont.getStyle(),size);
		textArea.setFont(newFont);
	}

	private void changeStyle(String style){
		Font currentFont = textArea.getFont();
		Font newFont = new Font(style,currentFont.getStyle(),currentFont.getSize());
		textArea.setFont(newFont);
	}

	private void changeColor(){
		Color newColor = JColorChooser.showDialog(null, "Select a color", textArea.getForeground());
		if(newColor !=null)
			textArea.setForeground(newColor);
	}

	public class PicPanel extends JPanel{

		private BufferedImage image;
		
		public PicPanel(String fname){

			//reads the image
			try {
				image = ImageIO.read(new File(fname));
				
			} catch (IOException ioe) {
				System.out.println("Could not read in the pic");
				System.exit(0);
			}
		}
		
		//this will draw the image
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(image,0,0,this);
		}
	}

	public static void main(String[] args){
		new TextEditor();
	}
}
