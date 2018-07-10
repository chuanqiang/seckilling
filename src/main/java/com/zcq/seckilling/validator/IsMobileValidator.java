package com.zcq.seckilling.validator;

import com.zcq.seckilling.utils.ValidatorUtil;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

	private boolean required = false;

	@Override
	public void initialize(IsMobile isMobile) {
		required = isMobile.required();
	}

	@Override
	public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
		if(required){
			return ValidatorUtil.isMobile(s);
		}else if(StringUtils.isEmpty(s)){
			return true;
		}else{
			return ValidatorUtil.isMobile(s);
		}
	}
}
