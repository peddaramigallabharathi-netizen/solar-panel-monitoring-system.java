import java.util.Random;
import java.util.Scanner;

public class SolarPanelMonitoring {

    private static final double SAMPLE_TIME_HOURS = 1.0 / 60.0;
    private static final int TOTAL_READINGS = 10;

    public static double calculatePower(double voltage, double current) {
        return voltage * current;
    }

    public static double calculateEnergy(double power, double timeHours) {
        return (power / 1000.0) * timeHours;
    }

    public static String getPanelStatus(
            double voltage,
            double current,
            double temperature) {

        if (temperature > 70) {
            return "HIGH TEMPERATURE";
        }

        if (voltage < 10 || current < 0.2) {
            return "LOW OUTPUT";
        }

        return "NORMAL";
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("==============================================");
        System.out.println("       SOLAR PANEL MONITORING SYSTEM");
        System.out.println("==============================================");

        System.out.print("Enter solar panel rated voltage (V): ");
        double ratedVoltage = scanner.nextDouble();

        System.out.print("Enter solar panel rated current (A): ");
        double ratedCurrent = scanner.nextDouble();

        if (ratedVoltage <= 0 || ratedCurrent <= 0) {
            System.out.println("Error: Values must be greater than zero.");
            scanner.close();
            return;
        }

        double totalEnergy = 0;

        System.out.println("\nStarting monitoring...\n");

        System.out.printf(
                "%-10s %-12s %-12s %-12s %-12s %-20s%n",
                "Reading",
                "Voltage(V)",
                "Current(A)",
                "Power(W)",
                "Temp(C)",
                "Status"
        );

        System.out.println(
                "---------------------------------------------------------------------"
        );

        for (int i = 1; i <= TOTAL_READINGS; i++) {

            // Simulated sensor readings
            double voltage =
                    ratedVoltage * (0.90 + random.nextDouble() * 0.10);

            double current =
                    ratedCurrent * (0.70 + random.nextDouble() * 0.30);

            double temperature =
                    25 + random.nextDouble() * 40;

            double power = calculatePower(
                    voltage,
                    current
            );

            double energy = calculateEnergy(
                    power,
                    SAMPLE_TIME_HOURS
            );

            totalEnergy += energy;

            String status = getPanelStatus(
                    voltage,
                    current,
                    temperature
            );

            System.out.printf(
                    "%-10d %-12.2f %-12.2f %-12.2f %-12.2f %-20s%n",
                    i,
                    voltage,
                    current,
                    power,
                    temperature,
                    status
            );

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        double estimatedDailyEnergy = totalEnergy * 6;

        System.out.println("\n==============================================");
        System.out.println("             MONITORING SUMMARY");
        System.out.println("==============================================");

        System.out.printf(
                "Energy during monitoring : %.4f kWh%n",
                totalEnergy
        );

        System.out.printf(
                "Estimated daily energy   : %.4f kWh%n",
                estimatedDailyEnergy
        );

        System.out.println("==============================================");

        scanner.close();
    }
}
