import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Scanner;

public class Main7 {
    public static void main(String[] args) throws IOEx, FileNotFoundException {
        boolean choice = true;
        while (choice) {
            Scanner scanner = new Scanner(System.in);
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
                } else if (words[i].contains(".")) {
                    birthday = words[i];
                } else if (words[i].matches("\\d+")) {
                    numfon = words[i];
                } else {
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
             * Дата 01.01.1900 ограничительная по возрасту
             */
            int postYear = 1900;
            if (Integer.parseInt(newBD[2]) < postYear) { // Проверка на то что пользователь ввел дату рождения не раньше
                                                         // чем 1900 год
                System.out.println("\nДата раньше 1 января 1900 года.");
                throw new ExeptionDate("Ошибка ввода даты", birthday, 0);
            } else if (Integer.parseInt(newBD[2]) > year
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
