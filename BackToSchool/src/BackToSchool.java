import javax.swing.JFrame;


public class BackToSchool 
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Back To School");
		frame.setContentPane(new CampusPanel());
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}