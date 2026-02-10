package br.com.dio.validator;

import br.com.dio.Model.UserModel;
import br.com.dio.exception.ValidatorException;

public class UserValidator {

    private UserValidator(){

    }

    public static void verfyModel(final UserModel model) throws ValidatorException {
        if(strngIsBlank(model.getName()))
            throw new ValidatorException("Infrme um nome valido");
        if (model.getName().length() <=1 )
            throw new ValidatorException("O nome precisa ter mais que 1 caractér");
        if(strngIsBlank(model.getEmail()))
            throw new ValidatorException("Informe email valido");
        if(model.getEmail().contains("@") || (!model.getEmail().contains(".")))
            throw new ValidatorException("Informe um email valido");
    }

    private static boolean strngIsBlank(final String value){
        return value == null || value.isBlank();
    }

}
