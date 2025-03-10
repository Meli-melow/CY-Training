package v3bis.team.mapper.Interfaces;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import v3bis.team.dtos.UtilisateurDto;
import v3bis.team.model.Utilisateur;

@Mapper(componentModel = "spring")
public interface UtilisateurMapper {

    @Mapping(source="prenom", target = "prenom")
    @Mapping(source="nom", target = "nom")
    @Mapping(source = "email",target = "email")
    @Mapping(source = "nbQuestionsValidees", target = "nbQuestionsValidees")
    UtilisateurDto toDto(Utilisateur utilisateur);

    @Mapping(source="prenom", target = "prenom")
    @Mapping(source="nom", target = "nom")
    @Mapping(source = "email",target = "email")
    @Mapping(source = "nbQuestionsValidees", target = "nbQuestionsValidees")
    Utilisateur toClasse (UtilisateurDto utilisateurDto);
}
