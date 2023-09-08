package com.mlorenzo.spring5mvcrest.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mlorenzo.spring5mvcrest.api.v1.mapper.VendorMapper;
import com.mlorenzo.spring5mvcrest.api.v1.model.VendorDTO;
import com.mlorenzo.spring5mvcrest.domain.Vendor;
import com.mlorenzo.spring5mvcrest.exceptions.ResourceNotFoundException;
import com.mlorenzo.spring5mvcrest.repositories.VendorRepository;


public class VendorServiceImplTest {
	private static final Long ID = 2L;
    private static final String NAME = "Nuts for Nuts Company";
    
    @Mock
    VendorRepository vendorRepository;
    
    VendorService vendorService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        vendorService = new VendorServiceImpl(vendorRepository,VendorMapper.INSTANCE);
    }

    @Test
    public void getAllVendors() throws Exception {
        //given
        List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor(), new Vendor());
        //mockito BDD syntax
        given(vendorRepository.findAll()).willReturn(vendors);
        //when
        List<VendorDTO> vendorsDTOS = vendorService.getAllVendors();
        //then
        then(vendorRepository).should(times(1)).findAll();
        //JUnit Assert that with matchers
        assertThat(vendorsDTOS.size(), is(equalTo(3)));
    }

    @Test
    public void getVendorById() throws Exception {
        //given
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);
        Optional<Vendor> vendorOptional = Optional.of(vendor);
        //mockito BDD syntax
        given(vendorRepository.findById(anyLong())).willReturn(vendorOptional);
        //when
        VendorDTO vendorDTO = vendorService.getVendorById(ID);
        //then
        then(vendorRepository).should(times(1)).findById(anyLong());
        //JUnit Assert that with matchers
        assertThat(vendorDTO.getName(), is(equalTo(NAME)));
    }
    
    @Test(expected = ResourceNotFoundException.class)
    public void getVendorByIdNotFound() throws Exception {
        //given
        //mockito BBD syntax since mockito 1.10.0
        given(vendorRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        //should go boom
        vendorService.getVendorById(1L);
        //then
        then(vendorRepository).should(times(1)).findById(anyLong());
    }
    
    @Test
    public void saveVendor() throws Exception {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);
        Vendor savedVendor = new Vendor();
        savedVendor.setName(vendorDTO.getName());
        savedVendor.setId(ID);
        //mockito BDD syntax
        given(vendorRepository.save(any(Vendor.class))).willReturn(savedVendor);
        //when
        VendorDTO savedDto = vendorService.saveOrUpdateVendor(vendorDTO,null);
        //then
        // 'should' defaults to times = 1
        then(vendorRepository).should().save(any(Vendor.class));
        assertThat(savedDto.getVendorUrl(), containsString("1"));
    }
    
    @Test
    public void updateVendor() throws Exception {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Fun Fresh Fruits Ltd.");
        Vendor vendor = new Vendor();
        vendor.setName(NAME);
        vendor.setId(ID);
        //mockito BDD syntax
        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);
        //when
        VendorDTO updatedVendor = vendorService.saveOrUpdateVendor(vendorDTO,ID);
        //then
        // 'should' defaults to times = 1
        then(vendorRepository).should().save(any(Vendor.class));
        assertThat(updatedVendor.getVendorUrl(), containsString("1"));
    }
    
    @Test
    public void patchVendor() throws Exception {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Fun Fresh Fruits Ltd.");
        Vendor vendor = new Vendor();
        vendor.setName(NAME);
        vendor.setId(ID);
        //mockito BDD syntax
        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));
        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);
        //when
        VendorDTO savedVendorDTO = vendorService.patchVendor(ID,vendorDTO);
        //then
        // 'should' defaults to times = 1
        then(vendorRepository).should().save(any(Vendor.class));
        then(vendorRepository).should(times(1)).findById(anyLong());
        assertThat(savedVendorDTO.getVendorUrl(), containsString("1"));
    }

    @Test
    public void deleteVendorById() throws Exception {
        Long id = 1L;
        //when
        vendorService.deleteVendorById(id);
        //then
        // 'should' defaults to times = 1
        then(vendorRepository).should().deleteById(anyLong());
    }

}
