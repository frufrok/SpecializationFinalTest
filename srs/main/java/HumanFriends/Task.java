package HumanFriends;

public class Task {
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String description;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    private double requiredStamina;

    public double getRequiredStamina() {
        return this.requiredStamina;
    }

    public void setRequiredStamina(double requiredStamina) {
        if (requiredStamina < 0) {
            System.out.println("Task can't have required stamina below zero.");
        } else {
            this.requiredStamina = requiredStamina;
        }
    }
    
    public Task(String name, String description, double requiredStamina) {
        this.name = name;
        this.description = description;
        this.requiredStamina = requiredStamina > 0 ? requiredStamina : 0;
    }

    @Override
    public String toString() {
        return String.format("Task: %s (%s)", this.name, this.description);
    }
    
}
