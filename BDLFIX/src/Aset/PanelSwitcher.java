/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Aset;

/**
 *
 * @author M. HASNAN AL ABIYYU
 */
import java.util.Stack;

import javax.swing.JFrame;

import java.awt.Container;

public class PanelSwitcher {
  private final Stack<Container> panels;
  private final JFrame frame;

  public PanelSwitcher(JFrame frame) {
    this.frame = frame;
    panels = new Stack<>();
  }

  public void switchPanelTo(Container panel) {
    panels.push(panel);
    frame.setContentPane(panel);
    frame.pack();
    frame.setLocationRelativeTo(null);
  }

  public void back() {
    panels.pop();
    frame.setContentPane(panels.lastElement());
    frame.pack();
    frame.setLocationRelativeTo(null);
  }
}
