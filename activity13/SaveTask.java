
package com.ayrinhaha.thread;

import com.ayrinhaha.MultiThreadedListApp;

/**
 * Runnable task responsible for saving data periodically.
 *
 * @author ayrinhaha
 */
public class SaveTask implements Runnable {

    private MultiThreadedListApp app;

    public SaveTask(MultiThreadedListApp app) {
        this.app = app;
    }

    @Override
    public void run() {
        while (true) {
            app.saveToDisk();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
