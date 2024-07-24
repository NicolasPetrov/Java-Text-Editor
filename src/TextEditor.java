import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class TextEditor extends JFrame {
    private JTextPane textPane;
    private JFileChooser fileChooser;
    private JComboBox<String> fontComboBox;
    private JComboBox<Integer> sizeComboBox;

    private static final String[] STANDARD_FONTS = {
            "Arial", "Helvetica", "sans-serif", "Arial Black", "Gadget",
            "Georgia", "serif", "Courier New", "Courier", "monospace",
            "Times New Roman", "Times"
    };

    public TextEditor() {
        setTitle("Java Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        textPane = new JTextPane();
        fileChooser = new JFileChooser();

        JScrollPane scrollPane = new JScrollPane(textPane);

        JMenuBar menuBar = createMenuBar();

        add(scrollPane, BorderLayout.CENTER);
        setJMenuBar(menuBar);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem exportMenuItem = new JMenuItem("Export");

        openMenuItem.addActionListener(e -> openFile());
        saveMenuItem.addActionListener(e -> saveFile());
        exportMenuItem.addActionListener(e -> exportFile());

        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(exportMenuItem);

        JMenu editMenu = new JMenu("Edit");
        JMenuItem insertImageMenuItem = new JMenuItem("Insert Image");

        insertImageMenuItem.addActionListener(e -> insertImage());

        editMenu.add(insertImageMenuItem);

        JMenu formatMenu = new JMenu("Format");

        JMenu fontMenu = new JMenu("Font");
        fontComboBox = new JComboBox<>(STANDARD_FONTS);
        fontComboBox.addActionListener(e -> updateFont());
        fontMenu.add(fontComboBox);

        JMenu sizeMenu = new JMenu("Size");
        Integer[] sizes = new Integer[32];
        for (int i = 0; i < 32; i++) {
            sizes[i] = 4 + i * 4;
        }
        sizeComboBox = new JComboBox<>(sizes);
        sizeComboBox.setSelectedItem(12);
        sizeComboBox.addActionListener(e -> updateSize());
        sizeMenu.add(sizeComboBox);

        formatMenu.add(fontMenu);
        formatMenu.add(sizeMenu);

        JMenuItem boldMenuItem = new JMenuItem("Bold");
        JMenuItem italicMenuItem = new JMenuItem("Italic");

        boldMenuItem.addActionListener(e -> toggleBold());
        italicMenuItem.addActionListener(e -> toggleItalic());

        formatMenu.add(boldMenuItem);
        formatMenu.add(italicMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);

        return menuBar;
    }

    private void openFile() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String format = getFileExtension(file);

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                if ("md".equalsIgnoreCase(format)) {
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    applyMarkdownStyles(sb.toString());
                } else {
                    textPane.read(reader, null);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile() {
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                textPane.write(writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void exportFile() {
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String format = getFileExtension(file);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                if ("md".equalsIgnoreCase(format)) {
                    writer.write(toMarkdown(textPane.getStyledDocument()));
                } else if ("html".equalsIgnoreCase(format)) {
                    writer.write(toHTML(textPane.getStyledDocument()));
                } else {
                    textPane.write(writer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertImage() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                textPane.insertIcon(new ImageIcon(file.getPath()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void toggleBold() {
        StyledDocument doc = textPane.getStyledDocument();
        int start = textPane.getSelectionStart();
        int end = textPane.getSelectionEnd();
        if (start != end) {
            Style style = textPane.addStyle("Bold", null);
            StyleConstants.setBold(style, !StyleConstants.isBold(doc.getCharacterElement(start).getAttributes()));
            doc.setCharacterAttributes(start, end - start, style, false);
        }
    }

    private void toggleItalic() {
        StyledDocument doc = textPane.getStyledDocument();
        int start = textPane.getSelectionStart();
        int end = textPane.getSelectionEnd();
        if (start != end) {
            Style style = textPane.addStyle("Italic", null);
            StyleConstants.setItalic(style, !StyleConstants.isItalic(doc.getCharacterElement(start).getAttributes()));
            doc.setCharacterAttributes(start, end - start, style, false);
        }
    }

    private void updateFont() {
        StyledDocument doc = textPane.getStyledDocument();
        int start = textPane.getSelectionStart();
        int end = textPane.getSelectionEnd();
        if (start != end) {
            String selectedFont = (String) fontComboBox.getSelectedItem();

            Style style = textPane.addStyle("Font", null);
            StyleConstants.setFontFamily(style, selectedFont);

            doc.setCharacterAttributes(start, end - start, style, false);
        }
    }

    private void updateSize() {
        StyledDocument doc = textPane.getStyledDocument();
        int start = textPane.getSelectionStart();
        int end = textPane.getSelectionEnd();
        if (start != end) {
            int selectedSize = (Integer) sizeComboBox.getSelectedItem();

            Style style = textPane.addStyle("Size", null);
            StyleConstants.setFontSize(style, selectedSize);

            doc.setCharacterAttributes(start, end - start, style, false);
        }
    }

    private String toMarkdown(StyledDocument doc) {
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

    private String toHTML(StyledDocument doc) {
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

    private String getFileExtension(File file) {
        String name = file.getName();
        int index = name.lastIndexOf('.');
        return index == -1 ? "" : name.substring(index + 1);
    }

    private void applyMarkdownStyles(String text) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TextEditor editor = new TextEditor();
            editor.setVisible(true);
        });
    }
}