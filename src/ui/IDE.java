package ui;

import modules.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class IDE {
    private JPanel container;
    private JPanel inputPanel;
    private JPanel outputPanel;
    private JTextArea inputArea;

    public IDE() {
        setupInputPanel();
        setupOutpuPanel();
        setupContainer();
        setupFrame();
    }

    private void setupFrame() {
        JFrame frame = new JFrame("poster 1");
        frame.setSize(new Dimension(1000, 700));

        frame.add(new JScrollPane(container,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void setupInputPanel() {
        inputPanel = new JPanel();
//        inputPanel.setBackground(Color.green);
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputArea = new JTextArea(30, 40);
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        JButton executeButton = new JButton("Execute");
        executeButton.setPreferredSize(new Dimension(300, 50));
        executeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                execute();
            }
        } );

        JScrollPane scrollPane = new JScrollPane(inputArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        inputPanel.add(scrollPane);
        inputPanel.add(executeButton);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        executeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void setupOutpuPanel() {
        outputPanel = new JPanel();
        outputPanel.setBackground(Color.white);
    }

    private void setupContainer() {
        container = new JPanel();
        container.setLayout(new BorderLayout());

        container.setLocation(0, 0);
        container.add(inputPanel, BorderLayout.LINE_START);
        container.add(outputPanel, BorderLayout.CENTER);
    }

    private PosterTokenize getTokenizer(String inputText) {
        List<String> fixedLiterals = Arrays.asList("PANEL", "WIDTH:", "HEIGHT:", "VAR:", "FILEPATH:", "POS:", "IMG", "TXT",
                "CONTENT:", "FONTSIZE:", ";", "TIMES:" , "DIR:", "SHF:", "IF", "THEN" , "HOR", "VER", "LOOP", "ELSE", "TCOLOR:");
        return new PosterTokenize(inputText, fixedLiterals);
    }

    private List<PosterObject> getPosterObjectsFromParser(PosterTokenize tokenizer) {
        PosterParse parser = new PosterParse(tokenizer);
        parser.run();
        return parser.getParsedList();
    }
    private void validateTheObjects(List<PosterObject> objects) {
        PosterValidate validator = new PosterValidate(objects);
        validator.validate();
    }

    public void execute() {
        try {
            String inputText = inputArea.getText();
            // tokenize
            PosterTokenize tokenizer = getTokenizer(inputText);
            // parse
            List<PosterObject> posterObjects = getPosterObjectsFromParser(tokenizer);
            // validate
            validateTheObjects(posterObjects);
            // evaluate
            PosterEvaluate eva = new PosterEvaluate(posterObjects);
            eva.evaluate();

            outputPanel.removeAll();
            outputPanel.add(eva.canvas);

            outputPanel.revalidate();
            outputPanel.repaint();
        } catch (UserException e) {
            JOptionPane.showMessageDialog(outputPanel,e.getMessage());
        }
    }
}
