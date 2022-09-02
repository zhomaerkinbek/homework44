package kz.attractor.java.homework44;

public class LoginModel {
    private String descr = " ";
    private boolean status = false;

    public LoginModel(boolean status) {
        this.status = status;
        descr = this.status ? "авторизоваться не удалось, неверный идентификатор или пароль" : " ";
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

}
