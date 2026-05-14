
package com.ayrinhaha.thread;

import com.ayrinhaha.service.JsonService;
import com.ayrinhaha.service.FinanceService;

public class AutoSaveThread extends Thread {

    private JsonService jsonService;
    private FinanceService financeService;

    public AutoSaveThread(JsonService jsonService,
            FinanceService financeService) {
        this.jsonService = jsonService;
        this.financeService = financeService;
    }

    @Override
    public void run() {

        while (true) {
            try {

                Thread.sleep(10000);

                jsonService.save(financeService);

                //System.out.println("[AutoSave Completed]");

            } catch (InterruptedException e) {
                System.out.println("[Thread Interrupted]");
                break;
            }
        }
    }
}