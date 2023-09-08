package com.mlorenzo.spring5mvcrest.api.v1.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mlorenzo.spring5mvcrest.api.v1.controllers.VendorController;
import com.mlorenzo.spring5mvcrest.api.v1.model.VendorDTO;
import com.mlorenzo.spring5mvcrest.domain.Vendor;

public class VendorMapperTest {
	private static final String NAME = "Nuts for Nuts Company";
	private static final long ID = 1L;
	private static final String URL = VendorController.BASE_URL + "/" + ID;
	
	VendorMapper vendorMapper = VendorMapper.INSTANCE;
	
	@Test
    public void vendorToVendorDTO() throws Exception {
        //given
        Vendor vendor = new Vendor();
        vendor.setName(NAME);
        vendor.setId(ID);
        //when
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
        //then
        assertEquals(NAME, vendorDTO.getName());
    }
	
	@Test
    public void vendorDTOToVendor() throws Exception {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);
        vendorDTO.setVendorUrl(URL);
        //when
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        //then
        assertEquals(NAME, vendor.getName());
    }

}
