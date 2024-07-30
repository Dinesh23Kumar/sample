package main;

import devices.Device;
import factory.DeviceFactory;
import proxy.DeviceProxy;
import scheduler.Schedule;
import scheduler.Scheduler;
import trigger.Trigger;
import trigger.TriggerManager;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class SmartHomeSystem {
    private Map<Integer, Device> devices = new HashMap<>();
    private Scheduler scheduler = new Scheduler();
    private TriggerManager triggerManager = new TriggerManager();

    public void addDevice(String type, int id) {
        Device device = DeviceFactory.createDevice(type, id);
        devices.put(id, new DeviceProxy(device));
    }

    public void turnOn(int id) {
        Device device = devices.get(id);
        if (device != null) {
            device.turnOn();
        }
    }

    public void turnOff(int id) {
        Device device = devices.get(id);
        if (device != null) {
            device.turnOff();
        }
    }

    public void setSchedule(int id, String time, String command) {
        Device device = devices.get(id);
        if (device != null) {
            scheduler.addSchedule(new Schedule(device, LocalTime.parse(time), command));
        }
    }

    public void addTrigger(String condition, String action) {
        triggerManager.addTrigger(new Trigger(condition, action));
    }

    public void checkScheduler() {
        scheduler.checkSchedules();
    }

    public void checkTriggers() {
        for (Device device : devices.values()) {
            triggerManager.checkTriggers(device);
        }
    }

    public void printStatus() {
        for (Device device : devices.values()) {
            System.out.println(device.getStatus());
        }
    }
}
