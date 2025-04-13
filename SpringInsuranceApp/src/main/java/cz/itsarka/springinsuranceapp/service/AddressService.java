package cz.itsarka.springinsuranceapp.service;

import cz.itsarka.springinsuranceapp.dto.AddressDTO;
import cz.itsarka.springinsuranceapp.entity.Address;
import cz.itsarka.springinsuranceapp.mapper.AddressMapper;
import cz.itsarka.springinsuranceapp.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Služba pro správu adres.
 * Poskytuje metody pro CRUD operace s entitou Address a její konverzi na AddressDTO.
 */
@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    /**
     * Konstruktor pro injektování AddressRepository.
     * Mapování entit na DTO se provádí pomocí AddressMapper.
     *
     * @param addressRepository repository pro práci s adresami
     */
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
        this.addressMapper = AddressMapper.INSTANCE;
    }

    /**
     * Získá seznam všech adres v databázi.
     * @return seznam AddressDTO
     */
    public List<AddressDTO> getAllAddresses() {
        return addressRepository.findAll()
                .stream()
                .map(addressMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Najde adresu podle jejího ID.
     * @param id ID adresy
     * @return AddressDTO nebo null, pokud adresa neexistuje
     */
    public AddressDTO getAddressById(Long id) {
        return addressRepository.findById(id)
                .map(addressMapper::toDTO)
                .orElse(null);
    }

    /**
     * Vytvoří novou adresu a uloží ji do databáze.
     * @param addressDTO data nové adresy
     * @return vytvořená adresa jako AddressDTO
     */
    public AddressDTO createAddress(AddressDTO addressDTO) {
        Address address = addressMapper.toEntity(addressDTO);
        return addressMapper.toDTO(addressRepository.save(address));
    }

    /**
     * Aktualizuje existující adresu podle jejího ID.
     * @param id ID adresy k aktualizaci
     * @param addressDTO nové hodnoty pro adresu
     * @return aktualizovaná adresa jako AddressDTO nebo null, pokud adresa neexistuje
     */
    public AddressDTO updateAddress(Long id, AddressDTO addressDTO) {
        return addressRepository.findById(id).map(address -> {
            address.setStreet(addressDTO.getStreet());
            address.setStreetNumber(addressDTO.getStreetNumber());
            address.setCity(addressDTO.getCity());
            address.setPostalCode(addressDTO.getPostalCode());
            address.setCountry(addressDTO.getCountry());
            return addressMapper.toDTO(addressRepository.save(address));
        }).orElse(null);
    }

    /**
     * Odstraní adresu podle jejího ID.
     * @param id ID adresy k odstranění
     */
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}
