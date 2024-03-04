import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controller.StockControllerImpl;
import view.IView;
import view.IViewImpl;

public class Main {
  public static void main(String[] args) {

    IView view = new IViewImpl(System.out);
    StockControllerImpl a = new StockControllerImpl(view, System.in);
    a.go();
  }
}
