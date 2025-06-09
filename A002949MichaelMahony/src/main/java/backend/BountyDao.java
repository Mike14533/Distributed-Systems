package backend;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum BountyDao {
	instance;

	private Connection connection;

	private Map<Integer, Bounty> bountysMap = new HashMap<Integer, Bounty>();
	 private ParseBountys parser; 
	private BountyDao() {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/oneDB", "SA", "Passw0rd");
		

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Bounty> getBountys() {
		List<Bounty> bountys = new ArrayList<Bounty>();

		try (Statement stmt = connection.createStatement(); 
				ResultSet rs = stmt.executeQuery("SELECT * FROM bounty")) {
			while (rs.next()) {
				Bounty b = new Bounty();
				b.setId(rs.getInt("id"));
				b.setCrew(rs.getString("crew"));
				b.setName(rs.getString("name"));
				b.setBounty(rs.getInt("bounty"));
				b.setPosition(rs.getString("position"));
				b.setAge(rs.getInt("age"));
				b.setGender(rs.getString("gender"));
				bountys.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		bountys.addAll(bountysMap.values());
		return bountys;
	}

	public Bounty getBounty(int id) {
		try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM bounty WHERE id = ?")) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Bounty b = new Bounty();
				b.setId(rs.getInt("id"));
				b.setCrew(rs.getString("crew"));
				b.setName(rs.getString("name"));
				b.setBounty(rs.getInt("bounty"));
				b.setPosition(rs.getString("position"));
				b.setAge(rs.getInt("age"));
				b.setGender(rs.getString("gender"));
				
				return b;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return bountysMap.get(id);
	}

	public Bounty addBounty(Bounty bounty) {

		
		try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO bounty (id, name, crew, bounty, position, age, gender) VALUES (?, ?,?,?, ? ,? ,?)", Statement.RETURN_GENERATED_KEYS)) {
			stmt.setInt(1, bounty.getId());
			stmt.setString(2, bounty.getName());
            stmt.setString(3, bounty.getCrew());
            stmt.setInt(4, bounty.getBounty());
            stmt.setString(5, bounty.getPosition());
            stmt.setInt(6, bounty.getAge());
            stmt.setString(7, bounty.getGender());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
		return bounty;
	}

	public Bounty deleteBounty(int id) {
		try (Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("DELETE FROM bounty WHERE id ="+id)) {
			rs.next();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bountysMap.remove(id);
	}
	public Bounty deleteAllBounty() {
		try (Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("DELETE FROM bounty")) {
			rs.next();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	public Bounty updateBounty(Bounty newBounty) {

		
		
		try (PreparedStatement stmt = connection.prepareStatement("UPDATE bounty SET name = ?, crew= ?, bounty= ?, position= ?, age= ?, gender= ? WHERE ID = ?", Statement.RETURN_GENERATED_KEYS)) {
		
			stmt.setString(1, newBounty.getName());
            stmt.setString(2, newBounty.getCrew());
            stmt.setInt(3, newBounty.getBounty());
            stmt.setString(4, newBounty.getPosition());
            stmt.setInt(5, newBounty.getAge());
            stmt.setString(6, newBounty.getGender());
        	stmt.setInt(7, newBounty.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
		return newBounty;
	}
	


}
