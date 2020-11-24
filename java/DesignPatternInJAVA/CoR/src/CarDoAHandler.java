public class CarDoAHandler extends CarHandler {
    @Override
    public void CarHandler() {
        System.out.println("DoA");
        if(this.carHandler != null){
            this.carHandler.CarHandler();
        }
    }
}
