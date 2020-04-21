import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Print {

    public static void getNumberFromFile(File fileName, Map<String, List<String>> phonesAndEmails) throws Exception { // метод getNumberFromFile c параметрами: указание файла и map
        FileReader fr = new FileReader(fileName); // чтение файла fileName
        Scanner scan = new Scanner(fr); // сканирование файла
        EditTextFile.editFile(fileName);

        while (scan.hasNextLine()) { // пока scan имеет следующую строку будет выпоняться сканирование
            String line = scan.nextLine(); 
            Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(line); // метод Matcher выделяет из файла почтовые адреса
            List<String> emails = new ArrayList<>();// создание списка emails
            /*if (line.endsWith(")")) {
                break;
            }*/
            while (m.find()) { // пока Matcher будет находить адреса
                String email = m.group(); // они будут добавляться в переменную email которой присваивается группа всех найденных адресов
                emails.add(email); // в emails добавляе все email.
            }
            String firstMatch = emails.get(0); // создаем переменную и присваиваем значение взятое из emails ( первый эмейл)
            int indexOfFirstMatch = line.indexOf(firstMatch); // метод indexOf применяем к переменной line, с параметром firstMatch, то есть с первого эмейла присваиваем значение.
            String telNumberString = line.substring(0, indexOfFirstMatch); // присваиваем значение с начала line до indexOfFirstMatch (первого эмейла)

            TelNumber phone = new TelNumber();
            phone.setFirstNumber(telNumberString.substring(0, telNumberString.indexOf("(")));
            phone.setCityCode(telNumberString.substring(telNumberString.indexOf("("), telNumberString.indexOf(")") + 1));
            phone.setNumber(telNumberString.substring(telNumberString.indexOf(")") + 1));
            String modNumber = phone.getNumber().replaceAll("\\D+", "");
            String changedPhone = phone.getFirstNumber() + phone.getCityCode() + modNumber;
            //String extractedNumber = "+" + telNumberString.replaceAll("\\D+", ""); // приводим все номера к одному виду с помощью замены
            if (phonesAndEmails.containsKey(changedPhone) && !phonesAndEmails.containsValue(phonesAndEmails.get(changedPhone))) { // проверяем содержит ли map совпадение по ключу
                List<String> listEmail = phonesAndEmails.get(changedPhone); // получаем эмейлы по ключу и добавляем их в listEmail
                listEmail.addAll(emails); // добавляем в listEmail все emails
                phonesAndEmails.replace(changedPhone, listEmail); // обновляем map phoneAndEmails
            } else {
                phonesAndEmails.put(changedPhone, emails); // если совпадения нет, то кладём в map ключ и значение
            }

        }


        fr.close();
    }
}


