package atilla.demo.services.Interfaces;

import atilla.demo.dto.AdminDto;

import java.util.stream.Stream;

public interface AdminService {

    Stream<AdminDto> afficherAdmins();
    AdminDto inscrire(AdminDto adminDto);

}
