public class Facade {
    private PackageA packageA;
    private PackageB packageB;
    private PackageC packageC;

    public Facade() {
        this.packageA = new PackageA();
        this.packageB = new PackageB();
        this.packageC = new PackageC();
    }
    public void funAB(){
        this.packageA.function();
        this.packageB.function();
    }
    public void funBC(){
        this.packageB.function();
        this.packageC.function();
    }
}
