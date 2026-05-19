// CLASS HARDDISK
class Harddisk {

    private String brand;
    private int capacity;

    public Harddisk(String brand, int capacity) {
        this.brand = brand;
        this.capacity = capacity;
    }

    public void displayHarddisk() {
        System.out.println("Brand Harddisk : " + brand);
        System.out.println("Capacity : " + capacity + " GB");
    }
}

// CLASS MONITOR
class Monitor {

    private String monitorName;
    private int size;

    public Monitor(String monitorName, int size) {
        this.monitorName = monitorName;
        this.size = size;
    }

    public void displayMonitor() {
        System.out.println("Monitor : " + monitorName);
        System.out.println("Size : " + size + " inch");
    }
}

// CLASS KOMPUTERSERVER
class KomputerServer {

    private String serverName;
    // COMPOSITION
    // Harddisk dibuat di dalam Server,kalo server rusak, harddisk ikut hilang
    private Harddisk harddisk;
    // AGGREGATION
    // Monitor bisa berdiri sendiri, jadi nya bisa dilepas dari server
    private Monitor[] monitors;

    // Constructor
    public KomputerServer(String serverName,
                           String harddiskBrand,
                           int harddiskCapacity,
                           Monitor[] monitors) {

        this.serverName = serverName;
        this.harddisk = new Harddisk(harddiskBrand, harddiskCapacity);
        this.monitors = monitors;
    }
    public void displayServer() {

        System.out.println("Server Name : " + serverName);

        System.out.println("\n=== Harddisk ===");
        harddisk.displayHarddisk();

        System.out.println("\n=== Monitor List ===");

        for (Monitor monitor : monitors) {
            monitor.displayMonitor();
            System.out.println();
        }
    }
}
// MAIN CLASS
public class Main {

    public static void main(String[] args) {
        Monitor m1 = new Monitor("Samsung", 24);
        Monitor m2 = new Monitor("AOC", 27);

        Monitor[] monitorList = {m1, m2};
        KomputerServer server =
                new KomputerServer(
                        "Server Database",
                        "Seagate",
                        1000,
                        monitorList
                );

        server.displayServer();
    }
}