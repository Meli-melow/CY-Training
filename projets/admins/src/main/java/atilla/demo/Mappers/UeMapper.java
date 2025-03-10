package atilla.demo.Mappers;

import atilla.demo.classes.UE;
import atilla.demo.dto.UeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UeMapper {
    @Mapping(source = "id", target="idUE")
    UE toClasse (UeDto ueDto);

    @Mapping(source = "idUE", target="id")
    UeDto todTO(UE ue);


    List<UE> toEntityList(List<UeDto> ueDtos);
    List<UeDto> toDtoList(List<UE> ues);


}
