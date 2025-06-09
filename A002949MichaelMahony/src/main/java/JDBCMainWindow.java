import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class JDBCMainWindow extends JFrame implements ActionListener
	{
		private JMenuItem exitItem;
		private JMenuItem infoItem;
		private JMenuItem fillItem;
		private JMenuItem clearItem;
		private JMenuItem pirateItem;
		 JDBCMainWindowContent aWindowContent = new JDBCMainWindowContent( "Pirates");

		public JDBCMainWindow()
		{
			// Sets the Window Title
			super( "A00292349-Distributed-Systems-Assignment" ); 
			
			//Setup fileMenu and its elements
			JMenuBar menuBar=new JMenuBar();
			JMenu fileMenu=new JMenu("File");
			exitItem =new JMenuItem("Exit");
			infoItem =new JMenuItem("Project Info");
			fillItem =new JMenuItem("Fill Table");
			clearItem =new JMenuItem("Clear Table");
			pirateItem = new JMenuItem("Pirates");
		
			fileMenu.add(exitItem);
			fileMenu.add(infoItem);
			fileMenu.add(fillItem);
			fileMenu.add(clearItem);
			menuBar.add(fileMenu );
		
	
	
			setJMenuBar(menuBar);
			
			exitItem.addActionListener(this);
			infoItem.addActionListener(this);
			fillItem.addActionListener(this);
			clearItem.addActionListener(this);
			// Create an instance of our class JDBCMainWindowContent 
			
			// Add the instance to the main section of the window
			getContentPane().add( aWindowContent );
			
			setSize( 1200, 600 );
			setVisible( true );
			
		}
	
		
		

		
		
		
		
//		// The event handling for the main frame
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource().equals(exitItem)){
				this.dispose();
			}
			else if(e.getSource().equals(infoItem)){
				
			JOptionPane.showMessageDialog(null,
                        "This information contained within the database in this project is based on the pirate bounty system in the fictional universe of One Piece. It displays the pirates name, crew, bounty, position etc");
			}
			else if(e.getSource().equals(fillItem)){
				aWindowContent.FillTables();
			}
			else if(e.getSource().equals(clearItem)){
				aWindowContent.DeleteTables();
			}
//			else if(e.getSource().equals(dogItem)){
//				this.dispose();
//				new JDBCMainWindowDog();
//			}
//				
//			}
		}
		
		}
		
		
	