	package strategy;

import mvc.DrawingModel;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SaveDrawing implements SaveStrategy {

    private DrawingModel model;

    public SaveDrawing(DrawingModel model) {
        this.model = model;
    }

    @Override
    public void save() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Bin file", "bin"));
        fileChooser.setDialogTitle("Save drawing");

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.endsWith(".bin")) {
                filePath += ".bin";
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
                oos.writeObject(model.getShapes());
                JOptionPane.showMessageDialog(null, "Uspešno sačuvan crtež!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Greška pri čuvanju crteža!", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
}