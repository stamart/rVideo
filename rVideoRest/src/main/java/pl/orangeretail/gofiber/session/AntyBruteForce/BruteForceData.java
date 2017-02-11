/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.orangeretail.gofiber.session.AntyBruteForce;

import java.util.Date;

/**
 *
 * @author staszek
 */
public class BruteForceData {
    
    private int bruteForceCount = 0;
    private Date bruteForceBlockTime;
    private boolean bruteForceLock = false;

    public int getBruteForceCount() {
        return bruteForceCount;
    }

    public void setBruteForceCount(int bruteForceCount) {
        this.bruteForceCount = bruteForceCount;
    }

    public Date getBruteForceBlockTime() {
        return bruteForceBlockTime;
    }

    public void setBruteForceBlockTime(Date bruteForceBlockTime) {
        this.bruteForceBlockTime = bruteForceBlockTime;
    }

    public boolean isBruteForceLock() {
        return bruteForceLock;
    }

    public void setBruteForceLock(boolean bruteForceLock) {
        this.bruteForceLock = bruteForceLock;
    }
    
}
