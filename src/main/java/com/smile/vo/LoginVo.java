package com.smile.vo;

import com.smile.annotation.validate.IsMobile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * TODO
 *
 * @author smilePlus
 * @version 1.0
 * @date 2021/3/5 17:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVo {
    @IsMobile
    private String mobile;
    @NotBlank
    @Length(min = 32)
    private String password;
}
