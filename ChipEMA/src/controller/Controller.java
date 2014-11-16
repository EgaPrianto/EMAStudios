/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import interfaces.Drawable;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.Canvas;
import world.Board;
import world.Player;
import world.Stash;
import world.Status;

/**
 *
 * @author i13047
 */
public class Controller implements Runnable, KeyListener {

    private Canvas canvas;
    private Player player1;
    private Board papan;
    private Status stat;
    private Stash inventory;
    private boolean isFinish;
    private Thread thread;
    private Drawable[] drawable;

    public Controller(Canvas c) {
        isFinish = false;
        this.papan = new Board(1);
        stat = papan.getStats();
        player1 = new Player(6, 8);
        inventory = papan.getInventory();
        canvas = c;
        thread = new Thread(this);
        this.drawable = new Drawable[2];
        drawable[0] = this.player1;
        drawable[1] = this.papan;
    }

    public void start() {
        isFinish = false;
        thread.start();
    }

    public void stop() {
        this.isFinish = true;
        try {
            this.thread.join();
        } catch (InterruptedException ex) {
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (papan.getMap()[player1.getLocationX()][player1.getLocationY() - 1].isSteppable()) {
                player1.move(Player.ATAS);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (papan.getMap()[player1.getLocationX()][player1.getLocationY() + 1].isSteppable()) {
                player1.move(Player.BAWAH);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (papan.getMap()[player1.getLocationX() + 1][player1.getLocationY()].isSteppable()) {
                player1.move(Player.KANAN);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (papan.getMap()[player1.getLocationX() - 1][player1.getLocationY()].isSteppable()) {
                player1.move(Player.KIRI);
            }
        }
    }

    @Override
    public void run() {
        while (!player1.isIsDead() || !isFinish) {
            if (papan.getMap()[player1.getLocationX()][player1.getLocationY()].getNama().equals("Fire")) {
                player1.setIsDead(true);
            }
            if (papan.getMap()[player1.getLocationX()][player1.getLocationY()].getNama().equals("Finish")) {
                isFinish = true;
            }
            if (papan.getMap()[player1.getLocationX()][player1.getLocationY()].getNama().equals("IC")) {
                stat.decreaseChip(1);
            }
            canvas.repaint();
            try {
                Thread.sleep(16);
            } catch (InterruptedException ex) {
            }
        }
    }

    public Drawable[] getAllDrawable() {
        return drawable;
    }
}
