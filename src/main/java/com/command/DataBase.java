package com.command;

import com.util.UserInputUtil;

public class DataBase implements Command {
    @Override
    public void execute() {
        int numberOfInvoices = Integer.parseInt(UserInputUtil.getUserInput("Print the number of invoices you want:"));

    }
}
