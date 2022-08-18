package main;

public class RandomString {

    // function to generate a random string of length n
    public static String getAlphaNumericString(int n) {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFG";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            if(i%2 == 0)
            {
                sb.append(AlphaNumericString
                        .charAt(index));
            }
            else
            {
                sb.append(" ");
            }

        }

        return sb.toString();
    }
    public static Character getAlphaNumericChar(int n) {
        n = 1;

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFG";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            if(i%2 == 0)
            {
                sb.append(AlphaNumericString
                        .charAt(index));
            }
            else
            {
                sb.append(" ");
            }

        }
        sb.toString();
        Character Finalresult = new Character(sb.charAt(0));
        return Finalresult;
    }
}