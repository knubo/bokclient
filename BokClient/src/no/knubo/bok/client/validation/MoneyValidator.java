package no.knubo.bok.client.validation;

public class MoneyValidator extends ValidatorBase {

    public MoneyValidator(String error) {
        super(error);
    }

    @Override
    protected boolean validate(Validateable val) {
        try {
            String money = val.getText();
            
            /* Handled by mandatory validator */
            if(money.length() == 0) {
            	return true;
            }

            int comma = money.indexOf('.');

            if (comma == -1) {
                comma = money.indexOf(',');
            }

            if (comma == -1) {
                return Integer.parseInt(money) >= 0;
            }

            int big = Integer.parseInt(money.substring(0, comma));

            if (big < 0) {
                return false;
            }

            String smallstring = money.substring(comma + 1);

            if (smallstring.length() > 2) {
                return false;
            }

            return Integer.parseInt(smallstring) >= 0;

        } catch (NumberFormatException e) {
            return false;
        }
    }

}
