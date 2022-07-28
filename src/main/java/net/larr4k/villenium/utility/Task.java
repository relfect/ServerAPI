package net.larr4k.villenium.utility;

import lombok.Setter;
import net.larr4k.villenium.ApiPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;


import java.util.HashMap;


public abstract class Task extends BukkitRunnable {

    public final static HashMap<String, Task> tasks = new HashMap<>();

    @Setter
    private static JavaPlugin executorPlugin;

    private int periods, delay, period;
    private final String name;
    private final JavaPlugin plugin;

    public Task(JavaPlugin plugin, String name, int periods, int delayInMilliseconds, int periodInMilliseconds) {
        if (delayInMilliseconds != 0 && delayInMilliseconds < 50) {
            throw new IllegalArgumentException("Delay time must be 0 or not less than 50ms!");
        }
        if (periodInMilliseconds < 50) {
            throw new IllegalArgumentException("Period time must be not less than 50ms!");
        }
        this.name = name;
        this.plugin = plugin;
        if (periods == 0) {
            this.periods = -1;
        }
        this.periods = periods;
        this.delay = delayInMilliseconds / 50;
        this.period = periodInMilliseconds / 50;
        runTaskTimer(plugin, this.delay, this.period);
        tasks.put(name, this);
    }

    public abstract void onTick();

    @Override
    public void run() {
        if (this.periods > 0) {
            --this.periods;
        }
        onTick();
        if (this.periods == 0) {
            cancel();
        }
    }

    public int getPeriods() {
        return this.periods;
    }

    public int getDelayInTicks() {
        return this.delay;
    }

    public int getPeriodInTicks() {
        return this.period;
    }

    public String getName() {
        return this.name;
    }

    public JavaPlugin getPlugin() {
        return this.plugin;
    }

    public void setPeriods(int periods) {
        this.periods = periods;
    }

    @Override
    public void cancel() {
        super.cancel();
        tasks.remove(getName());
    }

    public static Task getTask(String name) {
        return tasks.get(name);
    }

    public static void schedule(Runnable r) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(getExecutorPlugin(), r);
    }

    public static void schedule(Runnable r, long delay) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(getExecutorPlugin(), r, delay);
    }

    public static void schedule(Runnable r, long delay, long period) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(getExecutorPlugin(), r, delay, period);
    }

    public static void runAsync(Runnable r) {
        Bukkit.getScheduler().runTaskAsynchronously(getExecutorPlugin(), r);
    }

    private static JavaPlugin getExecutorPlugin() {
        if (executorPlugin == null) {
            executorPlugin = ApiPlugin.getInstance();
        }
        return executorPlugin == null ? null : executorPlugin.isEnabled() ? executorPlugin : ApiPlugin.getInstance();
    }

}
