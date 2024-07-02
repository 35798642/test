package org.example;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class AuctionImpl extends UnicastRemoteObject implements Auction {
    private Map<String, Double> currentBids;
    private Map<String, String> highestBidder;
    private ReadWriteLock lock;

    protected AuctionImpl() throws RemoteException {
        super();
        currentBids = new HashMap<>();
        highestBidder = new HashMap<>();
        lock = new ReentrantReadWriteLock();
    }

    @Override
    public boolean placeBid(String itemID, double bid, String bidderName) throws RemoteException {
        Lock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            if (!currentBids.containsKey(itemID) || bid > currentBids.get(itemID)) {
                currentBids.put(itemID, bid);
                highestBidder.put(itemID, bidderName);
                return true;
            }
            return false;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public String getCurrentBid(String itemID) throws RemoteException {
        Lock readLock = lock.readLock();
        readLock.lock();
        try {
            if (currentBids.containsKey(itemID)) {
                double bid = currentBids.get(itemID);
                String bidder = highestBidder.get(itemID);
                return "Current bid for item " + itemID + ": $" + bid + " by " + bidder;
            } else {
                return "No bid for item " + itemID;
            }
        } finally {
            readLock.unlock();
        }
    }
}