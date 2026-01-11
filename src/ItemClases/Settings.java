package ItemClases;

public class Settings {
    private int id;
    private String Settingname;
    private String SettingValue;


    public Settings(int id, String settingname, String settingValue) {
        this.id = id;
        Settingname = settingname;
        SettingValue = settingValue;
    }


    public int getId() {
        return id;
    }


    public String getSettingname() {
        return Settingname;
    }


    public String getSettingValue() {
        return SettingValue;
    }


    @Override
    public String toString() {
        return "Settings [id=" + id + ", Settingname=" + Settingname + ", SettingValue=" + SettingValue + "]";
    }

    
    
    

}
