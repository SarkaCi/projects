package cz.itsarka.homebudgetfromits.mapper;

import cz.itsarka.homebudgetfromits.dto.ExpectedContributionEntryDTO;
import cz.itsarka.homebudgetfromits.entity.ExpectedContributionEntry;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpectedContributionMapper {

    ExpectedContributionMapper INSTANCE = Mappers.getMapper(ExpectedContributionMapper.class);

    ExpectedContributionEntryDTO toDto(ExpectedContributionEntry entry);

    ExpectedContributionEntry toEntity(ExpectedContributionEntryDTO dto);

    List<ExpectedContributionEntryDTO> toDtoList(List<ExpectedContributionEntry> entries);

    List<ExpectedContributionEntry> toEntityList(List<ExpectedContributionEntryDTO> dtos);
}

