package cz.itsarka.springinsuranceapp.mapper;


import cz.itsarka.springinsuranceapp.dto.InsuranceDTO;
import cz.itsarka.springinsuranceapp.entity.Insurance;
import cz.itsarka.springinsuranceapp.entity.InsuredPerson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * MapStruct mapper pro převod mezi entitou Insurance a DTO InsuranceDTO.
 * Automatizuje mapování mezi databázovým modelem a DTO, což zjednodušuje přenos dat mezi backendem a frontendem.
 */
@Mapper
public interface InsuranceMapper {
    InsuranceMapper INSTANCE = Mappers.getMapper(InsuranceMapper.class);

    /**
     * Převádí entitu Insurance na InsuranceDTO.
     * Mapuje seznam pojištěných osob na seznam jejich ID.
     *
     * @param insurance entita Insurance
     * @return InsuranceDTO
     */
    @Mapping(target = "insuredPersonIds", source = "insuredPersons", qualifiedByName = "mapInsuredPersonsToIds")
    InsuranceDTO toDTO(Insurance insurance);

    /**
     * Převádí DTO InsuranceDTO na entitu Insurance.
     * Pojištěné osoby jsou ignorovány, protože jsou mapovány zvlášť.
     *
     * @param insuranceDTO objekt InsuranceDTO
     * @return entita Insurance
     */
    @Mapping(target = "insuredPersons", ignore = true)
    Insurance toEntity(InsuranceDTO insuranceDTO);

    /**
     * Převádí seznam entit InsuredPerson na seznam jejich ID.
     * Používá se pro mapování mezi entitou Insurance a InsuranceDTO.
     *
     * @param insuredPersons seznam pojištěných osob
     * @return seznam ID pojištěných osob
     */
    @Named("mapInsuredPersonsToIds")
    default List<Long> mapInsuredPersonsToIds(List<InsuredPerson> insuredPersons) {
        return insuredPersons != null ? insuredPersons.stream().map(InsuredPerson::getId).collect(Collectors.toList()) : null;
    }
}




