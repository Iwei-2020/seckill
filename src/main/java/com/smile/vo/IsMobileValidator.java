package com.smile.vo;

import com.smile.annotation.validate.IsMobile;
import com.smile.utils.PhoneFormatCheckUtils;
import org.springframework.util.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * TODO
 *
 * @author smilePlus
 * @version 1.0
 * @date 2021/3/5 20:46
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (required) {
            return PhoneFormatCheckUtils.isChinaPhoneLegal(value);
        } else {
            if (StringUtils.hasLength(value)) {
                return PhoneFormatCheckUtils.isChinaPhoneLegal(value);
            } else {
                return true;
            }
        }
    }
}
