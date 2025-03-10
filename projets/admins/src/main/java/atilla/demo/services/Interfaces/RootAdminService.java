package atilla.demo.services.Interfaces;


import atilla.demo.dto.RootAdminDto;
import atilla.demo.services.Classes.UtilisateurServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface RootAdminService {

     RootAdminDto findRootAdmin(int id );

     RootAdminDto inscrireAdmin( RootAdminDto rootAdminDto);
     public void certifierUtilisateur(int utilisateurId, int matiereId);
     public void certifierAdmin( int adminId , int matiereId, int rootAdminId);
     public void removeCertification( int adminId , int matiereId, int rootAdminId);





}
