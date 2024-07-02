package org.example;

import java.rmi.Remote;
import java.rmi.RemoteException;

//定义公用接口
public interface Auction extends Remote {
    boolean placeBid(String itemID, double bid, String bidderName) throws RemoteException;
    String getCurrentBid(String itemID) throws RemoteException;
}