package pl.edu.agh.offerseeker.collecting;


import org.jsoup.nodes.Document;
import pl.edu.agh.offerseeker.WebPagePuller;
import pl.edu.agh.offerseeker.preprocessing.ContentInSeparateLines;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class GUICollector extends JFrame {
    private JComboBox _contentBox;
    private JTextField _url;
    private JButton _collect;

    public GUICollector() {
        _url = new JTextField();
        add(_url, BorderLayout.NORTH);

        _url.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (_contentBox != null)
                    remove(_contentBox);
                _contentBox = new JComboBox();
                try {
                    WebPagePuller puller = new WebPagePuller();
                    Document page = puller.pullPage(new URL(_url.getText()));
                    String[] lines = new ContentInSeparateLines(page).preprocess().split("\n");
                    for (String line : lines)
                        _contentBox.addItem(line);

                } catch (Exception x) {
                    JOptionPane.showMessageDialog(null, "URL is not accessible! +\n\t" + x.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                add(_contentBox, BorderLayout.CENTER);
                validate();
            }
        });

        _collect = new JButton("COLLECT");
        add(_collect, BorderLayout.SOUTH);

        setSize(400, 400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}

class Program {
    public static void main(String[] args) {
        new GUICollector();
    }
}