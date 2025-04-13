package cz.itsarka.springinsuranceapp.controller;

import cz.itsarka.springinsuranceapp.dto.AddressDTO;
import cz.itsarka.springinsuranceapp.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST kontroler pro správu adres.
 * Poskytuje CRUD operace pro entity Address prostřednictvím API endpointů.
 */
@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;


    /**
     * Konstruktor s injektovanou službou pro práci s adresami.
     */
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * Získá seznam všech adres.
     * @return ResponseEntity obsahující seznam AddressDTO
     */
    @GetMapping
    public ResponseEntity<List<AddressDTO>> getAllAddresses() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }

    /**
     * Získá adresu podle jejího ID.
     * @param id ID adresy
     * @return ResponseEntity obsahující AddressDTO nebo 404, pokud adresa neexistuje
     */
    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long id) {
        return ResponseEntity.ofNullable(addressService.getAddressById(id));
    }

    /**
     * Vytvoří novou adresu.
     * @param addressDTO DTO obsahující data nové adresy
     * @return ResponseEntity obsahující vytvořenou adresu
     */
    @PostMapping
    public ResponseEntity<AddressDTO> createAddress(@RequestBody AddressDTO addressDTO) {
        return ResponseEntity.ok(addressService.createAddress(addressDTO));
    }

    /**
     * Aktualizuje existující adresu podle jejího ID.
     * @param id ID adresy k aktualizaci
     * @param addressDTO DTO s novými daty adresy
     * @return ResponseEntity obsahující aktualizovanou adresu nebo 404, pokud adresa neexistuje
     */
    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long id, @RequestBody AddressDTO addressDTO) {
        AddressDTO updated = addressService.updateAddress(id, addressDTO);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    /**
     * Odstraní adresu podle jejího ID.
     * @param id ID adresy k odstranění
     * @return ResponseEntity s HTTP statusem 204 (No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}
