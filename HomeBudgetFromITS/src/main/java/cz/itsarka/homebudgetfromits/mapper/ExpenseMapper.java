package cz.itsarka.homebudgetfromits.mapper;

import cz.itsarka.homebudgetfromits.dto.ExpenseDTO;
import cz.itsarka.homebudgetfromits.entity.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


import java.util.List;

@Mapper(componentModel = "spring", uses = {ShoppingEntryMapper.class, ExpectedContributionMapper.class})
public interface ExpenseMapper {

    ExpenseMapper INSTANCE = Mappers.getMapper(ExpenseMapper.class);

    @Mapping(target = "expectedContributions", source = "expectedContributions")
    ExpenseDTO toDto(Expense expense);

    Expense toEntity(ExpenseDTO dto);

    List<ExpenseDTO> toDtoList(List<Expense> expenses);

    List<Expense> toEntityList(List<ExpenseDTO> dtos);

}


/*
@Mapper(componentModel = "spring"):
Určuje, že mapper bude spravován Springem a může být injektován (@Autowired nebo přes konstruktor).
MapStruct vygeneruje implementaci této rozhraní třídy za běhu.
uses = {ShoppingEntryMapper.class, ExpectedContributionMapper.class}:
Používá ShoppingEntryMapper a ExpectedContributionMapper k převodu vnořených objektů (shoppingEntries a expectedContributions).
Tyto mappery mapují jednotlivé položky nákupů (ShoppingEntry) a očekávané příspěvky (ExpectedContributionEntry).

Mappers.getMapper(ExpenseMapper.class) – vytváří statickou instanci mapperu.
To umožňuje volat metody bez potřeby Spring Contextu (pokud by nebylo použito componentModel = "spring").

@Mapping(target = "expectedContributions", source = "expectedContributions")
ExpenseDTO toDto(Expense expense);
Převádí entitu Expense (z databáze) do DTO ExpenseDTO (které posíláme na frontend).
Data pocházejí z databáze (Expense) a jdou do API (ExpenseDTO).
expectedContributions je explicitně mapováno (není nutné, pokud jsou názvy stejné).

*/
