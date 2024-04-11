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


  /**
   * this method displays an error message.
   * @param error The error message to be displayed.
   */
  void displayError(String error);

  /**
   * this method prints a message.
   * @param message The message to be printed.
   */
  void print(String message);



}
