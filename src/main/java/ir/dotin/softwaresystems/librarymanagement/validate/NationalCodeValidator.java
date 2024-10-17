package ir.dotin.softwaresystems.librarymanagement.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NationalCodeValidator implements ConstraintValidator<ValidNationalCode, String> {

    @Override
    public void initialize(ValidNationalCode nationalCode) {
    }

    @Override
    public boolean isValid(String nationalCode, ConstraintValidatorContext context) {
        if (nationalCode == null || !nationalCode.matches("^\\d{10}$")) {
            return false;
        }

        int check = Integer.parseInt(nationalCode.substring(9, 10));
        int sum = 0;

        for (int i = 0; i < 9; i++) {
            sum += Integer.parseInt(nationalCode.substring(i, i+1)) * (10 - i);
        }

        int remainder = sum % 11;
        return (remainder < 2 && check == remainder) || (remainder >= 2 && check == 11 - remainder);
    }
}