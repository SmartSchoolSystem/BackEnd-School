package com.seven.schoolapi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfValidator implements ConstraintValidator<CpfValidation, String> {

    @Override
    public void initialize(CpfValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || cpf.length() != 11) {
            return false;
        }

        int[] cpfArray = CPFtoArray(cpf);
        return verificaCodigo(1, cpfArray);
    }

    private int[] CPFtoArray(String cpf) {
        int[] cpfArray = new int[11];
        for (int i = 0; i < 11; i++) {
            cpfArray[i] = Integer.parseInt(String.valueOf(cpf.charAt(i)));
        }
        return cpfArray;
    }

    private boolean verificaCodigo(int posicaoCodigo, int[] cpf) {
        int j = posicaoCodigo == 1 ? 10 : 11;

        int indexParameter = 7 + posicaoCodigo;
        int resultado = 0;

        for (int i = 0; i <= indexParameter; i++) {
            resultado += j * cpf[i];
            j--;
        }

        int restoDiv = resultado % 11;
        if (restoDiv < 2) {
            if (cpf[indexParameter + 1] == 0) {
                if (posicaoCodigo == 1) {
                    return verificaCodigo(2, cpf);
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            int dif = 11 - restoDiv;
            if (cpf[indexParameter + 1] == dif) {
                if (posicaoCodigo == 1) {
                    return verificaCodigo(2, cpf);
                } else {
                    return true;
                }
            } else {
                return false;
            }
        }
    }
}
