package br.itcampos.mycepapplication.utils;

import android.text.TextUtils;

public class CepValidator {
    public static boolean isValid(String cep) {
        return !TextUtils.isEmpty(cep) && cep.length() == 8;
    }
}
