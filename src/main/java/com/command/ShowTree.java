package com.command;

import com.model.Auto;
import com.model.VehicleType;
import com.repository.BinaryTree;
import com.util.VehicleFactory;

public class ShowTree implements Command {
    @Override
    public void execute() {
        System.out.println("This is example of binary tree");
        BinaryTree tree = new BinaryTree();
        for (int i = 0; i < 6; i++) {
            final Auto auto = (Auto) VehicleFactory.build(VehicleType.AUTO);
            tree.add(auto);
        }
        System.out.println("As tree:");
        tree.printToConsole();
        System.out.println("Sum of left = " + tree.sumLeft());
        System.out.println("Sum of right = " + tree.sumRight());
    }
}
