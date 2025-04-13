package cz.itsarka.springinsuranceapp.mapper;

import cz.itsarka.springinsuranceapp.dto.InsuredPersonDTO;
import cz.itsarka.springinsuranceapp.entity.InsuredPerson;
import cz.itsarka.springinsuranceapp.entity.Insurance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;


import java.util.List;
import java.util.stream.Collectors;

/**
 * MapStruct mapper pro převod mezi entitou InsuredPerson a DTO InsuredPersonDTO.
 * Automatizuje mapování mezi databázovým modelem a DTO, což zjednodušuje přenos dat mezi backendem a frontendem.
 */
@Mapper
public interface InsuredPersonMapper {
    InsuredPersonMapper INSTANCE = Mappers.getMapper(InsuredPersonMapper.class);

    /**
     * Převádí entitu InsuredPerson na InsuredPersonDTO.
     * Mapuje seznam pojištění na seznam jejich ID.
     *
     * @param insuredPerson entita InsuredPerson
     * @return InsuredPersonDTO
     */
    @Mapping(target = "insuranceIds", source = "insurances", qualifiedByName = "mapInsurancesToIds")
    @Mapping(target = "dateOfBirth", source = "dateOfBirth")
    InsuredPersonDTO toDTO(InsuredPerson insuredPerson);

    /**
     * Převádí DTO InsuredPersonDTO na entitu InsuredPerson.
     * Pojištění jsou ignorována, protože jsou mapována zvlášť.
     *
     * @param insuredPersonDTO objekt InsuredPersonDTO
     * @return entita InsuredPerson
     */
    @Mapping(target = "insurances", ignore = true) // Při vytváření entity ignorujeme pojistky
    @Mapping(target = "dateOfBirth", source = "dateOfBirth") // Bez konverzí
    InsuredPerson toEntity(InsuredPersonDTO insuredPersonDTO);

    /**
     * Převádí seznam entit Insurance na seznam jejich ID.
     * Používá se pro mapování mezi entitou InsuredPerson a InsuredPersonDTO.
     *
     * @param insurances seznam pojištění
     * @return seznam ID pojištění
     */
    @Named("mapInsurancesToIds")
    default List<Long> mapInsurancesToIds(List<Insurance> insurances) {
        return insurances != null ? insurances.stream().map(Insurance::getId).collect(Collectors.toList()) : null;
    }


}
