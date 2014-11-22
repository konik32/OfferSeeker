package pl.edu.agh.offerseeker.collecting;


import org.jsoup.nodes.Document;
import pl.edu.agh.offerseeker.WebPagePuller;
import pl.edu.agh.offerseeker.preprocessing.ContentInSeparateLines;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public class GUICollector extends JFrame {
    private JComboBox _contentBox;
    private JTextField _url;
    private JButton _collect;
    private Collector _collector;

    public GUICollector() {
        _collector = new Collector(System.getProperty("user.home") + "/offer_seeker");

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
                    _contentBox.addItem("<<BRAK OFERTY>>");
                    for (String line : lines)
                        _contentBox.addItem(line);

                } catch (Exception x) {
                    JOptionPane.showMessageDialog(null, "URL is not accessible! +\n\t" + x.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }

                _contentBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int offerIndex = _contentBox.getSelectedIndex();
                        String offer = _contentBox.getSelectedItem().toString();
                        if (!offer.equals("<<BRAK OFERTY>>")) {
                            ComboBoxModel model = _contentBox.getModel();
                            // bez <<BRAK OFERTY>>
                            for (int i = 1; i < model.getSize(); i++) {
                                if (i != offerIndex) {
                                    try {
                                        _collector.collect(model.getElementAt(i).toString());
                                    } catch (IOException e1) {
                                        JOptionPane.showMessageDialog(null, "Cannot access ComboBox model!", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                } else {
                                    try {
                                        _collector.collect(model.getElementAt(i).toString(), UUID.randomUUID());
                                    } catch (IOException e1) {
                                        JOptionPane.showMessageDialog(null, "Cannot access ComboBox model!", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }
                        }

                        _url.setText("");
                        remove(_contentBox);
                        validate();
                    }
                });

                add(_contentBox, BorderLayout.CENTER);
                validate();
            }
        });

        _collect = new JButton("COLLECT");
        //add(_collect, BorderLayout.SOUTH);

        setSize(400, 400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new GUICollector();
    }
}
