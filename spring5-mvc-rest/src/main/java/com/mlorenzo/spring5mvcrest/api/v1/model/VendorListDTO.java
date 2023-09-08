package com.mlorenzo.spring5mvcrest.api.v1.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VendorListDTO {
	private List<VendorDTO> vendors;
}
