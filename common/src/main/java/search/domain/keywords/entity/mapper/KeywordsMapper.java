package search.domain.keywords.entity.mapper;

import search.domain.keywords.entity.Keywords;
import search.domain.keywords.dto.KeywordsDTO;
import search.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface KeywordsMapper extends GenericMapper<KeywordsDTO, Keywords> {

    default KeywordsDTO asDTOWithLiveCount(Keywords keywords, String wordCount){
        return KeywordsDTO.of(keywords.getId(), keywords.getWord(), wordCount);
    }

}
