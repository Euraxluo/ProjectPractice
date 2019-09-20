public class CarDoBHandler extends CarHandler {
    @Override
    public void CarHandler() {
        System.out.println("DoB");
        if(this.carHandler != null){
            this.carHandler.CarHandler();
        }
    }
}
