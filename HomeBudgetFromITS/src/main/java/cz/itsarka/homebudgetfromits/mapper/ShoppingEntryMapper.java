package cz.itsarka.homebudgetfromits.mapper;

import cz.itsarka.homebudgetfromits.dto.ShoppingEntryDTO;
import cz.itsarka.homebudgetfromits.entity.ShoppingEntry;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ShoppingEntryMapper {

    ShoppingEntryMapper INSTANCE = Mappers.getMapper(ShoppingEntryMapper.class);

    ShoppingEntryDTO toDto(ShoppingEntry entry);

    ShoppingEntry toEntity(ShoppingEntryDTO dto);

    List<ShoppingEntryDTO> toDtoList(List<ShoppingEntry> entries);

    List<ShoppingEntry> toEntityList(List<ShoppingEntryDTO> dtos);
}


