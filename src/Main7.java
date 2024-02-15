import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Scanner;

public class Main7 {
    public static void main(String[] args) throws IOEx, FileNotFoundException {
        boolean choice = true;
        while (choice) {
            try (Scanner scanner = new Scanner(System.in)){
                System.out.println("\nВведите данные в ОДНУ строчку в формате:");
            System.out.println("Фамилия Имя Отчество Дата рождения Номер телефона Пол");
            System.out.println("Дату рождения указывать в формате ДД.ММ.ГГГГ");
            System.out.println("Пол указывать буквами f или m в нижнем регистре");
            String input = scanner.nextLine();
            String[] words = input.split(" ");

            if (words.length != 6) { // Первоначальная проверка на количество введенных данных пользователем
                throw new ExeptionInput();
            }
            int j = 0;
            String[] FIO = new String[3];
            String gender = "";
            String numfon = "";
            String birthday = "";
            for (int i = 0; i < words.length; i++) { // Через цикл мы записываем в созданные переменные данные
                                                     // пользователя
                if (words[i].length() < 2) {
                    gender = words[i];
                } else if (convertToDate(words[i])) {
                    birthday = words[i];
                } else if (words[i].matches("\\d+")) {
                    numfon = words[i];
                }
                else if (words[i].contains(",")) {
                    String[] a = words[i].split(",");
                    for (int k = 0; k < a.length; k++) {
                        birthday += a[k];
                        if (k < a.length - 1) {
                            birthday += ".";
                        }
                    }
                    if (!convertToDate(birthday)) { // Метод определяет то, что если нельзя конвертировать заданную дату в строку, тогда переменная остается пустая
                                                    // что в итоге приводит к ошибке
                        birthday = "";
                    }
                }
                else {
                    FIO[j] = words[i];
                    j++;
                }
            }
            
            String[] newBD = birthday.split("\\.");
            if (newBD.length != 3) { // Проверка на количество введенных пользователем день месяц и год
                throw new ExeptionDate("Ошибка ввода даты", birthday, 0);
            } else if (newBD[0].length() != 2 || newBD[1].length() != 2 || newBD[2].length() != 4) { // Проверка на
                                                                                                     // длину ввода даты
                                                                                                     // рождения
                throw new ExeptionDate("Ошибка ввода даты", birthday, 0);
            }
            if (!gender.equals("m") && !gender.equals("f")) { // Проверка на ввод пола
                throw new IOEx();
            }

            /*
             * Дата сегодня
             */
            LocalDate localDate = LocalDate.now();
            int year = localDate.getYear();
            int month = localDate.getMonthValue();
            int today = localDate.getDayOfMonth();
            
            /*
             * postYear - ограничение по году
             * postMonth - ограничение по месяцу
             */
            int postYear = 1900;
            int postMonth = 12;
            if (Integer.parseInt(newBD[0])<=0||Integer.parseInt(newBD[1])<=0||Integer.parseInt(newBD[2])<=0) { // Проверка на неотрицательный ввод даты
                System.out.println("\nДень месяц или год не могут быть нулём или отрицательным числом");
                throw new ExeptionDate("Ошибка ввода даты", birthday, 0);
            }

            if (Integer.parseInt(newBD[2]) < postYear) { // Проверка на то что пользователь ввел дату рождения не раньше
                                                         // чем 1900 год
                System.out.println("\nДата раньше 1 января 1900 года.");
                throw new ExeptionDate("Ошибка ввода даты", birthday, 0);
            }
            else if(Integer.parseInt(newBD[1])>postMonth){        // Все проверки от этой и кроме последней на правильный ввод даты
                System.out.println("\nНеправильный ввод месяца!");
                throw new ExeptionDate("Ошибка ввода даты", birthday, 0);
            }
            else if ((Integer.parseInt(newBD[1])==1||Integer.parseInt(newBD[1])==3||Integer.parseInt(newBD[1])==5||Integer.parseInt(newBD[1])==7||
            Integer.parseInt(newBD[1])==8||Integer.parseInt(newBD[1])==10||Integer.parseInt(newBD[1])==12)&&Integer.parseInt(newBD[0])>31) {
                System.out.println("\nНеправильный ввод дня месяца!");
                throw new ExeptionDate("Ошибка ввода даты", birthday, 0);
            }
            else if ((Integer.parseInt(newBD[1])==4||Integer.parseInt(newBD[1])==6||Integer.parseInt(newBD[1])==9||Integer.parseInt(newBD[1])==11)&&Integer.parseInt(newBD[0])>30) {
                System.out.println("\nНеправильный ввод дня месяца!");
                throw new ExeptionDate("Ошибка ввода даты", birthday, 0);
            }
            else if (Integer.parseInt(newBD[0])>28&&Integer.parseInt(newBD[1])==2&&!(Integer.parseInt(newBD[2])%4==0)) {
                System.out.println("\nВ этом году 28 дней в феврале!");
                throw new ExeptionDate("Ошибка ввода даты", birthday, 0);
            }
            else if(Integer.parseInt(newBD[1])==2&&Integer.parseInt(newBD[0])>29){
                System.out.println("\nВ этом году в феврале не может быть больше 29 дней!");
                throw new ExeptionDate("Ошибка ввода даты", birthday, 0);
            }
             else if (Integer.parseInt(newBD[2]) > year
                    || (Integer.parseInt(newBD[2]) == year && Integer.parseInt(newBD[1]) > month) ||
                    (Integer.parseInt(newBD[2]) == year && Integer.parseInt(newBD[1]) == month
                            && Integer.parseInt(newBD[0]) > today)) { // Проверка на то что пользователь ввел дату
                                                                      // рождения завтра или позднее
                System.out.println("\nВведенная дата не может быть позже чем сегодняшняя");
                throw new ExeptionDate("Ошибка ввода даты", birthday, 0);
            }
            String filename = FIO[0] + ".txt";
            String path = "C:\\Users\\Алибек\\Desktop\\ExeptionWork\\ExeptionWork";
            String separation = "-----------------------------------";

            File file = new File(filename);

            try {
                // Проверяем существует ли файл
                if (Files.exists(Paths.get(path, filename))) { // Если файл с введенной фамилией существует, то
                                                               // записываем данные в него
                    FileWriter fileWriter = new FileWriter(file, true);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.newLine();
                    for (int i = 0; i < FIO.length; i++) {
                        bufferedWriter.write(FIO[i] + " ");
                    }
                    bufferedWriter.newLine();
                    bufferedWriter.write(birthday);
                    bufferedWriter.newLine();
                    bufferedWriter.write(numfon);
                    bufferedWriter.newLine(); 
                    bufferedWriter.write(gender);
                    bufferedWriter.newLine();
                    bufferedWriter.write(separation); 
                    bufferedWriter.newLine();
                    bufferedWriter.close();
                    System.out.println("\nДанные добавлены в файл: " + filename);
                }

                else { // Файл не существует, создаем новый файл c названием введенной фамилии и записываем данные в него
                    FileWriter fileWriter = new FileWriter(file);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    for (int i = 0; i < FIO.length; i++) {
                        bufferedWriter.write(FIO[i] + " ");
                    }
                    bufferedWriter.newLine();
                    bufferedWriter.write(birthday);
                    bufferedWriter.newLine();
                    bufferedWriter.write(numfon);
                    bufferedWriter.newLine();
                    bufferedWriter.write(gender);
                    bufferedWriter.newLine();
                    bufferedWriter.write(separation);
                    bufferedWriter.newLine();
                    bufferedWriter.close();
                    System.out.println("\nДанные добавлены в новый файл: " + filename);
                }

                System.out.println("\nЕсли хотите завершить программу введите букву N, иначе нажмите Enter");
                String decision = scanner.nextLine();
                if (decision.equals("N")) {
                    choice = false;

                }
            } catch (IOException e) {
                System.out.println("Ошибка при записи в файл: " + e.getMessage());
            }
            } 
        }
    }

    private static boolean convertToDate(String data) { // Создал отдельный метод, который проверяет введенную дату на соответствие с заданными критериями
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        try {
            format.parse(data);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}