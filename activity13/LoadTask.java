package com.ayrinhaha.thread;

import com.ayrinhaha.MultiThreadedListApp;

/**
 * Runnable task responsible for loading data periodically.
 *
 * @author ayrinhaha
 */
public class LoadTask implements Runnable {

    private MultiThreadedListApp app;

    public LoadTask(MultiThreadedListApp app) {
        this.app = app;
    }

    @Override
    public void run() {
        while (true) {
            app.readFile();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
