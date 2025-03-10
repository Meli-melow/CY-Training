package v4.team.mapper.Interfaces;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import v4.team.dtos.MatiereDto;
import v4.team.model.Matiere;

/**
 * Le mapper dédié à l'entité Matiere.
 * Son implémentation est utilisée par les services.
 * Elle sera générée lors du lancement de l'app dans les sous-dossiers
 * classes et generated-classes de target.
 * Ne pas se soucier de leur absence dans les services avant la
 * première exécution (détails dans le README.md).
 */

@Mapper(componentModel = "spring", uses = QuestionMapper.class)
public interface MatiereMapper {

    @Mapping(source = "nom", target = "nom")
    @Mapping(source = "questionsMatiere", target = "questionsMatiere")
    MatiereDto toDto(Matiere matiere);

    @Mapping(source = "nom", target = "nom")
    @Mapping(source = "questionsMatiere", target = "questionsMatiere")
    Matiere toClasse(MatiereDto matiereDto);
}
