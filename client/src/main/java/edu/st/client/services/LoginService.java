package edu.st.client.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import edu.st.client.Main;

import java.util.Random;

import javafx.scene.image.Image;

public class LoginService {
  public static void readBackgrounds(ArrayList<Image> backgrounds) {
    String filePath = Main.class.getResource("data/backgrounds.db").getPath();
    try {
      Scanner input = new Scanner(new File(filePath));
      while (input.hasNextLine()) {
        String line = input.nextLine();
        backgrounds.add(new Image(new FileInputStream(line)));
      }
      input.close();
    } catch (FileNotFoundException e) {
      System.out.println("File not found: " + filePath);
    }
  }

  public static Image getRandomBackground(ArrayList<Image> backgrounds) {
    return backgrounds.get(new Random().nextInt(backgrounds.size()));
  }

}
