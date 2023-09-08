package com.mlorenzo.spring5mvcrest.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mlorenzo.spring5mvcrest.api.v1.controllers.VendorController;
import com.mlorenzo.spring5mvcrest.api.v1.mapper.VendorMapper;
import com.mlorenzo.spring5mvcrest.api.v1.model.VendorDTO;
import com.mlorenzo.spring5mvcrest.domain.Vendor;
import com.mlorenzo.spring5mvcrest.exceptions.ResourceNotFoundException;
import com.mlorenzo.spring5mvcrest.repositories.VendorRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VendorServiceImpl implements VendorService {
	private final VendorRepository vendorRepository;
	private final VendorMapper vendorMapper;

	@Override
	public List<VendorDTO> getAllVendors() {
		return vendorRepository.findAll().stream()
				.map(vendor -> {
					VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
					vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
					return vendorDTO;
				})
				.collect(Collectors.toList());
	}

	@Override
	public VendorDTO getVendorById(Long id) {
		return vendorRepository.findById(id).map(vendor -> {
			VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
			vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
			return vendorDTO;
		})
		.orElseThrow(ResourceNotFoundException::new); // Versión simplificada de la expresión "() -> new ResourceNotFoundException()"
	}

	@Override
	public VendorDTO saveOrUpdateVendor(VendorDTO vendorDTO, Long id) {
		Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
		if(id != null)
			vendor.setId(id);
		Vendor savedVendor = vendorRepository.save(vendor);
		VendorDTO returnDTO = vendorMapper.vendorToVendorDTO(savedVendor);
		returnDTO.setVendorUrl(getVendorUrl(savedVendor.getId()));
		return returnDTO;
	}

	@Override
	public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
		return vendorRepository.findById(id).map(vendor -> {
            if(vendorDTO.getName() != null){
            	vendor.setName(vendorDTO.getName());
            }
            Vendor savedVendor = vendorRepository.save(vendor);
            VendorDTO returnDTO = vendorMapper.vendorToVendorDTO(savedVendor);
            returnDTO.setVendorUrl(getVendorUrl(savedVendor.getId()));
            return returnDTO;
        })
        .orElseThrow(ResourceNotFoundException::new); // Versión simplificada de la expresión "() -> new ResourceNotFoundException()"
	}

	@Override
	public void deleteVendorById(Long id) {
		vendorRepository.deleteById(id);

	}
	
	private String getVendorUrl(Long id) {
		return VendorController.BASE_URL + "/" + id;
	}

}
