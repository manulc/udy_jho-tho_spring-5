package com.mlorenzo.spring5mvcrest.services;

import java.util.List;

import com.mlorenzo.spring5mvcrest.api.v1.model.VendorDTO;

public interface VendorService {
	List<VendorDTO> getAllVendors();
	VendorDTO getVendorById(Long id);
	VendorDTO saveOrUpdateVendor(VendorDTO vendorDTO,Long id);
	VendorDTO patchVendor(Long id, VendorDTO vendorDTO);
	void deleteVendorById(Long id);
}
