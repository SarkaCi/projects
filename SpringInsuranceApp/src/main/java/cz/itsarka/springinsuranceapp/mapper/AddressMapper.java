package cz.itsarka.springinsuranceapp.mapper;

import cz.itsarka.springinsuranceapp.dto.AddressDTO;
import cz.itsarka.springinsuranceapp.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper pro převod mezi Address entitou a AddressDTO.
 * Automatizuje mapování mezi databázovým modelem a DTO, což zjednodušuje přenos dat mezi backendem a frontendem.
 */
@Mapper
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    /**
     * Převádí entitu Address na AddressDTO.
     * @param address entita Address
     * @return AddressDTO
     */
    AddressDTO toDTO(Address address);

    /**
     * Převádí AddressDTO na entitu Address.
     * @param addressDTO objekt AddressDTO
     * @return entita Address
     */
    Address toEntity(AddressDTO addressDTO);
}

