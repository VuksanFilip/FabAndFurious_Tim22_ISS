package rs.ac.uns.ftn.informatika.jpa;

public class ValidateData {

    public ValidateData(){}

    public boolean isNumericOrNegativeLong(String string) {
        long longValue;

        if(string == null || string.equals("")) {
            System.out.println("String cannot be parsed, it is null or empty.");
            return false;
        }

        try {
            longValue = Long.parseLong(string);
            if(longValue < 0){
                System.out.println("String cannot be negative");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Input String cannot be parsed to Long.");
        }
        return false;
    }
}
