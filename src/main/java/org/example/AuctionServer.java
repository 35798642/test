package org.example;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AuctionServer {
    public static void main(String[] args) {
        try {
            //创建 AuctionImpl 实例；
            Auction auction = new AuctionImpl();
            //启动 RMI 注册表；
            Registry registry = LocateRegistry.createRegistry(1099);
            //该实例绑定到名为 "Auction" 的远程对象注册中；
            registry.rebind("Auction", auction);
            //打印服务器状态；
            System.out.println("Auction server is ready.");
        } catch (Exception e) {
            System.err.println("Auction server exception:");
            e.printStackTrace();
        }
    }
}