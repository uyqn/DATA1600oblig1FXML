package person;

import exceptions.InvalidDateException;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public class Birthdate implements Serializable {
    private String birthdate;
    private int year, month, date;
    private static int[] daysInMonth =
            {31, 28, 31, 30, 31, 30, 31, 31, 30, 30, 30, 31};
    public static final String[] monthName =
            {"januar", "februar", "mars", "april",
                    "mai", "juni", "juli", "august",
                    "september", "oktober", "november", "desember"};
    private static final String PATTERN = "\\d{1,2}(\\.|[/]|-)?(\\s[A-Za-z]+|\\d{1,2})(\\.|[/]|-|\\s)?\\d{4}";

    private LocalDate currentDate = LocalDate.now();

    public Birthdate(String birthdate) throws InvalidDateException{
        setBirthdate(birthdate);
    }

    public Birthdate(int year, int month, int date) throws InvalidDateException {
        setYear(year);
        setMonth(month);
        setDate(date);
        this.birthdate = this.date + ". " + monthName[this.month-1]  + " " + this.year;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) throws InvalidDateException {
        if(year < 1900 || year > currentDate.getYear()){
            throw new InvalidDateException("Årstall må være imellom 1900 og " + currentDate.getYear());
        }
        if(year%4==0){
            daysInMonth[1] = 29;
        }
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) throws InvalidDateException {
        if(month < 1 || month > 12){
            throw new InvalidDateException("Måned må være mellom 01 og 12");
        }
        this.month = month;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) throws InvalidDateException {
        if(date < 1 || date > daysInMonth[month-1]){
            throw new InvalidDateException("Dato må være mellom 1 og " + daysInMonth[month-1]);
        }
        this.date = date;
    }

    public String getBirthdate() {
        return getDate() + ". " + monthName[getMonth() - 1] + " " + getYear();
    }

    public void setBirthdate(String birth) throws InvalidDateException{
        if(!birth.matches(PATTERN)|birth.isBlank()){
            throw new InvalidDateException("Ugyldig dato format oppgitt");
        }

        int monthIndex = -1;
        for(int i = 0 ; i < monthName.length ; i ++){
            if(birth.replaceAll("\\d|\\W","").equals(monthName[i])){
                monthIndex = i+1;
                setMonth(monthIndex);
            }
        }

        try{
            setYear(Integer.parseInt(birth.trim().substring(birth.length()-4)));
        }catch(NumberFormatException e){
            throw new InvalidDateException("Årstall må være et heltall på 4 siffer");
        }

        if(monthIndex == -1){
            throw new InvalidDateException("Måneden finnes ikke");
        }

        try{
            String dateString = birth.replaceAll("\\W|[A-Za-z]","");
            setDate(Integer.parseInt(dateString.substring(0,dateString.length()-4)));
        }catch (NumberFormatException e){
            throw new InvalidDateException("dato må være et heltall mellom 1 og 2 siffer");
        }

        this.birthdate = this.date + ". " + monthName[this.month-1] + " " + this.year;
    }

    public void setAge(int age){
        LocalDate currentDate = LocalDate.now();
        LocalDate birthday = LocalDate.of(currentDate.getYear(), getMonth(), getDate());

        int newYearOfBirth;
        if(birthday.isBefore(currentDate)) {
            newYearOfBirth = currentDate.getYear() - age;
        }
        else{
            newYearOfBirth = currentDate.getYear() - age - 1;
        }

        setYear(newYearOfBirth);
    }

    public int getAge(){
        LocalDate currentDate = LocalDate.now();
        LocalDate birtdate = LocalDate.of(
                this.year, this.month, this.date
        );

        return Period.between(birtdate,currentDate).getYears();
    }

    /*
    public void setBirthdate(String birth) throws InvalidDateException {
        String checkMonth = birth.replaceAll("\\.|[/]|\\s+|\\d", "");

        int monthNumber = 0;

        for(int i = 0 ; i<monthName.length ; i++){
            if(checkMonth.equals(monthName[i])){
                monthNumber = i+1;
            }
        }

        String finalResult = "";
        if(monthNumber<10){
            finalResult = "0" + monthNumber;
        }
        else{
            finalResult = "" + monthNumber;
        }

        String birthdate = birth.trim().substring(0,2) + finalResult + birth.trim().substring(birth.length()-4);

        if(!birthdate.matches("[0-9]{8}") || birthdate.isBlank()){
            throw new InvalidDateException("Formatet på input må være ddmmyyyy");
        }

        int year = Integer.parseInt(birthdate.substring(4,8));
        int month = Integer.parseInt(birthdate.substring(2,4));
        int date = Integer.parseInt(birthdate.substring(0,2));

        if(year < 1900 || year > currentDate.getYear()){
            throw new InvalidDateException("Årstall må være imellom 1900 og " + currentDate.getYear());
        }
        if(year%4==0){
            daysInMonth[1] = 29;
        }
        if(month < 1 || month > 12){
            throw new InvalidDateException("Måned må være mellom 01 og 12");
        }
        if(date < 1 || date > daysInMonth[month-1]){
            throw new InvalidDateException("Dato må være mellom 1 og " + daysInMonth[month-1]);
        }

        this.year = year;
        this.month = month;
        this.date = date;
        this.birthdate = this.date + ". " + monthName[this.month-1] + " " + this.year;
    }
     */

    @Override
    public String toString(){
        return this.birthdate;
    }
}
