package the.one.aqtour.bean;

public class QSPSeries {

    public String name;
    public String url;

    public QSPSeries() {
    }

    public QSPSeries(String name, String url) {
        this.name = name;
        this.url = url;
    }

    @Override
    public String toString() {
        return "QSPSeries{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
