
public class Main {
	public static void main(String[] args) {
		FruitFactory af = new AppleFactory();
		Fruit apple = af.getFruit();
		apple.get();

		FruitFactory bf = new BananaFactory();
		Fruit banana = bf.getFruit();
		banana.get();
		
		//PearFactory
		FruitFactory pf= new PearFactory();
		Fruit pear = pf.getFruit();
		pear.get();
	}
}
