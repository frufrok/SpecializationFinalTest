package HumanFriends;

import java.time.LocalDate;
import java.time.Period;

public class PackAnimal extends Animal {
    
    private int recoveryPeriod;

    public int getRecoveryPeriod() {
        return this.recoveryPeriod;
    }

    public void setRecoveryPeriod(int days) {
        if (days < 1) {
            System.out.println("Recovery period can't be less than 1 day.");
        }
        else {
            this.recoveryPeriod = days;
        }
    }

    private double stamina;
    
    public double getStamina() {
        
        this.stamina = Math.min(this.stamina + ((double) Period.between(lastWorkMade, LocalDate.now()).getDays() / recoveryPeriod), 1);
        return this.stamina;
    }

    private LocalDate lastWorkMade;

    public LocalDate getLastWorkMade() {
        return this.lastWorkMade;
    }

    public void work(Task task) {
        if (task.getRequiredStamina() > this.getStamina()) {
            System.out.printf("This task is too hard: required stamina is %.2f, available stamina is %.2f\n",
                    task.getRequiredStamina(), this.getStamina());
        }
        else {
            this.stamina -= task.getRequiredStamina();
        }
    }

    public PackAnimal(String name, String kind, LocalDate birthDate, int recoveryPeriod) {
        super(name, kind, birthDate);
        this.subType = "Pack animal";
        this.recoveryPeriod = recoveryPeriod > 0 ? recoveryPeriod : 1;
    }

    @Override
    public String toString() {
        return String.format("Pack animal %s the %s", this.name, this.kind);
    }
}
