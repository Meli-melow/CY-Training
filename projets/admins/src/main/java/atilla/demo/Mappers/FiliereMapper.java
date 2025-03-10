package atilla.demo.Mappers;

import atilla.demo.classes.Filiere;
import atilla.demo.dto.FiliereDto;
import org.hibernate.query.derived.AnonymousTupleBasicValuedModelPart;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Bean;

import javax.sound.midi.Soundbank;

@Mapper(componentModel = "spring", uses = { UeMapper.class })
public interface FiliereMapper {


    @Mapping(source = "ues", target = "ues")

    FiliereDto toDto (Filiere filiere);

    @Mapping(source = "ues", target = "ues")
    Filiere toClasse(FiliereDto filiereDto);





}
