package be.thomasmore.locateit.classes;

public class UserSettings {
    private String firstName;
    private boolean allowPrivacy = false;
    private String mail;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public boolean isAllowPrivacy() {
        return allowPrivacy;
    }

    public void setAllowPrivacy(boolean allowPrivacy) {
        this.allowPrivacy = allowPrivacy;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
