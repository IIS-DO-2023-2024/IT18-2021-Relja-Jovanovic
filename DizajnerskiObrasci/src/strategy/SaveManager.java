package strategy;

public class SaveManager implements SaveStrategy {
    private SaveStrategy strategy;

    public SaveManager(SaveStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void save() {
        strategy.save();
    }
}