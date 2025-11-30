package FleetManagementSystem.simulation;

public class Highway {
    private static int highwayDistance = 0;
    private static volatile boolean useSynchronization = false;

    public static void addDistance(int distance) {
        if (useSynchronization) {
            addDistanceSynchronized(distance);
        } else {
            addDistanceUnsynchronized(distance);
        }
    }

    private static synchronized void addDistanceSynchronized(int distance) {
            highwayDistance += distance;
    }


    private static void addDistanceUnsynchronized(int distance) {
            int current = highwayDistance;
            try {
                Thread.sleep(5); // Small delay to force race condition
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            highwayDistance = current + distance;
    }

    public static void setUseSynchronization(boolean enable) {
        useSynchronization = enable;
        System.out.println("Highway Synchronization set to: " + enable);
    }

    public static int getDistance() {
        return highwayDistance;
    }
    
    public static void reset() {
        highwayDistance = 0;
    }
}