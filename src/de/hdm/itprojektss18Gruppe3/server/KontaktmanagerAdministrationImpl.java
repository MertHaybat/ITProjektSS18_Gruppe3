package de.hdm.itprojektss18Gruppe3.server;

import java.util.Date;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.hdm.itprojektss18Gruppe3.shared.KontaktmanagerAdministration;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaft;
import de.hdm.itprojektss18Gruppe3.shared.bo.Eigenschaftsauspraegung;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontakt;
import de.hdm.itprojektss18Gruppe3.shared.bo.Kontaktliste;
import de.hdm.itprojektss18Gruppe3.shared.bo.Teilhaberschaft;


@SuppressWarnings("serial")
public class KontaktmanagerAdministrationImpl extends RemoteServiceServlet implements KontaktmanagerAdministration {

	private TeilhaberschaftMapper teilhaberschaftMapper = null;
	
	private KontaktMapper kontaktMapper = null;
	
	private KontaktlisteMapper kontaktlisteMapper = null;
	
	private EigenschaftMapper eigenschaftMapper = null;
	
	private EigenschaftsauspraegungMapper eigenschaftauspraegungMapper = null;
	
	
	
	public void init(){
	}
	
	public Teilhaberschaft createTeilhaberschaft(int kontaktlisteID, int kontaktID, int nutzerID, int eigenschaftsauspraegungID){
	}
	
	public Teilhaberschaft findByTeilhaberschaftID(int teilhaberschaftID){
		
	}
	
	public Vector <Teilhaberschaft> findAllTeilhaberschaft(){
		
	}
	
	public Teilhaberschaft findTeilhaberschaftByBesitzerID(int besitzerID){
		
	}
	
	public Teilhaberschaft updateTeilhaberschaft(Teilhaberschaft teilhaberschaft){
		
	}
	
	public void deleteTeilhaberschaft(Teilhaberschaft teilhaberschaft){
		
	}
	
	public Kontakt createNutzer(Date erzeugungsDatum, int NutzerID, int eigenschaftID, Date modifikationsdatum){
		
	}
	
	public Vector<Kontakt> findAllNutzer(){
		
	}
	
	public Kontakt updateNutzer(Kontakt kontakt){
		
	}
	
	public void deleteNutzer(Kontakt kontakt){
		
	}
	
	public Kontakt createKontakt(int nutzerID, int eigenschaftID, Date erzeugungsDatum, Date modifikationsdatum, String kontaktname, String status){
		
	}
	
	public Kontakt findByKontaktID(int kontaktID){
		
	}
	
	public Vector<Kontakt> findAllKontakt(){
		
	}
	
	public Vector<Kontakt> findKontaktByNutzerID(int nutzerID){
		
	}
	
	public Vector<Kontakt> findKontaktByEigenschaftID(int eigenschaftID){
		
	}
	
	public Kontakt findKontaktByEmail(String email){
		
	}
	
	public Vector<Kontakt> getAllKontakteByKontaktlisteID(int kontaktlisteID){
		
	}
	
	public Vector<Kontakt> getAllKontakteByTeilhaberschaft(int kontaktlisteID){
		
	}
	
	public Kontakt updateKontakt(Kontakt kontakt){
		
	}
	
	public void deleteKontakt(Kontakt kontakt){
		
	}
	
	public Kontaktliste createKontaktliste(String gruppenName, int kontaktID, int nutzerID, String status){
		
	}
	
	public Vector<Kontaktliste> findAllKontaktliste(){
		
	}
	
	public Vector<Kontaktliste> findKontaktlisteByNutzerID(int nutzerID){
		
	}
	
	public Vector<Kontaktliste> findKontaktlisteByKontaktID(int kontaktID){
		
	}
	
	public Vector<Kontaktliste> getAllKontaktlisteByTeilhaberschaft(int kontaktlisteID){
		
	}
	
	public Kontaktliste updateKontaktliste(Kontaktliste kontaktliste){
		
	}
	
	public void deleteKontaktliste(Kontaktliste kontaktliste){
		
	}
	
	public Eigenschaft createEigenschaft(String bezeichnung){
		
	}
	
	public Vector<Eigenschaft> findEigenschaftByNutzerID(int nutzerID){
		
	}
	
	public Vector<Eigenschaft> findEigenschaftByKontaktID(int kontaktID){
		
	}
	
	public Vector<Eigenschaft> findEigenschaftByEigenschaftsauspraegungID(int eigenschaftsauspraegungID){
		
	}
	
	public Eigenschaft updateEigenschaft(Eigenschaft eigenschaft){
		
	}
	
	public void deleteEigenschaft(Eigenschaft eigenschaft){
		
	}
	
	public Eigenschaftsauspraegung createEigenschaftsauspraegung(String wert, int eigenschaftsID, String status){
		
	}
	
	public Vector<Eigenschaftsauspraegung> findEigenschaftsauspraegungByEigenschaftID(int eigenschaftID){
		
	}
	
	public Vector<Eigenschaftsauspraegung> findAllEigenschaftsauspraegung(){
		
	}
	
	public Vector<Eigenschaftsauspraegung> getAllEigenschaftsauspraegungByTeilhaberschaft(int eigenschaftsauspraegung){
		
	}
	
	public Eigenschaftsauspraegung updateEigenschaftsauspraegung(Eigenschaftsauspraegung eigenschaftsauspraegung){
		
	}
	
	public void deleteEigenschaftsauspraegung(Eigenschaftsauspraegung eigenschaftsauspraegung){
		
	}
	
}
