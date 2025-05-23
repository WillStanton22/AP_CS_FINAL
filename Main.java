import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Main extends JFrame implements ActionListener {
    private JTextField textField;
    private JButton button;
    private JLabel label;
    private ArrayList<Profile> profiles;
    private ArrayList<String> currentAnswers;
    private JPanel cpuPanel;
    private People people;
    private JPanel proPanel;
    private JTextArea profileDetailsArea;

    private String[] questions = {
        "What's your name?",
        "What grade are you in?",
        "What is your hobby?",
        "Where are you located?",
        "How tall are you?",
        "What is your gender?",
        "Tell us a little about yourself:"
    };

    private int qCount = 0;

    public Main() {
        profiles = new ArrayList<>();
        currentAnswers = new ArrayList<>();
        people = new People();
        addDefaultCPUs();

        setTitle("Profile Survey");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        Font font = new Font("Segoe UI", Font.PLAIN, 16);

        // Survey input panel (center)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        label = new JLabel(questions[qCount]);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(font);

        textField = new JTextField();
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        textField.setFont(font);

        button = new JButton("Submit");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(font);
        button.addActionListener(this);

        centerPanel.add(label);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(textField);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(button);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Top panel with profile button
        proPanel = new JPanel();
        proPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.add(proPanel, BorderLayout.EAST);

        // CPU grid panel with scroll
        cpuPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        JScrollPane cpuScrollPane = new JScrollPane(cpuPanel);
        cpuScrollPane.setPreferredSize(new Dimension(600, 250));

        JLabel profileHeader = new JLabel("Profiles");
        profileHeader.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        profileHeader.setAlignmentX(Component.CENTER_ALIGNMENT);

        profileDetailsArea = new JTextArea(7, 50);
        profileDetailsArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        profileDetailsArea.setEditable(false);
        profileDetailsArea.setLineWrap(true);
        profileDetailsArea.setWrapStyleWord(true);
        profileDetailsArea.setBorder(BorderFactory.createTitledBorder("Profile Details"));

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        southPanel.add(profileHeader);
        southPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        southPanel.add(cpuScrollPane);
        southPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        southPanel.add(profileDetailsArea);

        mainPanel.add(topBar, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            String answer = textField.getText().trim();
            if (!answer.isEmpty()) {
                currentAnswers.add(answer);
                qCount++;
                textField.setText("");
            }

            if (qCount < questions.length) {
                label.setText(questions[qCount]);
            } else {
                Profile newProfile = new Profile(currentAnswers);
                profiles.add(newProfile);
                textField.setEnabled(false);
                textField.setVisible(false);
                button.setEnabled(false);
                button.setVisible(false);
                label.setText("Survey complete! Click a profile to view it:");
                showProfileButtons();
                addProfileButton();
            }
        }
    }

    private void showProfileButtons() {
        for (CPU cpu : people.getCPUs()) {
            ImageIcon icon = new ImageIcon(cpu.getImagePath());
            Image scaledImg = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaledImg);

            JButton cpuButton = new JButton(cpu.getName(), icon);
            cpuButton.setHorizontalTextPosition(SwingConstants.CENTER);
            cpuButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            cpuButton.setPreferredSize(new Dimension(120, 120));

            cpuButton.addActionListener(e -> {
                profileDetailsArea.setText(
                    "Name: " + cpu.getName() + "\n" +
                    "Age: " + cpu.getAge() + "\n" +
                    "Hobbies: " + cpu.getHobbies() + "\n" +
                    "Location: " + cpu.getLocation() + "\n" +
                    "Height: " + cpu.getHeight() + "\n" +
                    "Gender: " + (cpu.isFemale() ? "Female" : "Male") + "\n" +
                    "Bio: " + cpu.getBio()
                );
            });

            cpuPanel.add(cpuButton);
        }

        cpuPanel.revalidate();
        cpuPanel.repaint();
    }

    private void addProfileButton() {
        JButton profileBut = new JButton(currentAnswers.get(0));
        profileBut.setPreferredSize(new Dimension(60, 60));
        profileBut.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        profileBut.setContentAreaFilled(false);
        profileBut.setFocusPainted(false);
        profileBut.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        profileBut.setOpaque(true);
        profileBut.setBackground(Color.LIGHT_GRAY);
        profileBut.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        profileBut.setBorderPainted(false);

        proPanel.removeAll();
        proPanel.add(profileBut);
        proPanel.revalidate();
        proPanel.repaint();

        profileBut.addActionListener(e -> {
            profileDetailsArea.setText(
                "Name: " + currentAnswers.get(0) + "\n" +
                "Grade: " + currentAnswers.get(1) + "\n" +
                "Hobbies: " + currentAnswers.get(2) + "\n" +
                "Location: " + currentAnswers.get(3) + "\n" +
                "Height: " + currentAnswers.get(4) + "\n" +
                "Gender: " + currentAnswers.get(5) + "\n" +
                "Bio: " + currentAnswers.get(6)
            );
        });
    }

    private void addDefaultCPUs() {
        people.addCPU(new CPU("Emily Jones", 21, "softball", "stanford", "5'5", true, "i hate kaden choi", "images/will!.webp"));
        people.addCPU(new CPU("Kiana Choi", 18, "dance", "san carlos", "4’11", true, "??", "images/will!.webp"));
        people.addCPU(new CPU("Sofie Budman", 16, "?", "redwood shores", "??", true, "??", "images/will!.webp"));
        people.addCPU(new CPU("??", 18, "cheerleading", "near you", "5’8", true, "??", "images/will!.webp"));
        people.addCPU(new CPU("??", 5, "watching cocomelon", "belmont", "4’2", false, "i stole my mommy's ipad", "images/will!.webp"));
        people.addCPU(new CPU("Eileen Gu", 21, "skiing, modeling", "stanford", "5’9", true, "??", "images/will!.webp"));
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new Main());
    }
}

class Profile {
    private ArrayList<String> answers;
    public Profile(ArrayList<String> answers) {
        this.answers = new ArrayList<>(answers);
    }
    public ArrayList<String> getAnswers() {
        return answers;
    }
}

class CPU {
    private String name;
    private int age;
    private String hobbies;
    private String location;
    private String height;
    private boolean isFemale;
    private String bio;
    private String imagePath;

    public CPU(String name, int age, String hobbies, String location, String height, boolean isFemale, String bio, String imagePath) {
        this.name = name;
        this.age = age;
        this.hobbies = hobbies;
        this.location = location;
        this.height = height;
        this.isFemale = isFemale;
        this.bio = bio;
        this.imagePath = imagePath;
    }

    public String getImagePath() { return imagePath; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getHobbies() { return hobbies; }
    public String getHeight() { return height; }
    public String getLocation() { return location; }
    public boolean isFemale() { return isFemale; }
    public String getBio() { return bio; }
}

class People {
    private ArrayList<CPU> cpu;

    public People() {
        cpu = new ArrayList<>();
    }

    public void addCPU(CPU c) {
        cpu.add(c);
    }

    public ArrayList<CPU> getCPUs() {
        return cpu;
    }
}
