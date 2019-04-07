package info.sandroalmeida.angularjsexample.error;

import info.sandroalmeida.angularjsexample.dto.UserDTO;

/**
 * Created by sandro on 07/04/19
 */
public class CustomErrortype extends UserDTO {

    private String errorMessage;

    public CustomErrortype(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
