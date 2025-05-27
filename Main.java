import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class Main extends JFrame implements ActionListener {
    private JTextField textField;
    private JButton button;
    private JLabel label;
    private ArrayList<Profile> profiles = new ArrayList<>();
    private ArrayList<String> currentAnswers = new ArrayList<>();
    private JPanel cpuPanel, proPanel, filterPanel;
    private People people = new People();
    private ArrayList<CPU> favorites = new ArrayList<>();


    private JCheckBox femaleBox, hobbiesBox, locationBox, hairBox, eyesBox, raceBox, williamBox;
    private JButton toggleFilterButton;
    private int qCount = 0;

    private String[] questions = {
        "What's your name?", "How old are you?", "What is your hobby?",
        "Where are you located?", "How tall are you?", "What is your gender?", "What is your preferred gender?", 
        "Tell us a little about yourself:", "Preferred eye color?",
        "Preferred hair color?", "Preferred race?"
    };

    public Main() {
        addDefaultCPUs();
        setupUI();
    }

    private void setupUI() {
        setTitle("Profile Survey");
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Font font = new Font("Segoe UI", Font.PLAIN, 16);

        // Top Bar
        proPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        add(proPanel, BorderLayout.NORTH);

        // Center panel for survey
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        label = new JLabel(questions[qCount]);
        label.setFont(font);
        centerPanel.add(label, gbc);

        textField = new JTextField(20);
        textField.setFont(font);
        gbc.gridy++;
        centerPanel.add(textField, gbc);

        button = new JButton("Submit");
        button.setFont(font);
        button.addActionListener(this);
        gbc.gridy++;
        centerPanel.add(button, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // South panel for CPU profiles
        cpuPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        JScrollPane scrollPane = new JScrollPane(cpuPanel);
        scrollPane.setPreferredSize(new Dimension(600, 250));
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));

        JLabel cpuLabel = new JLabel("Profiles");
        cpuLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.add(cpuLabel);

        toggleFilterButton = new JButton("Click to Filter");
        toggleFilterButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        toggleFilterButton.setVisible(false);
        toggleFilterButton.addActionListener(e -> filterPanel.setVisible(!filterPanel.isVisible()));
        headerPanel.add(toggleFilterButton);

        southPanel.add(headerPanel);
        southPanel.add(scrollPane);
        add(southPanel, BorderLayout.SOUTH);

        // Create a wrapper panel with BorderLayout for the top bar
proPanel = new JPanel(new BorderLayout());
add(proPanel, BorderLayout.NORTH);

// Create the left and right sub-panels
JPanel leftTopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
JPanel rightTopPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

// Add them to the main top bar
proPanel.add(leftTopPanel, BorderLayout.WEST);
proPanel.add(rightTopPanel, BorderLayout.EAST);

// Create the favorites button and add to left
JButton showFavoritesButton = new JButton("Show Favorites");
showFavoritesButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
showFavoritesButton.addActionListener(e -> showFavoritesDialog());
leftTopPanel.add(showFavoritesButton);



        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
    String input = textField.getText().trim();
    if (!input.isEmpty()) {
        // Age validation for qCount == 1
        if (qCount == 1) {
            try {
                int age = Integer.parseInt(input);
                if (age < 15) {
                    JOptionPane.showMessageDialog(this, "Too young!", "Age Warning", JOptionPane.WARNING_MESSAGE);
                    return; // Block progress
                } else if (age > 20) {
                    JOptionPane.showMessageDialog(this, "You're old!", "Age Warning", JOptionPane.WARNING_MESSAGE);
                    return; // Block progress
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for age.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return; // Block progress
            }
        }

        currentAnswers.add(input);
        qCount++;
        textField.setText("");
        if (qCount < questions.length) {
            label.setText(questions[qCount]);
        } else {
            finalizeProfile();
        }
    }
}



    private void finalizeProfile() 
    {
        filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        profiles.add(new Profile(currentAnswers));
        label.setText("Survey complete! Click a profile to view it:");
        textField.setVisible(false);
        button.setVisible(false);
        showProfileButtons();
        addProfileButton();
        createFilterPanel();
        add(filterPanel, BorderLayout.WEST);
        toggleFilterButton.setVisible(true);
    }

    

    private void createFilterPanel() {
        Font font = new Font("Segoe UI", Font.PLAIN, 16);

        femaleBox = new JCheckBox(currentAnswers.get(6));
        hobbiesBox = new JCheckBox("Likes " + currentAnswers.get(2));
        locationBox = new JCheckBox("Lives near " + currentAnswers.get(3));
        hairBox = new JCheckBox(currentAnswers.get(9) + " Hair");
        eyesBox = new JCheckBox(currentAnswers.get(8) + " Eyes");
        raceBox = new JCheckBox(currentAnswers.get(10));
        williamBox = new JCheckBox("Is William Stanton");

        JCheckBox[] boxes = {femaleBox, hobbiesBox, locationBox, hairBox, eyesBox, raceBox, williamBox};
        for (JCheckBox box : boxes) {
            box.setFont(font);
            box.addItemListener(e -> filterCPUs());
            filterPanel.add(box);
        }
    }

    private void showProfileButtons() {
        cpuPanel.removeAll();
        for (CPU cpu : people.getCPUs()) {
            cpuPanel.add(createCPUButton(cpu));
        }
        cpuPanel.revalidate();
        cpuPanel.repaint();
    }

    private JButton createCPUButton(CPU cpu) {
    ImageIcon icon = new ImageIcon(cpu.getImagePath());
    Image scaledImage = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
    JButton button = new JButton(cpu.getName(), new ImageIcon(scaledImage));
    button.setHorizontalTextPosition(SwingConstants.CENTER);
    button.setVerticalTextPosition(SwingConstants.BOTTOM);
    button.setPreferredSize(new Dimension(120, 120));

    button.addActionListener(e -> {
        String message = "Name: " + cpu.getName() + "\n" +
                         "Age: " + cpu.getAge() + "\n" +
                         "Hobbies: " + cpu.getHobbies() + "\n" +
                         "Location: " + cpu.getLocation() + "\n" +
                         "Height: " + cpu.getHeight() + "\n" +
                         "Gender: " + (cpu.isFemale() ? "Female" : "Male") + "\n" +
                         "Hair: " + cpu.getHairColor() + "\n" +
                         "Eyes: " + cpu.getEyeColor() + "\n" +
                         "Race: " + cpu.getRace() + "\n" +
                         "Bio: " + cpu.getBio();

        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea(message);
        textArea.setEditable(false);
        textArea.setBackground(null);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(textArea, BorderLayout.CENTER);

        ImageIcon heartIcon = new ImageIcon("images/heart.png");
        Image scaledHeart = heartIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        JButton heartButton = new JButton(new ImageIcon(scaledHeart));
        heartButton.setContentAreaFilled(false);
        heartButton.setBorderPainted(false);
        heartButton.setFocusPainted(false);
        heartButton.setToolTipText("Add to Favorites");

        heartButton.addActionListener(ev -> {
            if (!favorites.contains(cpu)) {
                favorites.add(cpu);
                JOptionPane.showMessageDialog(this, cpu.getName() + " has been added to your favorites!", "Favorites", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Already in favorites.", "Favorites", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        panel.add(heartButton, BorderLayout.SOUTH);
        JOptionPane.showMessageDialog(this, panel, "Profile Details", JOptionPane.PLAIN_MESSAGE);
    });

    return button;
}


    private void filterCPUs() {
    cpuPanel.removeAll();
    for (CPU cpu : people.getCPUs()) {
        boolean match = true;

        // Gender preference filter
        if (femaleBox.isSelected()) {
            String pref = currentAnswers.get(6).toLowerCase();
            if ((pref.contains("f") && !cpu.isFemale()) ||
                (pref.contains("m") && cpu.isFemale())) {
                match = false;
            }
        }

        // Hobby match
        if (hobbiesBox.isSelected()) {
            String hobbyPref = currentAnswers.get(2).toLowerCase();
            if (!cpu.getHobbies().toLowerCase().contains(hobbyPref)) {
                match = false;
            }
        }

        // Location match
        if (locationBox.isSelected()) {
            String locPref = currentAnswers.get(3).toLowerCase();
            if (!cpu.getLocation().toLowerCase().contains(locPref)) {
                match = false;
            }
        }

        // Hair color match
        if (hairBox.isSelected()) {
            String hairPref = currentAnswers.get(9).toLowerCase();
            if (!cpu.getHairColor().toLowerCase().contains(hairPref)) {
                match = false;
            }
        }

        // Eye color match
        if (eyesBox.isSelected()) {
            String eyePref = currentAnswers.get(8).toLowerCase();
            if (!cpu.getEyeColor().toLowerCase().contains(eyePref)) {
                match = false;
            }
        }

        // Race match
        if (raceBox.isSelected()) {
            String racePref = currentAnswers.get(10).toLowerCase();
            if (!cpu.getRace().toLowerCase().contains(racePref)) {
                match = false;
            }
        }

        // Special Easter egg
        if (williamBox.isSelected()) {
            if (!cpu.getName().toLowerCase().contains("william stanton")) {
                match = false;
            }
        }

        if (match) {
            cpuPanel.add(createCPUButton(cpu));
        }
    }

    cpuPanel.revalidate();
    cpuPanel.repaint();
}




    private void addProfileButton() {
        JButton profileBut = new JButton(currentAnswers.get(0));
        profileBut.setPreferredSize(new Dimension(60, 60));
        profileBut.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        profileBut.setContentAreaFilled(false);
        profileBut.setOpaque(true);
        profileBut.setBackground(Color.LIGHT_GRAY);
        profileBut.setBorderPainted(false);
        JPanel rightTopPanel = (JPanel) ((BorderLayout) proPanel.getLayout()).getLayoutComponent(BorderLayout.EAST);
rightTopPanel.removeAll();
rightTopPanel.add(profileBut);
rightTopPanel.revalidate();
rightTopPanel.repaint();

        proPanel.revalidate();
        proPanel.repaint();

        profileBut.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "Name: " + currentAnswers.get(0) + "\n" +
                "Age: " + currentAnswers.get(1) + "\n" +
                "Age: " + currentAnswers.get(1) + "\n" +
                "Hobbies: " + currentAnswers.get(2) + "\n" +
                "Location: " + currentAnswers.get(3) + "\n" +
                "Height: " + currentAnswers.get(4) + "\n" +
                "Gender: " + currentAnswers.get(5) + "\n" +
                "Bio: " + currentAnswers.get(7) + "\n");
        });
    }

    private void addDefaultCPUs() {
        people.addCPU(new CPU("Emily Jones", 21, "softball", "stanford", "5'5", true, "i hate kaden choi", "images/ej18.png", "brown", "brown", "white & asian"));
        people.addCPU(new CPU("Kiana Choi", 18, "dance", "san carlos", "4’11", true, "inner beauty is great but so is a good tan", "images/kiana.jpg", "brown", "brown", "asian"));
        people.addCPU(new CPU("Sofie Budman", 16, "basketball", "redwood shores", "5'6", true, "put down whatever", "images/sofie.jpg", "brown", "brown", "white & asian"));
        people.addCPU(new CPU("Chloe Smith", 18, "cheerleading", "near you", "5’8", true, "looking for hot females near me", "images/heart.png", "blonde", "blue", "white"));
        people.addCPU(new CPU("Michael Brown", 5, "watching cocomelon", "belmont", "4’2", false, "i stole my mommy's ipad", "images/heart.png", "brown", "green", "white"));
        people.addCPU(new CPU("Eileen Gu", 21, "skiing, modeling", "stanford", "5’9", true, "??", "images/eileengu.jpg", "brown", "brown", "white & asian"));
        people.addCPU(new CPU("Mikael Brunshteyn", 16, "basketball", "carlmont", "6'7", false, "I like em young", "images/mikael.jpg", "light brown", "brown", "white"));
        people.addCPU(new CPU("William Stanton", 17, "soccer", "carlmont", "6'2", false, "i love men", "images/will.png", "brown", "green", "white"));
        people.addCPU(new CPU("Ben Brown", 17, "skiing and golf and cubing", "Carlmont", "5'8", false, "looking for OS", "images/heart.png", "blond", "blue", "white"));
        people.addCPU(new CPU("Nico Golomb", 17, "Soccer and Reina", "San Carlos", "5'8", false, "Anyone looking for a good time!", "images/heart.png", "black", "brown", "white"));
        people.addCPU(new CPU("Kaia Baker-Malone", 16, "cheerleading and skiing", "San Carlos", "5'5", true, "I broke my back, looking for someone to break it again", "images/kaia.png", "brown", "brown", "white"));
        people.addCPU(new CPU("Helen Boone", 17, "Dance", "San Carlos", "5'6", true, "About to move not looking for anything serious", "images/heart.png", "blonde", "blue", "white"));
        people.addCPU(new CPU("Sophia Klar", 17, "lacrosse, jiujitsu", "San Carlos", "5'5", true, "I can wrestle on the mat and in bed", "images/sophiak.jpg", "brown", "brown", "white"));
        people.addCPU(new CPU("Jazlynn Chuo", 18, "Cello, baking and photography", "Redwood Shores", "5'3", true, "I'm good with my fingers", "images/jazlynn.jpg", "brown", "brown", "asian"));
        people.addCPU(new CPU("Suni Lee", 22, "gymnastics and winning the Olympics", "Minnesota", "5'0", true, "Gymnastics isn't just my sport, it's my art in motion", "images/suni.jpg", "black", "brown", "asian"));
        people.addCPU(new CPU("Sydney Agudong", 24, "Actress from Lilo and Stitch", "Hawaii", "5'4", true, "You can be the Lilo to my Stitch", "images/sydau.jpg", "brown", "brown", "Hawaiian"));
        people.addCPU(new CPU("Sara Ho", 17, "Chatting up Aiden Paz", "San Mateo", "5'2", true, "xx", "images/heart.png", "brown", "brown", "asian"));
    }
    private void showFavoritesDialog() 
    {
    if (favorites.isEmpty()) {
        JOptionPane.showMessageDialog(this, "You have no favorites yet.", "Favorites", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    StringBuilder sb = new StringBuilder("Favorite Profiles:\n\n");
    for (CPU cpu : favorites) {
        sb.append(cpu.getName()).append("\n");
    }

    JOptionPane.showMessageDialog(this, sb.toString(), "Favorites", JOptionPane.INFORMATION_MESSAGE);
    }


    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(Main::new);
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
    private String name, hobbies, location, height, bio, imagePath, hairColor, eyeColor, race;
    private int age;
    private boolean isFemale;

    public CPU(String name, int age, String hobbies, String location, String height, boolean isFemale,
               String bio, String imagePath, String hairColor, String eyeColor, String race) {
        this.name = name;
        this.age = age;
        this.hobbies = hobbies;
        this.location = location;
        this.height = height;
        this.isFemale = isFemale;
        this.bio = bio;
        this.imagePath = imagePath;
        this.hairColor = hairColor;
        this.eyeColor = eyeColor;
        this.race = race;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getHobbies() { return hobbies; }
    public String getLocation() { return location; }
    public String getHeight() { return height; }
    public boolean isFemale() { return isFemale; }
    public String getBio() { return bio; }
    public String getImagePath() { return imagePath; }
    public String getHairColor() { return hairColor; }
    public String getEyeColor() { return eyeColor; }
    public String getRace() { return race; }
}

class People {
    private ArrayList<CPU> cpuList = new ArrayList<>();
    public void addCPU(CPU c) { cpuList.add(c); }
    public ArrayList<CPU> getCPUs() { return cpuList; }
}
