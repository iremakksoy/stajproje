package com.iremaksoy.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
	private String firstname;
    private String lastname;
    private String email;
    private Long version;

}
