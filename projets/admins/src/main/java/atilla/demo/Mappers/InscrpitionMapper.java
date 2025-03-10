package atilla.demo.Mappers;

import atilla.demo.classes.Utilisateur;
import atilla.demo.dto.InscriptionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {FiliereMapper.class})
public interface InscrpitionMapper {
    @Mapping(source = "mail", target = "mail")
    InscriptionDto toDto (Utilisateur utilisateur);
    @Mapping(source = "mail", target = "mail")
    Utilisateur toClasse(InscriptionDto inscriptionDto);
}
