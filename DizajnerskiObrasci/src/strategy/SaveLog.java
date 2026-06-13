package strategy;

import drawing.FrmDrawing;
import java.io.BufferedWriter;
import java.io.FileWriter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SaveLog implements SaveStrategy {

    private FrmDrawing frame;

    public SaveLog(FrmDrawing frame) {
        this.frame = frame;
    }

    @Override
    public void save() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text file", "txt"));
        fileChooser.setDialogTitle("Save log");

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.endsWith(".txt")) {
                filePath += ".txt";
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(frame.getTxtLog().getText());
                JOptionPane.showMessageDialog(null, "Uspešno sačuvan log!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Greška pri čuvanju loga!", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
}