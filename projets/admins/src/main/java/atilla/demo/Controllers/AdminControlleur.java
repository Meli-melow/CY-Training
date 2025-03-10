package atilla.demo.Controllers;


import atilla.demo.dto.AdminDto;
import atilla.demo.services.Classes.AdminServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "Admin")
public class AdminControlleur {

    private AdminServiceImpl adminService ;

    public AdminControlleur(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }


    @PostMapping(path = "inscrire/admin",consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<AdminDto> inscrireAdmin(@RequestBody AdminDto adminDto){

        AdminDto savedAdmin =this.adminService.inscrire(adminDto);
        return new ResponseEntity<>(savedAdmin, HttpStatus.CREATED);
    }
    @GetMapping(path="admins", produces =APPLICATION_JSON_VALUE)
    public Stream<AdminDto> afficherAdmins (){
        return this.adminService.afficherAdmins();
    }


}
