package com.ayrinhaha.thread;

import com.ayrinhaha.service.FinanceService;
import com.ayrinhaha.service.JsonService;

public class AutoSaveThread extends Thread {

    private JsonService jsonService;
    private FinanceService financeService;

    public AutoSaveThread(
            JsonService jsonService,
            FinanceService financeService) {

        this.jsonService = jsonService;
        this.financeService = financeService;
    }

    @Override
    public void run() {

        while (true) {

            try {

                // autosave every 10 seconds
                Thread.sleep(10000);

                // export finance data as JSON string
                String data = financeService.exportData();

                // save to finance.json
                jsonService.save(data);

                //System.out.println(
                        //"[AutoSave Completed]");

            } catch (InterruptedException e) {

                System.out.println(
                        "[Thread Interrupted]");

                break;
            }
        }
    }
}