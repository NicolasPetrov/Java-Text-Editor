# Java Text Editor

**JavaTextEditor** is a desktop text editing application with a graphical user interface, built to create, edit, and format text documents. It supports basic file operations such as opening, saving, and exporting files, along with text formatting options like font selection, size adjustment, bold, and italic styles. Additionally, it allows users to insert images into documents, making it a versatile tool for simple rich-text editing.

<img width="871" alt="screen" src="https://github.com/user-attachments/assets/42b95c76-f5db-4148-ba99-82f62f1c1e19" />

This project demonstrates the development of a Java-based text editor using the Swing library for its GUI. It leverages JTextPane for rich-text editing, integrates file I/O operations, and provides a customizable formatting experience, serving as a practical example for learning desktop application development with text manipulation capabilities.

#### Features

- **File Operations**: Open existing text files, save changes, and export documents in plain text, Markdown (.md), or HTML (.html) formats.
- **Text Formatting**: Adjust font family (e.g., Arial, Times New Roman), font size (4 to 128), and toggle bold or italic styles for selected text.
- **Image Insertion**: Embed images into the document from local files.
- **Rich-Text Editing**: Utilizes JTextPane for styled text with real-time formatting updates.
- **Menu-Driven Interface**: Access all features through a structured menu bar (File, Edit, Format).

#### Technical Details

- **Language**: Java (version not specified, compatible with JDK 8 and above)

**Main Libraries:**

- `javax.swing`: For building the GUI (JFrame, JTextPane, JComboBox, etc.).
- `javax.swing.text`: For styled text manipulation (StyledDocument, StyleConstants).
- `java.awt`: For layout and event handling.
- `java.io`: For file input/output operations.

#### Key Components:

- **Text Editing**: Uses JTextPane with StyledDocument for dynamic formatting of selected text (font, size, bold, italic).
- **File Handling**: Opens files with basic Markdown support (though parsing is incomplete), saves via textPane.write(), and exports with format-specific logic.
- **Formatting Controls**: Dropdown menus (JComboBox) for font and size selection, with immediate application to selected text.
- **Image Support**: Inserts images as icons into the document using insertIcon.
- **Event-Driven Design**: Menu actions handled via ActionListener for seamless user interaction.
- **Multithreading**: Launches GUI in SwingUtilities.invokeLater for thread-safe Swing operation.
