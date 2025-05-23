import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.BorderLayout;

class Main extends JFrame implements ActionListener {
    private JTextField textField;
    private JButton button;
    private JLabel label;
    private ArrayList<Profile> profiles;
    private ArrayList<String> currentAnswers;
    private JPanel cpuPanel;
    private People people;
    private JPanel proPanel;

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
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        Font font = new Font("Segoe UI", Font.PLAIN, 16);

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

        mainPanel.add(label);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(textField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(button);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        cpuPanel = new JPanel();
        cpuPanel.setLayout(new BoxLayout(cpuPanel, BoxLayout.Y_AXIS));
        cpuPanel.setBorder(BorderFactory.createTitledBorder("Profiles"));
        cpuPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cpuPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        proPanel = new JPanel();
        proPanel.setLayout(new BoxLayout(proPanel, BoxLayout.Y_AXIS));
        proPanel.setBorder(BorderFactory.createTitledBorder("Your Profile"));
        proPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        proPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        mainPanel.add(cpuPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(proPanel);

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

            cpuButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(null,
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
        proPanel.add(profileBut);
        profileBut.setVisible(true);
        profileBut.setEnabled(true);
        profileBut.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,
                "Name: " + currentAnswers.get(0) + "\n" +
                "Age: " + currentAnswers.get(1) + "\n" +
                "Hobbies: " + currentAnswers.get(2) + "\n" +
                "Location: " + currentAnswers.get(3) + "\n" +
                "Height: " + currentAnswers.get(4) + "\n" +
                "Gender: " + currentAnswers.get(5) + "\n" +
                "Bio: " + currentAnswers.get(6)
            );
        });
    }

    private void addDefaultCPUs() {
        CPU ej18 = new CPU("Emily Jones", 21, "softball", "stanford", "5'5", true, "i hate kaden choi", "will!.webp");
        people.addCPU(ej18);
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

    public String getImagePath() {
        return imagePath;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public String getHobbies() {
        return hobbies;
    }
    public String getHeight() {
        return height;
    }
    public String getLocation() {
        return location;
    }
    public boolean isFemale() {
        return isFemale;
    }
    public String getBio() {
        return bio;
    }
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
