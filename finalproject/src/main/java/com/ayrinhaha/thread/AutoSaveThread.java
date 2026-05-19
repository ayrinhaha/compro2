package com.ayrinhaha.thread;

import com.ayrinhaha.model.UserAccount;
import com.ayrinhaha.service.AccountService;
import com.ayrinhaha.service.FinanceService;

/**
 * Background autosave thread responsible for:
 * - syncing finance state to user account
 * - automatically saving accounts.json
 *
 * This thread runs continuously every 10 seconds.
 *
 * @author ayrinhaha
 */
public class AutoSaveThread extends Thread {

    private final AccountService accountService;

    private final FinanceService financeService;

    private final UserAccount currentUser;

    /**
     * Constructs the autosave thread.
     *
     * @param accountService handles account persistence
     * @param financeService runtime finance operations
     * @param currentUser    currently logged-in user
     */
    public AutoSaveThread(
            AccountService accountService,
            FinanceService financeService,
            UserAccount currentUser) {

        this.accountService = accountService;
        this.financeService = financeService;
        this.currentUser = currentUser;
    }

    /**
     * Continuously autosaves account data every 10 seconds.
     */
    @Override
    public void run() {

        while (true) {

            try {

                Thread.sleep(10000);

                currentUser.budget = financeService.getBudget();

                currentUser.expenses = financeService.getExpensesCopy();

                currentUser.tuition = financeService.getTuition();

                accountService.sync(currentUser);

            } catch (InterruptedException e) {
                break;
            }
        }
    }
}