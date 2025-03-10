package v3.team.mapper.Interfaces;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import v3.team.dtos.UtilisateurDto;
import v3.team.model.Utilisateur;

@Mapper(componentModel = "spring")
public interface UtilisateurMapper {

    @Mapping(source="prenom", target = "prenom")
    @Mapping(source="nom", target = "nom")
    @Mapping(source = "email",target = "email")
    UtilisateurDto toDto(Utilisateur utilisateur);

    @Mapping(source="prenom", target = "prenom")
    @Mapping(source="nom", target = "nom")
    @Mapping(source = "email",target = "email")
    Utilisateur toClasse (UtilisateurDto utilisateurDto);
}
