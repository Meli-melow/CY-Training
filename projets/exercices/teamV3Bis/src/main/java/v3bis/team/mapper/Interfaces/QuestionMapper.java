package v3bis.team.mapper.Interfaces;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import v3bis.team.dtos.QuestionDto;
import v3bis.team.model.Question;

@Mapper(componentModel = "spring", uses = UtilisateurMapper.class)
public interface QuestionMapper {

    @Mapping(source="question", target = "question")
    @Mapping(source="correction", target = "correction")
    @Mapping(source = "indBonneRep",target = "indBonneRep")
    @Mapping(source = "indice",target = "indice")
    @Mapping(source = "etatValidation", target = "etatValidation")
    @Mapping(source = "dateValidee",target = "dateValidee")
    @Mapping(source = "tempsAttente", target = "tempsAttente")
    @Mapping(source = "dateDemandeAjout", target = "dateDemandeAjout")
    QuestionDto toDto(Question question);

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
