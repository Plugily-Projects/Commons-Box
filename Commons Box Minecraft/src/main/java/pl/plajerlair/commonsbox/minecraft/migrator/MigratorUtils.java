package pl.plajerlair.commonsbox.minecraft.migrator;

import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Plajer
 * <p>
 * Created at 09.03.2019
 * <p>
 * Manage file strings without YamlConfiguration
 * and without losing comments
 * @deprecated api subject to removal
 */
@Deprecated
public class MigratorUtils {

  /**
   * Remove specified line from file
   *
   * @param file         file to use
   * @param lineToRemove line to remove
   */
  public static void removeLineFromFile(File file, String lineToRemove) {
    try {
      List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
      List<String> updatedLines = lines.stream().filter(s -> !s.contains(lineToRemove)).collect(Collectors.toList());
      Files.write(file.toPath(), updatedLines, StandardCharsets.UTF_8);
    } catch(IOException e) {
      e.printStackTrace();
      Bukkit.getLogger().warning("[CommonsBox] Something went horribly wrong with migration! Please contact Plugily Projects!");
    }
  }

  /**
   * Insert text after specified string
   *
   * @param file   file to use
   * @param search string to check
   * @param text   text to insert after search string
   */
  public static void insertAfterLine(File file, String search, String text) {
    try {
      int i = 1;
      List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
      for(String line : lines) {
        if(line.contains(search)) {
          lines.add(i, text);
          Files.write(file.toPath(), lines, StandardCharsets.UTF_8);
          break;
        }
        i++;
      }
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds new lines to file
   *
   * @param file     file to use
   * @param newLines new lines to add
   */
  public static void addNewLines(File file, String newLines) {
    try {
      FileWriter fw = new FileWriter(file.getPath(), true);
      fw.write(newLines);
      fw.close();
    } catch(IOException e) {
      e.printStackTrace();
      Bukkit.getLogger().warning("[CommonsBox] Something went horribly wrong with migration! Please contact Plugily Projects!");
    }
  }

}
