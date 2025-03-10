package v4.team.mapper.Interfaces;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import v4.team.dtos.UtilisateurDto;
import v4.team.model.Utilisateur;

/**
 * Le mapper dédié à l'entité Utilisateur.
 * Son implémentation est utilisée par les services.
 * Elle sera générée lors du lancement de l'app dans les sous-dossiers
 * classes et generated-classes de target.
 * Ne pas se soucier de leur absence dans les services avant la
 * première exécution (détails dans le README.md).
 */

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
