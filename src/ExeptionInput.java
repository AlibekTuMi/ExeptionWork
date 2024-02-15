public class ExeptionInput extends RuntimeException{

    public ExeptionInput(){
        super("Вы не ввели все необходимые данные");
    }
    public static void ExIn(){
        System.out.println("Неправильный ввод пола!");
    }
   
}
