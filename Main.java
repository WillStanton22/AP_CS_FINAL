import java.util.ArrayList;
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

    JLabel imageWill =new JLabel (new ImageIcon(getClass().getResource("will.png")));

    private String[] questions = 
    {
        "What's your name?", 
        "How old are you?",
        "What is your hobby?",
        "Where are you located?",
        "How tall are you?",
        "What is your gender?",
        "Tell us a little about yourself:"
    };

    private int qCount = 0;

    public Main() 
    {
        profiles = new ArrayList<Profile>();
        currentAnswers = new ArrayList<String>();
        people = new People();
        addDefaultCPUs();
        

        setTitle("Input Example");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        label = new JLabel(questions[qCount]);
        label.setBounds(50, 20, 400, 20);
        textField = new JTextField();
        textField.setBounds(50, 50, 250, 20);
        button = new JButton("Submit");
        button.setBounds(50, 100, 100, 30);

        add(label);
        add(textField);
        add(button);
        button.addActionListener(this);

        // CPU Button Panel
        cpuPanel = new JPanel();
        cpuPanel.setBounds(50, 150, 400, 200);
        cpuPanel.setLayout(new BoxLayout(cpuPanel, BoxLayout.Y_AXIS));
        add(cpuPanel);

        // Profile Panel
        proPanel = new JPanel();
        proPanel.setBounds(400, 15, 450, 100);
        proPanel.setLayout(new BoxLayout(proPanel, BoxLayout.Y_AXIS));
        add(proPanel);

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
                textField.setEnabled(false);  // Disable input
                textField.setVisible(false);
                button.setEnabled(false);     // Disable submit
                button.setVisible(false);
                label.setText("Survey complete! Click a profile to view it:");
                showProfileButtons();         // Show CPU buttons
                addProfileButton();
            }
        }
    }

    private void showProfileButtons() {
        for (CPU cpu : people.getCPUs()) {
            JButton cpuButton = new JButton(cpu.getName());
            cpuPanel.add(cpuButton);
            cpuButton.setVisible(true);
            cpuButton.setEnabled(true);
            cpuButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null,
                        "Name: " + cpu.getName() + "\n" +
                        "Age: " + cpu.getAge() + "\n" +
                        "Hobbies: " + cpu.getHobbies() + "\n" +
                        "Location: " + cpu.getLocation() + "\n" +
                        "Height: " + cpu.getHeight() + "\n" +
                        "Gender: " + (cpu.isFemale() ? "Female" : "Male") + "\n" +
                        "Bio: " + cpu.getBio()
                    );
                }
            });
            
        }
        cpuPanel.revalidate();
        cpuPanel.repaint();
    }
    private void addProfileButton(){
        JButton profileBut = new JButton(currentAnswers.get(0));
        JPanel imagePanel = new JPanel();
        add(imagePanel);
        JLabel profileInfo = new JLabel("Name: " + currentAnswers.get(0)+ "\n" +
                        "Age: " + currentAnswers.get(1) + "\n" +
                        "Hobbies: " + currentAnswers.get(2) + "\n" +
                        "Location: " + currentAnswers.get(3) + "\n" +
                        "Height: " + currentAnswers.get(4) + "\n" +
                        "Gender: " + currentAnswers.get(5) + "\n" +
                        "Bio: " + currentAnswers.get(6));
        imagePanel.setLayout(new BorderLayout());
        imageWill.setVisible(true);
        imagePanel.add(imageWill, BorderLayout.WEST);
        imagePanel.add(profileInfo, BorderLayout.EAST);
        proPanel.add(profileBut);
        profileBut.setVisible(true);
        profileBut.setEnabled(true);
        profileBut.setBounds(425, 0, 100, 30);
        profileBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, imagePanel, "Profile",JOptionPane.DEFAULT_OPTION);    
                }
            });
    }

    private void addDefaultCPUs() {
        CPU ej18= new CPU("Emily Jones", 21, "softball", "stanford", "5'5", true, "i hate kaden choi");
        CPU kChoi = new CPU ("Kiana Choi", 18, "Dance", "Carlmont", "4'11", true, "Its not the size of the dog in the fight it's the size of the fight in the dog");
        people.addCPU(ej18);
        people.addCPU(kChoi);
    }

    public static void main(String[] args) {
        new Main();
    }
}




class Profile 
{
    private ArrayList<String> answers;
    public Profile(ArrayList<String> answers) 
    {
        this.answers = new ArrayList<String>(answers);
    }
    public ArrayList<String> getAnswers() 
    {
        return answers;
    }
}
class CPU 
{
	private String name;
	private int age;
	private String hobbies;
	private String location;
	private String height;
	private boolean isFemale;
	private String bio;
	public CPU  (String name, int age, String hobbies, String location, String height, boolean isFemale, String bio)
	{
		this.name = name;
		this.age = age;
		this.hobbies = hobbies;
		this.location = location;
		this.height = height;
		this.isFemale = isFemale;
		this.bio = bio;
	}
    public String getName()
    {
        return name;
    }
    public int getAge()
    {
        return age;
    }
    public String getHobbies()
    {
        return hobbies;
    }
    public String getHeight()
    {
        return height;
    }
    public String getLocation()
    {
        return location;
    }
    public boolean isFemale()
    {
        return isFemale;
    }
    public String getBio()
    {
        return bio;
    }
    private void showProfileButtons(People people, JPanel buttonPanel) 
    {
    for (CPU cpu : people.getCPUs()) 
    {
        JButton cpuButton = new JButton(cpu.getName()); // use a getter
        cpuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                    "Name: " + cpu.getName() + "\n" +
                    "Age: " + cpu.getAge() + "\n" +
                    "Hobbies: " + cpu.getHobbies() + "\n" +
                    "Location: " + cpu.getLocation() + "\n" +
                    "Height: " + cpu.getHeight() + "\n" +
                    "Gender: " + (cpu.isFemale() ? "Female" : "Male") + "\n" +
                    "Bio: " + cpu.getBio()
                );
            }
        });
        buttonPanel.add(cpuButton);
    }
    
}
}
class People 
{
    private ArrayList<CPU> cpu;

    public People() {
        cpu = new ArrayList<CPU>();
    }

    public void addCPU(CPU c) {
        cpu.add(c);
    }

    public ArrayList<CPU> getCPUs() {
        return cpu;
    }

}