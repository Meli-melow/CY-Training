package v4.team.mapper.Interfaces;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import v4.team.dtos.QuestionDto;
import v4.team.model.Question;

/**
 * Le mapper dédié à l'entité Question.
 * Son implémentation est utilisée par les services.
 * Elle sera générée lors du lancement de l'app dans les sous-dossiers
 * classes et generated-classes de target.
 * Ne pas se soucier de leur absence dans les services avant la
 * première exécution (détails dans le README.md).
 */

@Mapper(componentModel = "spring", uses = UtilisateurMapper.class)
public interface QuestionMapper {

    @Mapping(source="matiereQuestion", target = "matiereQuestion")
    @Mapping(source="question", target = "question")
    @Mapping(source="correction", target = "correction")
    @Mapping(source = "indBonneRep",target = "indBonneRep")
    @Mapping(source = "indice",target = "indice")
    @Mapping(source = "etatValidation", target = "etatValidation")
    @Mapping(source = "dateValidee",target = "dateValidee")
    @Mapping(source = "tempsAttente", target = "tempsAttente")
    @Mapping(source = "dateDemandeAjout", target = "dateDemandeAjout")
    QuestionDto toDto(Question question);


    @Mapping(source="matiereQuestion", target = "matiereQuestion")
    @Mapping(source="question", target = "question")
    @Mapping(source="correction", target = "correction")
    @Mapping(source = "indBonneRep",target = "indBonneRep")
    @Mapping(source = "indice",target = "indice")
    @Mapping(source = "etatValidation", target = "etatValidation")
    @Mapping(source = "dateValidee",target = "dateValidee")
    @Mapping(source = "tempsAttente", target = "tempsAttente")
    @Mapping(source = "dateDemandeAjout", target = "dateDemandeAjout")
    Question toClasse(QuestionDto questionDto);
}
