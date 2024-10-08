package com.pbyt.finance.auth.model;

import com.pbyt.finance.applicationEntity.TblStateDistrict;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponse {
    private String token;
    private String name;
    private Long mobileNumber;
    private List<TblStateDistrict> workArea;
    private Integer id;
}
