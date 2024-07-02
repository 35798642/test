package org.example;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class AuctionClient {
    //添加注释
    //添加注释b2
    public static void main(String[] args) {
        String host = (args.length < 1) ? null : args[0];
        try {
            //连接到 RMI 注册表，与服务端建立连接；
            Registry registry = LocateRegistry.getRegistry(host);
            //查找并获取 "Auction" 远程对象的存根（stub）;
            Auction auction = (Auction) registry.lookup("Auction");
            Scanner scanner = new Scanner(System.in);
            //通过命令行界面，提供用户界面，用户可以查看物品的当前出价并进行出价操作
            while (true) {
                System.out.println("Enter command (placeBid/getCurrentBid/exit):");
                String command = scanner.nextLine();
                if (command.equalsIgnoreCase("exit")) {
                    break;
                } else if (command.equalsIgnoreCase("placeBid")) {
                    System.out.println("Enter itemID, bid amount, and bidder name separated by spaces:");
                    String input = scanner.nextLine();
                    String[] parts = input.split(" ");
                    if (parts.length != 3) {
                        System.out.println("Invalid input format. Please enter itemID, bid amount, and bidder name separated by spaces.");
                        continue;
                    }
                    try {
                        boolean success = auction.placeBid(parts[0], Double.parseDouble(parts[1]), parts[2]);
                        System.out.println("Bid placed: " + (success ? "Success" : "Failed"));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid bid amount. Please enter a valid number.");
                    } catch (Exception e) {
                        System.out.println("An error occurred while placing the bid: " + e.getMessage());
                    }
                } else if (command.equalsIgnoreCase("getCurrentBid")) {
                    System.out.println("Enter itemID:");
                    String itemID = scanner.nextLine();
                    try {
                        System.out.println(auction.getCurrentBid(itemID));
                    } catch (Exception e) {
                        System.out.println("An error occurred while getting the current bid: " + e.getMessage());
                    }
                } else {
                    System.out.println("Unknown command. Please enter 'placeBid', 'getCurrentBid', or 'exit'.");
                }
            }
            scanner.close();
        } catch (Exception e) {
            System.err.println("Auction client exception:");
            e.printStackTrace();
        }
    }
}
