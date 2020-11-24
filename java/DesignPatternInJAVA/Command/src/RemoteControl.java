public class RemoteControl {
    private Command onC,offC;

    public RemoteControl(Command onC, Command offC) {
        this.onC = onC;
        this.offC = offC;
    }

    public void trunOn(){
        onC.excute();
    }
    public void trunOff(){
        offC.excute();
    }
}
