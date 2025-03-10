package atilla.demo.Mappers;


import atilla.demo.classes.Matiere;
import atilla.demo.dto.MatiereDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UeMapper.class})
public interface MatiereMapper {

    Matiere toClasse (MatiereDto matiereDto);

    @Mapping(source = "nom", target = "nom")
    MatiereDto toDto (Matiere matiere);
}
