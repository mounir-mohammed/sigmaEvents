package ma.sig.events.service.dto;

import java.io.Serializable;
import ma.sig.events.domain.PrintingCentre;
import ma.sig.events.domain.User;

/**
 * A DTO representing a user, with only the public attributes.
 */
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String login;

    private PrintingCentre printingCentre;

    public UserDTO() {
        // Empty constructor needed for Jackson.
    }

    public UserDTO(User user) {
        this.id = user.getId();
        // Customize it here if you need, or not, firstName/lastName/etc
        this.login = user.getLogin();
        this.printingCentre = user.getPrintingCentre();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public PrintingCentre getPrintingCentre() {
        return printingCentre;
    }

    public void setPrintingCentre(PrintingCentre printingCentre) {
        this.printingCentre = printingCentre;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDTO{" +
            "id='" + id + '\'' +
            ", login='" + login + '\'' +
            ", printingCentre='" + printingCentre + '\'' +
            "}";
    }
}
