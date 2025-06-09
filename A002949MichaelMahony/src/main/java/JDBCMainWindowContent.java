import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.List;
import java.util.Vector;

import backend.Bounty;
import backend.BountyDao;
import backend.BountysClient;

import java.sql.*;
@SuppressWarnings("serial")
public class JDBCMainWindowContent extends JInternalFrame implements ActionListener
{	
	 private DefaultTableModel tableModel;
	    private JTable table;

	    String cmd = null;
	// DB Connectivity Attributes
	private Connection con = null;

	private Statement stmt = null;
	private ResultSet rs = null;
	private Container content;

	private JPanel detailsPanel;
	private JPanel detailsPanel1;
	JPanel buttonPanel = new JPanel(new GridLayout(5, 1));
	//private JPanel exportConceptDataPanel;
	private JScrollPane dbContentsPanel;

	private Border lineBorder;

	private JLabel IDLabel=new JLabel("ID:                 ");
	private JLabel NameLabel=new JLabel("Name:               ");
	private JLabel CrewLabel=new JLabel("Crew:      ");
	private JLabel BountyLabel=new JLabel("Bounty:      ");
	private JLabel PositionLabel=new JLabel("Position:        ");
	private JLabel AgeLabel=new JLabel("Age:                 ");
	private JLabel GenderLabel=new JLabel("Gender:               ");


	private JTextField IDTF= new JTextField(10);
	private JTextField NameTF=new JTextField(10);
	private JTextField CrewTF=new JTextField(10);
	private JTextField BountyTF=new JTextField(10);
	private JTextField PositionTF=new JTextField(10);
	private JTextField AgeTF=new JTextField(10);
	private JTextField GenderTF=new JTextField(10);



	

	//Buttons for inserting, and updating members
	//also a clear button to clear details panel
	private JButton getButton = new JButton("Get");
	private JButton deleteButton = new JButton("Delete");
	private JButton updateButton  = new JButton("Put");
	private JButton insertButton  = new JButton("Post");
	private JButton clearButton  = new JButton("Clear");
	private JButton showAllButton  = new JButton("Show All");
	

	private JButton ListAllPirates  = new JButton("ListAllBountys");
	private JPanel exportButtonPanel;



	public JDBCMainWindowContent( String aTitle)
	{	
		
		//setting up the GUI
		super(aTitle, false,false,false,false);
		setEnabled(true);



		content=getContentPane();
		content.setLayout(null);
		content.setBackground(new java.awt.Color(101, 168, 255));
		//lineBorder = BorderFactory.createEtchedBorder(15, Color.black, Color.blue);

		//setup details panel and add the components to it
		detailsPanel=new JPanel();
		detailsPanel.setLayout(new GridLayout(11,2));
		detailsPanel.setBackground(new java.awt.Color(205, 251, 255));
		detailsPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "CRUD Actions"));
		
		detailsPanel.add(IDLabel);			
		detailsPanel.add(IDTF);
		detailsPanel.add(NameLabel);		
		detailsPanel.add(NameTF);
		detailsPanel.add(CrewLabel);		
		detailsPanel.add(CrewTF);
		detailsPanel.add(BountyLabel);	
		detailsPanel.add(BountyTF);
		detailsPanel.add(PositionLabel);		
		detailsPanel.add(PositionTF);
		detailsPanel.add(AgeLabel);
		detailsPanel.add(AgeTF);
		detailsPanel.add(GenderLabel);
		detailsPanel.add(GenderTF);
	

		//setup details panel and add the components to it

		insertButton.setSize(100, 30);
		updateButton.setSize(100, 30);
		getButton.setSize (100, 30);
		deleteButton.setSize (100, 30);
		clearButton.setSize (100, 30);
		showAllButton.setSize (100, 30);
		updateButton.setBackground(Color.gray);
		insertButton.setBackground(Color.green);
		updateButton.setBackground(Color.orange);
		deleteButton.setBackground(Color.red);
		clearButton.setBackground(Color.white);
		showAllButton.setBackground(Color.pink);
		insertButton.setLocation(370, 10);
		updateButton.setLocation(370, 110);
		getButton.setLocation (370, 160);
		deleteButton.setLocation (370, 60);
		clearButton.setLocation (370, 210);
		showAllButton.setLocation (370, 260);


		insertButton.addActionListener(this);
		updateButton.addActionListener(this);
		getButton.addActionListener(this);
		deleteButton.addActionListener(this);
		clearButton.addActionListener(this);
		showAllButton.addActionListener(this);
		exportButtonPanel=new JPanel();
		exportButtonPanel.setLayout(new GridLayout(3,2));
		exportButtonPanel.setBackground(Color.lightGray);
		exportButtonPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Export Data"));

		ListAllPirates.setBackground(Color.green);
		exportButtonPanel.add(ListAllPirates);

		exportButtonPanel.add(ListAllPirates);
	
		exportButtonPanel.setSize(500, 200);
		exportButtonPanel.setLocation(3, 300);

		this.ListAllPirates.addActionListener(this);




		content.add(insertButton);
		content.add(updateButton);
		content.add(getButton);
		content.add(deleteButton);
		content.add(clearButton);
		content.add(showAllButton);
	


		String[] columns = {"ID", "Name", "Crew", "Bounty", "Position", "Age", "Gender"};
		tableModel = new DefaultTableModel(columns, 0);
		table = new JTable(tableModel);

		// Add the table to a JScrollPane
		JScrollPane dbContentsPanel = new JScrollPane(table);
		  dbContentsPanel.setBounds(10, 220, 800, 300);
			detailsPanel.setSize(360, 300);
			detailsPanel.setLocation(3,0);
		   content.add(detailsPanel);
		   content.add(exportButtonPanel);
	        content.add(buttonPanel, BorderLayout.EAST);
	        dbContentsPanel.setSize(700, 300);
			dbContentsPanel.setLocation(500,0);
	        content.add(dbContentsPanel);

	        setSize(982, 645);
	        setVisible(true);
	        refreshDB();
	}

	public void refreshDB()
	{
		try {
			 tableModel.setRowCount(0);
			   List<Bounty> bountyList = BountysClient.getBounties();
			   
		        
		        for (Bounty bounty : bountyList) {
		            tableModel.addRow(new Object[]{
		                bounty.getId(), bounty.getName(), bounty.getCrew(),
		                bounty.getBounty(), bounty.getPosition(),
		                bounty.getAge(), bounty.getGender()
		            });
		        }
		       
		  } catch (Exception e) {
		        e.printStackTrace();

	}
	}
	public void InitiateOneBounty(Bounty bounty) {
	    if (bounty != null) {
	        tableModel.setRowCount(0);  
	        tableModel.addRow(new Object[]{
	            bounty.getId(), bounty.getName(), bounty.getCrew(),
	            bounty.getBounty(), bounty.getPosition(),
	            bounty.getAge(), bounty.getGender()
	        });
	    } 
	}
	public void DeleteTables() {
		BountyDao.instance.deleteAllBounty();
		refreshDB();
	}
	public void FillTables() {
		Bounty insertedBounty = new Bounty();
		insertedBounty.setId(1);
		insertedBounty.setName("Monkey D. Luffy");
		insertedBounty.setCrew("Straw Hat Pirates");
		insertedBounty.setBounty(300000000);
		insertedBounty.setPosition("Captain");
		insertedBounty.setAge(19);
		insertedBounty.setGender("M");
		insertedBounty = BountyDao.instance.addBounty(insertedBounty);
		Bounty insertedBounty1 = new Bounty();
		insertedBounty1.setId(2);
		insertedBounty1.setName("Roronoa Zoro");
		insertedBounty1.setCrew("Straw Hat Pirates");
		insertedBounty1.setBounty(100000000);
		insertedBounty1.setPosition("Swordsman");
		insertedBounty1.setAge(21);
		insertedBounty1.setGender("M");
		insertedBounty1 = BountyDao.instance.addBounty(insertedBounty1);
		Bounty insertedBounty2 = new Bounty();
		insertedBounty2.setId(3);
		insertedBounty2.setName("Usopp");
		insertedBounty2.setCrew("Straw Hat Pirates");
		insertedBounty2.setBounty(500000);
		insertedBounty2.setPosition("Sniper");
		insertedBounty2.setAge(19);
		insertedBounty2.setGender("M");
		insertedBounty2 = BountyDao.instance.addBounty(insertedBounty2);
		refreshDB();
	}
	public void initiate_db_conn()
	{
		try
		{
			// Load the JConnector Driver
			Class.forName("org.hsqldb.jdbcDriver");
			String url = "jdbc:hsqldb:hsql://localhost/oneDB";
			
			// Connect to DB using DB URL, Username and password
			con = DriverManager.getConnection(url, "SA", "Passw0rd");
			
			//Create a generic statement which is passed to the TestInternalFrame1
			stmt = con.createStatement();
		}
		catch(Exception e)
		{
			System.out.println("Error: Failed to connect to database\n"+e.getMessage());
		}
	}
	//event handling 
	@SuppressWarnings("null")
	public void actionPerformed(ActionEvent e)
	{
		Object target=e.getSource();
		if (target == clearButton)
		{
			IDTF.setText("");
			NameTF.setText("");
			CrewTF.setText("");
			BountyTF.setText("");
			PositionTF.setText("");
			AgeTF.setText("");
			GenderTF.setText("");


			

		}

		if (target == insertButton)
		{		 
			try
			{
				String idtext = IDTF.getText();
				String nametext = NameTF.getText();
				
				String crewtext = CrewTF.getText();
				String BountyText = BountyTF.getText();
				
				String PositionText = PositionTF.getText();
				String AgeText = AgeTF.getText();
			
				String gendertext = GenderTF.getText();
				if(IDTF.getText().isEmpty() ||	NameTF.getText().isEmpty() || CrewTF.getText().isEmpty() ||BountyTF.getText().isEmpty()
						|| PositionTF.getText().isEmpty() || AgeTF.getText().isEmpty() || GenderTF.getText().isEmpty()){
					
				
					JOptionPane.showMessageDialog(null, "Ensure all boxes have information in them");
					return;
				}
				int id = Integer.parseInt(idtext);
				int bountyInt = Integer.parseInt(BountyText);
				int ageint = Integer.parseInt(AgeText);
				Bounty insertedBounty = new Bounty();
				insertedBounty.setId(id);
				insertedBounty.setName(nametext);
				insertedBounty.setCrew(crewtext);
				insertedBounty.setBounty(bountyInt);
				insertedBounty.setPosition(PositionText);
				insertedBounty.setAge(ageint);
				insertedBounty.setGender(gendertext);
				insertedBounty = BountyDao.instance.addBounty(insertedBounty);
		   
				
			}
			finally
			{
				refreshDB();
		}
		}
		
		if (target == deleteButton)
		{		 
			try
			{
				String idtext = IDTF.getText();
				if(IDTF.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "An ID is required to delete a member");
					return;
				}
				int id = Integer.parseInt(idtext);
				Bounty deletedBounty = new Bounty();
				deletedBounty= BountyDao.instance.deleteBounty(id);

			}
			finally
			{
				refreshDB();
			}
		}

		if (target == showAllButton)
		{		 
		
			
				refreshDB();
			
		}
		//put
		if (target == updateButton)
		{		 
			try			{
				
				String idtext = IDTF.getText();
				if(IDTF.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "An ID is required to update a member");
					return;
				}
				String nametext = NameTF.getText();
				int id = Integer.parseInt(idtext);
				String crewtext = CrewTF.getText();
				String BountyText = BountyTF.getText();
				//int bountyInt = Integer.parseInt(BountyText);
				String PositionText = PositionTF.getText();
				String AgeText = AgeTF.getText();
				//int ageint = Integer.parseInt(AgeText);
				String gendertext = GenderTF.getText();
				
			
				Bounty updatedBounty = BountyDao.instance.getBounty(id);
				updatedBounty.setId(id);
				if(!NameTF.getText().isEmpty()) {
					updatedBounty.setName(nametext);
				}
				if(!CrewTF.getText().isEmpty()) {
					updatedBounty.setCrew(crewtext);
				}
				if(!BountyTF.getText().isEmpty()) {
					updatedBounty.setBounty(Integer.parseInt(BountyText));
				}
				if(!PositionTF.getText().isEmpty()) {
					updatedBounty.setPosition(PositionText);
				}
				if(!AgeTF.getText().isEmpty()) {
					updatedBounty.setAge(Integer.parseInt(AgeText));
				}
				if(!GenderTF.getText().isEmpty()) {
					updatedBounty.setGender(gendertext);
				}
				
			
				updatedBounty = BountyDao.instance.updateBounty(updatedBounty);
			}
			
		finally
			{
			refreshDB();
			}
		}
		
		//get
		
		if(target == this.getButton){

			
			
			try{					
				String idtext = IDTF.getText();
				if(IDTF.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "An ID is required to retrieve a member");
					return;
				}
				int id = Integer.parseInt(idtext);
				Bounty gBounty = new Bounty();
			
				gBounty = BountyDao.instance.getBounty(id);
			
					InitiateOneBounty(gBounty);
			}
			finally
			{
			
			}

		}


		
		if(target == this.ListAllPirates){
			initiate_db_conn();
			cmd = "select * from bounty;";

			try{
			
				
				rs= stmt.executeQuery(cmd); 	
				writeToFile(rs);
			}
			catch(Exception e1){e1.printStackTrace();}

		}

		
	
		
	

		

	}
	private void writeToFile(ResultSet rs){
		try{
			System.out.println("In writeToFile");
			FileWriter outputFile = new FileWriter("MyOutput.csv");
			PrintWriter printWriter = new PrintWriter(outputFile);
			ResultSetMetaData rsmd = rs.getMetaData();
			int numColumns = rsmd.getColumnCount();

			for(int i=0;i<numColumns;i++){
				printWriter.print(rsmd.getColumnLabel(i+1)+",");
			}
			printWriter.print("\n");
			while(rs.next()){
				for(int i=0;i<numColumns;i++){
					printWriter.print(rs.getString(i+1)+",");
				}
				printWriter.print("\n");
				printWriter.flush();
			}
			printWriter.close();
		}
		catch(Exception e){e.printStackTrace();}
	}
	


}