package br.com.dio.dao;

import br.com.dio.Model.UserModel;
import br.com.dio.exception.EmpetyStoregeException;
import br.com.dio.exception.UserNotFundException;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private long nextId = 1L ;

    private final List<UserModel> models = new ArrayList<>();

    public UserModel save(final UserModel model){

        model.setId(nextId++);
        models.add(model);
        return model;
    }

    public UserModel update(final UserModel model){
        var toUpdate = findById(model.getId());
        models.remove(toUpdate);
        models.add(model);
        return model;
    }

    public void delete (final long id){
        var toDelete = findById(id);
        models.remove(toDelete);
    }

    public UserModel findById (final long id){
        verifyStorage();
        var message = String.format("Não exeiste Usuario com Id %s cadastrado",id);
        return models.stream().filter(u -> u.getId() == id)
                .findFirst().orElseThrow(() -> new UserNotFundException(message));
    }

    public List<UserModel> findAll(){
        List<UserModel> result;
        try {
            verifyStorage();
            result = models;
        }catch (EmpetyStoregeException ex){
            ex.printStackTrace();
            result = new ArrayList<>();
        }
        return result;
    }

    private void verifyStorage(){
        if (models.isEmpty()) throw new EmpetyStoregeException("o Armazenamento esta vazio");
    }
}
