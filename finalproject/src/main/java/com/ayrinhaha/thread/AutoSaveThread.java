

package com.ayrinhaha.thread;

import com.ayrinhaha.service.JsonService;

public class AutoSaveThread extends Thread {

    private JsonService jsonService;

    public AutoSaveThread(JsonService jsonService) {

        this.jsonService = jsonService;
    }

    @Override
    public void run() {

        while (true) {

            try {

                // TODO:
                // Automatically save system data

                Thread.sleep(10000);

            } catch (InterruptedException e) {

                // TODO:
                // Handle thread interruption

            }
        }
    }
}
