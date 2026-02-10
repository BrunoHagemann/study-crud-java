import br.com.dio.Model.MenuOption;
import br.com.dio.Model.UserModel;
import br.com.dio.dao.UserDAO;
import br.com.dio.exception.EmpetyStoregeException;
import br.com.dio.exception.UserNotFundException;
import br.com.dio.exception.ValidatorException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static br.com.dio.validator.UserValidator.verfyModel;

public class Main {

    private final static UserDAO dao = new UserDAO();

    private static final Scanner sacanner = new Scanner(System.in);
    public static void main(String[] args) {

        while (true){
            System.out.println("Bem vindoao cadastro de usuarios selecione a opreção desejada");
            System.out.println("1- Cadastrar");
            System.out.println("2 - Atualizar");
            System.out.println("3 - Excluir");
            System.out.println("4 - Buscar por indentificador");
            System.out.println("5 - Listar");
            System.out.println("6 - Sair");
            var userImput = sacanner.nextInt();
            var selectedOption = MenuOption.values()[userImput -1];
            switch (selectedOption){
                case SAVE -> {
                    try {
                        var user = dao.save(requestToSave());
                        System.out.printf("Usuario Cadastrado %s", user);
                    }catch (ValidatorException ex){
                        System.out.println(ex.getMessage());
                        ex.printStackTrace();
                    }
                }
                case UPDATE -> {
                    try {
                    var user = dao.update(requestToUpdate());
                    System.out.printf("Usuario Atualisado %s" ,user);
                    }catch (UserNotFundException | EmpetyStoregeException ex) {
                        System.out.println(ex.getMessage());
                    }catch (ValidatorException ex){
                        System.out.println(ex.getMessage());
                        ex.printStackTrace();
                    }
                    finally {
                        System.out.println("====================");
                    }
                }
                case DELETE -> {
                    try {
                    dao.delete(requestId());
                    System.out.println("Usuario Excluido");
                    }catch (UserNotFundException | EmpetyStoregeException ex){
                    System.out.println(ex.getMessage());}finally {
                        System.out.println("====================");
                    }
                }
                case FIND_BY_ID ->{
                    try {
                        var id = requestId();
                        var user = dao.findById(id);
                        System.out.printf("usuario com Id: %s" , id);
                        System.out.println(user);
                    }catch (UserNotFundException | EmpetyStoregeException ex){
                        System.out.println(ex.getMessage());
                    }finally {
                        System.out.println("====================");
                    }

                }

                case FIND_ALL -> {
                    var users = dao.findAll();
                    System.out.println("Usuarios cadastrados");
                    System.out.println("=====================================");
                    users.forEach(System.out::println);
                    System.out.println("=====================================");
                }
                case EXIT -> System.exit(0);
            }

        }

    }

    private static long requestId(){
        System.out.println("Informe o indentificador Do Usuario");
        return sacanner.nextLong();
    }

    private static UserModel requestToSave() throws ValidatorException {

        System.out.println("Informe o Nome Do Usuario");
        var name = sacanner.next();
        System.out.println("Informe o E-mail");
        var emal = sacanner.next();
        System.out.println("informe a data de nasimento do Usuario (dd/MM/yyyy)");
        var birthadayString = sacanner.next();
        var formater = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var birthday = LocalDate.parse(birthadayString,formater);
        return validadeInputs(0 ,name,emal,birthday);

    }

    private static UserModel validadeInputs(final long Id, final String name,
                                            final String emal, final LocalDate birthday) throws ValidatorException {
        var user = new UserModel(0,name , emal , birthday);
        verfyModel(user);
        return user;
    }

    private static UserModel requestToUpdate() throws ValidatorException {

        System.out.println("Informe o indentificador Do Usuario");
        var id = sacanner.nextLong();
        System.out.println("Informe o Nome Do Usuario");
        var name = sacanner.next();
        System.out.println("Informe o E-mail");
        var emal = sacanner.next();
        System.out.println("informe a data de nasimento do Usuario (dd/MM/yyyy)");
        var birthadayString = sacanner.next();
        var formater = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var birthday = LocalDate.parse(birthadayString,formater);
        return validadeInputs(id ,name,emal,birthday);
    }

}