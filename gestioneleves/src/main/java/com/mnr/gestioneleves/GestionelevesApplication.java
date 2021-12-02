package com.mnr.gestioneleves;

import com.mnr.gestioneleves.entities.Etudiant;
import com.mnr.gestioneleves.repos.EtudiantRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class GestionelevesApplication {

	public static void main(String[] args) throws ParseException {

		//acceder a tous les object creer par spring=> ApplicationContext=>import org.springframework.context.ApplicationContext;
		ApplicationContext context =SpringApplication.run(GestionelevesApplication.class, args);
		//retrieve bean by class
		EtudiantRepository etudiantRepository= context.getBean(EtudiantRepository.class);
		//formater date and add  throws ParseException
		DateFormat df= new SimpleDateFormat("yyyy-MM-dd");
		etudiantRepository.save(new Etudiant("ahmed",df.parse("1989-06-12"),"ahmed@gmail.com","ahmed.jpg"));
		etudiantRepository.save(new Etudiant("yahya",df.parse("2000-06-15"),"yahya@gmail.com","yahya.jpg"));
		etudiantRepository.save(new Etudiant("mounir",df.parse("2005-12-14"),"mounir@gmail.com","mounir.jpg"));
		etudiantRepository.save(new Etudiant("amine",df.parse("1989-06-12"),"ahmed@gmail.com","ahmed.jpg"));
		/*etudiantRepository.save(new Etudiant("kamal",df.parse("2000-06-15"),"yahya@gmail.com","yahya.jpg"));
		etudiantRepository.save(new Etudiant("jack",df.parse("2005-12-14"),"mounir@gmail.com","mounir.jpg"));
		etudiantRepository.save(new Etudiant("toil",df.parse("1989-06-12"),"ahmed@gmail.com","ahmed.jpg"));
		etudiantRepository.save(new Etudiant("maha",df.parse("2000-06-15"),"yahya@gmail.com","yahya.jpg"));
		etudiantRepository.save(new Etudiant("inass",df.parse("2005-12-14"),"mounir@gmail.com","mounir.jpg"));
		etudiantRepository.save(new Etudiant("mohamed",df.parse("1989-06-12"),"ahmed@gmail.com","ahmed.jpg"));
		etudiantRepository.save(new Etudiant("sabrine",df.parse("2000-06-15"),"yahya@gmail.com","yahya.jpg"));
		etudiantRepository.save(new Etudiant("kajole",df.parse("2005-12-14"),"mounir@gmail.com","mounir.jpg"));
		etudiantRepository.save(new Etudiant("charoukhane",df.parse("1989-06-12"),"ahmed@gmail.com","ahmed.jpg"));
		etudiantRepository.save(new Etudiant("spidermane",df.parse("2000-06-15"),"yahya@gmail.com","yahya.jpg"));
		etudiantRepository.save(new Etudiant("maykle",df.parse("2005-12-14"),"mounir@gmail.com","mounir.jpg"));
		etudiantRepository.save(new Etudiant("halime",df.parse("1989-06-12"),"ahmed@gmail.com","ahmed.jpg"));
		etudiantRepository.save(new Etudiant("jihane",df.parse("2000-06-15"),"yahya@gmail.com","yahya.jpg"));
		etudiantRepository.save(new Etudiant("bilale",df.parse("2005-12-14"),"mounir@gmail.com","mounir.jpg"));
*/

		//page d etudiant
		//je veux 5 enregistrement de la page 0, "%A%" =etudiant contenat a
		Page<Etudiant> etds=etudiantRepository.chercherEtudiants("%A%",PageRequest.of(0,5));

		//expression lamda= pour chaque etudiant e de list etds; j affiche nom etudiant
		etds.forEach(e->System.out.println(e.getNom()));





	}

}
