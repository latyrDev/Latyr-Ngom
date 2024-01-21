/**
 * 
 */
package com.gestion.etudiant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;


/**
 * 
 */
@RequestScoped
@Named
public class EtudiantEJB {
	private Etudiant etudiant;
	private List<Etudiant> listeEtudiants;
	private Date date;
	private boolean modif=false;
	private static int etuId;
	
	public EtudiantEJB() {
	//on va instancier notre classe Etudiant
		
		etudiant = new Etudiant();

	}
	public Connection connect() {
		try {
			//on choisi notre driver
			Class.forName("com.mysql.jdbc.Driver");
			
			//connection à la base de donnée 
				Connection con = DriverManager.getConnection("jdbc:mysql://Localhost:3308/dbcruudjsf","root","");
				return con;
				
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
			Connection con = null;
			return con;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Connection con = null;
			return con;
		}
	}
	/**
	 * la méthode qui permet de récupérer la liste des étudiants 
	 */
	public List<Etudiant> afficheEtudiants() {
		listeEtudiants = new ArrayList<Etudiant>();
		
		String req = "select * from etudiant";
		
		try {
			//on va préparer notre requête 
			PreparedStatement ps = connect().prepareStatement(req);
			
			ResultSet res = ps.executeQuery();
			
			// on recupere le resultat et on le stock dans listeEtudiant
			while(res.next()) {
			//on crée une instance d'étudiant
			Etudiant e = new Etudiant();
			e.setId(res.getInt("id"));
			e.setNom(res.getString("nom"));
			e.setPrenom(res.getString("prenom"));
			e.setDatenaiss(res.getDate("datenaiss"));
			listeEtudiants.add(e);
			}
			return listeEtudiants;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return listeEtudiants;
		}

	}

	public void ajoutEtudiant() {
		//definir la requête 
		String req = "INSERT INTO etudiant (nom, prenom, datenaiss) VALUE (?, ?, ?)";
		etudiant.setDatenaiss(convDate(date));
		 
		try {
			//préparation de la requête 
			PreparedStatement ps = connect().prepareStatement(req);
			//les paramètres de la requête 
			ps.setString(1, etudiant.getNom());
			ps.setString(2, etudiant.getPrenom());
			ps.setDate(3, etudiant.getDatenaiss());
			
			//on execute la requête 
			ps.execute();
			
			afficheEtudiants();
			etudiant = new Etudiant();
			date = null;
		}catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();		
				
		}
	}
	
	public void deleteEtudiant(Etudiant e) {
			 String req ="delete from etudiant where id = ?";
		try {
        	PreparedStatement ps = connect().prepareStatement(req);
    	            ps.setInt(1, e.getId());
    	            ps.execute();
        } catch (SQLException e1) {
        	// TODO Auto-generated catch block
        	e1.printStackTrace();
        }
}
	public void affiche(Etudiant etu) {
		etuId = etu.getId();
		date = etu.getDatenaiss();
		etudiant = etu;
		modif = true;
	}
	
	public void modifEtudiant() {
		etudiant.setDatenaiss(convDate(date));
	    try{
	        String req = "UPDATE etudiant SET nom= ?, prenom= ?, datenaiss= ? WHERE id= ?";
	        PreparedStatement ps = connect().prepareStatement(req);
	        ps.setString(1, etudiant.getNom());
	        ps.setString(2, etudiant.getPrenom());
	        ps.setDate(3, etudiant.getDatenaiss());
	        ps.setInt(4, etuId);

	            System.out.println(ps);
	            
	            ps.executeUpdate();
	            
	            afficheEtudiants();
	            etudiant = new Etudiant();
	            date = null;
	    } catch (SQLException e1) {
        	// TODO Auto-generated catch block
	        e1.printStackTrace();
	    }
	}

	public java.sql.Date convDate(java.util.Date calendarDate){
		return new java.sql.Date(calendarDate.getTime());
	}



	/**
	 * @return the etuid
	 */
	public int getEtuid() {
		return etuId;
	}
	/**
	 * @param etuid the etuid to set
	 */
	public void setEtuId(int etuId) {
		EtudiantEJB.etuId = etuId;
	}
	/**
	 * @return the modif
	 */
	public boolean isModif() {
		return modif;
	}

	/**
	 * @param modif the modif to set
	 */
	public void setModif(boolean modif) {
		this.modif = modif;
	}

	/**
	 * @return the butEtudiant
	 */
	public List<Etudiant> getButEtudiant() {
		return afficheEtudiants();
	}
	/**
	 * @param butEtudiant the butEtudiant to set
	 */
	public void setButEtudiant(List<Etudiant> butEtudiant) {
	}
	/**
	 * @param etudiant the etudiant to set
	 */
	public void setEtudiant1(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	/**
	 * @return the listeEtudiant
	 */
	public List<Etudiant> getListeEtudiant() {
		return listeEtudiants;
	}

	/**
	 * @param listeEtudiant the listeEtudiant to set
	 */
	public void setListeEtudiant(List<Etudiant> listeEtudiant) {
		this.listeEtudiants = listeEtudiant;
	}




	/**
	 * @return the etudiant
	 */
	public Etudiant getEtudiant() {
		return etudiant;
	}

	/**
	 * @param etudiant the etudiant to set
	 */
	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	/**
	 * @return the listeEtudiants
	 */
	public List<Etudiant> getListeEtudiants() {
		return listeEtudiants;
	}

	/**
	 * @param listeEtudiants the listeEtudiants to set
	 */
	public void setListeEtudiants(List<Etudiant> listeEtudiants) {
		this.listeEtudiants = listeEtudiants;
	}
	
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	}

