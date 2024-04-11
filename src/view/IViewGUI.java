package view;


import controller.Features;

/**
 * This IViewGUI interface defines methods for interacting with.
 * graphical user interface (GUI) components.
 */
public interface IViewGUI {

  /**
   * this method add feature sets Features instance for GUI and connects view with controller.
   * by defining all the call back functions on based of action to perform when used in view.
   * @param features is Features instance to be set.
   *
   */
  void addFeatures(Features features);



}
