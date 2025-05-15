import java.util.ArrayList;
import javax.swing.JFrame;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

class Main extends JFrame implements ActionListener {
    private JTextField textField;
    private JButton button;
    private JLabel label;
    private ArrayList<Profile> profiles;
    private ArrayList<String> currentAnswers;
    

    private String[] questions = 
    {
        "What's your name?", 
        "What grade are you in?"
    };

    private int qCount = 0;

    public Main() 
    {
        profiles = new ArrayList<Profile>();
        currentAnswers = new ArrayList<String>();
        
        setTitle("Input Example");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        label = new JLabel(questions[qCount]);
        label.setBounds(50, 20, 300, 20);
        textField = new JTextField();
        textField.setBounds(50, 50, 250, 20);
        button = new JButton("Submit");
        button.setBounds(50, 100, 100, 30);

        add(label);
        add(textField);
        add(button);
        button.addActionListener(this);

        setVisible(true);
    }
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == button) 
        {
            String answer = textField.getText().trim();
            if (!answer.isEmpty()) 
            {
                currentAnswers.add(answer);
                qCount++;
                textField.setText("");
            }
            if (qCount < questions.length) 
            {
                label.setText(questions[qCount]);
            } 
            else 
            {
                Profile newProfile = new Profile(currentAnswers);
                profiles.add(newProfile);
                qCount = 0;
                currentAnswers = new ArrayList<String>();
                label.setText(questions[qCount]);
            }
        }
    }
    public static void main(String[] args) 
    {
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
}