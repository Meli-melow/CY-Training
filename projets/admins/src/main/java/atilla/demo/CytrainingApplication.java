package atilla.demo;

import atilla.demo.Mappers.FiliereMapper;
import atilla.demo.Repositories.FiliereRepository;
import atilla.demo.Repositories.MatiereRepository;
import atilla.demo.Repositories.UtilisateurRepository;
import atilla.demo.classes.*;
import atilla.demo.dto.FiliereDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

import java.util.List;

@SpringBootApplication()
public class CytrainingApplication implements CommandLineRunner {
	@Autowired
	FiliereRepository filiereRepository ;
	@Autowired
	UtilisateurRepository utilisateurRepository;
	//FiliereMapper filiereMapper;
	@Autowired
	MatiereRepository matiereRepository;

	public static void main(String[] args) {SpringApplication.run(CytrainingApplication.class, args);}

	@Override
	public void run(String... args) throws Exception {

		//Filiere filiere1 = new Filiere(18, "qnjk");
		//System.out.println(filiere1);
		//filiere1.toString();
		//filiereRepository.save(filiere1);
		//Filiere filiere2 = new Filiere(21, "qnjk");*/
		//FiliereDto filiereDto1 = filiereMapper.toDto(filiere1);


		//filiereRepository.save(filiere2);
		//Utilisateur utilisateur = new Utilisateur(1, "kenza1", "mouharrar1", "jzhdiuz@zquhdoiz","dsjnfle",1,2,null);
		//utilisateurRepository.save(utilisateur);
		//Admin admin = new Admin(4,"kenza2","mouharrar2", "sduqeihdz@qsuhgPZ.com", "qheil", 4,5,null, 4);
		//utilisateurRepository.save(admin);
		//List<Utilisateur> utilisateurs = this.utilisateurRepository.findAll();
		//System.out.println(utilisateurs);
		//RootAdmin thomas = new RootAdmin(4,"haution", "thomas", "thomasHautioncom@gmailcom", "dgdsgst", 4, 5, null,4,4);
		//utilisateurRepository.save(thomas);
		//Matiere matiere = new Matiere(1, "probabilité");
		//matiereRepository.save(matiere);



	}
}
