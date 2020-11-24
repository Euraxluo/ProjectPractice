public class CarDoCHandler extends CarHandler {
    @Override
    public void CarHandler() {
        System.out.println("DoC");
        if(this.carHandler != null){
            this.carHandler.CarHandler();
        }
    }
}
