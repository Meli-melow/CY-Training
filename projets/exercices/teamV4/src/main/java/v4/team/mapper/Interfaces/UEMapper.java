package v4.team.mapper.Interfaces;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import v4.team.dtos.UEDto;
import v4.team.model.UE;

/**
 * Le mapper dédié à l'entité UE.
 * Son implémentation est utilisée par les services.
 * Elle sera générée lors du lancement de l'app dans les sous-dossiers
 * classes et generated-classes de target.
 * Ne pas se soucier de leur absence dans les services avant la
 * première exécution (détails dans le README.md).
 */

@Mapper(componentModel = "spring", uses = MatiereMapper.class)
public interface UEMapper {


    @Mapping(source = "nom", target = "nom")
    @Mapping(source = "matieresUE", target = "matieresUE")
    UEDto toDto(UE uE);


    @Mapping(source = "nom", target = "nom")
    @Mapping(source = "matieresUE", target = "matieresUE")
    UE toClasse(UEDto uEDto);
}
