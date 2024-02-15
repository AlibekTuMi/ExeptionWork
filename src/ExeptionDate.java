import java.time.format.DateTimeParseException;

public class ExeptionDate extends DateTimeParseException{

    public ExeptionDate(String message, CharSequence parsedData, int errorIndex) {
        super(message, parsedData, errorIndex);
    }
    
}
