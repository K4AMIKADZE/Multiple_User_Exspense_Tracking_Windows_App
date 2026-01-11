package ItemClases;

public class AdminChartThird {

    private String name;
    private int times = 1;


    public AdminChartThird(String name, int times) {
        this.name = name;
        this.times = times;
    }


    public String getName() {
        return name;
    }


    public int getTimes() {
        return times;
    }


    public void add(){
        times++;
    }

    

}
