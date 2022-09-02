package kz.attractor.java.homework44;

public class RegistrationModel {
    private String descr = " ";
    private boolean isStart = false;
    private boolean status = false;

    public RegistrationModel(boolean start, boolean status) {
        isStart = start;
        this.status = status;
        descr = this.status ? "Удачная регистрация" : "регистрация не удалась";
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

    public boolean getIsStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }
}
